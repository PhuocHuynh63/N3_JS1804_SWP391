package com.n3.mebe.dto.response.subcategory;

import com.n3.mebe.entity.Category;
import com.n3.mebe.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResponse {

    private String category_parent;
    private String name;
<<<<<<< HEAD
    private String image;
=======
    private String slug;
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0
}
