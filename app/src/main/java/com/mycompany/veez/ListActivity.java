package com.mycompany.veez;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity implements View.OnClickListener {

    private Button b_first_menu;
    private Button b_second_menu;
    private TextView tv_curr_items;
    private TextView tv_total_items;
    private TextView tv_likes;
    private AutoCompleteTextView ac_search_item;
    private Button b_add_item;
    private ListView lv_list_items;
    private Integer currentItmes=0;
    private Integer TotalItems=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //buttons code------------------------------------------------------------------------------
        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        b_second_menu = (Button) findViewById(R.id.b_second_menu);
        b_second_menu.setOnClickListener(this);

        b_add_item = (Button) findViewById(R.id.b_add_item);
        b_add_item.setOnClickListener(this);

        //TextView code
        tv_curr_items = (TextView) findViewById(R.id.tv_curr_items);
        tv_curr_items.setText(currentItmes.toString());

        tv_total_items = (TextView) findViewById(R.id.tv_total_items);
        tv_total_items.setText(TotalItems.toString());

        tv_likes = (TextView) findViewById(R.id.tv_likes);

        lv_list_items = (ListView) findViewById(R.id.lv_list_items);


    }

    private void addItem() {
        //TODO popup
        //TODO list handle
        TotalItems++;
        tv_total_items.setText(TotalItems.toString());
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.b_first_menu) {
            //TODO
        }
        if (viewId == R.id.b_second_menu) {
            //TODO
        }

        if (viewId == R.id.b_add_item) {
            addItem();
        }
//
//        if (viewId == R.id.b_add_tag) {
//            //TODO
//        }
//
//        if (viewId == R.id.b_leave_list) {
//            //TODO delete the list from the user
//
//            Intent intent = new Intent(getApplicationContext(), MyListsActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }
}
