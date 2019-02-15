package com.huashe.pizz.bean.greendao;

import org.greenrobot.greendao.test.AbstractDaoTestStringPk;

import com.huashe.pizz.bean.ModuleProduct.ModuleProductMenu;

public class ModuleProductMenuTest extends AbstractDaoTestStringPk<ModuleProductMenuDao, ModuleProductMenu> {

    public ModuleProductMenuTest() {
        super(ModuleProductMenuDao.class);
    }

    @Override
    protected ModuleProductMenu createEntity(String key) {
        ModuleProductMenu entity = new ModuleProductMenu();
        entity.setId(key);
        return entity;
    }

}
