package com.store.models;

import java.math.BigDecimal;

public class ShippingInfo {

    private final long id;
    private final String company;
    private final String type;
    private final BigDecimal price;

    public ShippingInfo() {
        this.id = 1;
        this.company = "Canada Post";
        this.type = "Regular";
        this.price = new BigDecimal(10);
    }

    public ShippingInfo(long id, String company, String type, BigDecimal price) {
        this.id = id;
        this.company = company;
        this.type = type;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ShippingInfo{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

}
