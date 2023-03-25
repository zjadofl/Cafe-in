package com.example.cafein;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Complain {
    private String complainName,complainWriteDate,complainDate,
            complainComplete, complainContent, complainType, complainReply;
    SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    Calendar time = Calendar.getInstance();

    public Complain() {
    }

    public Complain(String complainName, String complainWriteDate, String complainDate, String complainComplete,
                    String complainContent, String complainType, String complainReply) {
        this.complainName = complainName;
        this.complainWriteDate = complainWriteDate;
        this.complainDate = complainDate;
        this.complainComplete = complainComplete;
        this.complainContent = complainContent;
        this.complainType = complainType;
        this.complainReply = complainReply;
    }

    public String getComplainName() {
        return complainName;
    }

    public void setComplainName(String complainName) {
        this.complainName = complainName;
    }

    public String getComplainWriteDate() {return complainWriteDate;}

    public void setComplainWriteDate(String complainWriteDate) {this.complainWriteDate = format1.format(time.getTime()).toString();}

    public String getComplainDate() {
        return complainDate;
    }

    public void setComplainDate(String complainDate) {
        this.complainDate = complainDate;
    }

    public String getComplainComplete() {
        return complainComplete;
    }

    public void setComplainComplete(String complainComplete) {
        this.complainComplete = complainComplete;
    }

    public String getComplainContent() {
        return complainContent;
    }

    public void setComplainContent(String complainContent) {
        this.complainContent = complainContent;
    }

    public String getComplainType() {
        return complainType;
    }

    public void setComplainType(String complainType) {
        this.complainType = complainType;
    }

    public String getComplainReply() {
        return complainReply;
    }

    public void setComplainReply(String complainReply) {
        this.complainReply = complainReply;
    }
}
