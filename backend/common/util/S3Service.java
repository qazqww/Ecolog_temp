package com.thedebuggers.backend.common.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.thedebuggers.backend.common.exception.CustomException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor
public class S3Service {

    private AmazonS3 amazonS3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setAmazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        amazonS3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(MultipartFile multipartFile) {
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "Ecolog_file_" + format.format(new Date()) + "." + ext;

        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, filename, multipartFile.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR);
        }

        return amazonS3Client.getUrl(bucket, filename).toString();
    }

    public Map<String, String> upload(List<MultipartFile> multipartFileList) {

        int fileIdx = 0;
        Map<String, String> urlList = new HashMap<>();
        for (MultipartFile multipartFile : multipartFileList) {
            String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = "Ecolog_file_" + format.format(new Date()) + "_" + fileIdx++ + "." + ext;

            try {
                amazonS3Client.putObject(new PutObjectRequest(bucket, filename, multipartFile.getInputStream(), null)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                if (urlList.isEmpty()) {
                    urlList.put("result", amazonS3Client.getUrl(bucket, filename).toString());
                }
                else {
                    urlList.put("route", amazonS3Client.getUrl(bucket, filename).toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR);
            }
        }

        return urlList;
    }

}
