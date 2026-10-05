package com.n3.mebe.notification.service;

import com.n3.mebe.order.entity.Order;
import com.n3.mebe.user.entity.User;
import com.n3.mebe.wishlist.entity.WishList;

public interface ISendMailService {

    boolean createSendEmailForgot(String email);

    boolean createSendEmailVerifyOrder(Order order);

    boolean createSendEmailWishListConfirmation(User user, WishList wishList);

    boolean createSendEmailWishListNotifications(WishList wishList);

    boolean sendOtpCheckEmailExist(String email);

    boolean checkOtp(String otp);

    void invalidateOtp(String identifier);

    boolean sendMailCreateSuccess(String email);

}
