package com.n3.mebe.service.iml;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.n3.mebe.service.ICloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloundinaryService implements ICloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String saveFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error save file: " + e.getMessage());
        }
        return null;
    }
}
