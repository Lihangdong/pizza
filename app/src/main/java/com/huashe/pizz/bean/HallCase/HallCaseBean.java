package com.huashe.pizz.bean.HallCase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.huashe.pizz.bean.greendao.DaoSession;
import com.huashe.pizz.bean.greendao.HallCaseBeanDao;

@Entity(nameInDb = "hallcasebean",active = true)
public class HallCaseBean {
    /*
    {"image":"/jeeplus_zz/userfiles/1/images/pizz/案例/智能营业厅/我的啊w/1首页.png",
    "imagePath":"http://47.100.59.75:80/jeeplus_zz/userfiles/1/images/pizza/案例/智能营业厅/我的啊w","name":"我的啊wq",
    "id":"f5c9d7b941834fc2ba4bc793b7b8d1d6","type":"af433c7e5bb845f0b2c28e2d2598a679"}
     */
    @Id(autoincrement = false)
    private String id;
    private String image;
    private String imagePath;
    private String name;
    private String type;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1538626540)
    private transient HallCaseBeanDao myDao;
    @Generated(hash = 2010034328)
    public HallCaseBean(String id, String image, String imagePath, String name, String type) {
        this.id = id;
        this.image = image;
        this.imagePath = imagePath;
        this.name = name;
        this.type = type;
    }
    @Generated(hash = 414027239)
    public HallCaseBean() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
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
    @Generated(hash = 326554839)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHallCaseBeanDao() : null;
    }

    @Override
    public String toString() {
        return "HallCaseBean{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                '}';
    }
}
