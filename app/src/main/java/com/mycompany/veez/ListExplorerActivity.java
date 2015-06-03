package com.mycompany.veez;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 6/3/2015.
 */
public class ListExplorerActivity extends Activity {

    private Button b_first_menu;
    private Button b_second_menu;
    private TextView tv_curr_items;
    private TextView tv_total_items;
    private TextView tv_likes;
    private EditText ac_search_item;
    private Button b_add_item;
    private ListView lv_list_items;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        VeezListExplorer receivedList = (VeezListExplorer) getIntent().getSerializableExtra("list");

        lv_list_items = (ListView) findViewById(R.id.lv_list_items);
//        MyAdapter adapter = new MyAdapter(receivedList.getItems());
//
//        lv_list_items.setAdapter(adapter);
    }


//    private class MyAdapter extends BaseAdapter {
//
//        private List<VeezItem> allItems;
//        private List<VeezItem> itemsToShow;
//
//        public MyAdapter(List<VeezItem> aList) {
//            allItems = new ArrayList<VeezItem>(aList);
//            itemsToShow = new ArrayList<VeezItem>(aList);
//        }
//
//        @Override
//        public int getCount() {
//            return itemsToShow.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return itemsToShow.get(position);
//        }
//
//        //TODO return
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            View view;
//            final ViewHolder viewHolder;
//
//            Log.d("MY_TAG", "Position: " + position);
//
//            if (convertView == null) {
//                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = li.inflate(R.layout.list_view_element, null);
//
//                viewHolder = new ViewHolder();
//                viewHolder.cb_item_vee = (CheckBox) view.findViewById(R.id.cb_item_vee);
//                viewHolder.tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
//                viewHolder.b_info = (Button) view.findViewById(R.id.b_info);
//                viewHolder.b_friend_veed = (Button) view.findViewById(R.id.b_friend);
//                view.setTag(viewHolder);
//
//            } else {
//                view = convertView;
//                viewHolder = (ViewHolder) view.getTag();
//            }
//
//            // add info code
//            viewHolder.b_info.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//
//                    // get prompts.xml view
//                    LayoutInflater li = LayoutInflater.from(context);
//                    View addItemView = li.inflate(R.layout.activity_item_info, null);
//
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                            context);
//
//                    // set prompts.xml to alert dialog builder
//                    alertDialogBuilder.setView(addItemView);
//
//                    final EditText new_info = (EditText) addItemView.findViewById(R.id.et_info2);
//                    Button b_add_info_dialog = (Button) addItemView.findViewById(R.id.b_add_info_dialog);
//
//                    String currentInfo = itemsToShow.get(position).getInfo();
//                    if (currentInfo.length() > 0) {
//                        new_info.setText(currentInfo);
//                    } else {
//                        new_info.setText("No information for this item.");
//                    }
//
//                    // create alert dialog
//                    final AlertDialog alertDialog = alertDialogBuilder.create();
//                    // show it
//                    alertDialog.show();
//
//                    b_add_info_dialog.setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View v) {
//                            itemsToShow.get(position).setInfo(new_info.getText().toString());
//                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(new_info.getWindowToken(), 0);
//                            alertDialog.cancel();
//                        }
//                    });
//                }
//            });
//
//            // Put the content in the view
//            viewHolder.tv_item_name.setText(itemsToShow.get(position).getName());
//
//            // checkbox code
//            if (itemsToShow.get(position).isVee()) {
//                viewHolder.cb_item_vee.setChecked(true);
//                //TODO set the photo of the
//                viewHolder.b_friend_veed.setVisibility(View.VISIBLE);
//            } else {
//                viewHolder.cb_item_vee.setChecked(false);
//                viewHolder.b_friend_veed.setVisibility(View.INVISIBLE);
//            }
//
//            viewHolder.cb_item_vee.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg) {
//                    if (viewHolder.cb_item_vee.isChecked() == true) {
//                        //TODO set the photo of the
//                        //viewHolder.b_friend_veed.setImageBitmap();
//
//                        viewHolder.b_friend_veed.setVisibility(View.VISIBLE);
//                        ListActivity.this.currentItmes++;
//                        ListActivity.this.tv_curr_items.setText(ListActivity.this.currentItmes.toString());
//                    } else {
//                        viewHolder.b_friend_veed.setVisibility(View.INVISIBLE);
//                        ListActivity.this.currentItmes--;
//                        ListActivity.this.tv_curr_items.setText(ListActivity.this.currentItmes.toString());
//                    }
//                    VeezItem new_item = itemsToShow.get(position);
//                    new_item.setVee(viewHolder.cb_item_vee.isChecked());
//                    items.remove(itemsToShow.get(position));
//                    items.add(new_item);
//                    sortItems(items);
//                    MyAdapter.this.notifyDataSetChanged();
//                }
//            });
//
//
//
//            return view;
//        }
//  }

}