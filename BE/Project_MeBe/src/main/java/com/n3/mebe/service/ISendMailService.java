package com.n3.mebe.service;

import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.User;
import com.n3.mebe.entity.WishList;

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
