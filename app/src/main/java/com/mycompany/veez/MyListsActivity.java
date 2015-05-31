package com.mycompany.veez;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mycompany.veez.NavigationDrawerCallbacks;
import com.mycompany.veez.NavigationDrawerFragment;
import com.mycompany.veez.StatsFragment;

public class MyListsActivity extends ActionBarActivity
        implements View.OnClickListener, NavigationDrawerCallbacks {

    private Button b_first_menu;
    private Button b_second_menu;
    private Button b_add_list;
    private EditText et_search;
    private ListView lv_my_lists;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

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
                }
                else{
                    l_activityHeaderView.setVisibility(View.VISIBLE);
                }
            }
        });
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
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0: // my lists //
                break;
            case 1: // explorer //
                Intent intent = new Intent(getApplicationContext(), ExplorerActivity.class);
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

    /* ------------------------------------------------- */


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.b_first_menu) {
            mNavigationDrawerFragment.openDrawer();
        }
        if (viewId == R.id.b_add_list) {
            Intent intent = new Intent(getApplicationContext(), CreateListActivity.class);
            startActivity(intent);
        }
    }

}
