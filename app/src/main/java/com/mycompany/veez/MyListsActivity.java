package com.mycompany.veez;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.res.Configuration;
import java.util.ArrayList;


public class MyListsActivity extends ActionBarActivity implements View.OnClickListener {

    private Button b_first_menu;
    private Button b_second_menu;
    private Button b_add_list;
    private EditText et_search;
    private ListView lv_my_lists;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        //TODO- NICE TO HAVE
        /* -------------- Keyboard Handling---------------- */

        final View l_activityRootView = findViewById(R.id.l_main_layout);
        final View l_activityHeaderView = findViewById(R.id.l_header_layout);
        l_activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = l_activityRootView.getRootView().getHeight() - l_activityRootView.getHeight();
                if (heightDiff > 200) { // if more than 200 pixels, its probably a keyboard...
                   l_activityHeaderView.setVisibility(View.GONE);
                }
                else{
                    l_activityHeaderView.setVisibility(View.VISIBLE);
                }
            }
        });


        /* -------------- Side Menu ---------------- */

        mDrawerList = (ListView)findViewById(R.id.lv_navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().hide();

        /* -----------------------------------------*/

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        b_add_list = (Button) findViewById(R.id.b_add_list);
        b_add_list.setOnClickListener(this);

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

    /* ----------------- Menu function ------------------- */

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
                }
                else if (position == 1) {
                    // move to profile_layout
                }
                else if (position == 2) {
                    Intent intent = new Intent(getApplicationContext(), MyListsActivity.class);
                    startActivity(intent);
                }
                else if (position == 3) {
                    Intent intent = new Intent(getApplicationContext(), ExplorerActivity.class);
                    startActivity(intent);
                }
                else if (position == 4) {
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
        if (viewId == R.id.b_add_list) {
            Intent intent = new Intent(getApplicationContext(), CreateListActivity.class);
            startActivity(intent);
        }
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