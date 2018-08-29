package com.fightbackfoods.model;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fightbackfoods.Api;
import com.fightbackfoods.R;
import com.fightbackfoods.api.ResponseJournal;
import com.fightbackfoods.utils.ConfigPrefHelper;
import com.fightbackfoods.utils.PrefHelper;
import com.fightbackfoods.utils.TextUtils;
import com.fightbackfoods.utils.Validate;
import com.fightbackfoods.view.SpinnerHealthAspectCategory;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Map;

import retrofit2.Callback;

import static com.fightbackfoods.model.Journal.Rating.HAPPY;
import static com.fightbackfoods.model.Journal.Rating.NEUTRAL;
import static com.fightbackfoods.model.Journal.Rating.SAD;

public class Journal extends PrefHelper {
    private static final String SHARED_PREF = "shared_pref_journal";
    private static final String SHARED_PREF_KEY = "key_journal";
    public static final int PHYSICAL = 2;
    public static final int EMOTIONAL = 1;


    @SerializedName("userId")
    private String userId;

    @SerializedName("visibility")
    private String visibility;

    @SerializedName("visibility_id")
    private String visibilityId;

    @SerializedName("physicalRating")
    private String physicalRating;
    @SerializedName("mentalRating")
    private String mentalRating;
    @SerializedName("mentalCategory")
    private String mentalCategory;
    @SerializedName("physicalCategory")
    private String physicalCategory;

    @SerializedName("message")
    private String message;
    @SerializedName("shareToFb")
    private String shareToFb;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPhysicalRating() {
        return physicalRating;
    }

    public void setPhysicalRating(String physicalRating) {
        this.physicalRating = physicalRating;
    }

    public String getMentalRating() {
        return mentalRating;
    }

    public void setMentalRating(String mentalRating) {
        this.mentalRating = mentalRating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShareToFb() {
        return shareToFb;
    }

    public void setShareToFb(String shareToFb) {
        this.shareToFb = shareToFb;
    }

    public String getMentalCategory() {
        return mentalCategory;
    }

    public void setMentalCategory(String mentalCategory) {
        this.mentalCategory = mentalCategory;
    }

    public String getPhysicalCategory() {
        return physicalCategory;
    }

    public void setPhysicalCategory(String physicalCategory) {
        this.physicalCategory = physicalCategory;
    }

    public Journal() {
        Calendar s = Calendar.getInstance();
        s.get(Calendar.HOUR_OF_DAY);
    }

    public static boolean doneToday(){
       return ConfigPrefHelper.getJournalLastUpdate().equals(TextUtils.getDateFormatToday());
    }


    public static Journal getFromViews(View view) {
        Journal j = new Journal();
        j.setVisibility("1");
        RadioGroup rgMental = view.findViewById(R.id.rg_mental);
       // j.setMentalRating(String.valueOf(rgMental.indexOfChild(view.findViewById(rgMental.getCheckedRadioButtonId()))));
        j.setMentalRating(ratingValueOfIndex(rgMental.indexOfChild(view.findViewById(rgMental.getCheckedRadioButtonId()))));
        RadioGroup rgPhysical = view.findViewById(R.id.rg_physical);
        j.setPhysicalRating(ratingValueOfIndex(rgPhysical.indexOfChild(view.findViewById(rgPhysical.getCheckedRadioButtonId()))));
        j.setMessage(((EditText)view.findViewById(R.id.et_message)).getText().toString());
        j.setShareToFb(((CheckBox)view.findViewById(R.id.cb_fb)).isChecked()? "1":"0");
        SpinnerHealthAspectCategory spMental = view.findViewById(R.id.sp_mental);
        j.setMentalCategory(String.valueOf(spMental.getSelectedItemId()));
        SpinnerHealthAspectCategory spPhysical = view.findViewById(R.id.sp_physical);
        j.setPhysicalCategory(String.valueOf(spPhysical.getSelectedItemId()));

        return j;
    }

    public static String ratingValueOfIndex(int i){
        switch (i){
            case SAD:
                return "1";
            case NEUTRAL:
                return "2";
            case HAPPY:
                return "3";
            default:
                return "0";
        }
    }
    public static void add (Journal journal, Callback<ResponseJournal> callback){
        // Map<String, String> map = Token.toMap();

        Api.getInstance().journalsAdd(journal.toMap(), callback);
    }

    public void save(Callback<ResponseJournal> callback){
        Journal.add(this, callback);
    }

    private Map<String,String> toMap() {
        Map<String, String> map = Token.toMap();
        map.put("visibility",getVisibility());
        map.put("physicalRating",getPhysicalRating());
        map.put("mentalRating",getMentalRating());
        map.put("mentalCategory",getMentalCategory());
        map.put("physicalCategory",getPhysicalCategory());
        map.put("message",getMessage());
        map.put("shareToFb",getShareToFb());
        return map;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void save(boolean cache) {
        Journal.j =this;
        if(cache){
            SharedPreferences.Editor editor = getEditor();
            editor.putString(SHARED_PREF_KEY, toString());
            editor.commit();
            editor.apply();
        }
    }

    public static Journal fromCache(){
        try {
            if(j!=null) return j;
            return  new Gson().fromJson(getPref().getString(SHARED_PREF_KEY,null), Journal.class);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    public static int ratingToRes(int category) {
        String emoRating = "0";
        switch (category){
            case EMOTIONAL:
                emoRating = Journal.fromCache().getMentalRating();

                break;
            case PHYSICAL:
                emoRating = Journal.fromCache().getPhysicalRating();

                break;
        }
        int rating = Integer.parseInt(Validate.isNullString(emoRating)? "0":emoRating);
        switch (rating){
            case 3:
                return R.drawable.ic_face_happy_checked;

            case 2:
                return R.drawable.ic_face_neutral_checked;
            case 1:
                return R.drawable.ic_face_neutral_checked;
                default:
                    return R.drawable.ic_face_happy;

        }

    }

    public static class State {

        @SerializedName("userId")
        private String userId;


    }

    private static Journal j;

    public static class Rating {
         static final int SAD = 2;
         static final int NEUTRAL = 1;
         static final int HAPPY = 0;


    }

}
