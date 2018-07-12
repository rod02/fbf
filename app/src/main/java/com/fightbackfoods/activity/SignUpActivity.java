package com.fightbackfoods.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fightbackfoods.Api;
import com.fightbackfoods.R;
import com.fightbackfoods.api.ResponseUser;
import com.fightbackfoods.model.User;
import com.fightbackfoods.utils.TokenManager;
import com.fightbackfoods.view.SpinnerCancerStages;
import com.fightbackfoods.view.SpinnerCancerType;
import com.fightbackfoods.view.SpinnerGender;
import com.fightbackfoods.view.SpinnerHeightUnit;
import com.fightbackfoods.view.SpinnerMobility;
import com.fightbackfoods.view.SpinnerTreatment;
import com.fightbackfoods.view.SpinnerWeightUnit;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    private static final int RES_PHOTO = 11;

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_birthday)
    EditText etBirthday;
    @BindView(R.id.et_zipCode)
    EditText etZipCode;
  //  @BindView(R.id.sp_gender)
    SpinnerGender spGender;


    @BindView(R.id.et_weight)
    EditText etWeight;

    @BindView(R.id.sp_weight)
    SpinnerWeightUnit spWeight;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.sp_height)
    SpinnerHeightUnit spHeight;
    @BindView(R.id.sp_cancer_stage)
    SpinnerCancerStages spCancerStage;
    @BindView(R.id.sp_current_treatment)
    SpinnerTreatment spCurrentTreatment;
    @BindView(R.id.sp_mobility)
    SpinnerMobility spMobility;
    @BindView(R.id.sp_cancer_type)
    SpinnerCancerType spCancerType;
    @BindView(R.id.et_food_restrictions)
    EditText etFoodRestrictions;
    @BindView(R.id.iv_add_photo)
    ImageView ivAddPhoto;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.lay_progress)
    RelativeLayout layProgress;


    @BindView(R.id.tv_progress)
    TextView tvProgress;


    Calendar mCalendar;

    String image64;
    Uri imageUri;
    String birthday;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        spGender = findViewById(R.id.sp_gender);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ivAddPhoto.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, 1980);
        mCalendar.set(Calendar.MONTH, 5);
        mCalendar.set(Calendar.DAY_OF_MONTH, 14);

        etBirthday.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add_photo:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RES_PHOTO);
                break;
            case R.id.iv_delete:
                ivAvatar.setImageBitmap(null) ;
                image64 = null;

                break;
            case R.id.iv_avatar:
                ivDelete.setVisibility(ivDelete.isShown()? View.GONE : View.VISIBLE);

                break;
            case R.id.et_birthday:

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateBirthday();
                    }

                };
                new DatePickerDialog(SignUpActivity.this, date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_submit:
                attemptSave();

                break;
            
        }
    }

    private void attemptSave() {
        if(isSaving()){
            return;
        }
        hideSreenKeyboard(true);


        hideSreenKeyboard(true);
        // Reset errors.
        etEmail.setError(null);
        etPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String birthday = etBirthday.getText().toString();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));
            focusView = etPassword;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        // Check required birthday.
        if (TextUtils.isEmpty(birthday)) {
            etBirthday.setError(getString(R.string.error_field_required));
            focusView = etBirthday;
            cancel = true;
        }
        // Check required firstname.
        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError(getString(R.string.error_field_required));
            focusView = etBirthday;
            cancel = true;
        }
        // Check required lastname.
        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError(getString(R.string.error_field_required));
            focusView = etBirthday;
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
            Api.getInstance().signUp(getUserFromViews(), new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, retrofit2.Response<ResponseUser> response) {
                    try {

                        if(response.isSuccessful()){
                            ResponseUser mResponse = response.body();
                            Log.d(TAG, "onresponse " + mResponse.toString() );
                            if(mResponse.getStatus().equalsIgnoreCase("success")){
                                User user = mResponse.getUser();
                                User.setCurrentUser(user);
                                TokenManager.setToken(mResponse.getToken(),true);
                                if(image64!=null){
                                    uploadImage(image64);
                                }else{
                                    nextActivity();
                                }

                            }else{
                                String errorMessage = "";
                                if(mResponse.getErrorMessages()==null){
                                    errorMessage = mResponse.getMessage();
                                }else errorMessage = mResponse.getErrorMessages().get(0);
                                Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                showProgress(false);
                            }
                        }

                    }catch (NullPointerException e){

                    }

                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {
                    t.printStackTrace();
                    try{
                        showProgress(false);
                    }catch (NullPointerException e){

                    }


                }
            });

        }



    }

    private void uploadImage(String image64) {
        tvProgress.setText(R.string.uploading_image);
        Api.getInstance().updateAvatar(String.valueOf(User.getCurrentUserId()), image64, new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, retrofit2.Response<ResponseUser> response) {
                if(response.isSuccessful()){
                    ResponseUser json = response.body();
                    if(json.getStatus().equalsIgnoreCase("success")){
                        User.getCurrentUser().setAvatar(json.getImgUrl(), true);
                        nextActivity();
                        return;

                    }
                    showProgress(false);

                }else {
                    showProgress(false);
                }
                Toast.makeText(SignUpActivity.this, "failed to upload image",Toast.LENGTH_LONG).show();
                nextActivity();
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                showProgress(false);

            }
        });
    }

    private void nextActivity(){
        showProgress(false);
        Toast.makeText(this, "Save successful", Toast.LENGTH_SHORT).show();
        Intent main = new Intent(SignUpActivity.this, MainActivity.class);
        main .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(main);
        finish();
    }

    private Map<String, String> getUserFromViews() {
        Map<String, String> user = new HashMap<>();
        user.put("email", etEmail.getText().toString());
        user.put("password",etPassword.getText().toString());
        user.put("firstName", etFirstName.getText().toString());
        user.put("lastName",etLastName.getText().toString());
        user.put("birthday", birthday);
        user.put("zipCode",etZipCode.getText().toString());
        user.put("gender", String.valueOf(spGender.getSelectedItemPosition()));
        user.put("cancerType", String.valueOf(spCancerType.getSelectedItemPosition()));
        user.put("cancerStage", String.valueOf(spCancerStage.getSelectedItemPosition()));
        user.put("mobility", String.valueOf(spMobility.getSelectedItemPosition()));
        user.put("currentTreatment", String.valueOf(spCurrentTreatment.getSelectedItemPosition()));
        user.put("foodRestriction",etFoodRestrictions.getText().toString());
        user.put("weight",etWeight.getText().toString());
        user.put("weightUnit",String.valueOf(spWeight.getSelectedItemPosition()));
        user.put("heightUnit",String.valueOf(spHeight.getSelectedItemPosition()));
        user.put("height",etHeight.getText().toString());

        if(image64==null)user.put("avatar", "");

        return user;
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
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        layProgress.setVisibility(show ? View.GONE : View.VISIBLE);
        layProgress.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                layProgress.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        layProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        layProgress.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                layProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }


    private void updateBirthday() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etBirthday.setText(sdf.format(mCalendar.getTime()));
        birthday = String.valueOf(mCalendar.getTimeInMillis());
    }


    private boolean saving;
    public boolean isSaving() {
        return saving;
    }


    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case RES_PHOTO:

                if (resultCode == RESULT_OK) {
                    try {
                        final Uri uri = data.getData();
                        imageUri = uri;
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                        ivAvatar.setImageBitmap(selectedImage);
                        image64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(SignUpActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
