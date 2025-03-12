package de.district.api.experimental.random;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * The {@code ThreadingDetector} class is designed to help detect and handle potential threading issues
 * in concurrent environments. It provides mechanisms to lock and unlock resources while checking for
 * thread contention, and it logs detailed stack traces when multiple threads attempt to access the same
 * resource simultaneously.
 * </p>
 *
 * <p>
 * This class is particularly useful in debugging and testing scenarios where ensuring that certain code
 * paths are not accessed concurrently is critical.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * ThreadingDetector detector = new ThreadingDetector("ResourceName");
 * try {
 *     detector.checkAndLock();
 *     // Perform thread-sensitive operations
 * } catch (Exception e) {
 *     e.printStackTrace();
 * } finally {
 *     try {
 *         detector.checkAndUnlock();
 *     } catch (Exception e) {
 *         e.printStackTrace();
 *     }
 * }
 * }</pre>
 * </p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public class ThreadingDetector {

    private static final Logger LOGGER = Logger.getLogger(ThreadingDetector.class.getName());
    private final String name;
    private final Semaphore lock = new Semaphore(1);
    private final Lock stackTraceLock = new ReentrantLock();

    @Nullable
    private volatile Thread threadThatFailedToAcquire;
    @Nullable
    private volatile Exception fullException;

    /**
     * Constructs a new {@code ThreadingDetector} for the specified resource.
     *
     * @param resourceName the name of the resource being monitored for threading issues.
     */
    public ThreadingDetector(final String resourceName) {
        this.name = resourceName;
    }

    /**
     * Creates an exception with a detailed stack trace for threading issues.
     *
     * <p>
     * This method generates an exception that includes the stack traces of the current thread
     * and the specified thread, if available. It logs a warning with the thread dumps and returns
     * an {@link Exception} indicating that the resource is being accessed from multiple threads.
     * </p>
     *
     * @param resourceName the name of the resource being accessed.
     * @param thread       the thread that failed to acquire the resource, or {@code null} if not applicable.
     * @return an {@link Exception} indicating a threading issue.
     */
    public static Exception makeThreadingException(final String resourceName, @Nullable final Thread thread) {
        String stackTraces = Stream.of(Thread.currentThread(), thread)
                .filter(Objects::nonNull)
                .map(ThreadingDetector::stackTrace)
                .collect(Collectors.joining("\n"));
        String errorMessage = "Accessing " + resourceName + " from multiple threads";
        LOGGER.warning("Thread dumps: \n" + stackTraces);
        return new Exception(errorMessage);
    }

    /**
     * Returns a formatted stack trace for the specified thread.
     *
     * <p>
     * This method converts the stack trace of the given thread into a string format that
     * includes the thread name and each stack frame, making it easier to diagnose threading issues.
     * </p>
     *
     * @param thread the thread whose stack trace is to be formatted.
     * @return a string representation of the thread's stack trace.
     */
    private static String stackTrace(final Thread thread) {
        String threadName = thread.getName();
        return threadName + ": \n\tat " + Arrays.stream(thread.getStackTrace())
                .map(Object::toString)
                .collect(Collectors.joining("\n\tat "));
    }

    /**
     * Attempts to acquire the lock and checks for threading issues.
     *
     * <p>
     * This method attempts to acquire the lock. If the lock is already held by another thread,
     * it logs the thread that failed to acquire the lock and re-acquires the lock before throwing an exception.
     * </p>
     *
     * @throws Exception if the lock was previously held by another thread, indicating a threading issue.
     */
    public void checkAndLock() throws Exception {
        boolean lockFailed = false;

        try {
            this.stackTraceLock.lock();
            if (!this.lock.tryAcquire()) {
                this.threadThatFailedToAcquire = Thread.currentThread();
                lockFailed = true;
                this.stackTraceLock.unlock();

                try {
                    this.lock.acquire();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                Exception exception = this.fullException;
                assert exception != null;
                throw exception;
            }
        } finally {
            if (!lockFailed) {
                this.stackTraceLock.unlock();
            }
        }
    }

    /**
     * Releases the lock and checks for threading issues.
     *
     * <p>
     * This method releases the lock and checks if the lock was previously held by another thread.
     * If a threading issue is detected, it logs and throws an exception.
     * </p>
     *
     * @throws Exception if a threading issue is detected while releasing the lock.
     */
    public void checkAndUnlock() throws Exception {
        try {
            this.stackTraceLock.lock();
            Thread failedThread = this.threadThatFailedToAcquire;
            if (failedThread != null) {
                Exception reportedException = makeThreadingException(this.name, failedThread);
                this.fullException = reportedException;
                this.lock.release();
                throw reportedException;
            }

            this.lock.release();
        } finally {
            this.stackTraceLock.unlock();
        }
    }
}
