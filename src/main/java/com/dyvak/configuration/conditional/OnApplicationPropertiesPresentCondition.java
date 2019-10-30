package com.dyvak.configuration.conditional;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import static org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN;

public class OnApplicationPropertiesPresentCondition extends AllNestedConditions {

    public OnApplicationPropertiesPresentCondition() {
        super(REGISTER_BEAN);
    }

    @ConditionalOnProperty(
            name = {
                    "app.bucket-name",
                    "app.first-date",
                    "app.last-date",
                    "app.replace-to"
            })
    public static class OnApplicationPropertiesPresent {
    }

    @ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${app.bucket-name}')")
    public static class OnBucketNameNotEmpty {
    }

    @ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${app.first-date}')")
    public static class OnFirstDateNotEmpty {
    }

    @ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${app.last-date}')")
    public static class OnLastDateNotEmpty {
    }

    @ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${app.replace-to}')")
    public static class OnReplaceToNotEmpty {
    }
}