package com.dyvak.configuration.listeners;

import com.amazonaws.services.s3.AmazonS3;
import com.dyvak.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(AmazonS3.class)
public class ContextStartedWithPropertiesEventListener {

    private final AmazonClient service;

    @Autowired
    public ContextStartedWithPropertiesEventListener(AmazonClient service) {
        this.service = service;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        service.renameFilesFromS3bucket();
    }
}
