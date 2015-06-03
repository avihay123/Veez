package com.mycompany.veez;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 5/27/2015.
 */
public class VeezList implements Serializable{
    private String name;
    private boolean isPublic;
    private List<VeezItem> items;
    private List<VeezUser> users;
    private VeezUser Admin;
    private int likesCount;
    private int numOfItemsMarkedWithVee;
    private List<String> tags;
    private Bitmap photo;

    public VeezList(String name, boolean isPublic, List<VeezUser> users, VeezUser admin, List<String> tags, Bitmap photo) {
        this.name = name;
        this.isPublic = isPublic;
        this.users = users;
        Admin = admin;
        this.tags = tags;
        this.photo = photo;
        this.items = new ArrayList<VeezItem>();
        likesCount = 0;
        numOfItemsMarkedWithVee = 0;
    }

    //for debug
    public VeezList(int numOfItemsMarkedWithVee, int likesCount, List<VeezItem> items, boolean isPublic, String name) {
        this.numOfItemsMarkedWithVee = numOfItemsMarkedWithVee;
        this.likesCount = likesCount;
        this.items = items;
        this.isPublic = isPublic;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getNumOfItemsMarkedWithVee() {
        return numOfItemsMarkedWithVee;
    }

    public int getNumOfItems() {
        return items.size();
    }
}
