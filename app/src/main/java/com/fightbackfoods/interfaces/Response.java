package com.fightbackfoods.interfaces;

import com.fightbackfoods.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class Response {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("request")
    @Expose
    String request;

    @SerializedName("user")
    @Expose
    User user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
