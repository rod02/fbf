package com.fightbackfoods.model;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class Journal {

    @SerializedName("userId")
    private String userId;

    @SerializedName("visibility")
    private String visibility;

    @SerializedName("physicalRating")
    private String physicalRating;
    @SerializedName("mentalRating")
    private String mentalRating;
    /*
    @SerializedName("mentalCategory")
    private String mentalCategory;
    @SerializedName("physicalCategory")
    private String physicalCategory;
*/

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

    public Journal() {
        Calendar s = Calendar.getInstance();
        s.get(Calendar.HOUR_OF_DAY);
    }


    public static Journal getFromViews(View view) {
        Journal j = new Journal();
        RadioGroup rgMental = view.findViewById(R.id.rg_mental);
        j.setMentalRating(String.valueOf(rgMental.indexOfChild(view.findViewById(rgMental.getCheckedRadioButtonId()))));
        RadioGroup rgPhysical = view.findViewById(R.id.rg_physical);
        j.setMentalRating(String.valueOf(rgPhysical.indexOfChild(view.findViewById(rgPhysical.getCheckedRadioButtonId()))));
        j.setMessage(((EditText)view.findViewById(R.id.et_message)).getText().toString());
        j.setShareToFb(((CheckBox)view.findViewById(R.id.cb_fb)).isChecked()? "1":"0");

        return j;
    }
}
