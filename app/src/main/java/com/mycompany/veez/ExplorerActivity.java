package com.mycompany.veez;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class ExplorerActivity extends ActionBarActivity
        implements View.OnClickListener, NavigationDrawerCallbacks {
    
    private Button b_first_menu;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

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

        /* ------------------- Buttons ----------------------*/

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        /* ------------------- Auto Compelte ----------------------*/

        tv_explorer = (TextView) findViewById(R.id.tv_explorer);

        ArrayAdapter<String> adapterAutoComplete = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, TAGS);

        ac_search = (AutoCompleteTextView) findViewById(R.id.ac_search);

        ac_search.setAdapter(adapterAutoComplete);
        ac_search.setThreshold(1);

        ac_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchTags[tagsNum] = (String) ((TextView) view).getText();
                Log.d("EXPLORER", searchTags[tagsNum]);
                tagsNum++;
                ac_search.setText("");
                tv_explorer.setText(newHeadline(searchTags, tagsNum));
                tv_explorer.setTextSize(35 - 5 * tagsNum);
                if (tagsNum == 5) {
                    ac_search.setEnabled(false);
                    ac_search.setText("5 tags maximum");
                    Log.d("EXPLORER", "reached limit");
                }

            }
        });

        /* ------------------- Lists ----------------------*/
        lv_lists = (ListView) findViewById(R.id.lv_explorer_lists);
        List<VeezItem> items= new ArrayList<VeezItem>();
        List<VeezListExplorer> lists= new ArrayList<VeezListExplorer>();
        items.add(new VeezItem(("a")));
        lists.add(new VeezListExplorer(0,items,"abc"));
        items.add(new VeezItem(("b")));
        lists.add(new VeezListExplorer(1,items,"ab"));
        lists.add(new VeezListExplorer(1,items,"b1"));
        lists.add(new VeezListExplorer(1,items,"b2"));
        lists.add(new VeezListExplorer(1,items,"b3"));
        lists.add(new VeezListExplorer(1,items,"b4"));
        lists.add(new VeezListExplorer(1,items,"b5"));
        //TODO get the lists from the server
        MyAdapter adapter = new MyAdapter(lists);
        lv_lists.setAdapter(adapter);
    }

    /* --------------------------On Click ----------------------- */

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.b_first_menu) {
            mNavigationDrawerFragment.openDrawer();
        }
    }



    //----------------------- List ----------------------------

    private class MyAdapter extends BaseAdapter {

        private List<VeezListExplorer> myList;

        public MyAdapter(List<VeezListExplorer> aList) {
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
            viewHolder.tv_num_likes.setText(String.valueOf((myList.get(position)).getLikesCount()));
            viewHolder.tv_list_name.setText((myList.get(position)).getName());

            return view;
        }

        private class ViewHolder {
            TextView tv_num_likes;
            TextView tv_list_name;
            Button b_add_to_my_lists;
        }
    }

    /* --------------------------Auto Complete ----------------------- */
    private static String newHeadline(String[] tags, int tagsNum) {
        String headline = "";
        headline += tags[0];
        for (int i = 1; i < tagsNum; i++) {
            headline += " + " + tags[i];
        }
        return headline;
    }

    private static final String[] TAGS = new String[]{
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