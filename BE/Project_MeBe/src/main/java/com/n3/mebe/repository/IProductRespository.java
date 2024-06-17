package com.n3.mebe.repository;


import com.n3.mebe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRespository extends JpaRepository<Product, Integer> {


    boolean existsByName(String name);

    Product findByName(String name);

    List<Product> findBySubCategoryCategoryName(String categoryName);

    List<Product> findByProductIdOrName(int productId, String name);
}
