package com.huashe.pizz.bean.ModuleProduct;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Transient;

import com.huashe.pizz.bean.greendao.DaoSession;
import com.huashe.pizz.bean.greendao.ModuleProductMenuDao;

import java.util.List;

@Entity(nameInDb = "moduleproductmenu",active = true)
public class ModuleProductMenu {
    /*
    "icon1": "/jeeplus_zz/userfiles/1/images/pizz/pizzamenu/图标/线上自助体验/sidebar_icon_xszz_nor20190116131240.png",
		"icon2": "/jeeplus_zz/userfiles/1/images/pizz/pizzamenu/图标/线上自助体验/sidebar_icon_xszz_sel20190116131256.png",
		"id": "ac439ea63a4049458ef197ee41609fd6",
		"name": "线上自助体验",
		"parentid": "2",
     */
    @Id(autoincrement = false)
    private String id;
    @OrderBy("sort ASC")//按照年龄升序
    private String sort;
    private String icon1;
    private String icon2;
    private String name;
    private String parentid;

    public List<ModuleProductBean> getProducts() {
        return products;
    }

    public void setProducts(List<ModuleProductBean> products) {
        this.products = products;
    }

    @Transient
    private List<ModuleProductBean> products;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 197372837)
    private transient ModuleProductMenuDao myDao;
    @Generated(hash = 436046165)
    public ModuleProductMenu(String id, String sort, String icon1, String icon2, String name, String parentid) {
        this.id = id;
        this.sort = sort;
        this.icon1 = icon1;
        this.icon2 = icon2;
        this.name = name;
        this.parentid = parentid;
    }

    @Generated(hash = 1912816429)
    public ModuleProductMenu() {
    }
    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getParentid() {
        return parentid;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1467569655)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getModuleProductMenuDao() : null;
    }
}