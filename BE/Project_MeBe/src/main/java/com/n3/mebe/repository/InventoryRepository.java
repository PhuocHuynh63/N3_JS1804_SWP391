package com.n3.mebe.repository;

import com.n3.mebe.entity.Inventory;
import com.n3.mebe.entity.Product;
import com.n3.mebe.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {


    Inventory findByColorAttributeIdAndSizeAttributeId(ProductAttribute colorAttributeId, ProductAttribute sizeAttributeId);

    Inventory findByProduct(Product product);
}
