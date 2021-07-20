package com.codecool.shop.model;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private BillingAddress billingAddress;
    private ShippingAddress shippingAddress;
    private CreditCard creditCard;
    private Paypal paypal;

    public User(String userId, String firstName, String lastName, String email, String phone, BillingAddress billingAddress, ShippingAddress shippingAddress) {
        this.id = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public Paypal getPaypal() {
        return paypal;
    }

    public void setPaypal(Paypal paypal) {
        this.paypal = paypal;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public String getName(){
        return firstName + " " + lastName;
    }
}
