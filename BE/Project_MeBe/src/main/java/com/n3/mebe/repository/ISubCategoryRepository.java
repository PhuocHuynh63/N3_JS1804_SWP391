package com.n3.mebe.repository;

import com.n3.mebe.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    SubCategory findBySubCateId(int id);

}
