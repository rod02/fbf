package com.fightbackfoods.api;

import com.fightbackfoods.model.CustomHttpError;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseError {


    @SerializedName("error")
    private List<CustomHttpError> errors;

    public List<CustomHttpError> getErrors() {
        return errors;
    }

    public void setErrors(List<CustomHttpError> errors) {
        this.errors = errors;
    }
}
