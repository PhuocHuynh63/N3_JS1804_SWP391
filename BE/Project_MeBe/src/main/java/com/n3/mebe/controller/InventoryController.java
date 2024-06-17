package com.n3.mebe.controller;


import com.n3.mebe.dto.request.product.InventoryRequest;
import com.n3.mebe.dto.response.product.InventoryResponse;
import com.n3.mebe.entity.Inventory;
import com.n3.mebe.service.iml.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @Autowired
    private InventoryService productSkuService;


    /**
     *  Request from Client
     *
     */
    @PostMapping("/create/product_id={id}")
    public Inventory createInventory(@PathVariable("id") int prId, @RequestBody InventoryRequest request) {
        return productSkuService.createInventory(prId, request);
    }

    @PutMapping("/update/productSku_id={id}")
    public Inventory updateInventory(@PathVariable("id") int prIdSku, @RequestBody InventoryRequest request) {
        return productSkuService.updateInventory(prIdSku, request);
    }

    @DeleteMapping("/delete/productSku_id={id}")
    public void updateInventory(@PathVariable("id") int prIdSku) {
        productSkuService.deleteInventory(prIdSku);
    }



    /**
     *  Response from Client
     *
     */

    @GetMapping("/list")
    public List<InventoryResponse> getInventoryList() {
        return productSkuService.getAllInventory();
    }

}
