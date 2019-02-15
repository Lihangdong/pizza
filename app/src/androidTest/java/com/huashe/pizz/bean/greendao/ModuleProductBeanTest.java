package com.huashe.pizz.bean.greendao;

import org.greenrobot.greendao.test.AbstractDaoTestStringPk;

import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;

public class ModuleProductBeanTest extends AbstractDaoTestStringPk<ModuleProductBeanDao, ModuleProductBean> {

    public ModuleProductBeanTest() {
        super(ModuleProductBeanDao.class);
    }

    @Override
    protected ModuleProductBean createEntity(String key) {
        ModuleProductBean entity = new ModuleProductBean();
        entity.setId(key);
        return entity;
    }

}
