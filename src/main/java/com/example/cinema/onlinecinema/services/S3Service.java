package com.example.cinema.onlinecinema.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3;

    public List<String> getAllBucketNames() {
        return s3.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
    }

    public String readFileAsString(String bucketName, String fileName) {
        S3Object object = s3.getObject(bucketName, fileName);
        S3ObjectInputStream objectContent = object.getObjectContent();
        String line;
        try {
            line = IOUtils.toString(objectContent);
            objectContent.close();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read file from S3 bucket");
        }
        return line;
    }

    public void addFileInBucket(String bucketName, MultipartFile file) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(IOUtils.toByteArray(file.getInputStream()).length);
            s3.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), file.getInputStream(), objectMetadata));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't add file to S3 bucket");
        }
    }

    public ListObjectsV2Result getFilesInBucket(String bucketName) {
        return s3.listObjectsV2(bucketName);
    }

    public String createBucket(String bucketName) {
        return s3.createBucket(bucketName).getName();
    }

    public void deleteBucket(String bucketName) {
        s3.deleteBucket(bucketName);
    }

    public void deleteFile(String bucketName, String fileName) {
        s3.deleteObject(bucketName, fileName);
    }

    public StreamingResponseBody getFile(String bucketName, String fileName) {
        S3Object object = s3.getObject(bucketName, fileName);
        S3ObjectInputStream stream = object.getObjectContent();

        return outputStream -> {
            int numberOfBytesToWrite = 0;
            byte[] data = new byte[1024];
            while ((numberOfBytesToWrite = stream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, numberOfBytesToWrite);
            }
            stream.close();
        };
    }
}