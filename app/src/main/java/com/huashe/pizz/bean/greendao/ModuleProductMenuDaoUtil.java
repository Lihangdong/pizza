package com.huashe.pizz.bean.greendao;

import com.huashe.pizz.MyApplication;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductMenu;

import java.util.List;

public class ModuleProductMenuDaoUtil {
    /**
     * 添加数据，如果有重复则覆盖
     */
    public static void insertModuleProductMenu(ModuleProductMenu moduleProductMenu) {
        MyApplication.getDaoInstant().insertOrReplace(moduleProductMenu);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteModuleProductMenu(long id) {
        MyApplication.getDaoInstant().delete(id);
    }

    /**
     * 更新数据
     */
    public static void updateModuleProductMenu(ModuleProductMenu moduleProductMenu) {
        MyApplication.getDaoInstant().update(moduleProductMenu);
    }

    /**
     * 查询条件为Type=TYPE_ModuleProductMenu的数据
     *
     * @return
     */
    public static List<ModuleProductMenu> queryModuleProductMenu() {
        return MyApplication.getDaoInstant().queryBuilder(ModuleProductMenu.class).list();
    }

    /**
     * 查询全部数据
     */
    public static List<ModuleProductMenu> queryAll() {
        return MyApplication.getDaoInstant().loadAll(ModuleProductMenu.class);
    }
}
