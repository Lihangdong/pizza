package com.huashe.pizz.bean.greendao;

import com.huashe.pizz.MyApplication;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;

import java.util.List;

public class ModuleProductBeanDaoUtil {
    /**
     * 添加数据，如果有重复则覆盖
     */
    public static void insertModuleProductBean(ModuleProductBean moduleProductBean) {
        MyApplication.getDaoInstant().insertOrReplace(moduleProductBean);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteModuleProductBean(long id) {
        MyApplication.getDaoInstant().delete(id);
    }

    /**
     * 更新数据
     */
    public static void updateModuleProductBean(ModuleProductBean moduleProductBean) {
        MyApplication.getDaoInstant().update(moduleProductBean);
    }

    /**
     * 查询条件为Type=TYPE_ModuleProductBean的数据
     *
     * @return
     */
    public static ModuleProductBean queryModuleProductBeanById(String id) {//onDao.queryBuilder().where(SonDao.Properties.Name.eq("小明")).unique();
        return MyApplication.getDaoInstant().queryBuilder(ModuleProductBean.class).where(ModuleProductBeanDao.Properties.Id.eq(id)).unique();
    }

    /**
     * 查询全部数据
     */
    public static List<ModuleProductBean> queryAll() {
        return MyApplication.getDaoInstant().loadAll(ModuleProductBean.class);
    }
}
