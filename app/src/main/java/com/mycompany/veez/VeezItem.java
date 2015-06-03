package com.mycompany.veez;

import java.io.Serializable;

/**
 * Created by T on 5/27/2015.
 */
public class VeezItem implements Serializable{
    private String name;
    private String info;
    private boolean vee;
    private VeezUser userVeed;

    //for debug
    public VeezItem(String name) {
        this.name = name;
    }

    //for debug
    public VeezItem(String name, String info, boolean vee) {
        this.name = name;
        this.info = info;
        this.vee = vee;
    }

    public boolean isVee() {
        return vee;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public void setVee(Boolean vee) { this.vee = vee; }

    public void setInfo(String info) { this.info = info; }
}
