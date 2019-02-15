package com.huashe.pizz.bean.greendao;

import com.huashe.pizz.MyApplication;
import com.huashe.pizz.bean.AboutUs.AboutUsBean;

import java.util.List;

public class AboutUsDaoUtil {
    /**
     * 添加数据，如果有重复则覆盖
     */
    public static void insertAboutUsBean(AboutUsBean aboutUsBean) {
        MyApplication.getDaoInstant().insertOrReplace(aboutUsBean);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteAboutUsBean(long id) {
        MyApplication.getDaoInstant().delete(id);
    }

    /**
     * 更新数据
     */
    public static void updateHallCaseMenu(AboutUsBean aboutUsBean) {
        MyApplication.getDaoInstant().update(aboutUsBean);
    }

    /**
     * 查询条件为Type=TYPE_HallCaseMenu的数据
     *
     * @return
     */
    public static List<AboutUsBean> queryAboutUsBean() {
        return MyApplication.getDaoInstant().queryBuilder(AboutUsBean.class).list();
    }

    /**
     * 查询全部数据
     */
    public static List<AboutUsBean> queryAll() {
        return MyApplication.getDaoInstant().loadAll(AboutUsBean.class);
    }
}
