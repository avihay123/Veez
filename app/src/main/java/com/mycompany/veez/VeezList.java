package com.mycompany.veez;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 5/27/2015.
 */
public class VeezList implements Serializable {
    private String name;
    private boolean isPublic;
    private List<VeezItem> items;
    private String address;

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {

        return address;
    }

    public List<String> getTags() {
        return tags;
    }

    public VeezList(VeezListExplorer v){
        this.name = v.getName();
        this.isPublic = true;
        this.users = new ArrayList<>();
        Admin = null;
        this.tags = v.getTags();
        this.photo = "";
        this.items = v.getItems();
        likesCount = 0;
        numOfItemsMarkedWithVee = 0;
    }


    public String getPhoto() {
        return photo;
    }

    private List<VeezUser> users;
    private VeezUser Admin;
    private int likesCount;
    private int numOfItemsMarkedWithVee;
    private List<String> tags;
    private String photo;
    private String deadline;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public VeezList(String address,String name, boolean isPublic, List<VeezUser> users, VeezUser admin, List<String> tags, String photo) {
        this.name = name;
        this.address = address;
        this.isPublic = isPublic;
        this.users = users;
        Admin = admin;
        this.tags = tags;
        this.photo = photo;
        this.items = new ArrayList<VeezItem>();
        likesCount = 0;
        numOfItemsMarkedWithVee = 0;
    }


    public VeezList(String name, boolean isPublic, List<VeezUser> users, VeezUser admin, List<String> tags, String photo) {
        this.name = name;
        this.address = address;
        this.isPublic = isPublic;
        Admin = admin;
        this.tags = tags;
        this.photo = photo;
        this.items = new ArrayList<VeezItem>();
        likesCount = 0;
        numOfItemsMarkedWithVee = 0;
    }

    public String getStringPhoto() {
        return photo;
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

    public Integer getNumOfItemsMarkedWithVee() {
        return numOfItemsMarkedWithVee;
    }

    public Integer getNumOfItems() {
        return items.size();
    }

    public void setNumOfItemsMarkedWithVee(int numOfItemsMarkedWithVee) {
        this.numOfItemsMarkedWithVee = numOfItemsMarkedWithVee;
    }

    public List<VeezItem> getItems() {
        return items;
    }
}
