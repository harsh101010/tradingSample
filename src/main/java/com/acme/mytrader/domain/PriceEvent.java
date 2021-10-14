package com.acme.mytrader.domain;

/**
 * domain object for receiving price data for security
 */
public class PriceEvent {
    public PriceEvent(String security, double price) {
        this.security = security;
        this.price = price;
    }

    String security;
    double price;

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PriceEvent{" +
                "security='" + security + '\'' +
                ", price=" + price +
                '}';
    }
}
