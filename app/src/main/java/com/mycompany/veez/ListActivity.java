package com.mycompany.veez;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class ListActivity extends ActionBarActivity
        implements View.OnClickListener, NavigationDrawerCallbacks {
    
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

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

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
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        //Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("John Doe", "13 active lists",
                BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
        mNavigationDrawerFragment.closeDrawer();
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
            mNavigationDrawerFragment.openDrawer();
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
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Intent intent;
        switch (position) {
            case 0: // my lists //
                intent = new Intent(getApplicationContext(), MyListsActivity.class);
                startActivity(intent);
                break;
            case 1: // explorer //
                intent = new Intent(getApplicationContext(), ExplorerActivity.class);
                startActivity(intent);
                break;
            case 2: // friends //
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }
}
