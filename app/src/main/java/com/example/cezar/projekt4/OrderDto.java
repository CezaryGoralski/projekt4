package com.example.cezar.projekt4;

import java.util.List;

/**
 * Created by Kuba on 13.12.2015.
 */
public class OrderDto {
    private Long id;

    private List<RequestOrderDto> requestOrder;

    private Boolean delivered = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RequestOrderDto> getRequestOrder() {
        return requestOrder;
    }

    public void setRequestOrder(List<RequestOrderDto> requestOrder) {
        this.requestOrder = requestOrder;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }
}
