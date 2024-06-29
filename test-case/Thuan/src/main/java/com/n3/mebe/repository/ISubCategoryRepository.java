package com.n3.mebe.repository;

import com.n3.mebe.entity.Category;
import com.n3.mebe.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    SubCategory findBySubCateId(int id);

    List<SubCategory> findByCategory(Category subCateName);
    List<SubCategory> findBySlug(String slug);


}
