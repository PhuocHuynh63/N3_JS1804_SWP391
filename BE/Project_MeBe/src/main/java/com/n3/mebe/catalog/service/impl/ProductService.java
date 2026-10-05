package com.n3.mebe.catalog.service.impl;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;


import com.n3.mebe.order.dto.request.OrderDetailsRequest;
import com.n3.mebe.catalog.dto.request.ProductRequest;
import com.n3.mebe.catalog.dto.response.ProductResponse;
import com.n3.mebe.catalog.entity.Product;
import com.n3.mebe.catalog.entity.ProductStatus;
import com.n3.mebe.wishlist.entity.WishListStatus;
import com.n3.mebe.catalog.entity.SubCategory;
import com.n3.mebe.wishlist.entity.WishList;
import com.n3.mebe.shared.exception.AppException;
import com.n3.mebe.shared.exception.ErrorCode;
import com.n3.mebe.catalog.repository.IProductRepository;
import com.n3.mebe.catalog.repository.ISubCategoryRepository;
import com.n3.mebe.catalog.mapper.ProductMapper;
import com.n3.mebe.wishlist.repository.IWishListRepository;
import com.n3.mebe.shared.storage.service.ICloudinaryService;
import com.n3.mebe.catalog.service.IProductService;
import com.n3.mebe.wishlist.service.IWishListService;
import com.n3.mebe.notification.service.impl.SendMailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;

    private final ICloudinaryService cloudinaryService;

    private final ISubCategoryRepository iSubCategoryRepository;

    private final IWishListRepository wishListRepository;

    private final SendMailService sendMailService;

    private final ProductMapper productMapper;


    // <editor-fold default state="collapsed" desc="Send Email Wish List Done">
    public void sendEmailWishListDone(int productId) {
        List<WishList> wishLists = wishListRepository.findWishListsByProduct(productId, WishListStatus.WAITING);
        for (WishList wishList : wishLists) {

            wishList.setStatus(WishListStatus.AVAILABLE);
            //gửi mail thông báo đã có hàng
            wishList.setUpdatedAt(new Date());
            wishListRepository.save(wishList);
            sendMailService.createSendEmailWishListNotifications(wishList);
        }
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Reduce Update Quantity Product By Id">
    public boolean reduceProductQuantity(int quanti, int prId) throws AppException {
        Product product = getProductById(prId);
        int updateQuantity = product.getQuantity() - quanti;

        if (updateQuantity < 0) {
            return false;
        }else if (updateQuantity == 0){
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        }
        product.setQuantity(updateQuantity);
        productRepository.save(product);
        return true;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="reduce Product Quantity List">
    public boolean reduceProductQuantityList(List<OrderDetailsRequest> items) {
        boolean check = true;
        for (OrderDetailsRequest item : items) {
            Product product = getProductById(item.getProductId());
            //trừ số lượng trong product
            check = reduceProductQuantity(item.getQuantity(), product.getProductId());
            if(!check){
                return check;
            }
        }
        return check ;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Increase Quantity Product By Id">
    public void increaseProductQuantity(int quanti, int prId) throws AppException {
        Product product = getProductById(prId);
        int updateQuantity = product.getQuantity() + quanti;
        if (product.getStatus() == ProductStatus.OUT_OF_STOCK && updateQuantity > 0) {
            product.setStatus(ProductStatus.IN_STOCK);
        }

        product.setQuantity(updateQuantity);
        productRepository.save(product);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="increase Product Quantity List">
    public void increaseProductQuantityList(List<OrderDetailsRequest> items) {
        for (OrderDetailsRequest item : items) {
            Product product = getProductById(item.getProductId());
            //trừ số lượng trong product
            increaseProductQuantity(item.getQuantity(), product.getProductId());
        }
    }// </editor-fold>


    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Product">
    @Override
    public boolean createProduct(MultipartFile file, ProductRequest request) {
        boolean isInsertedSuccess = false;
        try {
            String folder = "Product";
            String imageUrl = cloudinaryService.saveFileToFolder(file, folder);
            if (imageUrl != null) {
                Product product = new Product();
                product.setImages(imageUrl);

                SubCategory subCategory = iSubCategoryRepository.findBySubCateId(request.getSubCategoryId());
                product.setSubCategory(subCategory);

                productMapper.applyRequestToProduct(request, product, true);

                Date now = new Date();
                product.setCreateAt(now);
                product.setUpdateAt(now);

                productRepository.save(product);
                isInsertedSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Lỗi thêm sản phẩm" + e.getMessage());
        }
        return isInsertedSuccess;
    }// </editor-fold>

    //  <editor-fold default state="collapsed" desc="Update Product">
    @Override
    public boolean updateProduct(int id, MultipartFile file, ProductRequest request) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NO_EXIST));
        boolean isInsertedSuccess = false;
        try {
            String folder = "Product";

            String imageUrl = null;
            if(file != null){
                imageUrl = cloudinaryService.saveFileToFolder(file, folder);
            }
            if (imageUrl != null) {
                product.setImages(imageUrl);
            }

            SubCategory subCategory = iSubCategoryRepository.findBySubCateId(request.getSubCategoryId());
            product.setSubCategory(subCategory);

            productMapper.applyRequestToProduct(request, product, false);

            Date now = new Date();
            product.setCreateAt(now);
            product.setUpdateAt(now);

            //gửi mail báo có hàng cho người dùng đặt trước
            if (product.getStatus() == ProductStatus.OUT_OF_STOCK && request.getQuantity() != 0) {
                sendEmailWishListDone(product.getProductId());
            }
            //set status này thành còn hàng sau khi cập nhập
            product.setStatus(request.getStatus());
            productRepository.save(product);
            isInsertedSuccess = true;

        } catch (Exception e) {
            System.out.println("Lỗi cập nhập sản phẩm" + e.getMessage());
        }
        return isInsertedSuccess;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Set Status Product">
    @Override
    public boolean setStatus(int prId, ProductStatus status) {

        Product product = getProductById(prId);
        product.setStatus(status);
        product.setUpdateAt(new Date());
        productRepository.save(product);
        return true;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Product">
    @Override
    public void deleteProduct(int id) {
        Product product = getProductById(id);
        product.setStatus(ProductStatus.DISCONTINUED);
        product.setUpdateAt(new Date());
        productRepository.save(product);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Product">
    @Override
    public void deleteProductReal(int id) {
        productRepository.deleteById(id);
    }// </editor-fold>


    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Ho_Chi_Minh") // Chạy hàng ngày vào lúc nửa đêm
    public void updateProductStatus() {
        Date currentDate = new Date();
        List<Product> productList = productRepository.findAllByOrderByQuantityOut();
        for (Product product : productList) {
            product.setStatus(ProductStatus.OUT_OF_STOCK);
            product.setUpdateAt(currentDate);
            productRepository.save(product);
        }
    }

    /**
     *  Response to Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get List Product">
    @Override
    public List<ProductResponse> getListProduct() {
        return productMapper.toResponseList(productRepository.findAll());
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="GetList Product Id">
    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow( () -> new AppException(ErrorCode.PRODUCT_NO_EXIST));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get Product By Id Response">
    @Override
    public ProductResponse getProductByIdResponse(int id) {
        Product product = getProductById(id);

        return productMapper.toResponse(product);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product Response By SubCate">
    @Override
    public List<ProductResponse> getProductResponseList(String slug) {
        return productMapper.toResponseList(productRepository.findBySubCategorySlug(slug));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product By Id Or Name">
    @Override
    public List<ProductResponse> getListProductByName(String name) {
        List<Product> productList = productRepository.findProductByName(name);

        if(productList == null) {
            throw new AppException(ErrorCode.PRODUCT_NO_EXIST);
        }

        return productMapper.toResponseList(productList);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product Created At Desc">
    @Override
    public List<ProductResponse> getListProductCreatedAtDesc() {
        return productMapper.toResponseList(productRepository.findAllProductByCreatedAtDesc());
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product Created At Asc">
    @Override
    public List<ProductResponse> getListProductCreatedAtAsc() {
        return productMapper.toResponseList(productRepository.findAllProductByCreatedAtAsc());
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product By Price Desc">
    @Override
    public List<ProductResponse> getListProductByPriceDesc() {
        return productMapper.toResponseList(productRepository.findAllProductByPriceDesc());
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product By Price Acs">
    @Override
    public List<ProductResponse> getListProductByPriceAcs() {
        return productMapper.toResponseList(productRepository.findAllProductByPriceAsc());
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="sort Product By Price Min To Max">
    @Override
    public List<ProductResponse> sortProductByPriceMinToMax(BigDecimal min, BigDecimal max) {
        return productMapper.toResponseList(productRepository.sortProductByPriceMinToMax(min, max));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="sort Product By A -> Z">
    @Override
    public List<ProductResponse> sortProductByAToZ() {
        return productMapper.toResponseList(productRepository.findAllByOrderByNameAsc());
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="sort Product By Z -> A">
    @Override
    public List<ProductResponse> sortProductByZToA() {
        return productMapper.toResponseList(productRepository.findAllByOrderByNameDesc());
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get Product Best Seller">
    @Override
    public List<ProductResponse> getProductBestSeller() {
        return productMapper.toResponseList(productRepository.findAllByOrderByTotalSoldDesc());
    }// </editor-fold>


}
