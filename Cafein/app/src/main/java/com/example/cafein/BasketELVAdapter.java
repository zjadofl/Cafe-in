package com.example.cafein;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityRecord;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cafein.Database.ModelDB.BasketItem;
import com.example.cafein.Utils.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasketELVAdapter extends BaseExpandableListAdapter {

    private static final int PARENT_ROW = R.layout.basket_item;
    private static final int CHILD_ROW = R.layout.basket_child_item;
    private Context context;
    private List<BasketItem> groupData;
    private LayoutInflater inflater = null;

    public BasketELVAdapter(Context context, List<BasketItem> groupData) {
        this.groupData = groupData;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(PARENT_ROW, parent, false);
        }

        //ImageView menuImg = (ImageView) convertView.findViewById(R.id.menuImg);
        TextView menuName = (TextView) convertView.findViewById(R.id.menuName);
        TextView menuPrice  = (TextView) convertView.findViewById(R.id.menuPrice);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);

        menuName.setText(groupData.get(groupPosition).name);
        //menuPrice.setText(groupData.get(groupPosition).price);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //groupData.remove(groupPosition);


                //Log.i("position",groupPosition+"");
                Common.basketRepository.deleteBasketItem(groupData.remove(groupPosition));
                //Log.i("position`2", groupData.get(groupPosition) + "");

                //Adapter attached to expListView
                notifyDataSetChanged();
                //notifyItemRemoved(getAdapterPosition());
                //notifyItemRangeChanged(getAdapterPosition(), mList.size());


                /*if (groupPosition == groupData.size() - 1) { // if last element is deleted, no need to shift
                    groupData.remove(groupPosition);
                    //Common.basketRepository.deleteBasketItem(groupData.get(groupPosition));
                    notifyDataSetChanged();
                } else { // if the element deleted is not the last one
                    int shift=1; // not zero, shift=0 is the case where position == dataList.size() - 1, which is already checked above
                    while (true) {
                        try {
                            groupData.remove(groupPosition-shift);
                            //Common.basketRepository.deleteBasketItem(groupData.get(groupPosition-shift));
                            notifyDataSetChanged();
                            break;
                        } catch (IndexOutOfBoundsException e) { // if fails, increment the shift and try again
                            shift++;
                        }
                    }
                }*/


            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(CHILD_ROW, parent, false);
        }

        TextView optionName = (TextView) convertView.findViewById(R.id.optionName);
        TextView optionPrice = (TextView) convertView.findViewById(R.id.optionPrice);

        //optionName.setText(groupData.get(groupPosition).child.get(childPosition).getOption());

        return convertView;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }
    @Override

    public int getChildrenCount(int groupPosition) {

        //return groupData.get(groupPosition).child.size();
        return 0;
    }



    @Override

    public Object getGroup(int groupPosition) {

        return groupData.get(groupPosition);

    }



    @Override

    public Object getChild(int groupPosition, int childPosition) {

        //return groupData.get(groupPosition).child.get(childPosition);
        return 0;

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
