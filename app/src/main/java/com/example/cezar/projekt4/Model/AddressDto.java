package com.example.cezar.projekt4.Model;

import java.util.Date;

/**
 * Created by Kuba on 13.12.2015.
 */
public class AddressDto {
    @Override
    public String toString() {
        return "AddressDto{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", post='" + post + '\'' +
                ", number='" + number + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    private String street;

    private String city;

    private String post;

    private String number;

    private Date createdAt;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
