package com.n3.mebe.service;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {

    String saveFile(MultipartFile file);
}
