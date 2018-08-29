package com.fightbackfoods.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fightbackfoods.R;
import com.fightbackfoods.api.ResponseJournal;
import com.fightbackfoods.model.Journal;
import com.fightbackfoods.model.JournalSuggestion;
import com.fightbackfoods.view.ImFeelingDialogFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaseActivity extends AppCompatActivity {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
    private static final String TAG = BaseActivity.class.getSimpleName();
    public int drawingStartLocation;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @Nullable
    @BindView(R.id.ll_toolbar_face)
    LinearLayout layFeelings;


    private MenuItem inboxMenuItem;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    protected void bindViews() {
        ButterKnife.bind(this);
        setupToolbar();
    }

    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_menu_dark);
            if(layFeelings!=null){
                layFeelings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imFeeling();
                    }
                });
            }
        }
    }

    protected void imFeeling() {

        Journal j = Journal.fromCache();
        try {
            JournalSuggestion.get(j.getMentalCategory(),
                    j.getPhysicalCategory(), journalSuggestionCallback);
            return;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        FragmentManager fm = getSupportFragmentManager();
        ImFeelingDialogFragment mImFeelingDialogFragment = ImFeelingDialogFragment.newInstance("Some Title");
        mImFeelingDialogFragment.show(fm, "fragment_im_feeling");



    }

    Callback<ResponseJournal> journalSuggestionCallback = new Callback<ResponseJournal>(){

        @Override
        public void onResponse(Call<ResponseJournal> call, Response<ResponseJournal> response) {
            Log.d(TAG, "JournalSuggest onResponse "+ response.toString());
            try {
                if(response.isSuccessful()){
                    ResponseJournal rs = response.body();
                    if(rs.isSuccessful()){
                        ResponseJournal.Suggestions suggestions = rs.getSuggestions();
                        imFeeling(suggestions.getEmotional(), suggestions.getPhysical());
                    }else {
                        Log.d(TAG, "JournalSuggest onResponse not successful 2");

                        return;
                    }
                }else {
                    Log.d(TAG, "JournalSuggest onResponse not successful "+ response.message());

                    return;
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<ResponseJournal> call, Throwable t) {

        }
    };

    private void imFeeling(List<JournalSuggestion> emotional, List<JournalSuggestion> physical) {
        FragmentManager fm = getSupportFragmentManager();
        ImFeelingDialogFragment mImFeelingDialogFragment = ImFeelingDialogFragment.newInstance(emotional,physical);
        mImFeelingDialogFragment.show(fm, "fragment_im_feeling");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_main, menu);
      //  inboxMenuItem = menu.findItem(R.id.action_feeling);
       // inboxMenuItem.setActionView(R.layout.menu_item_view);
        return true;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public MenuItem getInboxMenuItem() {
        return inboxMenuItem;
    }

    public ImageView getIvLogo() {
        return ivLogo;
    }

    @SuppressWarnings("unchecked") void transitionTo(Intent i) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(i, transitionActivityOptions.toBundle());
    }



    protected Transition enterTransition() {
        Slide enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }

    protected Visibility returnTransition() {
        Slide enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }



    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    protected void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
