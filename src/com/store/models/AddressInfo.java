package com.store.models;

/**
 * Holds shipping and billing address information for a Customer.
 *
 * @author Kenny Byrd
 */
public class AddressInfo {

    private Address billingAddress;
    private Address shippingAddress;

    public AddressInfo() {
        this.billingAddress = new Address();
        this.shippingAddress = new Address();
    }

    public AddressInfo(Address billingAddress, Address shippingAddress) {
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "billingAddress=" + billingAddress +
                ", shippingAddress=" + shippingAddress +
                '}';
    }

}
