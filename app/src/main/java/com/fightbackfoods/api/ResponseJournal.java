package com.fightbackfoods.api;

import com.fightbackfoods.model.Article;
import com.fightbackfoods.model.Journal;
import com.fightbackfoods.model.JournalSuggestion;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseJournal extends Response {


    @SerializedName("journals")
    private List<Journal> journals;

    @SerializedName("journal_id")
    private String journalId;
    @SerializedName("suggestions")
    private Suggestions suggestions;



    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public Suggestions getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(Suggestions suggestions) {
        this.suggestions = suggestions;
    }


    public boolean isSuccessful(){
       /* try {
            return super.isSuccessful();
        }catch (NullPointerException e){

        }*/

        try {
            return getSuccess().equalsIgnoreCase("true") ;
        }catch (NullPointerException e){
            e.printStackTrace();
            return super.isSuccessful();
        }
    }


    public static class Suggestions extends Response {
        @SerializedName("physical")
        private List<JournalSuggestion> physical ;
        @SerializedName("emotional")
        private List<JournalSuggestion> emotional ;

        public List<JournalSuggestion> getPhysical() {
            return physical;
        }

        public void setPhysical(List<JournalSuggestion> physical) {
            this.physical = physical;
        }

        public List<JournalSuggestion> getEmotional() {
            return emotional;
        }

        public void setEmotional(List<JournalSuggestion> emotional) {
            this.emotional = emotional;
        }

    }

}
