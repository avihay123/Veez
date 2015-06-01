package com.mycompany.veez;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;



public class LoginActivity extends Activity {

    //    private Button b_login;
    private LoginButton loginButton;
    private ImageView iv_logo;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private ParseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iv_logo = (ImageView) findViewById(R.id.iv_logo);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("PB", "Token changed");
                user = ParseUser.getCurrentUser();
                if (user != null){
                    Log.d("PB",user.getUsername());
                    GraphRequest fbRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                            try {
                                new BackgroundProfilePictureFetcher().execute(jsonObject.getString("id"));
                            } catch (JSONException e) {
                                Log.d("PB","error at parsing the json object");
                                e.printStackTrace();
                            }
                        }
                    });
                    fbRequest.executeAsync();
                }
                updateWithToken(currentAccessToken);
            }
        };


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        callbackManager = CallbackManager.Factory.create();



        // Other app specific specialization
        //TODO WHAT ARE LEGITIMATE PERMISSION VALUES??????????????
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, Arrays.asList("user_friends"), new LogInCallback() {
            @Override
            public void done(final ParseUser user, com.parse.ParseException err) {

                if (user == null) {
                    Log.d("PB", "Uh oh. The user cancelled the Facebook login.");
                    return;
                } else if (user.isNew()) {
                    Log.d("PB", "User signed up and logged in through Facebook!");
                } else {
                    Log.d("PB", "User logged in through Facebook!");
                }
                //link to parseuser
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



    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
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

    public static Bitmap getFacebookProfilePicture(String userID){
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
            Log.d("PBPic", "url created");
        } catch (MalformedURLException e) {
            Log.d("PB", "exception thrown while trying to create url of profile picture");
            e.printStackTrace();
            return null;
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

        return bitmap;
    }


    private class BackgroundProfilePictureFetcher extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String userID = params[0];
            URL imageURL = null;
            try {
                imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
                Log.d("PBPic", "url created");
            } catch (MalformedURLException e) {
                Log.d("PB", "exception thrown while trying to create url of profile picture");
                e.printStackTrace();
                return null;
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

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            iv_logo.setImageBitmap(bitmap);
        }
    }
}

