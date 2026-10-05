package com.n3.mebe.shared.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {


    String saveFileToFolder(MultipartFile file, String folder);
}
