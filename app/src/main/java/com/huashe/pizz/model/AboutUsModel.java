package com.huashe.pizz.model;

import android.content.Context;

import com.huashe.pizz.base.BaseModel;
import com.huashe.pizz.base.BaseResponse;
import com.huashe.pizz.bean.AboutUs.AboutUsBean;
import com.huashe.pizz.http.ApiClient;
import com.huashe.pizz.http.SubscribeHandler;
import com.huashe.pizz.http.rxjava.ProgressSubscriber;
import com.huashe.pizz.http.rxjava.SubscriberOnNextListener;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class AboutUsModel extends BaseModel {
    public Context context;

    public AboutUsModel(Context context) {
        this.context = context;
    }

    public void getMenu(final ModelCallBack modelCallBack, boolean isShowLoading, String type) {
        SubscriberOnNextListener<List<AboutUsBean>> subscriberOnNextListener = new SubscriberOnNextListener<List<AboutUsBean>>() {
            @Override
            public void onNext(List<AboutUsBean> aboutUsBeans) {
                modelCallBack.getMenuSucess(aboutUsBeans);
            }

            @Override
            public void onFail(String err) {
                modelCallBack.onFail(err);
            }
        };
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        System.out.println(params);
        Observable observable = ApiClient.getInstance(context).getMenu(params);
        Observer observer = new ProgressSubscriber<BaseResponse<List<AboutUsBean>>>(subscriberOnNextListener, context, isShowLoading);
        SubscribeHandler.observeOn(observable, observer);
    }

    public interface ModelCallBack {
        void getMenuSucess(List<AboutUsBean> list);

        void onFail(String err);
    }
}
