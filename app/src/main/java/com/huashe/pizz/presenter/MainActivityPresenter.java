package com.huashe.pizz.presenter;

import android.content.Context;

import com.huashe.pizz.base.BasePresenter;
import com.huashe.pizz.bean.AboutUs.AboutUsBean;
import com.huashe.pizz.contract.MainActivityContract;
import com.huashe.pizz.model.AboutUsModel;
import com.huashe.pizz.mvp.IView;

import java.util.List;


public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> implements MainActivityContract.Presenter {
    private AboutUsModel aboutUsModel;
    private ModelCallBack modelCallBack;

    @Override
    public void attachModel() {
        if (aboutUsModel == null) {
            IView mView=this.mView;
            aboutUsModel = new AboutUsModel((Context)mView.getContext());
        }
        if (modelCallBack == null) {
            modelCallBack = new ModelCallBack();
        }
    }

    @Override
    public void detachModel() {
        if (modelCallBack == null) {
            aboutUsModel = null;
        }
        if (modelCallBack == null) {
            modelCallBack = null;
        }
    }

    @Override
    public void getMenu(Boolean isShowLoading, String type) {

        if (aboutUsModel != null) {
            aboutUsModel.getMenu(modelCallBack, isShowLoading, type);
        }
    }

    private class ModelCallBack implements AboutUsModel.ModelCallBack {
        public void getMenuSucess(List<AboutUsBean> aboutUsBeans) {
            if (mView != null) {
                mView.getMenuSucess(aboutUsBeans);
            }
        }

        public void onFail(String err) {
            if (mView != null) {
                mView.onFail(err);
            }
        }
    }
}
