package com.huashe.pizz.bean.HallCase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.huashe.pizz.bean.greendao.DaoSession;
import com.huashe.pizz.bean.greendao.HallCaseMenuDao;

@Entity(nameInDb = "hallcasemenu",active = true)
public class HallCaseMenu {
    /*
    {"name":"智能营业厅","id":"af433c7e5bb845f0b2c28e2d2598a679",
    "icon1":"/jeeplus_zz/userfiles/1/images/pizz/pizzamenu/图标/智能营业厅/sidebar_icon_znyyt_nor20190116131511.png","parentid":"3","case"
     */
    @Id(autoincrement = false)
    private String id;
    private String name;
    private String icon1;

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }

    private String icon2;
    private String parentid;

    public List<HallCaseBean> getDemo() {
        return demo;
    }

    public void setDemo(List<HallCaseBean> demo) {
        this.demo = demo;
    }

    @Transient
    private List<HallCaseBean> demo;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 900520113)
    private transient HallCaseMenuDao myDao;
    @Generated(hash = 770694141)
    public HallCaseMenu(String id, String name, String icon1, String icon2, String parentid) {
        this.id = id;
        this.name = name;
        this.icon1 = icon1;
        this.icon2 = icon2;
        this.parentid = parentid;
    }

    @Generated(hash = 1851028838)
    public HallCaseMenu() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIcon1() {
        return this.icon1;
    }
    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }
    public String getParentid() {
        return this.parentid;
    }
    public void setParentid(String parentid) {
        this.parentid = parentid;
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
    @Generated(hash = 659979541)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHallCaseMenuDao() : null;
    }
}
