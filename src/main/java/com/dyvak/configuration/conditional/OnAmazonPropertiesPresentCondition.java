package com.dyvak.configuration.conditional;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import static org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN;

public class OnAmazonPropertiesPresentCondition extends AllNestedConditions {

    public OnAmazonPropertiesPresentCondition() {
        super(REGISTER_BEAN);
    }

    @ConditionalOnProperty(
            name = {
                    "cloud.aws.credentials.access-key",
                    "cloud.aws.credentials.secret-key"
            })
    public static class OnAmazonAccessKey {
    }

    @ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${cloud.aws.credentials.access-key}')")
    public static class OnAmazonAccessKeyNotEmpty {
    }

    @ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${cloud.aws.credentials.secret-key}')")
    public static class OnAmazonSecretKeyNotEmpty {
    }


}
