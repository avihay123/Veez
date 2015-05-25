package com.mycompany.veez;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ListInfoActivity extends Activity implements View.OnClickListener {

    private Button b_first_menu;
    //TODO
    private Button b_add_image;

    private Button b_add_friend;
    private Button b_add_tag;
    private EditText et_deadline;
    private CheckBox cb_private;
    private CheckBox cb_public;
    private Button b_leave_list;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_info);

        //buttons code------------------------------------------------------------------------------
        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        b_add_image = (Button) findViewById(R.id.b_add_image);
        b_add_image.setOnClickListener(this);

        b_add_friend = (Button) findViewById(R.id.b_add_friend);
        b_add_friend.setOnClickListener(this);

        b_add_tag = (Button) findViewById(R.id.b_add_tag);
        b_add_tag.setOnClickListener(this);

        b_leave_list = (Button) findViewById(R.id.b_leave_list);
        b_leave_list.setOnClickListener(this);

        //deadline code------------------------------------------------------------------------------
        et_deadline = (EditText) findViewById(R.id.et_deadline);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        et_deadline.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               new DatePickerDialog(ListInfoActivity.this, date, myCalendar
                                                       .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                       myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                           }
                                       }
        );
        //checkbox code-----------------------------------------------------------------------------
        cb_private = (CheckBox) findViewById(R.id.cb_private);
        cb_public = (CheckBox) findViewById(R.id.cb_public);

        cb_private.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_public.setChecked(!isChecked);
            }
        });

        cb_public.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_private.setChecked(!isChecked);
            }
        });
    }


    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_deadline.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.b_first_menu) {
            //TODO
        }
        if (viewId == R.id.b_add_image) {
            //TODO
        }

        if (viewId == R.id.b_add_friend) {
            //TODO next build
        }

        if (viewId == R.id.b_add_tag) {
            //TODO
        }

        if (viewId == R.id.b_leave_list) {
            //TODO delete the list from the user

            Intent intent = new Intent(getApplicationContext(), CreateListActivity.class);
            startActivity(intent);
        }

    }
}
