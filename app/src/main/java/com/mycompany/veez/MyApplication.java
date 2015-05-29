package com.mycompany.veez;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by T on 5/27/2015.
 */
public class MyApplication extends Application{

    private VeezUser user = new VeezUser();

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "acTRo6rNllCZChixQ72wphccXrJn1CGK7hHcKmEw", "fQdlWHh24YkNtohMmoMxtoN67BdS2iIMBtbjlUHy");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo2", "Bob2");
        testObject.saveInBackground();
        Log.d("PARSE2", "SuccessBOB");

    }

    public VeezUser getUser() {
        return user;
    }
}
