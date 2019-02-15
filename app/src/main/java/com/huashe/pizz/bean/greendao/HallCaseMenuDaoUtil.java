package com.huashe.pizz.bean.greendao;

import com.huashe.pizz.MyApplication;
import com.huashe.pizz.bean.HallCase.HallCaseMenu;

import java.util.List;

public class HallCaseMenuDaoUtil {
    /**
     * 添加数据，如果有重复则覆盖
     */
    public static void insertHallCaseMenu(HallCaseMenu hallCaseMenu) {
        MyApplication.getDaoInstant().insertOrReplace(hallCaseMenu);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteHallCaseMenu(long id) {
        MyApplication.getDaoInstant().delete(id);
    }

    /**
     * 更新数据
     */
    public static void updateHallCaseMenu(HallCaseMenu hallCaseMenu) {
        MyApplication.getDaoInstant().update(hallCaseMenu);
    }

    /**
     * 查询条件为Type=TYPE_HallCaseMenu的数据
     *
     * @return
     */
    public static List<HallCaseMenu> queryHallCaseMenu() {
        return MyApplication.getDaoInstant().queryBuilder(HallCaseMenu.class).list();
    }

    /**
     * 查询全部数据
     */
    public static List<HallCaseMenu> queryAll() {
        return MyApplication.getDaoInstant().loadAll(HallCaseMenu.class);
    }
}
