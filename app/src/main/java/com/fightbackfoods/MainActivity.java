package com.fightbackfoods;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.fightbackfoods.model.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private long lastPressed = Calendar.getInstance().getTimeInMillis();
    private final long BACK_PRESSED_EXIT_THRESHOLD = 3000; //in millis


    RoundedImageView ivAvatar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initDrawer();
    }

    private void init() {
        Api.initialized(getApplicationContext());

        if (Profile.getCurrentProfile() == null && User.getCurrentUser().getUserId() == 0) {
            Intent intent
                    = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }


    }

    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        User user =  User.getCurrentUser();
        ViewGroup navHeader = (ViewGroup) navigationView.getHeaderView(0);
        ivAvatar =  navHeader.findViewById(R.id.iv_avatar);
        String avatarUrl = user.getAvatar();
        Glide.with(getApplicationContext()).load(avatarUrl).into(ivAvatar);
        TextView tvName= navHeader.findViewById(R.id.tv_nav_title);

        TextView tvSubName= navHeader.findViewById(R.id.tv_nav_subTitle);
        tvName.setText(user.getSimpleName());
        tvSubName.setText(user.getEmail());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:

                    return true;
                case R.id.nav_food:

                    return true;
                case R.id.nav_lifestyle:

                    return true;

                case R.id.nav_community:

                    return true;
            }
            return false;
        }
    };


    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    void openenDrawer()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
            else{

                long now = Calendar.getInstance().getTimeInMillis();
                if(now - lastPressed < BACK_PRESSED_EXIT_THRESHOLD){
                    super.onBackPressed();
                    return;
                }
                lastPressed = now;
                Toast.makeText(MainActivity.this, R.string.prompt_double_back_exit, Toast.LENGTH_LONG).show();
            }

        }
    }
    void closeDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /**  reload the webview
     *
     */
    public void reloadPage() {

        if(!isConnectedOrConnecting(MainActivity.this)){
            showDialog("require_internet");

            //showProgress(btnRefresh,true);
            return;
        }
        // loadPage(current_url==null? BASE_URL:current_url);
    }

    public static boolean isConnectedOrConnecting(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /** Show simple alert box
     *
     * @param msg Message to display
     */
    public void showDialog(String msg) {
        switch (msg){
            case "require_internet":
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Require internet");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //show(btnRefresh, true);
                            }
                        });
                alertDialog.show();
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_food_nutrient_report) {

        } else if (id == R.id.nav_goals) {
        } else if (id == R.id.nav_food) {
        } else if (id == R.id.nav_activity) {

        } else if (id == R.id.nav_saved) {
        } else if (id == R.id.nav_inbox) {
        } else if (id == R.id.nav_trending) {

        } else if (id == R.id.nav_profile_settings) {
        } else if (id == R.id.nav_account_settings) {
        } else if (id == R.id.nav_contact_us) {

        }else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {

        User.clearPref();
        //User.setCurrentUser(null);
        /*recreate();
            *//*
            startActivity(getIntent());
            finish();

*/

        LoginManager.getInstance().logOut();
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        //closeDrawer();

        startActivity(login);
        finish();
    }
}
