package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.util.DateUtil;
import com.atguigu.gmall.product.pojo.MinioProperties;
import com.atguigu.gmall.product.service.FileUpService;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUpServiceImpl implements FileUpService {

    @Autowired
    MinioClient minioClient;

    @Autowired
    MinioProperties minioProperties;
    /**
     * 文件上传
     */
    @Override
    public String upFile(MultipartFile file) throws Exception {
        {
            try {


                //3获取文件名
                String dateStr = DateUtil.formatDate(new Date());
                String filename =dateStr + "/" + UUID.randomUUID().toString().replace("-","") + "_" +file.getOriginalFilename();
                String contentType = file.getContentType();
                //4、使用putObject上传一个文件到存储桶中。
                /**
                 * String bucketName, 桶名
                 * String objectName, 对象名，也就是文件名
                 *
                 * InputStream stream, 文件流  D:\0310\尚品汇\资料\03 商品图片\品牌\pingguo.png
                 * PutObjectOptions options, 上传的参数设置
                 */
                //文件流

                InputStream inputStream = file.getInputStream();
                //文件上传参数：long objectSize, long partSize
                PutObjectOptions options = new PutObjectOptions(file.getSize(), -1L);
                options.setContentType(contentType);
                //告诉Minio上传的这个文件的内容类型
                minioClient.putObject(minioProperties.getBucketName(),
                        filename,
                        inputStream,
                        options
                );
                System.out.println("上传成功");

                String url = minioProperties.getAddress() +"/" +minioProperties.getBucketName() + "/" +filename;
                return url;
            } catch (MinioException e) {
                System.err.println("发生错误: " + e);
            }
            return null;
        }
    }
}