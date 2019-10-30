package com.dyvak.configuration.conditional;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import static org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN;

public class OnApplicationPropertiesMissingCondition extends AnyNestedCondition {

    public OnApplicationPropertiesMissingCondition() {
        super(REGISTER_BEAN);
    }

    @ConditionalOnProperty(
            name = {
                    "app.bucket-name"
            },
            havingValue = "true",
            matchIfMissing = true)
    public static class OnBucketName {
    }

    @ConditionalOnExpression("T(org.springframework.util.StringUtils).isEmpty('${app.bucket-name}')")
    public static class OnBucketNameEmpty {
    }

    @ConditionalOnProperty(
            name = {
                    "app.first-date"
            },
            havingValue = "true",
            matchIfMissing = true)
    public static class OnFirstDate {
    }

    @ConditionalOnExpression("T(org.springframework.util.StringUtils).isEmpty('${app.first-date}')")
    public static class OnFirstDateEmpty {
    }

    @ConditionalOnProperty(
            name = {
                    "app.last-date"
            },
            havingValue = "true",
            matchIfMissing = true)
    public static class OnLastDate {
    }

    @ConditionalOnExpression("T(org.springframework.util.StringUtils).isEmpty('${app.last-date}')")
    public static class OnLastDateEmpty {
    }
}
