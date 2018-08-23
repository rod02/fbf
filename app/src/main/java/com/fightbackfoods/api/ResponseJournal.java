package com.fightbackfoods.api;

import com.fightbackfoods.model.Article;
import com.fightbackfoods.model.Journal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseJournal extends Response {


    @SerializedName("journals")
    private List<Journal> journals;

    @SerializedName("journal_id")
    private String journalId;



}
