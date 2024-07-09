package com.n3.mebe.dto.request.user;

import lombok.Data;

@Data
public class GoogleUser {
    private String sub;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String email;
    private boolean emailVerified;
}
