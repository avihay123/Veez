package com.mycompany.veez;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 5/27/2015.
 */
public class VeezUser {
    private String name; //TODO get from facebook;
    private long id;
    private List<VeezList> myLists;

    public String getName() {
        return name;
    }

    public VeezUser() {
        name = null;
        id = -1;
        myLists = new ArrayList<VeezList>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<VeezList> getLists() {
        return myLists;
    }
}
