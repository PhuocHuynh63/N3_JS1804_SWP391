package com.n3.mebe.catalog.repository;

import com.n3.mebe.catalog.entity.Category;
import com.n3.mebe.catalog.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    SubCategory findBySubCateId(int id);

    List<SubCategory> findByCategory(Category subCateName);

    List<SubCategory> findBySlug(String slug);


}
