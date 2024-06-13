package com.n3.mebe.service.iml;

import com.n3.mebe.dto.request.product.InventoryRequest;
import com.n3.mebe.dto.response.product.InventoryResponse;
import com.n3.mebe.entity.Product;
import com.n3.mebe.entity.ProductAttribute;
import com.n3.mebe.entity.Inventory;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.InventoryRepository;
import com.n3.mebe.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService implements IInventoryService {


    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAttributeService productAttributeService;



    // <editor-fold default state="collapsed" desc="get Inventory By Id">
    public Inventory getInventoryById(int inventoryId) throws AppException {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(()-> new AppException(ErrorCode.INVENTORY_NO_EXIST));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Quantity Inventory By Id">
    public void updateQuantity(int quanti, int inventoryId) throws AppException {
        Inventory inventory = getInventoryById(inventoryId);
        int updateQuanti = inventory.getQuantity() - quanti;

        if (updateQuanti < 0) {
            throw new AppException(ErrorCode.INVENTORY_QUANTITY_END);
        }
        inventory.setQuantity(updateQuanti);
        inventoryRepository.save(inventory);
    }// </editor-fold>

    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Inventory">
    @Override
    public Inventory createInventory(int prId, InventoryRequest inventoryRequest) {


        Product product = productService.getProductById(prId);
        Inventory inventory = new Inventory();

        //Lấy color từ Id color truyền từ request xuống
        ProductAttribute color = productAttributeService.getProductAttributeById(inventoryRequest.getColorAttributeId());
        //Lấy size từ Id size  truyền từ request xuống
        ProductAttribute size = productAttributeService.getProductAttributeById(inventoryRequest.getSizeAttributeId());


        inventory.setProduct(product);
        inventory.setSizeAttributeId(color);
        inventory.setSizeAttributeId(size);
        inventory.setQuantity(inventoryRequest.getQuantity());

        return inventoryRepository.save(inventory);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Inventory">
    @Override
    public Inventory updateInventory(int prIdSku, InventoryRequest inventoryRequest) {


        Inventory inventory = inventoryRepository.findById(prIdSku)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_SKU_NO_EXIST));

        //Lấy color từ Id color truyền từ request xuống
        ProductAttribute color = productAttributeService.getProductAttributeById(inventoryRequest.getColorAttributeId());
        //Lấy size từ Id size  truyền từ request xuống
        ProductAttribute size = productAttributeService.getProductAttributeById(inventoryRequest.getSizeAttributeId());

        inventory.setSizeAttributeId(color);
        inventory.setSizeAttributeId(size);
        inventory.setQuantity(inventoryRequest.getQuantity());

        return inventoryRepository.save(inventory);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Inventory">
    @Override
    public void deleteInventory(int id) {
        inventoryRepository.deleteById(id);
    }// </editor-fold>

    /**
     *  Response from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get All Inventory">
    @Override
    public List<InventoryResponse> getAllInventory() {
        List<Inventory> list = inventoryRepository.findAll();
        List<InventoryResponse> inventoryResponseList = new ArrayList<>();

        for (Inventory inventory : list) {
            InventoryResponse inventoryResponse = new InventoryResponse();
            inventoryResponse.setProductSkuId(inventory.getInId());
            inventoryResponse.setProduct(inventory.getProduct());
            inventoryResponse.setSizeAttributeId(inventory.getSizeAttributeId());
            inventoryResponse.setColorAttributeId(inventory.getColorAttributeId());

            inventoryResponse.setQuantity(inventory.getQuantity());
            inventoryResponseList.add(inventoryResponse);
        }
        return inventoryResponseList;
    }// </editor-fold>
}
