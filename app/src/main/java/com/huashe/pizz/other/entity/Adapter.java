package com.huashe.pizz.other.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.huashe.pizz.R;
import com.huashe.pizz.bean.PersonalCenter.PersonalLikeListBean;

import java.util.List;

/**
 * Created by Jamil
 * Time  2019/1/17
 * Description
 */
public class Adapter extends BaseAdapter {


    Context mContext;
    List<PersonalLikeListBean.DataBean> listData;

    public Adapter(List<PersonalLikeListBean.DataBean> list, Context context){
        listData=list;
        mContext=context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View convertView=  LayoutInflater.from(mContext).inflate(R.layout.list_adpter,viewGroup,false);
        TextView biaohao= convertView.findViewById(R.id.biaohao);
        TextView baocunshijian= convertView.findViewById(R.id.baocunshijian);
        TextView kehu=   convertView.findViewById(R.id.kehu);
        TextView  gongsimingcheng= convertView.findViewById(R.id.gongsimingcheng);
        TextView    xiangmumingcheng = convertView.findViewById(R.id.xiangmumingcheng);
        TextView    shoujihaoma = convertView.findViewById(R.id.shoujihaoma);

        biaohao.setText(listData.get(i).getLikeid()); //编号

        baocunshijian.setText(listData.get(i).getTime());
        kehu.setText(listData.get(i).getName());
        gongsimingcheng.setText(listData.get(i).getCompany());
        xiangmumingcheng.setText(listData.get(i).getProjectname());
        shoujihaoma.setText(listData.get(i).getMobile());
        return convertView;
    }
}
