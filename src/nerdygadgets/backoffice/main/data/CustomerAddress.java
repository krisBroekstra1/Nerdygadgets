package nerdygadgets.backoffice.main.data;

import nerdygadgets.backoffice.main.Route.Coördinates;

public class CustomerAddress {
    private String name;
    private String city;
    private String address;
    private String postalcode;
    private String orderid;
    private String customerid;
    private Coördinates coordinaten;
    private String province;

    public CustomerAddress(String city, String address, String province) {
        this.address = address;
        this.city = city;
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getName() {
        return name;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public String getOrderid() {
        return orderid;
    }

    public Coördinates getCoördinaten() {
        return coordinaten;
    }

    public void setCoördinaten(Coördinates c) {
        this.coordinaten = c;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }
}
