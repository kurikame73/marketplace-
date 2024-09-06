//package com.example.marketplace.domain.aws.s3;
//
//import com.amazonaws.HttpMethod;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.net.URL;
//import java.time.Duration;
//import java.util.Date;
//
//@Setter
//@RequiredArgsConstructor
//@Service
//@Slf4j
//public class AwsS3Service {
//    private final AmazonS3 amazonS3;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public URL generatePresignedUrl(String objectKey) {
//        Date expiration = new Date();
//        long expTimeMillis = Duration.ofDays(1).toMillis(); // 유효 기간 1일
//        expiration.setTime(System.currentTimeMillis() + expTimeMillis);
//
//        GeneratePresignedUrlRequest generatePresignedUrlRequest =
//                new GeneratePresignedUrlRequest(bucket, objectKey)
//                        .withMethod(HttpMethod.PUT)
//                        .withExpiration(expiration);
//        log.info("bucket!!!!!!!!! = {}", bucket);
//        log.info("url!!!!!!!!! = {}", generatePresignedUrlRequest.toString());
//
//        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
//        log.info("url222222222={}", url);
//        return url;
//    }
//}
