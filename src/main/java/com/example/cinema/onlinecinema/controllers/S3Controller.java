package com.example.cinema.onlinecinema.controllers;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.cinema.onlinecinema.dtos.BucketDto;
import com.example.cinema.onlinecinema.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.*;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    @Autowired
    S3Service s3Service;

    @GetMapping("/bucket/all")
    public List<String> getAllBuckets() {
        return s3Service.getAllBucketNames();
    }

    @GetMapping("/file/all")
    public List<S3ObjectSummary> getAllFilesInBucket(
            @RequestParam("bucketName")
                    String bucketName) {
        return s3Service.getFilesInBucket(bucketName).getObjectSummaries();
    }

    @PostMapping("/bucket")
    @ResponseStatus(HttpStatus.CREATED)
    public String createBucket(@RequestBody BucketDto bucketDto) {
        return s3Service.createBucket(bucketDto.getBucketName());
    }

    @DeleteMapping("/bucket")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBucket(@RequestBody BucketDto bucketName) {
        s3Service.deleteBucket(bucketName.getBucketName());
    }

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadFileToBucket(@RequestParam(required = true, name = "file") MultipartFile file,
                                     @RequestParam("bucketName") String bucketName) {
        s3Service.addFileInBucket(bucketName, file);
        return file.getOriginalFilename();
    }

    @GetMapping(value = "/download/file", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<StreamingResponseBody> downloadFile(@RequestParam("bucketName") String bucketName,
                                                              @RequestParam("path") String path) {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(s3Service.getFile(bucketName, path));
    }

    @GetMapping("/file")
    public String readFileFromBucket(@RequestParam String bucketName, @RequestParam String fileName) {
        return s3Service.readFileAsString(bucketName, fileName);
    }

    @DeleteMapping("/file")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteFileFromBucket(@RequestBody BucketDto bucketDto) {
        s3Service.deleteFile(bucketDto.getBucketName(), bucketDto.getFileName());
        return bucketDto.getFileName();
    }
}