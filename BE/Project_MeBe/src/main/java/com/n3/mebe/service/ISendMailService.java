package com.n3.mebe.service;

import com.n3.mebe.dto.response.gmail.GmailSendResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.User;
import com.n3.mebe.entity.WishList;
import jakarta.mail.MessagingException;

public interface ISendMailService {

    boolean createSendEmailForgot(String email);


    boolean createSendEmailVerifyOrder(String email, Order order);

    boolean createSendEmailWishListConfirmation(User user, WishList wishList);

    boolean createSendEmailWishListNotifications(WishList wishList);

}
