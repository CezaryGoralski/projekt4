package com.example.cezar.projekt4;

import java.util.Date;

/**
 * Created by Kuba on 13.12.2015.
 */
public class RequestOrderDto {
    private Long id;

    private String organization;

    private String contactName;

    private String phone;

    private String email;

    private Boolean agree = true;

    private Date createdAt;

    private AddressDto address;
}
