package com.huashe.pizz.bean.AboutUs;


import com.huashe.pizz.base.BaseModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.huashe.pizz.bean.greendao.DaoSession;
import com.huashe.pizz.bean.greendao.AboutUsBeanDao;
@Entity(nameInDb = "aboutusbean",active = true)
public class AboutUsBean extends BaseModel {

    private String name;
    private String icon1;
    private String ico2;
    private String id;
    private String sort;
    private String images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getIco2() {
        return ico2;
    }

    public void setIco2(String ico2) {
        this.ico2 = ico2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public List<AboutUsBean> getContent() {
        return content;
    }

    public void setContent(List<AboutUsBean> content) {
        this.content = content;
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
    @Generated(hash = 959844438)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAboutUsBeanDao() : null;
    }

    private String parentid;
    @Transient
    private List<AboutUsBean> content;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 910055739)
    private transient AboutUsBeanDao myDao;

    @Generated(hash = 240675408)
    public AboutUsBean(String name, String icon1, String ico2, String id,
            String sort, String images, String parentid) {
        this.name = name;
        this.icon1 = icon1;
        this.ico2 = ico2;
        this.id = id;
        this.sort = sort;
        this.images = images;
        this.parentid = parentid;
    }

    @Generated(hash = 1815571728)
    public AboutUsBean() {
    }

}