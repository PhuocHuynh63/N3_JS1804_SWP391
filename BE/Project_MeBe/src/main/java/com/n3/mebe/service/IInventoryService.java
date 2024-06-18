package com.n3.mebe.service;

import com.n3.mebe.dto.request.product.InventoryRequest;
import com.n3.mebe.dto.response.product.InventoryResponse;
import com.n3.mebe.entity.Inventory;

import java.util.List;

public interface IInventoryService {



    String createInventory(int prId, InventoryRequest inventoryRequest);

    String updateInventory(int prIdSku, InventoryRequest inventoryRequest);

    void deleteInventory(int id);

    List<InventoryResponse> getAllInventory();

    InventoryResponse getInventoryResponseById(int id);


    InventoryResponse getInventoryResponseByProductId(int prId);
}
