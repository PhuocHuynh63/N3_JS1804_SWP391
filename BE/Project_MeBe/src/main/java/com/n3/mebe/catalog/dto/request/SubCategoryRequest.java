package com.n3.mebe.catalog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequest {

    private String categoryParentName;
    private String name;
    private String slug;

}
