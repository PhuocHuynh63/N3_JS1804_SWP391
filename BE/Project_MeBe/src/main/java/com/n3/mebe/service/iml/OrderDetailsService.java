package com.n3.mebe.service.iml;

import com.n3.mebe.dto.response.order.details.OrderDetailsResponse;
import com.n3.mebe.entity.OrderDetail;
import com.n3.mebe.repository.IOrderDetailsRepository;
import com.n3.mebe.service.IOrderDetailsService;
import com.n3.mebe.service.iml.paymentOrder.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailsService implements IOrderDetailsService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;


    @Autowired
    private IOrderDetailsRepository orderDetailsRepository;



    /**
     *  Response from Client
     *
     */


    // <editor-fold default state="collapsed" desc="get Order Details by Order ID">
    @Override
    public List<OrderDetailsResponse> getOrderDetailsById(int orderId) {

        List<OrderDetail> list = orderDetailsRepository.findByOrderOrderId(orderId);

        List<OrderDetailsResponse> responses = new ArrayList<>();
        for (OrderDetail orderDetail : list) {
            OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();

            orderDetailsResponse.setOdId(orderDetail.getOdId());

            orderDetailsResponse.setOrder(orderService.getOrderResponse(orderId));
            orderDetailsResponse.setProduct(orderDetail.getProduct());
            orderDetailsResponse.setQuantity(orderDetail.getQuantity());
            orderDetailsResponse.setPrice(orderDetail.getPrice());
            orderDetailsResponse.setSalePrice(orderDetail.getSalePrice());
            responses.add(orderDetailsResponse);
        }


        return responses;
    }// </editor-fold>


}
