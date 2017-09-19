package com.akshaydeo.timelapse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.CLASS;

@Target({ElementType.TYPE, ElementType.PACKAGE, ElementType.METHOD})
@Retention(CLASS)
public @interface Measure {
}