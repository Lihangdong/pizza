package com.huashe.pizz.mvp;


public interface IPresenter<V extends IView>{
    /**
     * 绑定
     *
     * @param view
     */
    void attachView(V view);


    /**
     * 防止内存的泄漏,清楚presenter与activity之间的绑定
     *
     * @param
     */
    void detachView();
}
