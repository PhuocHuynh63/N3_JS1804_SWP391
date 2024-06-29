package login;

import com.n3.mebe.ProjectMeBeApplication;
import com.n3.mebe.controller.UserController;
import com.n3.mebe.dto.request.user.UserCreateRequest;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.exception.AppException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ProjectMeBeApplication.class)
@ActiveProfiles("test")
@Transactional
public class TestSigin {

    @Autowired
    private UserController userController;

    @ParameterizedTest
    @CsvFileSource(resources = "/sigin.csv", numLinesToSkip = 1)
    void testCreateUser(String firstName, String lastName, String email, String username, String password, String expectedResponse) {
        UserCreateRequest request = new UserCreateRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setUsername(username);
        request.setPassword(password);

        if (expectedResponse.equals("Email already exist")|| expectedResponse.equals("Username already exist")) {
            AppException exception = assertThrows(AppException.class, () -> {
                userController.createUser(request);
            });
            System.out.println("Expected: " + expectedResponse);
            System.out.println("Actual: " + exception.getMessage());
            assertEquals(expectedResponse, exception.getMessage());
        } else {
            ResponseEntity<ResponseData> responseEntity = userController.createUser(request);
            ResponseData responseData = responseEntity.getBody();
            System.out.println("Expected: " + expectedResponse);
            System.out.println("Actual: " + responseData.getDescription());
            assertEquals(expectedResponse, responseData.getDescription());
        }
    }
}
