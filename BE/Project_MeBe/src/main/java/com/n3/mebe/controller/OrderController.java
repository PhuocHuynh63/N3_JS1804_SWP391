package com.n3.mebe.controller;

import com.n3.mebe.dto.TransactionStatusDTO;
import com.n3.mebe.dto.request.order.CancelOrderRequest;
import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.request.order.OrderStatusRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.service.IOrderDetailsService;
import com.n3.mebe.service.IOrderService;
import com.n3.mebe.service.iml.ProductService;
import com.n3.mebe.service.iml.paymentOrder.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailsService orderDetailsService;

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private ProductService productService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Request from Client
     *
     */

    // Create order
    @PostMapping("/create_vnpay")
    public ResponseEntity<TransactionStatusDTO> createOrderByVNPay(@RequestBody OrderRequest orderRequest,
            @RequestParam Map<String, String> vnp_Params) {

        productService.reduceProductQuantityList(orderRequest.getItem()); // trừ số lượng Product
        // Lấy paymentId từ params
        String paymentId = vnp_Params.get("vnp_TxnRef");
        String transactionReference = vnp_Params.get("vnp_OrderInfo");
        if (paymentId == null) {
            // Không có paymentId trong params
            TransactionStatusDTO status = new TransactionStatusDTO();
            status.setStatus("No");
            status.setMessage("Payment information not found");
            productService.increaseProductQuantityList(orderRequest.getItem()); // Cộng lại Product
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
        }

        // Kiểm tra thông tin thanh toán trong Redis
        String paymentKey = "payment:" + paymentId;
        String paymentStatus = stringRedisTemplate.opsForValue().get(paymentKey);

        if (paymentStatus == null) {
            // Thông tin thanh toán không tồn tại hoặc đã hết hạn
            TransactionStatusDTO status = new TransactionStatusDTO();
            status.setStatus("No");
            status.setMessage("Payment information not found or expired");
            productService.increaseProductQuantityList(orderRequest.getItem()); // Cộng lại Product
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
        }

        boolean paymentSuccess = vnPayService.handleVNPayResponse(vnp_Params);

        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        if (paymentSuccess) {
            transactionStatusDTO.setStatus("Ok");
            transactionStatusDTO.setMessage("Payment successfully processed");
            // lưu order vào cơ sở dữ liệu
            orderRequest.setTransactionReference(transactionReference);
            orderRequest.setOrderType("Online");
            boolean success = orderService.createOrder(orderRequest);

            // Sau khi lưu order, xóa thông tin thanh toán khỏi Redis
            stringRedisTemplate.delete(paymentKey);
        } else {
            transactionStatusDTO.setStatus("No");
            transactionStatusDTO.setMessage("Payment failed");
            productService.increaseProductQuantityList(orderRequest.getItem()); // Cộng lại Product
        }

        return ResponseEntity.status(paymentSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(transactionStatusDTO);
    }

    // Create order
    @PostMapping("/create_cod")
    public ResponseEntity<TransactionStatusDTO> createOrderByCOD(@RequestBody OrderRequest orderRequest) {

        productService.reduceProductQuantityList(orderRequest.getItem()); // trừ số lượng Product
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        // lưu order vào cơ sở dữ liệu

        String type = "COD";
        orderRequest.setOrderType(type);
        boolean success = orderService.createOrder(orderRequest);
        if (success) {
            transactionStatusDTO.setStatus("Ok");
            transactionStatusDTO.setMessage("Create Order successfully processed");
        } else {
            transactionStatusDTO.setStatus("No");
            transactionStatusDTO.setMessage("Create Order failed");
            productService.increaseProductQuantityList(orderRequest.getItem()); // Cộng lại Product
        }

        return ResponseEntity.status(success ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(transactionStatusDTO);
    }

    // Update order by id
    @PutMapping("/update/orId={id}")
    public Order updateOrder(@PathVariable("id") int orId, @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(orId, orderRequest);
    }

    // Cancel order by id
    @PutMapping("/cancel/orId={id}")
    public String cancelOrder(@PathVariable("id") int id, @RequestBody CancelOrderRequest request) {
        return orderService.cancelOrder(id, request);
    }

    // Cancel order by id
    @PutMapping("/status")
    public String cancelOrder(@RequestBody OrderStatusRequest request) {
        return orderService.setStatusOrder(request);
    }

    /**
     * Response to Client
     *
     */

    @GetMapping("/list")
    public List<OrderResponse> getOrdersList() {
        return orderService.getOrdersList();
    }

    //search Order by Email
    @GetMapping("/search_order")
    public List<OrderResponse> searchOrderEmail(@RequestParam String email) {
        return orderService.getOrdersListEmail(email);
    }

    //search Order by phone number
    @GetMapping("/search_order")
    public List<OrderResponse> searchOrder(@RequestParam String phone) {
        return orderService.getOrdersListPhone(phone);
    }

    //search Order by id
    @GetMapping("/oId={id}")
    public OrderResponse getOrderByID(@PathVariable("id") int orderId) {
        return orderService.getOrderResponse(orderId);
    }

}
