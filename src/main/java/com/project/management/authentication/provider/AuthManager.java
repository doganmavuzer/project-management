package com.project.management.authentication.provider;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthManager {
}
