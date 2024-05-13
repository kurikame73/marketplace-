package com.example.marketplace.domain.aws.s3;

import com.example.marketplace.domain.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
@Transactional
class AwsS3ServiceTest {
    @Autowired
    private AwsS3Service awsS3Service;

    @Test
    void generatePresignedUrl() {
        // given
        String objectKey = "abcd";

        // when
        URL url = awsS3Service.generatePresignedUrl(objectKey);

        //then
        assertNotNull(url);
    }
}