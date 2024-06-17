package com.n3.mebe.dto.request.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


//Day la class cho Guess
@Data
public class OrderUserCreateRequest {

    private String firstName;
    private String lastName;
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy") //format date
    private Date birthOfDate;
    private String phoneNumber;
}
