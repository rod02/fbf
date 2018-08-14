package com.fightbackfoods.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("request")
    @Expose
    String request;


    @SerializedName("error_messages")
    @Expose
    List<String> errorMessages;
    @SerializedName("error_message")
    @Expose
    List<String> errorMessage;

    @SerializedName("required")
    @Expose
    List<String> required;


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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

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

    public boolean isSuccessful() {
        try {
            return status.equalsIgnoreCase("success");
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }
}
