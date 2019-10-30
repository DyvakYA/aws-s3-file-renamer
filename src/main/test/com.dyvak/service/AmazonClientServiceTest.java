package com.dyvak.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.dyvak.configuration.ApplicationProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AmazonClientServiceTest {

    public static final String BUCKET_NAME = "bucket";
    public static final String REPLACE_VALUE = "null";
    public static final String REPLACE_TO = "new";
    public static final String S_3_BUCKET_PREFIX = "somefolder";
    public static final String DELIMITER = "/";
    public static final String FILE_NAME = "file";
    public static final String EXTENTION_JPG = ".jpg";

    @Mock
    AmazonS3 s3client;

    @Mock
    ObjectListing objectListing;

    @Mock
    PutObjectResult result;

    @Mock
    ApplicationProperties applicationProperties;

    @InjectMocks
    AmazonClientService service;

    List<S3ObjectSummary> objectSummaries;

    @Before
    public void setUp() {
        objectSummaries = new ArrayList<>();
        // create test data
        for (int i = 0; i < 10; i++) {
            S3ObjectSummary summary = new S3ObjectSummary();
            summary.setKey(S_3_BUCKET_PREFIX + DELIMITER + REPLACE_VALUE + i);
            summary.setBucketName(BUCKET_NAME);
            summary.setLastModified(new Date());
            objectSummaries.add(summary);
        }
        for (int i = 0; i < 10; i++) {
            S3ObjectSummary summary = new S3ObjectSummary();
            summary.setKey(S_3_BUCKET_PREFIX + DELIMITER + REPLACE_VALUE + DELIMITER + FILE_NAME + i + EXTENTION_JPG);
            summary.setBucketName(BUCKET_NAME);
            summary.setLastModified(new Date());
            objectSummaries.add(summary);
        }
        for (int i = 0; i < 3; i++) {
            S3ObjectSummary summary = new S3ObjectSummary();
            summary.setKey(S_3_BUCKET_PREFIX + DELIMITER + REPLACE_VALUE + DELIMITER + FILE_NAME + i + EXTENTION_JPG);
            summary.setBucketName(BUCKET_NAME);
            summary.setLastModified(new Date());
            objectSummaries.add(summary);
        }
    }

    @Test
    public void renameFilesFromS3bucketTest() {
        Mockito.when(applicationProperties.getBucketName()).thenReturn(BUCKET_NAME);
        Mockito.when(applicationProperties.getBucketPrefix()).thenReturn(S_3_BUCKET_PREFIX);
        Mockito.when(applicationProperties.getReplaceValue()).thenReturn(REPLACE_VALUE);
        Mockito.when(applicationProperties.getReplaceTo()).thenReturn(REPLACE_TO);
        Mockito.when(applicationProperties.getFirstDate()).thenReturn(new Date(System.currentTimeMillis() - 1000000));
        Mockito.when(applicationProperties.getLastDate()).thenReturn(new Date(System.currentTimeMillis() + 1000000));

        Mockito.when(s3client.listObjects(BUCKET_NAME)).thenReturn(objectListing);
        Mockito.when(objectListing.getObjectSummaries()).thenReturn(objectSummaries);
        Mockito.when(s3client.putObject(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class))).thenReturn(result);
        Mockito.when(s3client.getObject(anyString(), anyString())).thenReturn(new S3Object());
        Mockito.doNothing().when(s3client).deleteObject(anyString(), anyString());

        service.renameFilesFromS3bucket();

        Mockito.verify(s3client, times(1)).listObjects(BUCKET_NAME);
        Mockito.verify(s3client, times(20)).putObject(any(), any(), any(), any());
        Mockito.verify(s3client, times(20)).deleteObject(anyString(), anyString());
        Mockito.verify(s3client, times(20)).getObject(anyString(), anyString());
    }
}
