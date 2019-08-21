package com.cherepanovky.callbackit.core.storage.olddb;

import android.net.Uri;

public class CallingSettings {

    public static final String OUTGOING_ON = "OUT_ON";
    public static final String INCOMING_ON = "IN_ON";
    public static final String INCOMING_MISSED_ON = "IN_MISSED_ON";
    public static final String PHONE_STATE = "PHONE_STATE";

    private Boolean isOutGoingOn = true;
    private Boolean isIncomingOn = true;
    private Boolean isMissedIncomingOn = true;
    private Uri ringtoneUri;
    private String account;

    public Boolean getOutGoingOn() {
        return isOutGoingOn;
    }

    public void setOutGoingOn(Boolean outGoingOn) {
        isOutGoingOn = outGoingOn;
    }

    public Boolean getIncomingOn() {
        return isIncomingOn;
    }

    public void setIncomingOn(Boolean incomingOn) {
        isIncomingOn = incomingOn;
    }

    public Boolean getMissedIncomingOn() {
        return isMissedIncomingOn;
    }

    public void setMissedIncomingOn(Boolean missedIncomingOn) {
        isMissedIncomingOn = missedIncomingOn;
    }

    public Uri getRingtoneUri() {
        return ringtoneUri;
    }

    public void setRingtoneUri(Uri ringtoneUri) {
        this.ringtoneUri = ringtoneUri;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
