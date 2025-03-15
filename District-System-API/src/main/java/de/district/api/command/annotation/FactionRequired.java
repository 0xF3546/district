package de.district.api.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FactionRequired {
    String[] factions() default {};

    int minLevel() default 0;

    boolean onlyLeader() default false;
}
