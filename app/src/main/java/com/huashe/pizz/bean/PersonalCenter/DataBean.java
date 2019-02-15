package com.huashe.pizz.bean.PersonalCenter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.huashe.pizz.bean.greendao.DaoSession;
import com.huashe.pizz.bean.greendao.DataBeanDao;

@Entity(nameInDb = "userinfo",active = true)
public  class DataBean {
        /**
         * id : 9ede0be9156e462cb099013d08a0178e
         * name : 李hangdong
         * photo : /jeeplus_zz/static/common/images/flat-avatar.png
         * mobile : 17754012599
         * email :
         * department : 行政部
         * station : 普通用户
         */

        private String id;
        private String name;
        private String photo;
    @Id(autoincrement = false)
        private String mobile;
        private String email;
        private String department;
        private String station;
        /** Used to resolve relations */
        @Generated(hash = 2040040024)
        private transient DaoSession daoSession;
        /** Used for active entity operations. */
        @Generated(hash = 532982881)
        private transient DataBeanDao myDao;

        @Generated(hash = 2143345008)
        public DataBean(String id, String name, String photo, String mobile,
                String email, String department, String station) {
            this.id = id;
            this.name = name;
            this.photo = photo;
            this.mobile = mobile;
            this.email = email;
            this.department = department;
            this.station = station;
        }

        @Generated(hash = 908697775)
        public DataBean() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
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
        @Generated(hash = 443544070)
        public void __setDaoSession(DaoSession daoSession) {
            this.daoSession = daoSession;
            myDao = daoSession != null ? daoSession.getDataBeanDao() : null;
        }
    }