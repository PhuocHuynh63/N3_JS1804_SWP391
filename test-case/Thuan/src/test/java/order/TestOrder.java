package order;

import com.n3.mebe.ProjectMeBeApplication;
import com.n3.mebe.controller.OrderController;
import com.n3.mebe.dto.TransactionStatusDTO;
import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.request.order.OrderUserCreateRequest;
import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.service.iml.ProductService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = ProjectMeBeApplication.class)
@ActiveProfiles("test")
@Transactional
public class TestOrder {

    @Autowired
    private OrderController orderController;

    @Autowired
    private ProductService productService;


    //order cho member thành công
    @Test
    public void testCreateOrderSuccessGuest() {
        OrderRequest orderRequest = new OrderRequest();
        OrderUserCreateRequest guest = new OrderUserCreateRequest();
        guest.setFirstName("John");
        guest.setLastName("Doe");
        guest.setEmail("john.doe@example.com");
        guest.setBirthOfDate(new Date());
        guest.setPhoneNumber("1234567890");
        guest.setAddress("123 Street");
        orderRequest.setGuess(guest);
        orderRequest.setUserId(0);
        orderRequest.setVoucherId(1);
        orderRequest.setStatus("Pending");
        orderRequest.setTotalAmount(100);
        orderRequest.setOrderType("COD");
        orderRequest.setPaymentStatus("Pending");
        orderRequest.setNote("Test note");
        List<OrderDetailsRequest> items = new ArrayList<>();
        OrderDetailsRequest item = new OrderDetailsRequest();
        item.setProductId(5);
        item.setQuantity(5);
        item.setPrice(20);
        item.setSalePrice(15);
        items.add(item);
        orderRequest.setItem(items);

        ResponseEntity<TransactionStatusDTO> response = orderController.createOrderByCOD(orderRequest);
        String actualStatus = response.getBody().getStatus();
        String actualMessage = response.getBody().getMessage();
        String expectedStatus = "Ok";
        String expectedMessage = "Create Order successfully processed";

        System.out.println("Kết quả thực tế: " + actualStatus + ", " + actualMessage);
        System.out.println("Kết quả mong muốn: " + expectedStatus + ", " + expectedMessage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedMessage, actualMessage);
    }

    //order cho guest thành công
    @Test
    public void testCreateOrderSuccessUser() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setGuess(null);
        orderRequest.setUserId(2);
        orderRequest.setVoucherId(2);
        orderRequest.setStatus("Pending");
        orderRequest.setTotalAmount(200);
        orderRequest.setOrderType("COD");
        orderRequest.setPaymentStatus("Pending");
        orderRequest.setNote("Test note");
        List<OrderDetailsRequest> items = new ArrayList<>();
        OrderDetailsRequest item = new OrderDetailsRequest();
        item.setProductId(2);
        item.setQuantity(10);
        item.setPrice(30);
        item.setSalePrice(25);
        items.add(item);
        orderRequest.setItem(items);

        ResponseEntity<TransactionStatusDTO> response = orderController.createOrderByCOD(orderRequest);
        String actualStatus = response.getBody().getStatus();
        String actualMessage = response.getBody().getMessage();
        String expectedStatus = "Ok";
        String expectedMessage = "Create Order successfully processed";

        System.out.println("Kết quả thực tế: " + actualStatus + ", " + actualMessage);
        System.out.println("Kết quả mong muốn: " + expectedStatus + ", " + expectedMessage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedMessage, actualMessage);
    }

    //order thất bại vì hết hàng
    @Test
    public void testCreateOrderInsufficientStock() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setGuess(null);
        orderRequest.setUserId(2);
        orderRequest.setVoucherId(2);
        orderRequest.setStatus("Pending");
        orderRequest.setTotalAmount(200);
        orderRequest.setOrderType("COD");
        orderRequest.setPaymentStatus("Pending");
        orderRequest.setNote("Test note");
        List<OrderDetailsRequest> items = new ArrayList<>();
        OrderDetailsRequest item = new OrderDetailsRequest();
        item.setProductId(1);
        item.setQuantity(100);
        item.setPrice(30);
        item.setSalePrice(25);
        items.add(item);
        orderRequest.setItem(items);

        AppException exception = assertThrows(AppException.class, () -> {
            orderController.createOrderByCOD(orderRequest);
        });

        String actualMessage = exception.getMessage();
        String expectedMessage = "Product quantity out";

        System.out.println("Kết quả thực tế: " + actualMessage);
        System.out.println("Kết quả mong muốn: " + expectedMessage);

        assertEquals(expectedMessage, actualMessage);
    }




}
