package com.eduunity.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

@Service
public class ImageService {

    public String saveImageToLocalStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    public String saveImagePinataStorage(MultipartFile imageFile) throws IOException {
        String bucketName = "uyhagtrfd78j";
        String fileObjKeyName = imageFile.getOriginalFilename(); // Use the original file name for the object in S3

        // Provide your Filebase or AWS Access Key and Secret Key
        String accessKey = "9AE433DA6F9511D95624";
        String secretKey = "tTTabgwJe3CQICV0f2508KHBaoVsyqdj7SbhJ2uF";

        try {
            // Set up credentials
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

            // Build the Amazon S3 client for Filebase
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("s3.filebase.com", "us-east-1"))
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds)) // Provide credentials here
                    .build();

            // Get the input stream from the image file
            InputStream inputStream = imageFile.getInputStream();

            // Prepare metadata for the file, setting content type as image/png
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
            metadata.setContentLength(imageFile.getSize()); // Set content length

            // Upload the file to the S3 bucket
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, inputStream, metadata);
            s3Client.putObject(request);

            System.out.println("PNG file uploaded successfully!");

            // Return the URL to the uploaded file
            return imageFile.getOriginalFilename();

        } catch (Exception e) {
            e.printStackTrace();
            return "File upload failed!";
        }
    }

    public String generatePresignedUrl(String fileName) throws IOException {
        String bucketName = "uyhagtrfd78j"; // Your bucket name

        // Provide your Filebase or AWS Access Key and Secret Key
        String accessKey = "9AE433DA6F9511D95624";
        String secretKey = "tTTabgwJe3CQICV0f2508KHBaoVsyqdj7SbhJ2uF";

        try {
            // Set up credentials
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

            // Build the Amazon S3 client for Filebase
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("s3.filebase.com", "us-east-1"))
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();

            // Generate a pre-signed URL
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                    .withMethod(com.amazonaws.HttpMethod.GET)
                    .withExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)); // URL valid for 1 hour

            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to generate pre-signed URL.", e);
        }
    }
}
