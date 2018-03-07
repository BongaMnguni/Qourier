package com.icourier.qourier;

/**
 * Created by Payghost on 8/15/2017.
 */

public class Package {

    private String pName;
    private String pDescription;
    private String pFullname;
    private String pPhone;
    private String pAddress;
    private String pEmail;
    private String pAccount;
    private String UniqueID;
    private String check;

    public Package(String pName, String pDescription, String pFullname, String pPhone, String pAddress, String pEmail, String pAccount, String uniqueID) {
        this.pName = pName;
        this.pDescription = pDescription;
        this.pFullname = pFullname;
        this.pPhone = pPhone;
        this.pAddress = pAddress;
        this.pEmail = pEmail;
        this.pAccount = pAccount;
        UniqueID = uniqueID;
    }


    public Package() {
    }

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
}
