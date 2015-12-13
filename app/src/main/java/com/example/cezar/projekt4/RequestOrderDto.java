package com.example.cezar.projekt4;

import java.util.Date;

/**
 * Created by Kuba on 13.12.2015.
 */
public class RequestOrderDto {
    @Override
    public String toString() {
        return "RequestOrderDto{" +
                "id=" + id +
                ", organization='" + organization + '\'' +
                ", contactName='" + contactName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", agree=" + agree +
                ", createdAt=" + createdAt +
                ", address=" + address +
                '}';
    }

    private Long id;

    private String organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    private String contactName;

    private String phone;

    private String email;

    private Boolean agree = true;

    private Date createdAt;

    private AddressDto address;
}
