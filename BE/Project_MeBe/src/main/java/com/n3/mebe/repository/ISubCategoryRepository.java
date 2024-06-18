package com.n3.mebe.repository;

import com.n3.mebe.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    SubCategory findBySubCateId(int id);
<<<<<<< HEAD
    SubCategory findByName(String name);
=======
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0

}
