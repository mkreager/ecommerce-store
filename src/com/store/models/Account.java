package com.store.models;

/**
 * Holds Customer information, including their
 * default Billing and Shipping address information.
 *
 * @author Kenny Byrd
 */
public class Account {

    private Customer customer;
    private AddressInfo defaultAddressInfo;

    public Account() {
        this.customer = new Customer();
        this.defaultAddressInfo = new AddressInfo();
    }

    public Account(Customer customer) {
        this.customer = customer;
        this.defaultAddressInfo = new AddressInfo();
    }

    public Account(Customer customer, AddressInfo defaultAddressInfo) {
        this.customer = customer;
        this.defaultAddressInfo = defaultAddressInfo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AddressInfo getDefaultAddressInfo() {
        return defaultAddressInfo;
    }

    public void setDefaultAddressInfo(AddressInfo defaultAddressInfo) {
        this.defaultAddressInfo = defaultAddressInfo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "customer=" + customer +
                ", defaultAddressInfo=" + defaultAddressInfo +
                '}';
    }

}
