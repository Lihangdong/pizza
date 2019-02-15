package com.huashe.pizz.contract;

import com.huashe.pizz.bean.AboutUs.AboutUsBean;
import com.huashe.pizz.mvp.IView;

import java.util.List;

public class MainActivityContract {
    /**
     * 界面回调
     */
    public interface View extends IView {
        void getMenuSucess(List<AboutUsBean> list);

//        fun getTopTitlesSuccess()

        void onFail(String err);
    }

    /**
     * 主持界面动作到model
     */
    public interface Presenter {
        void attachModel();

        void detachModel();

        void getMenu(Boolean isShowLoading, String type);
    }
}
