package com.n3.mebe.repository;

import com.n3.mebe.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);
    Category findByName(String name);
}
