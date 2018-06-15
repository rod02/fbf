package com.fightbackfoods;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fightbackfoods.interfaces.Response;
import com.fightbackfoods.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.Manifest.permission.READ_CONTACTS;
import static java.security.AccessController.getContext;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final List<String> FB_RD_PERMISSION = Arrays.asList("user_birthday","public_profile",
            "email","user_location","user_friends","user_age_range");


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private TextView tvSignUp;
    private TextView tvForgotPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
      //  populateAutoComplete();

        getSupportActionBar().hide();


        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);
        tvSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        tvForgotPassword = (TextView) findViewById(R.id.tv_forgot_password);
        tvForgotPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);*/
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        fbLoginInit();
    }

    private void fbLoginInit() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(FB_RD_PERMISSION);
        // If you are using in a fragment, call loginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    String email ="";
                                    String location ="";
                                    @Override
                                    public void onCompleted(final JSONObject object, GraphResponse response) {

                                        try {
                                            email = object.getString("email");
                                        }catch (Exception e)
                                        {
                                            email = "noemail@gmail.com";
                                        }
                                        String birthday = object.optString("birthday",""); // 01/31/1980 format
                                        try {
                                            location = object.getJSONObject("location").optString("name","");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        if(Profile.getCurrentProfile()==null){
                                            profileTracker = new ProfileTracker() {
                                                @Override
                                                protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                                                    Profile.setCurrentProfile(newProfile);

                                                    profileTracker.stopTracking();
                                                    String photoUrl = newProfile.getProfilePictureUri(200,200).toString();

                                                    User user = new User(email, newProfile.getName(),photoUrl);
                                                    User.setCurrentUser(user);
                                                    nextActivity( user);
                                                }
                                            };
                                            profileTracker.startTracking();
                                        }else {
                                            nextActivity( Profile.getCurrentProfile(),email,birthday);
                             /*   LoginManager.getInstance().logInWithReadPermissions(
                                        LoginActivity.this,
                                        Arrays.asList("user_birthday","email","user_location"));*/
                                        }


                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday,location");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        showProgress(false);

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        showProgress(false);

                    }
                });
/*
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/
    }

    private void signUp() {

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void forgotPassword() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(R.string.reset_password);
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.forgot_password, null, false);
        // Set up the input
        final EditText input = (EditText) view.findViewById(R.id.et_email);
        // Set up the buttons
        Button btnSend = (Button) view.findViewById(R.id.btn_send);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        builder.setView(view);

        final AlertDialog dialog = builder.show();
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input.getText().toString();
                boolean cancel = false;
                // Check for a valid email address.
                if (TextUtils.isEmpty(email)) {
                    input.setError(getString(R.string.error_field_required));
                    cancel = true;
                } else if (!isEmailValid(email)) {
                    input.setError(getString(R.string.error_invalid_email));
                    cancel = true;
                }
                if (!cancel){

                    if(!Api.isInitialized()){
                        Api.initialized(getApplicationContext());
                    }
                    Api.getInstance().resetPassword(email, new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                            if(response.isSuccessful()){
                                Response mResponse = response.body();

                                if(mResponse.getStatus().equalsIgnoreCase("success")){
                                    dialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setTitle(R.string.reset_password);
                                    builder.setMessage(mResponse.getMessage());
                                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
                                }
                            }else{
                                input.setError(response.message());

                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }

            }
        });
    }

    private void nextActivity(User user) {
        showProgress(false);

        Intent main = new Intent(LoginActivity.this, MainActivity.class);
/*
            main.putExtra("name", profile.getFirstName());
            main.putExtra("surname", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
*/
        main .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        User.setCurrentUser(user);
        startActivity(main);
        finish();

    }

    private void nextActivity(Profile currentProfile, String email, String birthday) {
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mProgressView.getVisibility() == View.VISIBLE) {
            return;
        }
        hideSreenKeyboard(true);
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            if(!Api.isInitialized()) Api.initialized(getApplicationContext());
            Api api = Api.getInstance();
            api.login(email, password, new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    showProgress(false);

                    if(response.isSuccessful()){
                        Response mResponse = response.body();
                        if(mResponse.getStatus().equalsIgnoreCase("success")){
                            User user = mResponse.getUser();
                            nextActivity(user);
                        }else{
                            Toast.makeText(LoginActivity.this, mResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    t.printStackTrace();

                    showProgress(false);

                }
            });

        }
    }

    private void hideSreenKeyboard(boolean hide) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}

