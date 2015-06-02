package com.mycompany.veez;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.content.Context;
import android.view.View.OnClickListener;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.content.res.Configuration;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    final Context context = this;

    private List<VeezItem> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /* -------------- List View ---------------- */
        lv_list_items = (ListView) findViewById(R.id.lv_list_items);

        items = new ArrayList<VeezItem>();
        items.add(new VeezItem("Coke","",false));
        items.add(new VeezItem("Beer","6 pack",true));
        items.add(new VeezItem("Wine","",false));
        items.add(new VeezItem("Steake","3 pieces",false));
        items.add(new VeezItem("Water","asd",true));
        items.add(new VeezItem("Chicken","",false));
        items.add(new VeezItem("Plates","",true));
        sortItems(items);
        MyAdapter adapter = new MyAdapter(items);

        lv_list_items.setAdapter(adapter);

        //-------------------------------- buttons code ----------------------------------
        b_second_menu = (Button) findViewById(R.id.b_second_menu);
        b_second_menu.setOnClickListener(this);

        b_add_item = (Button) findViewById(R.id.b_add_item);
        b_add_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View addItemView = li.inflate(R.layout.activity_add_item, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alert dialog builder
                alertDialogBuilder.setView(addItemView);

                final EditText item = (EditText) addItemView.findViewById(R.id.et_item);
                final EditText info = (EditText) addItemView.findViewById(R.id.et_info);
                Button b_add_item_dialog = (Button) addItemView.findViewById(R.id.b_add_item_dialog);

                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

                b_add_item_dialog.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(item.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "You must enter item name!", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean isAlreadyExists = false;
                            for (int i=0; i < items.size(); i++) {
                                if(items.get(i).getName().equals(item.getText().toString()))
                                    isAlreadyExists = true;
                            }
                            if (isAlreadyExists == true) {
                                Toast.makeText(getApplicationContext(), "This item is already exists!", Toast.LENGTH_SHORT).show();
                            } else {
                                TotalItems++;
                                tv_total_items.setText(TotalItems.toString());
                                items.add(new VeezItem(item.getText().toString(), info.getText().toString(), false));
                                sortItems(items);
                                ((BaseAdapter) lv_list_items.getAdapter()).notifyDataSetChanged();

                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(item.getWindowToken(), 0);
                                alertDialog.cancel();
                            }
                        }
                    }
                });
            }
        });

        //-------------------------------- TextView code --------------------------------
        tv_likes = (TextView) findViewById(R.id.tv_likes);
        tv_curr_items = (TextView) findViewById(R.id.tv_curr_items);
        tv_curr_items.setText(currentItmes.toString());
        tv_total_items = (TextView) findViewById(R.id.tv_total_items);
        if (items != null) {
            TotalItems = items.size();
        }
        tv_total_items.setText(TotalItems.toString());

        /* -------------- Side Menu ---------------- */
        mDrawerList = (ListView) findViewById(R.id.lv_navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

    }

    private void sortItems(List<VeezItem> items) {
        Collections.sort(items, new Comparator<VeezItem>() {
            public int compare(VeezItem item1, VeezItem item2) {
                int res = (item1.isVee()? 1 : 0) - (item2.isVee()? 1 : 0);
                if (res == 0) {
                    res = item1.getName().compareTo(item2.getName());
                }
                return res;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.b_first_menu) {
            mDrawerLayout.openDrawer(Gravity.START);
        }
        if (viewId == R.id.b_second_menu) {

            LayoutInflater li = LayoutInflater.from(context);
            View addItemView = li.inflate(R.layout.second_menu, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set prompts.xml to alert dialog builder
            alertDialogBuilder.setView(addItemView);

            Button b_list_info = (Button) addItemView.findViewById(R.id.b_menu_list_info);
            Button b_add_friend = (Button) addItemView.findViewById(R.id.b_menu_add_friend);
            Button b_leave_list = (Button) addItemView.findViewById(R.id.b_menu_leave_list);

            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();

            wmlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wmlp.x = -65;
            wmlp.y = -65;

            // show it
            alertDialog.show();
            alertDialog.getWindow().setLayout(190, 235);

            b_list_info.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ListInfoActivity.class);
                    startActivity(intent);
                }
            });

            b_add_friend.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO - next build
                }
            });

            b_leave_list.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO leave list and remove it
                    Intent intent = new Intent(getApplicationContext(), MyListsActivity.class);
                    startActivity(intent);
                }
            });

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
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view;
            final ViewHolder viewHolder;

            Log.d("MY_TAG", "Position: " + position);

            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.list_view_element, null);

                viewHolder = new ViewHolder();
                viewHolder.cb_item_vee = (CheckBox) view.findViewById(R.id.cb_item_vee);
                viewHolder.tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
                viewHolder.b_info = (Button) view.findViewById(R.id.b_info);
                viewHolder.b_friend_veed = (Button) view.findViewById(R.id.b_friend);

                view.setTag(viewHolder);
            } else {
                view = convertView;

                viewHolder = (ViewHolder) view.getTag();
            }

            // add info code
            viewHolder.b_info.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(context);
                    View addItemView = li.inflate(R.layout.activity_item_info, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set prompts.xml to alert dialog builder
                    alertDialogBuilder.setView(addItemView);

                    final EditText new_info = (EditText) addItemView.findViewById(R.id.et_info2);
                    Button b_add_info_dialog = (Button) addItemView.findViewById(R.id.b_add_info_dialog);

                    String currentInfo = myList.get(position).getInfo();
                    if (currentInfo.length() > 0) {
                        new_info.setText(currentInfo);
                    } else {
                        new_info.setText("No information for this item.");
                    }

                    // create alert dialog
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();

                    b_add_info_dialog.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            myList.get(position).setInfo(new_info.getText().toString());
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(new_info.getWindowToken(), 0);
                            alertDialog.cancel();
                        }
                    });
                }
            });

            // Put the content in the view
            viewHolder.tv_item_name.setText((myList.get(position)).getName());

            // checkbox code
            if ((myList.get(position)).isVee()) {
                viewHolder.cb_item_vee.setChecked(true);
                //TODO set the photo of the
                viewHolder.b_friend_veed.setVisibility(View.VISIBLE);
            } else {
                viewHolder.cb_item_vee.setChecked(false);
                viewHolder.b_friend_veed.setVisibility(View.INVISIBLE);
            }

            viewHolder.cb_item_vee.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg) {
                    if (viewHolder.cb_item_vee.isChecked() == true) {
                        //TODO set the photo of the
                        //viewHolder.b_friend_veed.setImageBitmap();

                        viewHolder.b_friend_veed.setVisibility(View.VISIBLE);
                        ListActivity.this.currentItmes++;
                        ListActivity.this.tv_curr_items.setText(ListActivity.this.currentItmes.toString());
                    } else {
                        viewHolder.b_friend_veed.setVisibility(View.INVISIBLE);
                        ListActivity.this.currentItmes--;
                        ListActivity.this.tv_curr_items.setText(ListActivity.this.currentItmes.toString());
                    }
                    VeezItem new_item = myList.get(position);
                    new_item.setVee(viewHolder.cb_item_vee.isChecked());
                    items.remove(myList.get(position));
                    items.add(new_item);
                    sortItems(items);
                    MyAdapter.this.notifyDataSetChanged();
                }
            });

            /* if ((myList.get(position)).getInfo().length() > 0) {
                viewHolder.b_info.setVisibility(View.VISIBLE);
            } else {
                viewHolder.b_info.setVisibility(View.INVISIBLE);
            } */

            return view;
        }

        private class ViewHolder {
            CheckBox cb_item_vee;
            TextView tv_item_name;
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
