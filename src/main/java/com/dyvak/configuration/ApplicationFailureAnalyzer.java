package com.dyvak.configuration;

import com.dyvak.exception.AmazonRequiredParametersMissingException;
import com.dyvak.exception.ApplicationRequiredParametersMissingException;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalyzer;

public class ApplicationFailureAnalyzer implements FailureAnalyzer {

    @Override
    public FailureAnalysis analyze(Throwable failure) {
        if (failure instanceof AmazonRequiredParametersMissingException) {
            String description = "Should have aws credentials (access-key, secret-key)";
            String action = "add : \n" +
                    "--cloud.aws.credentials.access-key=XXXXX \n" +
                    "--cloud.aws.credentials.secret-key=XXXXX";
            return new FailureAnalysis(description, action, failure);
        }
        if (failure instanceof ApplicationRequiredParametersMissingException) {
            String description = "Should have required parameters (bucket-name, first-date, last-date, change-value)";
            String action = "add : \n" +
                    "--app.bucket-name=XXXXX \n" +
                    "--app.first-date=dd-MM-yyyy \n" +
                    "--app.replace-value=dd-MM-yyyy \n" +
                    "--app.replace-to=XXXXX";
            return new FailureAnalysis(description, action, failure);
        }
        return null;
    }
}
