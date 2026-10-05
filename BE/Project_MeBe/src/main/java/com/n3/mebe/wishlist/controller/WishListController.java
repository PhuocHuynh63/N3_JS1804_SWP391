package com.n3.mebe.wishlist.controller;

import lombok.RequiredArgsConstructor;

import com.n3.mebe.wishlist.dto.request.WishListRequest;
import com.n3.mebe.wishlist.dto.response.WishListResponse;
import com.n3.mebe.wishlist.service.IWishListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final IWishListService wishListService;


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
        return wishListService.getWishListResponseByProductID(productId);
    }

}
