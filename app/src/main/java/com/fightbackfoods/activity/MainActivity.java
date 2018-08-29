package com.fightbackfoods.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.transition.Slide;
import android.support.transition.Visibility;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.fightbackfoods.Api;
import com.fightbackfoods.R;
import com.fightbackfoods.Utils;
import com.fightbackfoods.adapter.FeedAdapter;
import com.fightbackfoods.adapter.OnItemClick;
import com.fightbackfoods.api.ResponseJournal;
import com.fightbackfoods.interfaces.OnFragmentInteractionListener;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.Article;
import com.fightbackfoods.model.Banner;
import com.fightbackfoods.model.Journal;
import com.fightbackfoods.model.JournalSuggestion;
import com.fightbackfoods.model.User;
import com.fightbackfoods.model.UserDiet;
import com.fightbackfoods.utils.TokenManager;
import com.fightbackfoods.utils.Validate;
import com.fightbackfoods.view.ArticleFeatured;
import com.fightbackfoods.view.FeedContextMenu;
import com.fightbackfoods.view.FeedContextMenuManager;
import com.fightbackfoods.view.ImFeelingDialogFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseDrawerActivity implements FeedAdapter.OnFeedItemClickListener,
        FeedContextMenu.OnFeedContextMenuItemClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener,
        SerializableListener ,
        OnItemClick<UserDiet>, View.OnClickListener
{


    private static final String TAG = MainActivity.class.getSimpleName();
    private long lastPressed = Calendar.getInstance().getTimeInMillis();
    private final long BACK_PRESSED_EXIT_THRESHOLD = 3000; //in millis
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    @BindView(R.id.btnCreate)
    FloatingActionButton fabCreate;
    @BindView(R.id.content)
    CoordinatorLayout clContent;
    @BindView(R.id.content_main)
    ConstraintLayout contentMain;

    @BindView(R.id.tv_toolbar_face)
    TextView tvToolbarFace;
    @BindView(R.id.iv_toolbar_face)
    ImageView ivToolbarFace;
    @BindView(R.id.ll_toolbar_face)
    LinearLayout llToolbarFace;

    private FeedAdapter feedAdapter;

    private boolean pendingIntroAnimation;

    RoundedImageView ivAvatar;

    private boolean showJournalSuggestions = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        // initDrawer();*/
        // setupFeed();


        if (savedInstanceState == null && Validate.isLoggedIn() ) {
            pendingIntroAnimation = true;


        } else {
            //feedAdapter.updateItems(false);
        }

    }

    private void showJournal() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.PauseDialog);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.content_journal,
                null, false);
        LinearLayout layPost = view.findViewById(R.id.lay_post);
        final ImageView ivClose = view.findViewById(R.id.iv_close);
        builder.setView(view);
        builder.setCancelable(false);
        layPost.setTag(view);

        final AlertDialog dialog = builder.show();

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                replaceFragment(DashboardFragment.newInstance(0));
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        layPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Journal j = Journal.getFromViews((View) v.getTag());

               // Toast.makeText(MainActivity.this, j.getMessage()+ " save", Toast.LENGTH_LONG).show();
                Log.d(TAG, "journal: "+ j.toString());
                j.save(new Callback<ResponseJournal>() {
                    @Override
                    public void onResponse(Call<ResponseJournal> call, Response<ResponseJournal> response) {
                        Log.d(TAG, "journal save "+response.toString());
                        Log.d(TAG, "journal save "+call.request().body().toString());

                        try {
                            Toast.makeText(MainActivity.this, "Journal Save", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                            j.save(true);
                            if(showJournalSuggestions) imFeeling();
                            if(Journal.fromCache()!=null)ivToolbarFace.setImageResource(Journal.ratingToRes(Journal.EMOTIONAL));

                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseJournal> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                //dialog.dismiss();

            }
        });
        ivToolbarFace.setSelected(true);
        if(Journal.fromCache()!=null)ivToolbarFace.setImageResource(Journal.ratingToRes(Journal.EMOTIONAL));
    }

    private void init() {
        Api.initialized(getApplicationContext());

        if (!Validate.isLoggedIn()) {
            Intent intent
                    = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }
       // BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        vNavigation.setNavigationItemSelectedListener(this);
    }

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

    private void logout() {

        User.clearPref();
        TokenManager.clear();
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


 /*   private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new FeedAdapter(this);
        feedAdapter.setOnFeedItemClickListener(this);
        rvFeed.setAdapter(feedAdapter);
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvFeed.setItemAnimator(new FeedItemAnimator());
    }*/

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
      /*  if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }*/
    }
    /*
        private void showFeedLoadingItemDelayed() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rvFeed.smoothScrollToPosition(0);
                    feedAdapter.showLoadingView();
                }
            }, 500);
        }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    private void startIntroAnimation() {
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        llToolbarFace.setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        llToolbarFace.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //startContentAnimation();
                        try {
                            showJournal();
                           // replaceFragment(DashboardFragment.newInstance("para","para"));
                        }catch (WindowManager.BadTokenException e){

                        }catch (NullPointerException e){

                        }


                    }
                })
                .start();
    }

    /*   private void startContentAnimation() {
           fabCreate.animate()
                   .translationY(0)
                   .setInterpolator(new OvershootInterpolator(1.f))
                   .setStartDelay(300)
                   .setDuration(ANIM_DURATION_FAB)
                   .start();
           feedAdapter.updateItems(true);
       }
   */
    @Override
    public void onCommentsClick(View v, int position) {
        final Intent intent = new Intent(this, CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }

    @Override
    public void onProfileClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @OnClick(R.id.btnCreate)
    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        fabCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCreate.getWidth() / 2;
        TakePhotoActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        closeDrawer();
        MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.content_main);

        switch (item.getItemId()) {
            case R.id.nav_home:
                if(fragment!=null&&fragment.getPos()==0)return true;
                replaceFragmentWithAnimation(DashboardFragment.newInstance(0));

                return true;
            case R.id.nav_food:
                if(fragment!=null&&fragment.getPos()==1)return true;

                replaceFragmentWithAnimation(FoodFragment.newInstance(1));
                return true;
            case R.id.nav_lifestyle:
                if(fragment!=null&&fragment.getPos()==2)return true;
                replaceFragmentWithAnimation(LifestyleFragment.newInstance(2));

                return true;
            case R.id.nav_education:
                if(fragment!=null&&fragment.getPos()==3)return true;

                replaceFragmentWithAnimation(EducationListFragment.newInstance(3));
                return true;

            case R.id.nav_community:
                if(fragment!=null&&fragment.getPos()==4)return true;
                replaceFragmentWithAnimation(CommunityFragment.newInstance(4));

                return true;
            case R.id.nav_food_nutrient_report:

                return true;
            case R.id.nav_goals:
                return true;
            case R.id.nav_activity:
                return true;
            case R.id.nav_saved:
                return true;
            case  R.id.nav_inbox:

                return true;
            case   R.id.nav_trending :
                return true;
            case  R.id.nav_profile_settings :
                return true;

            case R.id.nav_account_settings:

                return true ;
            case R.id.nav_contact_us:
                return true;
            case R.id.nav_logout:

                logout();
                return true;
            default:
                return false;

        }

    }

    private void replaceFragmentWithAnimation(MyFragment fragment) {
        replaceFragmentWithAnimation(fragment,"my_fragment");
    }

    public void replaceFragmentWithAnimation(MyFragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MyFragment prevFrag = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.content_main);

        if(getSupportFragmentManager().getBackStackEntryCount() > 5) {

            getSupportFragmentManager().popBackStack(); // remove one (you can also remove more)
        }
        if(prevFrag.getPos()>fragment.getPos()){
            transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right,
                    R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        }else{
            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left, R.anim.slide_out_to_right );
        }

        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment)
                .addToBackStack("my_fragment")
                .commit();
    }


    @Override
    public void onFragmentInteraction(Object uri) {

    }
    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }


    @Override
    public void onClick(Serializable s, View v) {
        Log.d(TAG, "onCLick Serial " +v.getId());
        Log.d(TAG, "onCLick Serial banner " + R.id.banner);

        switch (v.getId()){
            case R.layout.view_article_featured:
                openArticleActivity(s,v);
                break;
            case R.id.banner:
                openBannerActivity(s,v);

                break;
                default:
                    break;
        }

    }

    private void openArticleActivity(Serializable s, View v) {
        final Intent intent = new Intent(this, ArticleActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        Article article = (Article) s;
        Log.d(TAG, "onClick "+ String.valueOf(article ==null));
        Log.d(TAG, "onClick "+ article.getTitle());
        //ArticleActivity.setArticle(article);
        intent.putExtra(ArticleActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        intent.putExtra("article",  article);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }


    public void openBannerActivity(Serializable s, View v) {
        final Intent intent = new Intent(this, BannerActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        Banner banner = (Banner) s;

        Log.d(TAG, "onClick "+ String.valueOf(banner ==null));
        Log.d(TAG, "onClick "+ banner.getName());
        //ArticleActivity.setArticle(article);
        intent.putExtra(ARG_DRAWING_START_LOCATION, startingLocation[1]);
        intent.putExtra(BannerActivity.KEY_EXTRA_BANNER,  banner);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemClick(UserDiet userDiet, int position, long id) {
        Log.d(TAG, "onItemClick userDiet"+ position);
        Intent i = new Intent(MainActivity.this, FoodDetailActivity.class);
        transitionTo(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_food:{
                bottomNavigation.setSelectedItemId(R.id.nav_food);
//                replaceFragmentWithAnimation(FoodFragment.newInstance(1));
                break;
            }
            case R.id.rl_lifestyle:{
                bottomNavigation.setSelectedItemId(R.id.nav_lifestyle);

               // replaceFragmentWithAnimation(LifestyleFragment.newInstance(2));
                break;
            }
            case R.id.rl_education:{
                bottomNavigation.setSelectedItemId(R.id.nav_education);

             //   replaceFragmentWithAnimation(EducationListFragment.newInstance(3));
                break;
            }
            case R.id.rl_community:{
                bottomNavigation.setSelectedItemId(R.id.nav_community);

               // replaceFragmentWithAnimation(CommunityFragment.newInstance(4));
                break;
            }
            default:
                break;
        }
    }
}
