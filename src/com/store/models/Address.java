package com.store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Object to represent an Address of type shipping
 * or billing and it's corresponding information.
 *
 * @author Kenny Byrd
 */
public class Address {

    private long id;
    private String fullName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String province;
    private String country;
    private String zip;
    private String phone;
    private AddressType type;

    public Address() {
        this.id = -1;
        this.fullName = "";
        this.addressLine1 = "";
        this.addressLine2 = "";
        this.city = "";
        this.province = "";
        this.country = "";
        this.zip = "";
        this.phone = "";
        this.type = AddressType.SHIPPING;
    }

    public Address(String fullName, String addressLine1, String addressLine2, String city, String province, String country, String zip, String phone, AddressType type) {
        this.id = -1;
        this.fullName = fullName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
        this.type = type;
    }

    public Address(String fullName, String addressLine1, String addressLine2, String city, String province, String country, String zip, String phone, String type) {
        this.id = -1;
        this.fullName = fullName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
        this.type = AddressType.valueOf(type);
    }

    public Address(long id, String fullName, String addressLine1, String addressLine2, String city, String province,
                   String country, String zip, String phone, String type) {
        this.id = id;
        this.fullName = fullName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
        this.type = AddressType.valueOf(type);
    }

    public Address(long id, String fullName, String addressLine1, String addressLine2, String city, String province,
                   String country, String zip, String phone, AddressType type) {
        this.id = id;
        this.fullName = fullName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    @JsonIgnore
    public boolean isShippingAddress() {
        return this.type.equals(AddressType.SHIPPING);
    }

    @JsonIgnore
    public boolean isBillingAddress() {
        return this.type.equals(AddressType.BILLING);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                '}';
    }

}
