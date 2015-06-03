package com.mycompany.veez;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 5/27/2015.
 */
public class VeezUser implements Serializable{
    private String name;
    private String profilePictureJson;
    private String facebookID;
    private List<VeezList> myLists;

    public String getName() {
        return name;
    }

    public VeezUser(String name, String profilePictureJson, String facebookID){
        this.name = name;
        this.profilePictureJson = profilePictureJson;
        this.facebookID = facebookID;
        myLists = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.facebookID = id;
    }

    public List<VeezList> getLists() {
        return myLists;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getProfilePicture() {
        return profilePictureJson;
    }

    public void setProfilePicture(String bitmap) {
        this.profilePictureJson = bitmap;
    }
}
