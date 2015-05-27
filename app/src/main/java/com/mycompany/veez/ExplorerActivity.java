package com.mycompany.veez;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.content.res.Configuration;
import java.util.ArrayList;
import java.util.List;

public class ExplorerActivity extends ActionBarActivity implements View.OnClickListener {

    private Button b_first_menu;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ListView lv_lists;
    private TextView tv_explorer;
    private AutoCompleteTextView ac_search;
    private String[] searchTags = new String[5];
    private int tagsNum = 0;
    private List<VeezList> allLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /* -------------- Side Menu ---------------- */

        mDrawerList = (ListView)findViewById(R.id.lv_navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().hide();

        /* -----------------------------------------*/

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        tv_explorer = (TextView) findViewById(R.id.tv_explorer);

        lv_lists = (ListView) findViewById(R.id.lv_explorer_lists);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, TAGS);

        ac_search = (AutoCompleteTextView) findViewById(R.id.ac_search);

        ac_search.setAdapter(adapter);
        ac_search.setThreshold(1);

        ac_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchTags[tagsNum] = (String) ((TextView)view).getText();
                Log.d("EXPLORER", searchTags[tagsNum]);
                tagsNum++;
                ac_search.setText("");
                tv_explorer.setText(newHeadline(searchTags, tagsNum));
                tv_explorer.setTextSize(35 - 5 * tagsNum);
                if (tagsNum == 5){
                    ac_search.setEnabled(false);
                    ac_search.setText("5 tags maximum");
                    Log.d("EXPLORER", "reached limit");
                }

            }
        });

    }

    private static String newHeadline(String[] tags, int tagsNum){
        String headline = "";
        headline += tags[0];
        for (int i = 1; i < tagsNum; i++){
            headline += " + " + tags[i];
        }
        return headline;
    }

    private static final String[] TAGS = new String[] {
            "BBQ", "Road Trip", "Camping", "Shopping", "Beach", "Sleep Over",
            "Books", "Songs", "TV Series", "Movies", "Holidays", "Action Movies",


            //stupid tags to have a tag for every letter
            "Dogs", "Entertainment", "Fonts", "Groupon Deals",
            "IMDB Top 10 Weekly", //checks a long tag too

            //TODO for debug
            "BBQ2",
            "BBQ3",
            "BBQ4",
            "BBQ5",
            "BBQ6",
            "BBQ7",
            "BBQ9",
            "BBQ8"
            /*
            TODO:
            - Add more tags!
                preferably have more tags than you can see at once all starting
                with same letter to show at presentation.
            - Move tags to resources!
            - Maybe allow new tags by users, requires storing tags on DB.
             */
    };

    /* ----------------- Menu function ------------------- */

    private void addDrawerItems() {

        ArrayList<String> values = new ArrayList<>();
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