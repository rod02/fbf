package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

public class JournalRecord {
    @SerializedName("journal_id")
    private String id;
    @SerializedName("share_to_fb")
    private String shareToFb;
    @SerializedName("visibility_id")
    private String visibility;
    @SerializedName("message")
    private String message;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("mentalState")
    private HealthState mentalState;
    @SerializedName("physicalState")
    private HealthState physicalState;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShareToFb() {
        return shareToFb;
    }

    public void setShareToFb(String shareToFb) {
        this.shareToFb = shareToFb;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public HealthState getMentalState() {
        return mentalState;
    }

    public void setMentalState(HealthState mentalState) {
        this.mentalState = mentalState;
    }

    public HealthState getPhysicalState() {
        return physicalState;
    }

    public void setPhysicalState(HealthState physicalState) {
        this.physicalState = physicalState;
    }

    public static class HealthState {
        @SerializedName("journal_state_id")
        private String id;
        @SerializedName("journal_id")
        private String journalId;
        @SerializedName("health_aspect_id")
        private String healthAspectId;
        @SerializedName("health_aspect_category_id")
        private String healthAspectCategoryId;
        @SerializedName("rating_id")
        private String ratingId;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updatedAt;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJournalId() {
            return journalId;
        }

        public void setJournalId(String journalId) {
            this.journalId = journalId;
        }

        public String getHealthAspectId() {
            return healthAspectId;
        }

        public void setHealthAspectId(String healthAspectId) {
            this.healthAspectId = healthAspectId;
        }

        public String getHealthAspectCategoryId() {
            return healthAspectCategoryId;
        }

        public void setHealthAspectCategoryId(String healthAspectCategoryId) {
            this.healthAspectCategoryId = healthAspectCategoryId;
        }

        public String getRatingId() {
            return ratingId;
        }

        public void setRatingId(String ratingId) {
            this.ratingId = ratingId;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }

}
