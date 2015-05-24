package com.mycompany.veez;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MyListsActivity extends Activity implements View.OnClickListener {

    private Button b_first_menu;
    private Button b_second_menu;
    private Button b_add_list;
    private EditText et_search;
    private ListView lv_my_lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        b_second_menu = (Button) findViewById(R.id.b_second_menu);
        b_second_menu.setOnClickListener(this);

        b_add_list = (Button) findViewById(R.id.b_add_list);
        b_second_menu.setOnClickListener(this);

        et_search = (EditText) findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv_my_lists = (ListView) findViewById(R.id.lv_my_lists);



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
        if (viewId == R.id.b_add_list) {
            Intent intent = new Intent(getApplicationContext(), CreateListActivity.class);
            startActivity(intent);
        }

    }
}