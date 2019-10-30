package com.dyvak.configuration.listeners;

import com.dyvak.configuration.conditional.ConditionalOnApplicationPropertiesMissing;
import com.dyvak.exception.ApplicationRequiredParametersMissingException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnApplicationPropertiesMissing
public class ContextStartedWithoutApplicationPropertiesEventListener {

    @EventListener
    public void handleContextStart(ContextRefreshedEvent event) {
        throw new ApplicationRequiredParametersMissingException();
    }
}
