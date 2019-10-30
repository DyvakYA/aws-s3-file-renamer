package com.dyvak.configuration.conditional;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import static org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN;

public class OnAmazonPropertiesMissingCondition extends AnyNestedCondition {

    public OnAmazonPropertiesMissingCondition() {
        super(REGISTER_BEAN);
    }

    @ConditionalOnProperty(
            name = {
                    "cloud.aws.credentials.access-key",
            },
            havingValue = "true",
            matchIfMissing = true)
    public static class OnAmazonAccessKey {
    }

    @ConditionalOnExpression("T(org.springframework.util.StringUtils).isEmpty('${cloud.aws.credentials.access-key}')")
    public static class OnAmazonAccessKeyEmpty {
    }

    @ConditionalOnProperty(
            name = {
                    "cloud.aws.credentials.secret-key"
            },
            havingValue = "true",
            matchIfMissing = true)
    public static class OnAmazonSecretKey {
    }

    @ConditionalOnExpression("T(org.springframework.util.StringUtils).isEmpty('${cloud.aws.credentials.secret-key}')")
    public static class OnAmazonSecretKeyEmpty {
    }
}
