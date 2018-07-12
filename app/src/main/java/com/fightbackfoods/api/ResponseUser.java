package com.fightbackfoods.api;

import com.fightbackfoods.model.User;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUser {

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

    @SerializedName("error_messages")
    @Expose
    List<String> errorMessages;

    @SerializedName("required")
    @Expose
    List<String> required;

    @SerializedName("alphaOnly")
    @Expose
    List<String> alphaOnly;

    @SerializedName("numberOnly")
    @Expose
    List<String> numberOnly;

    @SerializedName("img_url")
    @Expose
    String imgUrl;



    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public List<String> getAlphaOnly() {
        return alphaOnly;
    }

    public void setAlphaOnly(List<String> alphaOnly) {
        this.alphaOnly = alphaOnly;
    }

    public List<String> getNumberOnly() {
        return numberOnly;
    }

    public void setNumberOnly(List<String> numberOnly) {
        this.numberOnly = numberOnly;
    }

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


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResponseUser() {
    }


    @Override
    public String toString(){
        return  new Gson().toJson(this,ResponseUser.class).toString();
    }
}
