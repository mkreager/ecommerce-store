package com.store.models;

/**
 * Represents a Customer of the CD Store. This contains
 * basic information about the customer to identify them
 * for accessing their account.
 *
 * @author Kenny Byrd
 */
public class Customer {

    private long id;
    private String firstName;
    private String lastName;
    private String email; // Customer's login username
    private String password;
    private long defaultShippingAddressId; // Do not need when creating new account
    private long defaultBillingAddressId; // Do not need when creating new account

    public Customer() {
        this.id = -1;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.defaultShippingAddressId = -1;
        this.defaultBillingAddressId = -1;
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Customer(long id, String firstName, String lastName, String email, String password, long defaultShippingAddressId, long defaultBillingAddressId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.defaultShippingAddressId = defaultShippingAddressId;
        this.defaultBillingAddressId = defaultBillingAddressId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDefaultShippingAddressId() {
        return defaultShippingAddressId;
    }

    public void setDefaultShippingAddressId(long defaultShippingAddressId) {
        this.defaultShippingAddressId = defaultShippingAddressId;
    }

    public long getDefaultBillingAddressId() {
        return defaultBillingAddressId;
    }

    public void setDefaultBillingAddressId(long defaultBillingAddressId) {
        this.defaultBillingAddressId = defaultBillingAddressId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", defaultShippingAddressId=" + defaultShippingAddressId +
                ", defaultBillingAddressId=" + defaultBillingAddressId +
                '}';
    }
}
