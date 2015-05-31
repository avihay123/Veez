package com.mycompany.veez;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.content.res.Configuration;
import java.util.ArrayList;

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
    private Integer currentItmes=0;
    private Integer TotalItems=0;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //buttons code------------------------------------------------------------------------------
        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        b_second_menu = (Button) findViewById(R.id.b_second_menu);
        b_second_menu.setOnClickListener(this);

        b_add_item = (Button) findViewById(R.id.b_add_item);
        b_add_item.setOnClickListener(this);

        //TextView code
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
        /* -----------------------------------------*/

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);
    }

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
//
//        if (viewId == R.id.b_add_tag) {
//            //TODO
//        }
//
//        if (viewId == R.id.b_leave_list) {
//            //TODO delete the list from the user
//
//            Intent intent = new Intent(getApplicationContext(), MyListsActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }
    /* ----------------- Menu function ------------------- */
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
