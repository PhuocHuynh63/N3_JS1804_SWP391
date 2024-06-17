package com.n3.mebe.repository;


import com.n3.mebe.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
    boolean existsByTypeAndValue(String type, String value);

}
