package codesquad.bookkbookk.image;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3ImageUploader {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucket;

    public URL upload(MultipartFile multipartFile) {
        String uuid = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(bucket, uuid, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return amazonS3.getUrl(bucket, uuid);
    }

}
