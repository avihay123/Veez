package com.mycompany.veez;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ListActivity extends Activity {

    private ListView list_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

}
