package com.mycompany.veez;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.parse.LogInCallback;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;



public class LoginActivity extends Activity {

    //    private Button b_login;
    private Button b_login;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private ParseUser user;
    private boolean startedActivity = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("PB", "Token changed");
                user = ParseUser.getCurrentUser();
                if (user != null){
                    GraphRequest fbRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                new BackgroundMakeVeezUser().execute(jsonObject);
                        }
                    });
                    fbRequest.executeAsync();
                }
                updateWithToken(currentAccessToken);
            }
        };


        b_login = (Button) findViewById(R.id.b_login);
        callbackManager = CallbackManager.Factory.create();



        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, Arrays.asList("user_friends"), new LogInCallback() {
                    @Override
                    public void done(final ParseUser user, com.parse.ParseException err) {

                        if (user == null) {
                            Log.d("PB", "Uh oh. The user cancelled the Facebook login.");
                            return;
                        } else if (user.isNew()) {
                            GraphRequest fbRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                    new BackgroundMakeVeezUser().execute(jsonObject);
                                }
                            });
                            fbRequest.executeAsync();
                            Log.d("PB", "User signed up and logged in through Facebook!");
                        } else {
                            Log.d("PB", "User logged in through Facebook!");
                        }
                        //link to parse user
                        if (!ParseFacebookUtils.isLinked(user)) {
                            Log.d("PB", "linking");
                            ParseFacebookUtils.linkWithReadPermissionsInBackground(user, LoginActivity.this, Arrays.asList("user_friends"), new SaveCallback() {
                                @Override
                                public void done(com.parse.ParseException ex) {
                                    if (ParseFacebookUtils.isLinked(user)) {
                                        Log.d("MyApp", "Woohoo, user logged in with Facebook!");
                                    }
                                }
                            });
                        }else{
                            Log.d("PB", "already linked");
                        }

                    }
                });


            }
        });
//        if (ParseUser.getCurrentUser() != null) {
//            if (ParseFacebookUtils.isLinked(ParseUser.getCurrentUser())) {
//                startedActivity = true;
//                Intent intent = new Intent(getApplicationContext(), MyListsActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }
    }



    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null && startedActivity == false) {
            startedActivity = true;
            Intent intent = new Intent(getApplicationContext(), MyListsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private class BackgroundMakeVeezUser extends AsyncTask<JSONObject, Void, VeezUser> {

        @Override
        protected VeezUser doInBackground(JSONObject... params) {
            JSONObject jsonObject = params[0];

            URL imageURL = null;
            try {
                String userID = jsonObject.getString("id");
                imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
                Log.d("PBPic", "url created");
            } catch (MalformedURLException e) {
                Log.d("PB", "exception thrown while trying to create url of profile picture");
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                Log.d("PB", "exception thrown while trying to deserialize json object");
                e.printStackTrace();
            }
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                Log.d("PBPic", "bitmap created");
            } catch (IOException e) {
                Log.d("PB", "exception thrown while trying to decode data stream from url of profile picture");
                e.printStackTrace();
            } catch (android.os.NetworkOnMainThreadException e){
                Log.d("PB", "i'm executin async, why is this thrown??"); //TODO
            }
            VeezUser result = null;
            try {
                result = new VeezUser(jsonObject.getString("name"), BitMapToString(bitmap), jsonObject.getString("id"));
            } catch (JSONException e) {
                Log.d("PB", "exception thrown while trying to deserialize json object");
                e.printStackTrace();
                return null;
            }

            return result;
        }

        public String BitMapToString(Bitmap bitmap) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        }

        @Override
        protected void onPostExecute(VeezUser vUser){
            if (vUser == null){
                Log.d("PB", "nal");
                return;
            }
            //add new user data to persistent memory
            SharedPreferences  mPrefs = getSharedPreferences("tomer",MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(vUser);
            prefsEditor.putString(vUser.getFacebookID(), json);
            prefsEditor.commit();

            //add user data to parse user
            ParseUser.getCurrentUser().put("facebookID", vUser.getFacebookID());
            ParseUser.getCurrentUser().put(vUser.getFacebookID(), json);
        }
    }
}

