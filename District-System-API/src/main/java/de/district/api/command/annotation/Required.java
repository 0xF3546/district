package de.district.api.command.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {
    @NotNull
    String[] permissions() default {};

    boolean inAduty() default false;

    boolean useInConsole() default true;
}
