package com.icourier.qourier;

/**
 * Created by Payghost on 9/26/2017.
 */

public class Reg {

      private String category;
      private String username;
      private String fullname;
      private String phone;
      private String email;
      private String address;
      private String bank;
      private String pictureurl;
      private String idurl;
      private String licenceurl;
      private String vehicleurl;
      private String terms;
      private String password;

      private String street;
      private String city;
      private String code;
      private String bankname;
      private String bankaccount;
      private String branchcode;

    public Reg(String category, String fullname, String phone, String email, String street,String city,String code,String bankname,String bankaccount,String branchcode,String pictureurl, String idurl, String licenceurl, String vehicleurl, String terms, String password) {
        this.category = category;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;

        this.street = street;
        this.city = city;
        this.code = code;
        this.bankname = bankname;
        this.bankaccount = bankaccount;
        this.branchcode = branchcode;

        this.pictureurl = pictureurl;
        this.idurl = idurl;
        this.licenceurl = licenceurl;
        this.vehicleurl = vehicleurl;
        this.terms = terms;
        this.password = password;
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

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public Reg(String pictureurl, String idurl, String licenceurl, String vehicleurl) {
        this.pictureurl = pictureurl;
        this.idurl = idurl;
        this.licenceurl = licenceurl;
        this.vehicleurl = vehicleurl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getIdurl() {
        return idurl;
    }

    public void setIdurl(String idurl) {
        this.idurl = idurl;
    }

    public String getLicenceurl() {
        return licenceurl;
    }

    public void setLicenceurl(String licenceurl) {
        this.licenceurl = licenceurl;
    }

    public String getVehicleurl() {
        return vehicleurl;
    }

    public void setVehicleurl(String vehicleurl) {
        this.vehicleurl = vehicleurl;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
