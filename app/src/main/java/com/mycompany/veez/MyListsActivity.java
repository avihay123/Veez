package com.mycompany.veez;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.content.res.Configuration;

import com.google.gson.Gson;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MyListsActivity extends ActionBarActivity implements View.OnClickListener {

    private Button b_first_menu;
    private Button b_add_list;
    private AutoCompleteTextView ac_search_list;
    private ListView lv_my_lists;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


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
                } else {
                    l_activityHeaderView.setVisibility(View.VISIBLE);
                }
            }
        });


        /* -------------- Side Menu ---------------- */

        mDrawerList = (ListView) findViewById(R.id.lv_navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        /* ------------------ Buttons ----------------*/

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        b_add_list = (Button) findViewById(R.id.b_add_list);
        b_add_list.setOnClickListener(this);

        /* ----------- listView -------------------------------------*/
        //----------------------------for debug -----------------------
        lv_my_lists = (ListView) findViewById(R.id.lv_my_lists);
        List<VeezItem> items = new ArrayList<VeezItem>();
        List<VeezList> lists = new ArrayList<VeezList>();
        items.add(new VeezItem(("a")));
        lists.add(new VeezList(0, 0, items, true, "abc"));
        items.add(new VeezItem(("b")));
        lists.add(new VeezList(1, 100, items, false, "ab"));
        lists.add(new VeezList(1, 100, items, false, "ab1"));
        lists.add(new VeezList(1, 100, items, false, "ab2"));
        lists.add(new VeezList(1, 100, items, false, "ab3"));
        lists.add(new VeezList(1, 100, items, false, "ab4"));
        lists.add(new VeezList(1, 100, items, false, "ab5"));
        lists.add(new VeezList(1, 100, items, false, "ab6"));
        MyAdapter adapter = new MyAdapter(lists);

        //reaplace to this in case of true use
        //   MyAdapter adapter = new MyAdapter(((MyApplication)getApplicationContext()).getUser().getLists());
        lv_my_lists.setAdapter(adapter);

        /* ----------- AutoComplete Search ----------------*/

        ArrayAdapter<String> adapterAutoComplete = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getListsName(lists));

        ac_search_list = (AutoCompleteTextView) findViewById(R.id.ac_search_list);
        ac_search_list.setAdapter(adapterAutoComplete);
        ac_search_list.setThreshold(1);
    }

    //TODO the parameter is only for debug
    String[] getListsName(List<VeezList> myLists) {
        List<String> res = new ArrayList<String>();

        // List<VeezList> myLists= ((MyApplication)getApplicationContext()).getUser().getLists();
        for (VeezList list : myLists)
            res.add(0, list.getName());
        String[] $ = new String[res.size()];
        return res.toArray($);
    }


    //----------------------- on click ----------------------------

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

    //----------------------- List ----------------------------

    private class MyAdapter extends BaseAdapter {

            private List<VeezList> myList;

            public MyAdapter(List<VeezList> aList) {
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
            public View getView(final int position, View convertView, ViewGroup parent) {

                View view;
                ViewHolder viewHolder;

                Log.d("MY_TAG", "Position: " + position);

                if (convertView == null) {
                    LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = li.inflate(R.layout.list_view_list, null);

                    viewHolder = new ViewHolder();
                    viewHolder.tv_num_likes = (TextView) view.findViewById(R.id.tv_num_likes);
                    viewHolder.tv_list_name = (TextView) view.findViewById(R.id.tv_list_name);
                    viewHolder.tv_curr_items = (TextView) view.findViewById(R.id.tv_curr_items);
                    viewHolder.tv_total_items = (TextView) view.findViewById(R.id.tv_total_items);
                    viewHolder.iv_lock = (ImageView) view.findViewById(R.id.im_lock);

                    view.setTag(viewHolder);
                } else {
                    view = convertView;

                    viewHolder = (ViewHolder) view.getTag();
                }

                // Put the content in the view
                viewHolder.tv_num_likes.setText(String.valueOf((myList.get(position)).getLikesCount()));
                viewHolder.tv_list_name.setText((myList.get(position)).getName());
                viewHolder.tv_curr_items.setText(String.valueOf((myList.get(position)).getNumOfItemsMarkedWithVee()));
                viewHolder.tv_total_items.setText(String.valueOf((myList.get(position)).getNumOfItems()));

                //viewHolder.myImage.setImageResource(imageId.get(position));
                if ((myList.get(position)).isPublic())
                    viewHolder.iv_lock.setVisibility(View.GONE);
                else
                    viewHolder.iv_lock.setVisibility(View.VISIBLE);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VeezList list = myList.get(position);
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                        intent.putExtra("list", list);
                        startActivity(intent);
                        finish();
                    }
                });

                return view;
            }

            //TODO next build add the field tv_list_friends
            //TODO handle the deadline
            private class ViewHolder {
                TextView tv_num_likes;
                TextView tv_list_name;
                ImageView iv_lock;
                TextView tv_curr_items;
                TextView tv_total_items;
            }
        }

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
                        // move to friends activity
                        //using button for debug
                        ParseUser parseUser = ParseUser.getCurrentUser();
                        if (parseUser != null){
                            SharedPreferences prefs = getSharedPreferences("tomer", MODE_PRIVATE);
                            String facebookID = (String) parseUser.get("facebookID");
                            String userJson = prefs.getString(facebookID, "");
                            Log.d("GSON",userJson);
                            Gson gson = new Gson();
                            VeezUser veezUser = gson.fromJson(userJson, VeezUser.class);
                            Log.d("Persistent", veezUser.getName());
                            Log.d("Persistent", veezUser.getFacebookID());
                        }
                        else{
                            Log.d("Persistent", "how to get user");
                        }

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
