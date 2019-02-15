package com.huashe.pizz.VMBanner;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 2018/5/14.
 */

public class BannerViewAdapter extends PagerAdapter
{
    private List<View> listBean;
    private OnBannerListener listener;
    int count=0;
    public BannerViewAdapter(List<View> list, OnBannerListener listener){
        if (list == null){
            list = new ArrayList<>();
        }
        this.listBean = list;
        this.listener=listener;
    }

    public void setDataList(List<View> list){
        this.count=list.size();
        if (list != null && list.size() > 0){
            this.listBean = list;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        View view = listBean.get(position);
        container.addView(view);
        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnBannerClick(toRealPosition(position));
                }
            });
        }
        return view;
    }
    /**
     * 返回真实的位置
     *
     * @param position
     * @return 下标从0开始
     */
    public int toRealPosition(int position) {
        int realPosition = (position - 1) % count;
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }
    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }

    @Override
    public int getCount()
    {
        return listBean.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

}
