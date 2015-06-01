package com.mycompany.veez;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 5/27/2015.
 */
public class VeezUser {
    private String name;
    private Bitmap profilePicture;
    private String facebookID;
    private List<VeezList> myLists;

    public String getName() {
        return name;
    }

    public VeezUser(String name, Bitmap profilePicutre, String facebookID){
        this.name = name;
        this.profilePicture = profilePicture;
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

    public Bitmap getBitmap() {
        return profilePicture;
    }

    public void setBitmap(Bitmap bitmap) {
        this.profilePicture = bitmap;
    }
}
