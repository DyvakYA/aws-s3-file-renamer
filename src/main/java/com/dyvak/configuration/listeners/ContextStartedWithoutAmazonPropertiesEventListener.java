package com.dyvak.configuration.listeners;

import com.dyvak.configuration.conditional.ConditionalOnAmazonPropertiesMissing;
import com.dyvak.exception.AmazonRequiredParametersMissingException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnAmazonPropertiesMissing
public class ContextStartedWithoutAmazonPropertiesEventListener {

    @EventListener
    public void handleContextStart(ContextRefreshedEvent event) {
        throw new AmazonRequiredParametersMissingException();
    }
}
