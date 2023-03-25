package com.example.cafein;

import java.util.ArrayList;

public class GroupData {
    private String groupName;

    public ArrayList<ChildData> child;

    public GroupData(String groupName) {
        this.groupName = groupName;
        child = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
