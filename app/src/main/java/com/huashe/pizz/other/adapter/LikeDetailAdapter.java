package com.huashe.pizz.other.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huashe.pizz.R;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;

import java.util.List;

public class LikeDetailAdapter extends BaseAdapter {


    List<ModuleProductBean> mList;
    Context mContext;

    public LikeDetailAdapter(List<ModuleProductBean> list, Context context){
        mList=list;
        mContext=context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;

        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.like_detail_item,viewGroup,false);
            viewHolder.chanpin=convertView.findViewById(R.id.chanpin_txt);
            viewHolder.description=convertView.findViewById(R.id.jianjie_txt);
            viewHolder.product_img=convertView.findViewById(R.id.product_img);
            convertView.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.description.setText(mList.get(i).getDescription().get(0).getContent());
        viewHolder.chanpin.setText(mList.get(i).getName());
        viewHolder.product_img.setImageBitmap(BitmapFactory.decodeFile(mList.get(i).getImage()));
        return convertView;
    }


    class ViewHolder{
        TextView chanpin;
        TextView description;
        ImageView product_img;
    }
}
