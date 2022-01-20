package com.example.suiterentals.Model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Suite implements Serializable {
    String uid,Suitetitle,description,price,rooms,latitude,longitude,piclocation,address,phoneno;

    public Suite() {
    }


    public Suite(String uid, String suitetitle, String description, String price, String rooms, String latitude, String longitude, String piclocation, String address, String phoneno) {
        this.uid = uid;
        Suitetitle = suitetitle;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.latitude = latitude;
        this.longitude = longitude;
        this.piclocation = piclocation;
        this.address = address;
        this.phoneno = phoneno;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSuitetitle() {
        return Suitetitle;
    }

    public void setSuitetitle(String suitetitle) {
        Suitetitle = suitetitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPiclocation() {
        return piclocation;
    }

    public void setPiclocation(String piclocation) {
        this.piclocation = piclocation;
    }
}
