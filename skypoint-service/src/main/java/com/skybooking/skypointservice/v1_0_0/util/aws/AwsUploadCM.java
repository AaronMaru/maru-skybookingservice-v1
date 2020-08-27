package com.skybooking.skypointservice.v1_0_0.util.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class AwsUploadCM {

    @Autowired
    private AmazonS3 s3client;

    @Autowired
    private Environment environment;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Implement upload image
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param url
     * @Param fileName
     * @Param largPath
     * @Param smallPath
     * @Return String
     */
    public String uploadFileForm(MultipartFile multipartFile) {

        String fileName = generateFileName(null);
        Optional<String> imageEx;

        try {

            File file = convertMultiPartToFile(multipartFile);

            String fileNames = multipartFile.getOriginalFilename();

            imageEx = getExtensionByStringHandling(fileNames);
            limitFileType(imageEx.get(), new String[]{"PNG", "JPG", "JPEG"});

            uploadFileTos3bucket(fileName + "." + imageEx.get(), file, "/uploads");

            file.delete();

        } catch (Exception e) {
            throw new InternalServerError("up_img_type", null);
        }

        return fileName + "." + imageEx.get();

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {

        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;

    }

    private Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private String generateFileName(String ext) {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String name = UUID.randomUUID().toString().replace("-", "").substring(0, 10) + timeStamp;

        if (ext != null) {
            name = name + "." + ext;
        }

        return name;

    }

    private void limitFileType(String ext, String[] fileType) {

        Boolean b = false;

        for (int i = 0; i < fileType.length; i++) {
            if (fileType[i].equals(ext.toUpperCase())) {
                b = true;
            }
        }
        if (!b) {
            throw new BadRequestException("file_type", null);
        }

    }

    private String uploadFileTos3bucket(String fileName, File file, String path) {

        try {
            s3client.putObject(new PutObjectRequest(
                    environment.getProperty("spring.aws.bucketUpload") + path, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
        }

        return "Uploading Successfully";

    }


}