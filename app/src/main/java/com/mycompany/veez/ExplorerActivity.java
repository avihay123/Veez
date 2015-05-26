package com.mycompany.veez;

import android.os.Bundle;
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
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.content.res.Configuration;
import java.util.ArrayList;

public class ExplorerActivity extends ActionBarActivity implements View.OnClickListener {

    private Button b_first_menu;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);

        /* -------------- Side Menu ---------------- */

        mDrawerList = (ListView)findViewById(R.id.lv_navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().hide();

        /* -----------------------------------------*/

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

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