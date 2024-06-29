package login;

import com.n3.mebe.ProjectMeBeApplication;
import com.n3.mebe.controller.LoginController;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.util.JwtUtilHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ProjectMeBeApplication.class)
@ActiveProfiles("test")
public class TestLogin {

    @Autowired
    private LoginController loginController;

    @Autowired
    private JwtUtilHelper jwtUtilHelper;

    @ParameterizedTest
    @CsvFileSource(resources = "/login.csv", numLinesToSkip = 1)
    void testLogin(String username, String password, boolean expectedResult) {
        // Gọi hàm đăng nhập từ loginController
        ResponseEntity<ResponseData> response = loginController.signin(username, password);
        ResponseData responseData = response.getBody();

        // In ra kết quả thực tế và mong muốn
        System.out.println("Username: " + username);
        System.out.println("Expected Result: " + expectedResult);
        System.out.println("Actual Success: " + responseData.isSuccess());
        System.out.println("Expected Description: " + (expectedResult ? "Login successful" : "Invalid username or password"));
        System.out.println("Actual Description: " + responseData.getDescription());
        if (expectedResult) {
            System.out.println("Expected Token: " + jwtUtilHelper.genarateToken(username));
            System.out.println("Actual Token: " + responseData.getData());
        } else {
            System.out.println("Expected Status: 401");
            System.out.println("Actual Status: " + responseData.getStatus());
        }

        // Kiểm tra kết quả đăng nhập
        assertEquals(expectedResult, responseData.isSuccess());
        if (expectedResult) {
            assertEquals("Login successful", responseData.getDescription());
            assertEquals(jwtUtilHelper.genarateToken(username), responseData.getData());
        } else {
            assertEquals(401, responseData.getStatus());
            assertEquals("Invalid username or password", responseData.getDescription());
        }
    }
}
