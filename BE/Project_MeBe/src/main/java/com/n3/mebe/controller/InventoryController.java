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

    @PostMapping("/create/{prId}")
    public Inventory createInventory(@PathVariable("prId") int prId, @RequestBody InventoryRequest request) {
        return inventoryService.createInventory(prId, request);
    }

    @PutMapping("/update/{invenId}")
    public Inventory updateInventory(@PathVariable("invenId") int invenId, @RequestBody InventoryRequest request) {
        return inventoryService.updateInventory(invenId, request);
    }

    @DeleteMapping("/delete/{invenId}")
    public void deleteInventory(@PathVariable("invenId") int invenId) {
        inventoryService.deleteInventory(invenId);
    }



    /**
     *  Response from Client
     *
     */

    @GetMapping("/list")
    public List<InventoryResponse> getInventoryList() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{invenId}")
    public InventoryResponse getInventoryById(@PathVariable("invenId") int id) {
        return inventoryService.getInventoryResponseById(id);
    }

    @GetMapping("/{prId}")
    public InventoryResponse getInventoryByProductId(@PathVariable("prId") int prId) {
        return inventoryService.getInventoryResponseByProductId(prId);
    }

}
