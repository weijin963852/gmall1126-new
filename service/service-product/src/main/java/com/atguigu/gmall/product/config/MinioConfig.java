package com.atguigu.gmall.product.config;

import com.atguigu.gmall.product.pojo.MinioProperties;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Autowired
    MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient minioClient =
                new MinioClient(minioProperties.getAddress(),
                        minioProperties.getUsername(),
                        minioProperties.getPassword());

        //2、检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(minioProperties.getBucketName());

        if (isExist) {
            System.out.println("Bucket already exists.");
        } else {
            //3、如果桶不存在需要先创建一个桶
            minioClient.makeBucket(minioProperties.getBucketName());
        }

        return minioClient;
    }
}
