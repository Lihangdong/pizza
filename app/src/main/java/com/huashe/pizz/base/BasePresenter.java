package com.huashe.pizz.base;


import com.huashe.pizz.mvp.IPresenter;
import com.huashe.pizz.mvp.IView;

public class BasePresenter<V extends IView> implements IPresenter<V> {
    protected V mView;
    @Override
    public void attachView(V view) {
        mView=view;
    }

    @Override
    public void detachView() {
        mView=null;
    }
}
