package de.district.api.fail.exception;

public class DistrictRoleplayException extends RuntimeException {
    public DistrictRoleplayException() {
    }

    public DistrictRoleplayException(final String message) {
        super(message);
    }

    public DistrictRoleplayException(final Throwable cause) {
        super(cause);
    }

    public DistrictRoleplayException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
