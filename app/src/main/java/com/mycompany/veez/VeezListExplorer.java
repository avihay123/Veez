package com.mycompany.veez;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by רביבו on 30/05/2015.
 */
public class VeezListExplorer {
    private String name;
    private List<VeezItem> items;
    private int likesCount;
    private Bitmap photo;

    public VeezListExplorer(int likesCount, List<VeezItem> items,String name) {
        this.name = name;
        this.items = items;
        this.likesCount = likesCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getName() {
        return name;
    }
}
