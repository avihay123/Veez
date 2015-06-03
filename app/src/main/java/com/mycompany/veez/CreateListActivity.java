package com.mycompany.veez;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.res.Configuration;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

public class CreateListActivity extends ActionBarActivity implements View.OnClickListener {

    private static final int REQUEST_CAMERA = 34;
    private static final int SELECT_FILE = 79;
    private Button b_first_menu;
    private ImageView b_add_image;
    private Button b_add_friend;
    private Button b_add_tag;
    private EditText et_list_name;
    private EditText et_deadline;
    private CheckBox cb_private;
    private CheckBox cb_public;
    private Button b_create_list;
    private Calendar myCalendar;
    private RelativeLayout rl_image_change;
    private TextView tv_tags;
    private Button b_remove_tag;
    private ImageView im_photo;
    private Bitmap bmp_listPhoto = null;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<String> tags = new ArrayList<String>();
    private VeezUser veezUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        /*---------------- Get Users bitmap--------------*/
        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser != null) {
            SharedPreferences prefs = getSharedPreferences("tomer", MODE_PRIVATE);
            String facebookID = (String) parseUser.get("facebookID");
            String userJson = prefs.getString(facebookID, "");
            Log.d("GSON", userJson);
            Gson gson = new Gson();
            veezUser = gson.fromJson(userJson, VeezUser.class);
            if (veezUser == null)
                Log.d("Persistent", "WTF");
            Log.d("Persistent", veezUser.getName());
            Log.d("Persistent", veezUser.getFacebookID());
            if (veezUser == null)
                Log.d("Persistent", "WTF2");
            Log.d("Persistent",veezUser.getProfilePicture());
            Bitmap bm = StringToBitMap(veezUser.getProfilePicture());
           // Bitmap bm = new Gson().fromJson(veezUser.getProfilePicture(), Bitmap.class);
            Log.d("Persistent","1");
            ImageView im_photo = (ImageView) findViewById(R.id.im_photo);
            im_photo.setImageBitmap(getRoundedShape(bm));
        } else {
            Log.d("Persistent", "how to get user");
        }



        /* -------------- Side Menu ---------------- */
        mDrawerList = (ListView) findViewById(R.id.lv_navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        /* -------------------Buttons----------------------*/
        b_first_menu = (Button) findViewById(R.id.b_first_menu);
        b_first_menu.setOnClickListener(this);

        b_add_image = (ImageView) findViewById(R.id.b_add_image);
        b_add_image.setOnClickListener(this);
        //TODO next iteration care about this
        b_add_image.setVisibility(View.INVISIBLE);

        b_add_friend = (Button) findViewById(R.id.b_add_friend);
        b_add_friend.setOnClickListener(this);

        b_add_tag = (Button) findViewById(R.id.b_add_tag);
        b_add_tag.setOnClickListener(this);
    //    b_add_tag.setHeight(b_add_tag.getWidth());

        b_create_list = (Button) findViewById(R.id.b_create_list);
        b_create_list.setOnClickListener(this);

        rl_image_change = (RelativeLayout) findViewById(R.id.rl_image_change);
        rl_image_change.setOnClickListener(this);

        b_remove_tag = (Button) findViewById(R.id.b_remove_tag);
        b_remove_tag.setOnClickListener(this);
     //   b_remove_tag.setHeight(b_remove_tag.getWidth());

        /* -------------- Deadline ---------------- */
        et_deadline = (EditText) findViewById(R.id.et_deadline);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (new Date(myCalendar.getTimeInMillis() + 86400000).before((new Date()))) {
                    {
                        //kv Make them try again
                        et_deadline.performClick();
                        Toast.makeText(getApplicationContext(), "Invalid date, please try again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                et_deadline.setText(sdf.format(myCalendar.getTime()));
            }
        };

        et_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateListActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });
        /* -------------- CheckBoxes ---------------- */
        cb_private = (CheckBox) findViewById(R.id.cb_private);
        cb_public = (CheckBox) findViewById(R.id.cb_public);

        cb_private.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_public.setChecked(!isChecked);
            }
        });

        cb_public.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_private.setChecked(!isChecked);
            }
        });

        cb_public.setChecked(true);

        /* -------------- EditText -------------------*/
        et_list_name = (EditText) findViewById(R.id.et_list_name);
        if (et_list_name.getText().length() > 0) {
            et_list_name.setHint("");
        }

        /* -------------- TextView -------------------*/
        tv_tags = (TextView) findViewById(R.id.tv_tags);

        if (savedInstanceState != null) {
            String tagsString = savedInstanceState.getString("tv_tags");
            tv_tags.setText(tagsString);
            String bitMapString = savedInstanceState.getString("rl_image_change");
            if (bitMapString != null) {
                Bitmap bm = StringToBitMap(bitMapString);
                Drawable dr = new BitmapDrawable(bm);
                rl_image_change.setBackgroundDrawable(dr);

            }
            if (et_list_name.getText().length() > 0) {
                et_list_name.setHint("");
                et_list_name.setText(et_list_name.getText());
            }
        }
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.b_first_menu) {

            Log.d("matan", "button click");
            mDrawerLayout.openDrawer(Gravity.START);

        } else if (viewId == R.id.b_add_friend) {

            //TODO next build
        } else if (viewId == R.id.b_add_tag || viewId == R.id.b_remove_tag) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Enter Tag");

            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    if (viewId == R.id.b_add_tag)
                        addTag(value);
                    else
                        removeTag(value);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alert.show();

        } else if (viewId == R.id.rl_image_change) {
            Log.d("matan", "change image click");
            selectImage();
        } else if (viewId == R.id.b_create_list) {

            if (et_list_name.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "You have to choose list name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (et_deadline.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "You have to choose deadline", Toast.LENGTH_SHORT).show();
                return;
            }

            //VeezList newList= new VeezList(et_list_name.getText(),cb_public.isChecked(),new ArrayList<VeezUser>().add(veezUser), veezUser, tags);
            VeezList newList = null;
            if(bmp_listPhoto!=null)
                  newList= new VeezList(et_list_name.getText().toString(),cb_public.isChecked(),null, null, tags, BitMapToString(bmp_listPhoto));
            else
                 newList= new VeezList(et_list_name.getText().toString(),cb_public.isChecked(),null, null, tags, null);
            //TODO add to userVeez and to the server!!!!!
            //TODO jump to the list
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("listToShow", newList);
            intent.putExtra("userPhoto",veezUser.getProfilePicture());
            startActivity(intent);
            finish();
        }
    }

    //----------------------------- Take a photo ------------------------------
    private void selectImage() {
//        final CharSequence[] items = { "Take Photo", "Choose from Library",
//                "Cancel" };

        final CharSequence[] items = {"Take Photo", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bmp_listPhoto = thumbnail;
                Drawable dr = new BitmapDrawable(thumbnail);
                rl_image_change.setBackgroundDrawable(dr);
                b_add_image.setVisibility(View.INVISIBLE);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                Bitmap bm = BitmapFactory.decodeFile(selectedImagePath, options);

                bmp_listPhoto = bm;

                Drawable dr = new BitmapDrawable(bm);
                rl_image_change.setBackgroundDrawable(dr);
                b_add_image.setVisibility(View.INVISIBLE);
            }
        }

    }
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 125;
        int targetHeight = 125;

        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(
                ((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(
                sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap
                        .getHeight()), new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }

    private void addTag(String tag) {
        if (tags.contains(tag)) {
            Toast.makeText(getApplicationContext(), "This tag is already included", Toast.LENGTH_SHORT).show();
        } else {
            tags.add(tag);
            String newTagsString = makeTagsString();
            tv_tags.setText(newTagsString);
        }
    }

    private void removeTag(String tag) {
        if (!tags.contains(tag)) {
            Toast.makeText(getApplicationContext(), "This tag is not include in tags", Toast.LENGTH_SHORT).show();
        } else {
            tags.remove(tag);
            String newTagsString = makeTagsString();
            tv_tags.setText(newTagsString);
        }
    }

    private String makeTagsString() {
        String res = "";
        for (String tag : tags) {
            res = res + tag + ",";
        }
        if (res.length() > 1 && res.charAt(res.length() - 1) == ',') {
            res = res.substring(0, res.length() - 1);
        }
        return res;
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

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tv_tags", tv_tags.getText().toString());
        outState.putString("rl_image_change", BitMapToString(((BitmapDrawable) (rl_image_change.getBackground())).getBitmap()));

    }


    /* ----------------- Menu functions ------------------- */

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

        et_deadline.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                et_deadline.setHint("");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        et_list_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                et_list_name.setHint("");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);

        if (et_list_name.getText().length() > 0) {
            et_list_name.setHint("");
        }
    }

}