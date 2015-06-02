package com.mycompany.veez;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends Activity {

    private EditText et_item;
    private EditText et_info;
    private Button b_add_item_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // TODO get the VeezList from dictionary

        et_item = (EditText) findViewById(R.id.et_item);
        et_info = (EditText) findViewById(R.id.et_info);

        b_add_item_dialog = (Button) findViewById(R.id.b_add_item_dialog);
        b_add_item_dialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}
