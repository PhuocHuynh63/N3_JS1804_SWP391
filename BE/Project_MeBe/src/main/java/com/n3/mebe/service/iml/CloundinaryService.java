package com.n3.mebe.service.iml;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.n3.mebe.service.ICloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class CloundinaryService implements ICloudinaryService {

    @Autowired
    private Cloudinary cloudinary;




    @Override
    public String saveFileToFolder(MultipartFile file, String folder) {
        try {
            // Tạo public_id với tên thư mục và tên file
            String publicId = folder.replace(" ", "_") + "/" + file.getOriginalFilename();

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "auto",
                            "public_id", publicId,
                            "folder", folder.replace(" ", "_") // Truyền tên thư mục vào tham số folder
                    ));
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error save file: " + e.getMessage());
        }
        return null;
    }

//    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Ho_Chi_Minh") // Chạy vào nửa đêm mỗi ngày theo giờ Hồ Chí Minh
//    public void cleanupOldImages() {
//        try {
//            // Lấy danh sách các hình ảnh
//            ApiResponse response = cloudinary.api().resources(ObjectUtils.emptyMap());
//
//            // Duyệt qua từng hình ảnh
//            for (Map<String, Object> resource : (Iterable<Map<String, Object>>) response.get("resources")) {
//                String publicId = (String) resource.get("public_id");
//                Date createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse((String) resource.get("created_at"));
//
//                // Kiểm tra nếu hình ảnh đã không được sử dụng trong 3 ngày
//                // 3 ngày 24 tiếng 60 phút 60 giây
//                if (new Date().getTime() - createdAt.getTime() > 3 * 24 * 60 * 60 * 1000L) {
//                    // Xóa hình ảnh
//                    cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Ho_Chi_Minh") // Chạy vào nửa đêm mỗi ngày theo giờ Hồ Chí Minh
    public void cleanupOldImages() {
        String folder = "Avatar_User"; // Thay thế bằng tên thư mục của bạn
        try {
            // Lấy danh sách các hình ảnh trong thư mục cụ thể
            Map<String, Object> params = ObjectUtils.asMap("type", "upload", "prefix", folder + "/");
            ApiResponse response = cloudinary.api().resources(params);

            // Duyệt qua từng hình ảnh
            for (Map<String, Object> resource : (Iterable<Map<String, Object>>) response.get("resources")) {
                String publicId = (String) resource.get("public_id");
                Date createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse((String) resource.get("created_at"));

                // Kiểm tra nếu hình ảnh đã không được sử dụng trong 2 phút
                if (new Date().getTime() - createdAt.getTime() > 2 * 60 * 1000L) {
                    // Xóa hình ảnh
                    cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                    System.out.println("Deleted image with public ID: " + publicId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
