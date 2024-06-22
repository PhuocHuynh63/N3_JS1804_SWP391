package com.n3.mebe.repository;


import com.n3.mebe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRespository extends JpaRepository<Product, Integer> {


    boolean existsByName(String name);

    Product findByName(String name);

    List<Product> findBySubCategorySlug(String slug);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findProductByName(@Param("name") String name);


    @Query("SELECT p FROM Product p ORDER BY p.createAt DESC")
    List<Product> findAllProductByCreatedAtDesc();

    @Query("SELECT p FROM Product p ORDER BY p.createAt ASC ")
    List<Product> findAllProductByCreatedAtAsc();

    @Query("SELECT p FROM Product p ORDER BY p.price ASC ")
    List<Product> findAllProductByPriceAsc();

    @Query("SELECT p FROM Product p ORDER BY p.price DESC ")
    List<Product> findAllProductByPriceDesc();

    @Query("SELECT p FROM Product p WHERE p.price > :min AND p.price < :max")
    List<Product> sortProductByPriceMinToMax(@Param("min") float min, @Param("max") float max);


}
