package com.mycompany.veez;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by רביבו on 30/05/2015.
 */
public class VeezListExplorer implements Serializable{
    private String name;
    private List<VeezItem> items;
    private int likesCount;
//    private Bitmap photo;
    private List<String> tags;

    public VeezListExplorer(int likesCount, List<VeezItem> items,String name, List<String> tags) {
        this.name = name;
        this.items = items;
        this.likesCount = likesCount;
        this.tags = tags;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getName() {
        return name;
    }

    public boolean hasTag(String tag){
        return tags.contains(tag);
    }

    public boolean hasAllTags(List<String> searchTags){
        for (String tag : searchTags) {
            if (!tags.contains(tag)) {
                return false;
            }
        }
        return true;
    }
}
