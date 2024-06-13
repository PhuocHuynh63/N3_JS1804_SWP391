package com.n3.mebe.service.iml;

import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;
import com.n3.mebe.dto.response.order.details.OrderDetailsResponse;
import com.n3.mebe.entity.Inventory;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.OrderDetail;
import com.n3.mebe.entity.Product;
import com.n3.mebe.repository.IOrderDetailsRepository;
import com.n3.mebe.service.IOrderDetailsService;
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
    private InventoryService inventoryService;

    @Autowired
    private IOrderDetailsRepository orderDetailsRepository;

    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Order Details">
    public OrderDetail createOrderDetail(OrderDetailsRequest request) {

        OrderDetail orderDetail = new OrderDetail();

        Order order = orderService.getOrder(request.getOrderId());

        // cap nhap so luong product co trong inventory sau khi có orderDetails
        inventoryService.updateQuantity(request.getQuantity() , request.getInventoryId());

        Inventory inventory = inventoryService.getInventoryById(request.getInventoryId());

        Product product = inventory.getProduct();

        orderDetail.setOrder(order);
        orderDetail.setInventory(inventory);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(request.getQuantity());
        orderDetail.setPrice(request.getPrice());
        orderDetail.setSalePrice(request.getSalePrice());

        return orderDetailsRepository.save(orderDetail);
    }// </editor-fold>




    /**
     *  Response from Client
     *
     */

    // <editor-fold default state="collapsed" desc="get All Order Details">
    @Override
    public List<OrderDetailsResponse> getAllOrderDetails() {

        List<OrderDetail> orderDetailList = orderDetailsRepository.findAll();

        List<OrderDetailsResponse> responses = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetailList) {
            OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();

            orderDetailsResponse.setOdId(orderDetail.getOdId());
            orderDetailsResponse.setOrder(orderDetail.getOrder());
            orderDetailsResponse.setProduct(orderDetail.getProduct());
            orderDetailsResponse.setInventory(orderDetail.getInventory());
            orderDetailsResponse.setQuantity(orderDetail.getQuantity());
            orderDetailsResponse.setPrice(orderDetail.getPrice());
            orderDetailsResponse.setSalePrice(orderDetail.getSalePrice());

            responses.add(orderDetailsResponse);
        }

        return responses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get Order Details by Order ID">
    @Override
    public List<OrderDetailsResponse> getOrderDetailsById(int orderId) {

        List<OrderDetail> list = orderDetailsRepository.findByOrderOrderId(orderId);

        List<OrderDetailsResponse> responses = new ArrayList<>();
        for (OrderDetail orderDetail : list) {
            OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();

            orderDetailsResponse.setOdId(orderDetail.getOdId());
            orderDetailsResponse.setOrder(orderDetail.getOrder());
            orderDetailsResponse.setProduct(orderDetail.getProduct());
            orderDetailsResponse.setInventory(orderDetail.getInventory());
            orderDetailsResponse.setQuantity(orderDetail.getQuantity());
            orderDetailsResponse.setPrice(orderDetail.getPrice());
            orderDetailsResponse.setSalePrice(orderDetail.getSalePrice());
            responses.add(orderDetailsResponse);
        }


        return responses;
    }// </editor-fold>


}
