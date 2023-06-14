package com.redhat.demo;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@ApplicationScoped
public class S3ListerService implements ListerService {

    @Inject
    S3Client s3;

    @ConfigProperty(name = "bucket.name", defaultValue = "default")
    String bucketName;

    @Override
    public Multi<String> listFiles() {
        var request = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();
        return Multi.createFrom().items(s3.listObjects(request).contents().stream())
                .map(S3Object::key);
    }
}
