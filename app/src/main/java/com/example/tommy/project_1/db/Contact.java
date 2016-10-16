package com.example.tommy.project_1.db;

/**
 * Created by tommy on 10/14/16.
 */

public class Contact {
    private String name;
    private String tel;
    private String telType;
    private String address;
    private String backgroundColor;

    public Contact(String name, String tel, String telType, String address, String backgroundColor) {
        this.name = name;
        this.tel = tel;
        this.telType = telType;
        this.address = address;
        this.backgroundColor = backgroundColor;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getTelType() {
        return telType;
    }
    public void setTelType(String telType) {
        this.telType = telType;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String[] getStringArray() {
        return new String[] {name, tel, telType, address, backgroundColor};
    }
    public static Contact gernarateContactFromStringArray(String[] array) {
        return new Contact(array[0], array[1], array[2], array[3], array[4]);
    }
}
