package com.huashe.pizz.bean.greendao;

import com.huashe.pizz.MyApplication;
import com.huashe.pizz.bean.HallCase.HallCaseBean;

import java.util.List;

public class HallCaseBeanDaoUtil {
    /**
     * 添加数据，如果有重复则覆盖
     */
    public static void insertHallCaseBean(HallCaseBean hallCaseBean) {
        MyApplication.getDaoInstant().insertOrReplace(hallCaseBean);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteHallCaseBean(long id) {
        MyApplication.getDaoInstant().delete(id);
    }

    /**
     * 更新数据
     */
    public static void updateHallCaseBean(HallCaseBean hallCaseBean) {
        MyApplication.getDaoInstant().update(hallCaseBean);
    }

    /**
     * 查询条件为Type=TYPE_HallCaseMenu的数据
     *
     * @return
     */
    public static List<HallCaseBean> queryHallCaseBean() {
        return MyApplication.getDaoInstant().queryBuilder(HallCaseBean.class).list();
    }

    /**
     * 查询全部数据
     */
    public static List<HallCaseBean> queryAll() {
        return MyApplication.getDaoInstant().loadAll(HallCaseBean.class);
    }
}
