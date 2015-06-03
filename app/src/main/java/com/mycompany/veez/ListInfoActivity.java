package com.mycompany.veez;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.content.res.Configuration;

import java.util.ArrayList;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ListInfoActivity extends ActionBarActivity implements View.OnClickListener {

    private Button b_first_menu;
    private Button b_add_image;
    private Button b_remove_tag;

    private Button b_add_friend;
    private Button b_add_tag;
    private EditText et_deadline;
    private CheckBox cb_private;
    private CheckBox cb_public;
    private Button b_leave_list;
    private Calendar myCalendar;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        /* -------------- Side Menu ---------------- */

        mDrawerList = (ListView) findViewById(R.id.lv_navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        /* ------------------ Buttons -----------------------*/

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        b_add_image = (Button) findViewById(R.id.b_add_image);
        b_add_image.setOnClickListener(this);
        b_add_image.setVisibility(View.INVISIBLE);

        b_add_image = (Button) findViewById(R.id.b_add_image);
        b_add_image.setOnClickListener(this);

        b_add_friend = (Button) findViewById(R.id.b_add_friend);
        b_add_friend.setOnClickListener(this);

        b_add_tag = (Button) findViewById(R.id.b_add_tag);
        b_add_tag.setOnClickListener(this);

        b_remove_tag = (Button) findViewById(R.id.b_remove_tag);
        b_remove_tag.setOnClickListener(this);

        b_leave_list = (Button) findViewById(R.id.b_create_list);
        b_leave_list.setOnClickListener(this);
        b_leave_list.setBackgroundDrawable((getResources().getDrawable(R.drawable.leave_list)));

        //------------------------------ Deadline code ---------
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


    /* ----------------- Menu functions ------------------- */

    private void addDrawerItems() {

        ArrayList<String> values = new ArrayList<String>();
        values.add("");
        values.add("Name");
        values.add("My Lists");
        values.add("Explorer");
        values.add("Friends");

        MyAdapter adapter = new MyAdapter(values);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // move to profile_layout
                } else if (position == 1) {
                    // move to profile_layout
                } else if (position == 2) {
                    Intent intent = new Intent(getApplicationContext(), MyListsActivity.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(getApplicationContext(), ExplorerActivity.class);
                    startActivity(intent);
                } else if (position == 4) {
                    // move to friends_layout
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* ------------------------------------------------- */

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.b_first_menu) {
            mDrawerLayout.openDrawer(Gravity.START);
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

            Intent intent = new Intent(getApplicationContext(), MyListsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        et_deadline.setText(sdf.format(myCalendar.getTime()));
    }

    private class MyAdapter extends BaseAdapter {

        private ArrayList<String> myList;

        public MyAdapter(ArrayList<String> aList) {
            myList = aList;
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public Object getItem(int position) {
            return myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view;
            ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.my_simple_list_item1, null);

                viewHolder = new ViewHolder();
                viewHolder.myText = (TextView) view.findViewById(android.R.id.text1);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.myText.setText(myList.get(position));
            return view;
        }

        private class ViewHolder {
            TextView myText;
        }
    }
}

