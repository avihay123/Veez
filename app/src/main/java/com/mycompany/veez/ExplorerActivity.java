package com.mycompany.veez;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.content.res.Configuration;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExplorerActivity extends ActionBarActivity implements View.OnClickListener{

    private Button b_first_menu;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    private ListView lv_lists;
    private TextView tv_explorer;
    private AutoCompleteTextView ac_search;
    private List<String> searchTags = new ArrayList<>();
    private TextView[] tv_array_tags = new TextView[9];
    private int tagsNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Side Menu

        mDrawerList = (ListView) findViewById(R.id.lv_navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        //Buttons

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);


        tv_array_tags[0] = (TextView) findViewById(R.id.tv_tag1);
        tv_array_tags[1] = (TextView) findViewById(R.id.tv_tag2);
        tv_array_tags[2] = (TextView) findViewById(R.id.tv_tag3);
        tv_array_tags[3] = (TextView) findViewById(R.id.tv_tag4);
        tv_array_tags[4] = (TextView) findViewById(R.id.tv_tag5);
        tv_array_tags[5] = (TextView) findViewById(R.id.tv_tag6);
        tv_array_tags[6] = (TextView) findViewById(R.id.tv_tag7);
        tv_array_tags[7] = (TextView) findViewById(R.id.tv_tag8);
        tv_array_tags[8] = (TextView) findViewById(R.id.tv_tag9);

        for (int i = 0; i < tv_array_tags.length; i++) {
            tv_array_tags[i].setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < tv_array_tags.length; i++) {
            tv_array_tags[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchTags.remove((String) ((TextView) v).getText());
                    tagsNum--;
                    ((MyAdapter) lv_lists.getAdapter()).updateTags(searchTags);
                    updateTags();
                }
            });
        }

        //Auto Compelte

        tv_explorer = (TextView) findViewById(R.id.tv_explorer);

        ArrayAdapter<String> adapterAutoComplete = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, TAGS);

        ac_search = (AutoCompleteTextView) findViewById(R.id.ac_search);

        ac_search.setAdapter(adapterAutoComplete);
        ac_search.setThreshold(1);


        ac_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tag = (String) ((TextView) view).getText();
                if (searchTags.contains(tag)){
                    Toast.makeText(getApplicationContext(),"This tag is already selected", Toast.LENGTH_SHORT).show();
                    ac_search.setText("");
                    return;
                }
                searchTags.add(tag);
                tagsNum++;
                ac_search.setText("");
                if (tagsNum == 9) {
                    ac_search.setEnabled(false);
                    ac_search.setText("9 tags maximum");
                    Log.d("EXPLORER", "reached limit");
                }
                ((MyAdapter)lv_lists.getAdapter()).updateTags(searchTags);
                updateTags();

            }
        });

        //Lists
        lv_lists = (ListView) findViewById(R.id.lv_explorer_lists);
        List<VeezItem> items= new ArrayList<>();
        List<VeezListExplorer> lists= new ArrayList<>();
        items.add(new VeezItem(("a")));
        lists.add(new VeezListExplorer(0,items,"BBQ",
                Arrays.asList("BBQ")));
        items.add(new VeezItem(("b")));
        lists.add(new VeezListExplorer(1,items,"BBQBBQ",
                Arrays.asList("BBQ","BBQ2")));
        lists.add(new VeezListExplorer(1,items,"Shopping",Arrays.asList("Shopping")));
        lists.add(new VeezListExplorer(1,items,"Camping",Arrays.asList("Camping")));
        lists.add(new VeezListExplorer(1,items,"Trip",Arrays.asList("Road Trip")));
        lists.add(new VeezListExplorer(1,items,"Movies",Arrays.asList("Movies")));
        lists.add(new VeezListExplorer(1,items,"Books",Arrays.asList("Books")));
        //TODO get the lists from the server
        MyAdapter adapter = new MyAdapter(lists);
        lv_lists.setAdapter(adapter);
    }

    private void updateTags(){
        for (int i = 0; i < 9; i++){
            if (i < tagsNum){
                //this means the view at index i should be visible and showing a tag
                tv_array_tags[i].setText(searchTags.get(i));
                tv_array_tags[i].setVisibility(View.VISIBLE);
            }
            else{
                tv_array_tags[i].setVisibility(View.INVISIBLE);
            }
        }
        if (tagsNum < 9){
            ac_search.setText("");
            ac_search.setEnabled(true);
        }
    }

    /* --------------------------On Click ----------------------- */

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.b_first_menu) {
            mDrawerLayout.openDrawer(Gravity.START);
        }
    }



    //----------------------- List ----------------------------

    private class MyAdapter extends BaseAdapter {

        private List<VeezListExplorer> allItems;
        private List<VeezListExplorer> itemsToShow;

        public MyAdapter(List<VeezListExplorer> aList){
            allItems = new ArrayList<>(aList);
            itemsToShow = new ArrayList<>(aList);
        }

        @Override
        public int getCount() {
            return itemsToShow.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsToShow.get(position);
        }

        //TODO return
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view;
            ViewHolder viewHolder;

            Log.d("MY_TAG", "Position: " + position);

            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.list_view_explorer, null);

                viewHolder = new ViewHolder();
                viewHolder.tv_num_likes = (TextView) view.findViewById(R.id.tv_num_likes);
                viewHolder.tv_list_name = (TextView) view.findViewById(R.id.tv_list_name);
                viewHolder.b_add_to_my_lists = (Button) view.findViewById(R.id.b_add_to_my_lists);
                view.setTag(viewHolder);

            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }



            // Put the content in the view
            viewHolder.tv_num_likes.setText(String.valueOf((itemsToShow.get(position)).getLikesCount()));
            viewHolder.tv_list_name.setText((itemsToShow.get(position)).getName());

            viewHolder.b_add_to_my_lists.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VeezListExplorer list = itemsToShow.get(position);
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    intent.putExtra("list", list);
                    startActivity(intent);
                    finish();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VeezListExplorer list = itemsToShow.get(position);
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    intent.putExtra("list", list);
                    startActivity(intent);
                    finish();
                }
            });

            return view;
        }

        public void updateTags(List<String> tags){
            List<VeezListExplorer> result = new ArrayList<>();
            for (VeezListExplorer list : allItems){
                if (list.hasAllTags(tags))
                    result.add(list);
            }
            itemsToShow = result;
            this.notifyDataSetChanged();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(ac_search.getWindowToken(), 0);
        }

        private class ViewHolder {
            TextView tv_num_likes;
            TextView tv_list_name;
            Button b_add_to_my_lists;
        }
    }

    /* --------------------------Auto Complete ----------------------- */
    private static String newHeadline(List<String> tags, int tagsNum) {
        String headline = "";
        headline += tags.get(0);
        for (int i = 1; i < tagsNum; i++) {
            headline += " + " + tags.get(i);
        }
        return headline;
    }

    private static final String[] TAGS = new String[]{
            //to have at least one tag of each letter
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U",
            "V","W","X","Y","Z",

            "BBQ", "Road Trip", "Camping", "Shopping", "Beach",
            "Books", "Songs", "TV Series", "Movies", "Holidays", "Action Movies",
            "Familiy Dinner", "Tutorials", "Lectures", "App Ideas", "Important Dates",


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

    /* ----------------- Menu functions ------------------- */

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


    private void addDrawerItems() {

        ArrayList<String> values = new ArrayList<>();
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

}