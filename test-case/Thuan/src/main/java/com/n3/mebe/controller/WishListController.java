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


    @GetMapping("/list")
    List<WishListResponse> getWishList() {
        return wishListService.getWishListResponses();
    }

    @GetMapping("/wlId={id}")
    List<WishListResponse> getWishList(@PathVariable("id") int id) {
        return wishListService.getWishListResponse(id);
    }

    @PostMapping("/create")
    String createWishList(@RequestBody WishListRequest wishListRequest) {
        String msg;
        if (wishListService.addWishList(wishListRequest)){
            msg = "Create WishList successfully ";
        }else {
            msg = "Create WishList failed";
        }
        return msg;
    }



}
