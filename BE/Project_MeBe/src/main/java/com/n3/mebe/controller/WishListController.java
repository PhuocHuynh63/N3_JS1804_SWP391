package com.n3.mebe.controller;

import com.n3.mebe.dto.request.wishList.WishListRequest;
import com.n3.mebe.dto.response.wishList.WishListResponse;
import com.n3.mebe.service.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private IWishListService wishListService;


    /**
     * Request from client
     *
     */
    @PostMapping("/create")
    String createWishList(@RequestBody WishListRequest wishListRequest) {
        String msg;
        
        if (wishListService.addWishList(wishListRequest)) {
            msg = "Create WishList successfully ";
        } else {
            msg = "Create WishList failed";
        }
        return msg;
    }


    /**
     * Response from client
     *
     */

    @GetMapping("/list")
    List<WishListResponse> getWishList() {
        return wishListService.getWishListResponsesAll();
    }

    @GetMapping("/userId={id}")
    List<WishListResponse> getWishList(@PathVariable("id") int id) {
        return wishListService.getWishListResponse(id);
    }

    // L
    @GetMapping("/productId={id}")
    List<WishListResponse> getWishListByWLId(@PathVariable("id") int productId) {
        return wishListService.getWishListResponse(productId);
    }

}
