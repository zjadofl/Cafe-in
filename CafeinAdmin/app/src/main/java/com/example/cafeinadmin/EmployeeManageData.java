package com.example.cafeinadmin;

public class EmployeeManageData {
    private String epNumber, epName, epPos, epTime;

    public EmployeeManageData() {
    }

    public EmployeeManageData(String epNumber, String epName, String epPos, String epTime) {
        this.epNumber = epNumber;
        this.epName = epName;
        this.epPos = epPos;
        this.epTime = epTime;
    }

    public EmployeeManageData(String epName, String epPos, String epTime) {
        this.epName = epName;
        this.epPos = epPos;
        this.epTime = epTime;
    }

    public String getEpNumber() {
        return epNumber;
    }

    public void setEpNumber(String epNumber) {
        this.epNumber = epNumber;
    }

    public String getEpName() {
        return epName;
    }

    public void setEpName(String epName) {
        this.epName = epName;
    }

    public String getEpPos() {
        return epPos;
    }

    public void setEpPos(String epPos) {
        this.epPos = epPos;
    }

    public String getEpTime() {
        return epTime;
    }

    public void setEpTime(String epTime) {
        this.epTime = epTime;
    }
}