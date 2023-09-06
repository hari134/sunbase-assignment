package com.hari134.sunbase.models;

public class Customer {
    private String Uuid;
    private String FirstName;
    private String LastName;
    private String Street;
    private String Address;
    private String City;
    private String State;
    private String Email;
    private String Phone;

    public Customer(String uuid,String firstName, String lastName, String street, String address, String city, String state,
            String email, String phone) {
        Uuid = uuid;
        FirstName = firstName;
        LastName = lastName;
        Street = street;
        Address = address;
        City = city;
        State = state;
        Email = email;
        Phone = phone;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
