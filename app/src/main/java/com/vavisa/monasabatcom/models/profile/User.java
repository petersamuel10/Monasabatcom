package com.vavisa.monasabatcom.models.profile;

public class User {

    private Integer Id;
    private String Name;
    private String Email;
    private String Mobile;
    private String Password;
    private String DeviceToken;
    private int DeviceType;
    private String Language;

    //register
    public User(String name, String email, String mobile, String password, String deviceToken) {
        Name = name;
        Email = email;
        Mobile = mobile;
        Password = password;
        DeviceToken = deviceToken;
        DeviceType = 1;
        Language = "ar";
    }

    //login
    public User(String email, String password, String deviceToken) {
        Email = email;
        Password = password;
        DeviceToken = deviceToken;
        DeviceType = 1;
        Language = "ar";
    }

    public User(Integer id, String name, String email, String mobile) {
        Id = id;
        Name = name;
        Email = email;
        Mobile = mobile;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer userId) {
        Id = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public int getDeviceType() {
        return DeviceType;
    }

    public String getLanguage() {
        return Language;
    }
}
