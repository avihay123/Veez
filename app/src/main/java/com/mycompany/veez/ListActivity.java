package com.mycompany.veez;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends ActionBarActivity implements View.OnClickListener {

    private Button b_first_menu;
    private Button b_second_menu;
    private TextView tv_curr_items;
    private TextView tv_total_items;
    private TextView tv_likes;
    private AutoCompleteTextView ac_search_item;
    private Button b_add_item;
    private ListView lv_list_items;
    //TODO delete it is exists in VeezList
    private Integer currentItmes = 0;
    private Integer TotalItems = 0;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //-------------------------------- buttons code ----------------------------------------------
        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        b_second_menu = (Button) findViewById(R.id.b_second_menu);
        b_second_menu.setOnClickListener(this);

        b_add_item = (Button) findViewById(R.id.b_add_item);
        b_add_item.setOnClickListener(this);

        //-------------------------------- TextView code --------------------------------
        tv_likes = (TextView) findViewById(R.id.tv_likes);
        tv_curr_items = (TextView) findViewById(R.id.tv_curr_items);
        tv_curr_items.setText(currentItmes.toString());
        tv_total_items = (TextView) findViewById(R.id.tv_total_items);
        tv_total_items.setText(TotalItems.toString());


        /* -------------- Side Menu ---------------- */

        mDrawerList = (ListView) findViewById(R.id.lv_navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().hide();

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        /* -------------- List View ---------------- */
        lv_list_items = (ListView) findViewById(R.id.lv_list_items);

        List<VeezItem> items= new ArrayList<VeezItem>();
        items.add(new VeezItem("Coke","",false));
        items.add(new VeezItem("Beer","6 pack",false));
        items.add(new VeezItem("Wine","",false));
        items.add(new VeezItem("Steake","3 pieces",false));
        items.add(new VeezItem("Water","asd",true));
        items.add(new VeezItem("Chicken","",true));
        items.add(new VeezItem("Plates","",true));
        MyAdapter adapter = new MyAdapter(items);

        lv_list_items.setAdapter(adapter);

    }

    //TODO change to VeezList.Length and remove the total items and currentItmes
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
            mDrawerLayout.openDrawer(Gravity.START);
        }
        if (viewId == R.id.b_second_menu) {
            //TODO
        }

        if (viewId == R.id.b_add_item) {
            addItem();
        }
    }


    private class MyAdapter extends BaseAdapter {

        private List<VeezItem> myList;

        public MyAdapter(List<VeezItem> aList) {
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

        //TODO return
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            ViewHolder viewHolder;

            Log.d("MY_TAG", "Position: " + position);

            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.list_view_element, null);

                viewHolder = new ViewHolder();
                viewHolder.cb_item_vee = (CheckBox) view.findViewById(R.id.cb_item_vee);
                viewHolder.b_info = (Button) view.findViewById(R.id.b_info);
                viewHolder.b_friend_veed = (Button) view.findViewById(R.id.b_friend);

                view.setTag(viewHolder);
            } else {
                view = convertView;

                viewHolder = (ViewHolder) view.getTag();
            }

            // Put the content in the view
            viewHolder.cb_item_vee.setText((myList.get(position)).getName());
            if ((myList.get(position)).isVee()) {
                viewHolder.cb_item_vee.setChecked(true);
                //TODO set the photo of the
                viewHolder.b_friend_veed.setVisibility(View.VISIBLE);
            } else {
                viewHolder.cb_item_vee.setChecked(false);
                viewHolder.b_friend_veed.setVisibility(View.INVISIBLE);
            }

            if ((myList.get(position)).getInfo().length() > 0)
                viewHolder.b_info.setVisibility(View.VISIBLE);
            else
                viewHolder.b_info.setVisibility(View.INVISIBLE);

            return view;
        }

        private class ViewHolder {
            CheckBox cb_item_vee;
            Button b_info;
            Button b_friend_veed;
        }
    }


    /* ----------------- Menu functions ------------------- */

    private void addDrawerItems() {

        ArrayList<String> values = new ArrayList<String>();
        values.add("");
        values.add("Name");
        values.add("My Lists");
        values.add("Explorer");
        values.add("Friends");

        MyAdapter2 adapter = new MyAdapter2(values);
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


    private class MyAdapter2 extends BaseAdapter {

        private ArrayList<String> myList;

        public MyAdapter2(ArrayList<String> aList) {
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
