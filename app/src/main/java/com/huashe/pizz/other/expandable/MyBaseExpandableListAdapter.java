package com.huashe.pizz.other.expandable;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.huashe.pizz.R;

import java.util.ArrayList;

/**
 * Created by Jamil
 * Time  2018/10/8
 * Description
 * @Param Group ListView中的Item
 * @Param
 */
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter{

    private ArrayList<Group> gData;
    private ArrayList<ArrayList<Item>> iData;
    private Context mContext;



    public MyBaseExpandableListAdapter(ArrayList<Group> gData, ArrayList<ArrayList<Item>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }


    @Override
    public int getGroupCount() { //组数
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {// 对应小组的列表数
        return iData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    // 取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolderGroup groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exlist_group, viewGroup,false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_group_name = convertView.findViewById(R.id.tv_group_name);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        groupHolder.tv_group_name.setText(gData.get(groupPosition).getgName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolderItem itemHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exlist_item, viewGroup,false);
            itemHolder = new ViewHolderItem();
            itemHolder.img_icon =convertView.findViewById(R.id.img_icon);
            itemHolder.tv_name = convertView.findViewById(R.id.tv_name);
            itemHolder.zuijingengxin=convertView.findViewById(R.id.zuijingengxin);
            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ViewHolderItem) convertView.getTag();
        }
        itemHolder.img_icon.setImageResource(iData.get(groupPosition).get(childPosition).getiId());
        itemHolder.tv_name.setText(iData.get(groupPosition).get(childPosition).getiName());
        itemHolder.zuijingengxin.setText(iData.get(groupPosition).get(childPosition).getZuijingengxin());
        return convertView;
    }



    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private static class ViewHolderGroup{
        private TextView tv_group_name;
    }

    private static class ViewHolderItem{
        private ImageView img_icon;
        private TextView tv_name;
        private TextView zuijingengxin;
    }
}
