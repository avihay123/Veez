package com.mycompany.veez;

import java.util.List;

/**
 * Created by T on 5/27/2015.
 */
public class VeezList {
    private String name;
    private boolean isPublic = true;
    private List<VeezItem> items;
    private List<VeezUser> users;
    private VeezUser Admin;
    private int likesCount;
    private int numOfItemsMarkedWithVee;
    private List<String> tags;


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
