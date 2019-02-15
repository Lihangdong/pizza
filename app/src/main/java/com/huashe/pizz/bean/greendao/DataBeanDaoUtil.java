package com.huashe.pizz.bean.greendao;

import com.huashe.pizz.MyApplication;
import com.huashe.pizz.bean.PersonalCenter.DataBean;

import java.util.List;

public class DataBeanDaoUtil {
    /**
     * 添加数据，如果有重复则覆盖
     */
    public static void insertDataBean(DataBean dataBean) {
        MyApplication.getDaoInstant().insertOrReplace(dataBean);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteAll() {
        MyApplication.getDaoInstant().deleteAll(DataBean.class);
    }

    /**
     * 更新数据
     */
    public static void updateDataBean(DataBean dataBean) {
        MyApplication.getDaoInstant().update(dataBean);
    }

    /**
     * 查询条件为Type=TYPE_HallCaseMenu的数据
     *
     * @return
     */
    public static List<DataBean> queryDataBean() {
        return MyApplication.getDaoInstant().queryBuilder(DataBean.class).list();
    }

    /**
     * 查询全部数据
     */
    public static List<DataBean> queryAll() {
        return MyApplication.getDaoInstant().loadAll(DataBean.class);
    }
}
