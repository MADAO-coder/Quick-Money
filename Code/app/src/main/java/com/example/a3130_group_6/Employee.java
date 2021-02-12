package com.example.a3130_group_6;

public class Employee {
    private String userName;
    private String password;
    private String phone;
    private String emailAddress;
    public Employee(){

    }
    public Employee(String userName, String password, String phone, String emailAddress){
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.emailAddress = emailAddress;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

