package login;

import com.n3.mebe.controller.LoginController;
import com.n3.mebe.dto.response.ResponseData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TestLogin {

    @InjectMocks
    private LoginController loginController;


    @ParameterizedTest
    @CsvFileSource(resources = "/login.csv", numLinesToSkip = 1)
    void testLogin(String username, String password, boolean isSuccess) {

        ResponseEntity<ResponseData> response = (ResponseEntity<ResponseData>) loginController.signin(username, password);
        assertEquals(isSuccess, response.getBody().isSuccess());


        }


}
