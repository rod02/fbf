package com.fightbackfoods.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.model.User;
import com.fightbackfoods.utils.CircleTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.BindDimen;
import butterknife.BindString;


public class BaseDrawerActivity extends BaseActivity {

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.vNavigation)
    NavigationView vNavigation;

    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;
    @BindString(R.string.user_profile_photo)
    String profilePhoto;

    //Cannot be bound via Butterknife, hosting view is initialized later (see setupHeader() method)
    private ImageView ivMenuUserProfilePhoto;
    private TextView tvUserTitle;
    private TextView tvUserSubtitle;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        bindViews();
        setupHeader();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    private void setupHeader() {

        User user = User.getCurrentUser();
        String avatarUrl = user.getAvatar();
        View headerView = vNavigation.getHeaderView(0);
        ivMenuUserProfilePhoto = (ImageView) headerView.findViewById(R.id.ivMenuUserProfilePhoto);
        tvUserTitle = (TextView) headerView.findViewById(R.id.tv_menu_user_title);
        tvUserSubtitle = (TextView) headerView.findViewById(R.id.tv_menu_user_subtitle);
        tvUserTitle.setText(user.getSimpleName());
        tvUserSubtitle.setText(user.getEmail());
        headerView.findViewById(R.id.vGlobalMenuHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGlobalMenuHeaderClick(v);
            }
        });

        setProfilePhoto(avatarUrl);
    }
    private void setProfilePhoto(String url){
       // ImageView profileImage = (ImageView) binding.navView.getHeaderView(0).findViewById(R.id.iv_profile_image);

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ivMenuUserProfilePhoto.setImageBitmap(bitmap);
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                getToolbar().setNavigationIcon(drawable);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

      //  profileImage.setTag(target);

      /*  Picasso.with(this)
                .load("YOUR_IMAGE_URL_HERE")
                .placeholder(R.drawable.placeholder_profile)
                .error(R.drawable.placeholder_profile)
                .into(target);*/
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(target);
    }

    public void onGlobalMenuHeaderClick(final View v) {
        drawerLayout.closeDrawer(Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                overridePendingTransition(0, 0);
            }
        }, 200);
    }


    void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                //super.onBackPressed();
                finish();
/*
                long now = Calendar.getInstance().getTimeInMillis();
                if (now - lastPressed < BACK_PRESSED_EXIT_THRESHOLD) {
                    super.onBackPressed();
                    return;
                }
                lastPressed = now;
                Toast.makeText(MainActivity.this, R.string.prompt_double_back_exit, Toast.LENGTH_LONG).show();*/
            }

        }
    }

}



