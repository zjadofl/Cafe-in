package com.example.cafeinadmin;

public class CSManageData {
    private String id, userName, csName, csWDate, csDate, csComplete, csContent, csType, csReply;

    public CSManageData() {
    }

    public CSManageData(String id, String userName, String csWDate, String csName,
                        String csDate, String csComplete, String csContent, String csType, String csReply) {
        this.id = id;
        this.userName = userName;
        this.csName = csName;
        this.csWDate = csWDate;
        this.csDate = csDate;
        this.csComplete = csComplete;
        this.csContent = csContent;
        this.csType = csType;
        this.csReply = csReply;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public String getCsWDate() {
        return csWDate;
    }

    public void setCsWDate(String csWDate) {
        this.csWDate = csWDate;
    }

    public String getCsDate() {
        return csDate;
    }

    public void setCsDate(String csDate) {
        this.csDate = csDate;
    }

    public String getCsComplete() {
        return csComplete;
    }

    public void setCsComplete(String csComplete) {
        this.csComplete = csComplete;
    }

    public String getCsContent() {
        return csContent;
    }

    public void setCsContent(String csContent) {
        this.csContent = csContent;
    }

    public String getCsType() {
        return csType;
    }

    public void setCsType(String csType) {
        this.csType = csType;
    }

    public String getCsReply() {
        return csReply;
    }

    public void setCsReply(String csReply) {
        this.csReply = csReply;
    }
}
