package com.dyvak.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.dyvak.configuration.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AmazonClientService implements AmazonClient {

    private final Logger log = LoggerFactory.getLogger(AmazonClientService.class);

    private final ApplicationProperties applicationProperties;

    private final AmazonS3 s3client;

    @Autowired
    public AmazonClientService(AmazonS3 s3client, ApplicationProperties properties) {
        this.s3client = s3client;
        this.applicationProperties = properties;
    }

    @Override
    public void renameFilesFromS3bucket() {
        printApplicationInformation();
        ObjectListing objectListing = s3client.listObjects(applicationProperties.getBucketName(), applicationProperties.getBucketPrefix());
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        Set<S3ObjectSummary> filteredObjects = findByReplaceValueBetweenFirstDateAndLastDate(objectSummaries);
        for (S3ObjectSummary filteredObjectSummary : filteredObjects) {
            String newKey = filteredObjectSummary.getKey().replaceAll(applicationProperties.getReplaceValue(), applicationProperties.getReplaceTo());
            List<S3ObjectSummary> updatedObjects = new ArrayList<>();
            if (!isFileName(newKey) || (!isFileWithNameExist(objectSummaries, newKey) && !isFileWithNameExist(updatedObjects, newKey))) {
                S3Object s3Object = downloadFileFromS3Bucket(filteredObjectSummary.getBucketName(), filteredObjectSummary.getKey());
                s3Object.setKey(newKey);
                deleteFileFromS3bucket(filteredObjectSummary.getBucketName(), filteredObjectSummary.getKey());
                uploadFileToS3bucket(s3Object);
                if (isFileName(filteredObjectSummary.getKey())) {
                    log.info("File : {} was renamed to : {}", filteredObjectSummary.getKey(), s3Object.getKey());
                } else {
                    log.info("Folder : {} was renamed to : {}", filteredObjectSummary.getKey(), s3Object.getKey());
                }
            } else {
                log.info("Can't rename file : {}, Reason : duplicates", filteredObjectSummary.getKey());
            }
            filteredObjectSummary.setKey(newKey);
            updatedObjects.add(filteredObjectSummary);
        }
    }

    private void printApplicationInformation() {
        log.info("Bucket name : {}, Bucket prefix : {}, First date : {}, Last date : {}, Replace value : {}, Replace to : {}",
                applicationProperties.getBucketName(),
                applicationProperties.getBucketPrefix(),
                applicationProperties.getFirstDate(),
                applicationProperties.getLastDate(),
                applicationProperties.getReplaceValue(),
                applicationProperties.getReplaceTo());
    }

    private Set<S3ObjectSummary> findByReplaceValueBetweenFirstDateAndLastDate(List<S3ObjectSummary> objects) {
        return objects.stream()
                .filter(s -> s.getLastModified().after(applicationProperties.getFirstDate())
                        && s.getLastModified().before(applicationProperties.getLastDate()))
                .filter(s -> s.getKey().contains(applicationProperties.getReplaceValue()))
                .collect(Collectors.toSet());
    }

    private boolean isFileWithNameExist(List<S3ObjectSummary> files, String fileName) {
        List<S3ObjectSummary> existedFiles = files.stream()
                .filter(s -> isFileName(s.getKey()))
                .filter(s -> s.getKey().equals(fileName))
                .collect(Collectors.toList());
        return existedFiles.size() == 0 ? false : true;
    }

    private boolean isFileName(String fileName) {
        String[] tokens = fileName.split("/");
        String lastToken = tokens[tokens.length - 1];
        return lastToken.split("\\.").length > 1 ? true : false;
    }

    private void uploadFileToS3bucket(S3Object s3Object) {
        s3client.putObject(s3Object.getBucketName(), s3Object.getKey(), s3Object.getObjectContent(), s3Object.getObjectMetadata());
    }

    private S3Object downloadFileFromS3Bucket(String bucketName, String fileName) {
        return s3client.getObject(bucketName, fileName);
    }

    private void deleteFileFromS3bucket(String bucketName, String fileName) {
        s3client.deleteObject(bucketName, fileName);
    }
}
