package com.icourier.qourier;

/**
 * Created by Payghost on 7/25/2017.
 */

public class Courier {

    private String snam;
    private String sphon;
    private String sbankname;
    private String sbankaccount;
    private String sbranch;

    private String name;
    private String phone;
    private String street;
    private String city;
    private String code;
    private String description;
    private String date;
    private String amount;
    private String url;
    private String packname;
    private String email;
    private String username;
    private String account;
    private String key;
    private String detination;
    private Double insurance;

    // Package details

    private String pName;
    private String pDescription;
    private String pFullname;
    private String pPhone;
    private String pAddress;

    private String pStreet;
    private String pCity;
    private String pCode;

    private String pEmail;
    private String pAccount;
    private String UniqueID;
    private String UpUrl;

    private String check;
    private String delete;

    private String puniqkey;

    public Courier(){}

    public Courier(String check,String username, String name, String phone, String street,String city,String code,String destination, String description, String date, String amount, String packname,String email,Double insurance, String url,String puniqkey) {
        this.username = username;
        this.puniqkey = puniqkey;
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.code = code;
        this.detination = destination;
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.pName = packname;
        this.url = url;
        this.email = email;
        this.insurance = insurance;
        this.check = check;
    }
    public Courier(String puniqkey,String date,String delete,String amount,String pName, String pDescription, String pFullname, String pPhone, String pStreet, String pCity, String pCode, String pEmail, String pAccount, String uniqueID,String purl
        ,String snam ,String sphon, String sbankname, String sbankaccount , String sbranch){

        this.puniqkey = puniqkey;

        this.snam = snam;
        this.sphon = sphon;
        this.sbankname = sbankname;
        this.sbankaccount = sbankaccount;
        this.sbranch = sbranch;

         this.pName = pName;
         this.pDescription = pDescription;
         this.pFullname = pFullname;
         this.pPhone = pPhone;
         this.pStreet = pStreet;
         this.pCity = pCity;
         this.pCode = pCode;
         this.pEmail = pEmail;
         this.pAccount = pAccount;
         this.UniqueID = uniqueID;
         this.UpUrl = purl;
         this.amount = amount;
         this.delete = delete;
         this.date = date;
    }

    public String getPuniqkey() {
        return puniqkey;
    }

    public void setPuniqkey(String puniqkey) {
        this.puniqkey = puniqkey;
    }

    public String getSnam() {
        return snam;
    }

    public void setSnam(String snum) {
        this.snam = snam;
    }

    public String getSphon() {
        return sphon;
    }

    public void setSphon(String sphon) {
        this.sphon = sphon;
    }

    public String getSbankname() {
        return sbankname;
    }

    public void setSbankname(String sbankname) {
        this.sbankname = sbankname;
    }

    public String getSbankaccount() {
        return sbankaccount;
    }

    public void setSbankaccount(String sbankaccount) {
        this.sbankaccount = sbankaccount;
    }

    public String getSbranch() {
        return sbranch;
    }

    public void setSbranch(String sbranch) {
        this.sbranch = sbranch;
    }

    public Double getInsurance() {
        return insurance;
    }

    public void setInsurance(Double insurance) {
        this.insurance = insurance;
    }

    public String getDetination() {
        return detination;
    }

    public void setDetination(String detination) {
        this.detination = detination;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUpUrl() {
        return UpUrl;
    }

    public void setUpUrl(String upUrl) {
        UpUrl = upUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String pname) {
        this.packname = packname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }
// Package details

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }


    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getpFullname() {
        return pFullname;
    }

    public void setpFullname(String pFullname) {
        this.pFullname = pFullname;
    }

    public String getpPhone() {
        return pPhone;
    }

    public void setpPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getpAccount() {
        return pAccount;
    }

    public void setpAccount(String pAccount) {
        this.pAccount = pAccount;
    }

    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(String uniqueID) {
        UniqueID = uniqueID;
    }

    public String getpStreet() {
        return pStreet;
    }

    public void setpStreet(String pStreet) {
        this.pStreet = pStreet;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }
}
