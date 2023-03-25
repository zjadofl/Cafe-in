package com.example.cafein;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityRecord;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentELVAdapter extends BaseExpandableListAdapter {

    private static final int PARENT_ROW = R.layout.order_group;
    private static final int CHILD_ROW = R.layout.order_group_detail;
    private Context context;
    private ArrayList<GroupData> groupData;
    private LayoutInflater inflater = null;

    public PaymentELVAdapter(Context context, ArrayList<GroupData> groupData) {
        this.groupData = groupData;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(PARENT_ROW, parent, false);
        }

        TextView menuNameGroup = (TextView) convertView.findViewById(R.id.menuNameGroup);
        menuNameGroup.setText(groupData.get(groupPosition).getGroupName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(CHILD_ROW, parent, false);
        }

        TextView optionChild = (TextView) convertView.findViewById(R.id.optionChild);
        optionChild.setText(groupData.get(groupPosition).child.get(childPosition).getOption());

        return convertView;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }
    @Override

    public int getChildrenCount(int groupPosition) {

        return groupData.get(groupPosition).child.size();

    }



    @Override

    public Object getGroup(int groupPosition) {

        return groupData.get(groupPosition);

    }



    @Override

    public Object getChild(int groupPosition, int childPosition) {

        return groupData.get(groupPosition).child.get(childPosition);

    }



    @Override

    public long getGroupId(int groupPosition) {

        return groupPosition;

    }



    @Override

    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;

    }



    @Override

    public boolean hasStableIds() {

        return true;

    }



    @Override

    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;

    }



}
