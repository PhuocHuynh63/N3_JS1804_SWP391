package com.n3.mebe.controller;


import com.n3.mebe.dto.request.product.InventoryRequest;
import com.n3.mebe.dto.response.product.InventoryResponse;
import com.n3.mebe.entity.Inventory;
import com.n3.mebe.service.IInventoryService;
import com.n3.mebe.service.iml.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @Autowired
    private IInventoryService inventoryService;


    /**
     *  Request from Client
     *
     */

    @PostMapping("/create/{id}")
    public Inventory createInventory(@PathVariable("id") int prId, @RequestBody InventoryRequest request) {
        return inventoryService.createInventory(prId, request);
    }

    @PutMapping("/update/{id}")
    public Inventory updateInventory(@PathVariable("id") int prIdSku, @RequestBody InventoryRequest request) {
        return inventoryService.updateInventory(prIdSku, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteInventory(@PathVariable("id") int prIdSku) {
        inventoryService.deleteInventory(prIdSku);
    }



    /**
     *  Response from Client
     *
     */

    @GetMapping("/list")
    public List<InventoryResponse> getInventoryList() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public InventoryResponse getInventoryById(@PathVariable("id") int id) {
        return inventoryService.getInventoryResponseById(id);
    }

    @GetMapping("/{prId}")
    public InventoryResponse getInventoryByProductId(@PathVariable("prId") int prId) {
        return inventoryService.getInventoryResponseByProductId(prId);
    }

}
