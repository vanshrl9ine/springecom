package com.ecommerce.ecom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //we need to genrate unique id so that files dont get ovelapped
        String originalFilename=file.getOriginalFilename();

        String randomId= UUID.randomUUID().toString();
        String fileName=randomId.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));//file extension is retained eg jpg or png
        String filePath=path + File.separator + fileName;//hardcodin  will have different behaviours on different OS

        File folder=new File(path);
        if(!folder.exists()) {
            folder.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

}
