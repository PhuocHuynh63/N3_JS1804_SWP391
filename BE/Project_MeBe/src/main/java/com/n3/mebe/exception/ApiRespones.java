package com.n3.mebe.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRespones<T>{

    private int code;
    private String msg;
    private T data;
}
