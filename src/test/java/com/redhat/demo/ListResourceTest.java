package com.redhat.demo;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class ListResourceTest {

    @ConfigProperty(name = "bucket.name", defaultValue = "default")
    String bucketName;

    @Inject
    S3Client s3;

    public void prepareFiles() {
        for (int i = 1; i <= 20; i++) {
            var request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key("test" + i + ".txt")
                    .contentType("text/plain")
                    .build();
            s3.putObject(request, RequestBody.fromString("Test File #" + i));
        }
    }

    @Test
    public void testListEndpoint() {
        prepareFiles();

        given()
                .when().get("/api/list")
                .then()
                .statusCode(200)
                .body(".", hasSize(20));
    }

}