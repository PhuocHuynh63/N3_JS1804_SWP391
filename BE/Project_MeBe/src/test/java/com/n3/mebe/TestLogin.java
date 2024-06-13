package com.n3.mebe;

import com.n3.mebe.controller.LoginController;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.service.ILoginService;
import com.n3.mebe.util.JwtUtilHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestLogin {

    @Mock
    private ILoginService loginService;

    @Mock
    private JwtUtilHelper jwtUtilHelper;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/login.csv", numLinesToSkip = 1)
    void testLogin(String username, String password, boolean isSuccess) {
        ResponseEntity<ResponseData> response = (ResponseEntity<ResponseData>) loginController.signin(username, password);
        if (response.getBody().isSuccess() == isSuccess) {
            when(loginService.checkLogin(username, password)).thenReturn(true);
            when(jwtUtilHelper.genarateToken(username)).thenReturn("mockToken");
            assertEquals(200, response.getStatusCodeValue());
        } else {
            when(loginService.checkLogin(username, password)).thenReturn(false);
            assertEquals(401, response.getStatusCodeValue());
        }

        verify(loginService, times(1)).checkLogin(username, password);
    }
}