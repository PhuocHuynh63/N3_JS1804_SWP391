package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;
import com.n3.mebe.dto.request.product.ProductRequest;
import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.entity.Product;
import com.n3.mebe.entity.SubCategory;
import com.n3.mebe.entity.WishList;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IProductRespository;
import com.n3.mebe.repository.ISubCategoryRepository;
import com.n3.mebe.repository.IWishListRepository;
import com.n3.mebe.service.ICloudinaryService;
import com.n3.mebe.service.IProductService;
import com.n3.mebe.service.IWishListService;
import com.n3.mebe.service.iml.mail.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRespository iProductRespository;

    @Autowired
    private ICloudinaryService cloudinaryService;

    @Autowired
    private ISubCategoryRepository iSubCategoryRepository;

    @Autowired
    private IWishListRepository wishListRepository;

    @Autowired
    private SendMailService sendMailService;



    // <editor-fold default state="collapsed" desc="Send Email Wish List Done">
    public void sendEmailWishListDone(int productId) {
        String status = "Chờ thông báo";
        List<WishList> wishLists = wishListRepository.findWishListsByProduct(productId, status);
        for (WishList wishList : wishLists) {

            wishList.setStatus("Đã có hàng");
            //gửi mail thông báo đã có hàng
            wishList.setUpdatedAt(new Date());
            wishListRepository.save(wishList);
            sendMailService.createSendEmailWishListNotifications(wishList);
        }
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Reduce Update Quantity Product By Id">
    public void reduceProductQuantity(int quanti, int prId) throws AppException {
        Product product = getProductById(prId);
        int updateQuantity = product.getQuantity() - quanti;

        if (updateQuantity < 0) {
            throw new AppException(ErrorCode.PRODUCT_QUANTITY_END);
        }else if (updateQuantity == 0){
            String outStock = "Hết hàng";
            setStatus(prId, outStock);
        }
        product.setQuantity(updateQuantity);
        iProductRespository.save(product);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="reduce Product Quantity List">
    public void reduceProductQuantityList(List<OrderDetailsRequest> items) {
        for (OrderDetailsRequest item : items) {
            Product product = getProductById(item.getProductId());
            //trừ số lượng trong product
            reduceProductQuantity(item.getQuantity(), product.getProductId());
        }
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Increase Quantity Product By Id">
    public void increaseProductQuantity(int quanti, int prId) throws AppException {
        Product product = getProductById(prId);
        int updateQuantity = product.getQuantity() + quanti;
        String out = "Hết hàng";
        if(product.getStatus().equals(out)){
            String inStock = "Còn hàng";
        }

        product.setQuantity(updateQuantity);
        iProductRespository.save(product);
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

                product.setSlug(request.getSlug());
                product.setName(request.getName());
                product.setDescription(request.getDescription());
                product.setPrice(request.getPrice());
                product.setSalePrice(request.getSalePrice());
                product.setStatus(request.getStatus());
                product.setTotalSold(request.getTotalSold());
                product.setQuantity(request.getQuantity());
                product.setProductView(request.getProductView());

                Date now = new Date();
                product.setCreateAt(now);
                product.setUpdateAt(now);

                iProductRespository.save(product);
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
        Product product = iProductRespository.findById(id).
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

            product.setSlug(request.getSlug());
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setSalePrice(request.getSalePrice());

            product.setTotalSold(request.getTotalSold());

            product.setQuantity(request.getQuantity());


            product.setProductView(request.getProductView());

            Date now = new Date();
            product.setCreateAt(now);
            product.setUpdateAt(now);

            //gửi mail báo có hàng cho người dùng đặt trước
            if(product.getStatus().equals("Hết hàng") && request.getQuantity() != 0){
                sendEmailWishListDone(product.getProductId());
            }
            //set status này thành còn hàng sau khi cập nhập
            product.setStatus(request.getStatus());
            iProductRespository.save(product);
            isInsertedSuccess = true;

        } catch (Exception e) {
            System.out.println("Lỗi cập nhập sản phẩm" + e.getMessage());
        }
        return isInsertedSuccess;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Set Status Product">
    @Override
    public boolean setStatus(int prId, String status) {

        Product product = getProductById(prId);
        product.setStatus(status);
        product.setUpdateAt(new Date());
        iProductRespository.save(product);
        return true;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Product">
    @Override
    public void deleteProduct(int id) {
        Product product = getProductById(id);
        String status = "Không còn bán";
        product.setStatus(status);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Product">
    @Override
    public void deleteProductReal(int id) {
        iProductRespository.deleteById(id);
    }// </editor-fold>


    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Ho_Chi_Minh") // Chạy hàng ngày vào lúc nửa đêm
    public void updateProductStatus() {
        Date currentDate = new Date();
        String status = "Hết hàng";
        List<Product> productList = iProductRespository.findAllByOrderByQuantityOut();
        for (Product product : productList) {
            product.setStatus(status);
            product.setUpdateAt(currentDate);
            iProductRespository.save(product);
        }
    }

    /**
     *  Response to Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get List Product">
    @Override
    public List<ProductResponse> getListProduct() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.findAll();
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="GetList Product Id">
    @Override
    public Product getProductById(int id) {
        return iProductRespository.findById(id).orElseThrow( () -> new AppException(ErrorCode.PRODUCT_NO_EXIST));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get Product By Id Response">
    @Override
    public ProductResponse getProductByIdResponse(int id) {
        Product product = getProductById(id);

        ProductResponse productResponse = new ProductResponse();

        productResponse.setProductId(product.getProductId());
        productResponse.setSubCategory(product.getSubCategory());
        productResponse.setSlug(product.getSlug());
        productResponse.setName(product.getName());
        productResponse.setImages(product.getImages());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setSalePrice(product.getSalePrice());
        productResponse.setStatus(product.getStatus());
        productResponse.setTotalSold(product.getTotalSold());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setProductView(product.getProductView());
        productResponse.setCreateAt(product.getCreateAt());
        productResponse.setUpdateAt(product.getUpdateAt());

        return productResponse;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product Response By SubCate">
    @Override
    public List<ProductResponse> getProductResponseList(String slug) {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.findBySubCategorySlug(slug);
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product By Id Or Name">
    @Override
    public List<ProductResponse> getListProductByName(String name) {
        List<Product> productList = iProductRespository.findProductByName(name);

        if(productList == null) {
            throw new AppException(ErrorCode.PRODUCT_NO_EXIST);
        }

        List<ProductResponse> productResponseList = new ArrayList<>();
        for(Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product Created At Desc">
    @Override
    public List<ProductResponse> getListProductCreatedAtDesc() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.findAllProductByCreatedAtDesc();
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product Created At Asc">
    @Override
    public List<ProductResponse> getListProductCreatedAtAsc() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.findAllProductByCreatedAtAsc();
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product By Price Desc">
    @Override
    public List<ProductResponse> getListProductByPriceDesc() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.findAllProductByPriceDesc();
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Product By Price Acs">
    @Override
    public List<ProductResponse> getListProductByPriceAcs() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.findAllProductByPriceAsc();
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="sort Product By Price Min To Max">
    @Override
    public List<ProductResponse> sortProductByPriceMinToMax(float min, float max) {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.sortProductByPriceMinToMax(min, max);
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="sort Product By A -> Z">
    @Override
    public List<ProductResponse> sortProductByAToZ() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.findAllByOrderByNameAsc();
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="sort Product By Z -> A">
    @Override
    public List<ProductResponse> sortProductByZToA() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.findAllByOrderByNameDesc();
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get Product Best Seller">
    @Override
    public List<ProductResponse> getProductBestSeller() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList = iProductRespository.findAllByOrderByTotalSoldDesc();
        for (Product product : productList) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(product.getProductId());
            productResponse.setSubCategory(product.getSubCategory());
            productResponse.setSlug(product.getSlug());
            productResponse.setName(product.getName());
            productResponse.setImages(product.getImages());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setStatus(product.getStatus());
            productResponse.setTotalSold(product.getTotalSold());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setProductView(product.getProductView());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());

            //add vào list
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }// </editor-fold>


}
