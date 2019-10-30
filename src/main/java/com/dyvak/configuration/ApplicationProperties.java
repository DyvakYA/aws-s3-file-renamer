package com.dyvak.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    private String bucketName;

    private String bucketPrefix;

    private String replaceValue;

    private String replaceTo;

    private Date firstDate;

    private Date lastDate;

    private String dateFormatter;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketPrefix() {
        if (bucketPrefix == null) {
            return "";
        }
        return bucketPrefix;
    }

    public void setBucketPrefix(String bucketPrefix) {
        this.bucketPrefix = bucketPrefix;
    }

    public String getReplaceTo() {
        return replaceTo;
    }

    public void setReplaceTo(String replaceTo) {
        this.replaceTo = replaceTo;
    }

    public String getReplaceValue() {
        if ("".equals(replaceValue)) {
            return "null";
        }
        return replaceValue;
    }

    public void setReplaceValue(String replaceValue) {
        this.replaceValue = replaceValue;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getDateFormatter() {
        return dateFormatter;
    }

    public void setDateFormatter(String dateFormatter) {
        this.dateFormatter = dateFormatter;
    }


}
