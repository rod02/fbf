package com.fightbackfoods.model;

import com.fightbackfoods.api.ResponseError;
import com.google.gson.annotations.SerializedName;

public class CustomHttpError {


    @SerializedName("status")
    private String status;
    @SerializedName("parameter")
    private String parameter;
    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
