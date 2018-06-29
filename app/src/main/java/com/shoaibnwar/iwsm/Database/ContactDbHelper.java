package com.shoaibnwar.iwsm.Database;

/**
 * Created by gold on 6/29/2018.
 */

public class ContactDbHelper {

    public String getId() {
        return id;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getContactLat() {
        return contactLat;
    }

    public String getContactLng() {
        return contactLng;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public void setContactLat(String contactLat) {
        this.contactLat = contactLat;
    }

    public void setContactLng(String contactLng) {
        this.contactLng = contactLng;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

    String contactName;
    String contactPhone;
    String contactAddress;
    String contactLat;
    String contactLng;
    String contactStatus;






}

