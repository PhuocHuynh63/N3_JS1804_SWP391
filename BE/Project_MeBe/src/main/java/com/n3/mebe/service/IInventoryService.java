package com.n3.mebe.service;

import com.n3.mebe.dto.request.product.InventoryRequest;
import com.n3.mebe.dto.response.product.InventoryResponse;
import com.n3.mebe.entity.Inventory;

import java.util.List;

public interface IInventoryService {

    List<InventoryResponse> getAllInventory();

    Inventory createInventory(int prId, InventoryRequest inventoryRequest);

    Inventory updateInventory(int prIdSku, InventoryRequest inventoryRequest);

    void deleteInventory(int id);
}
