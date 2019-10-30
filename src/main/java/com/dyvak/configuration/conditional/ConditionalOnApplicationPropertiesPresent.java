package com.dyvak.configuration.conditional;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnApplicationPropertiesPresentCondition.class)
public @interface ConditionalOnApplicationPropertiesPresent {
}
