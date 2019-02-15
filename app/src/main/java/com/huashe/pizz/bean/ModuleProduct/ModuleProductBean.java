package com.huashe.pizz.bean.ModuleProduct;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Transient;

import com.huashe.pizz.bean.greendao.DaoSession;
import com.huashe.pizz.bean.greendao.ModuleProductBeanConverter;
import com.huashe.pizz.bean.greendao.ModuleProductBeanDao;
@Entity(nameInDb = "moduleproductbean",active = true)
public class ModuleProductBean {
    /*
    {"description": [{
				"content": "\"用户将充电桩的插头插入电动汽车模型的插口内，电动汽车被激活发出光效。\r\n用户在三维互动环境中通过控制方向盘进行体验，在体验过程中感受电动汽车的各种优点。\r\n体验最后为客户提供电动汽车一站式购买解决方案以及充电桩办理服务引导，包括电动汽车、充电桩、汽车保险、汽车贷款以及国家补贴计算的一条龙服务流程。\"",
				"type": "体验方式"
			}, {
				"content": "虚拟驾驶体验，切实感受优势对比。",
				"type": "简短描述"
			}, {
				"content": "让电力用户在“虚拟驾驶”环境中获得体验乐趣，了解电动汽车的相关信息。",
				"type": "设计构思"
			}, {
				"content": "电动汽车与传统汽车经济性环保性对比、车联网概念、充电桩介绍；了解充电桩推广情况以及办理充电桩的业务说明。",
				"type": "内容策划"
			}],
			"id": "5c04782b1959461bb2cdf83182cc10dc",
			"image": "/jeeplus_zz/userfiles/1/images/pizz/产品目录/电动汽车/电动汽车驾驶版/_电动汽车_驾驶版_20190115222321.png",
			"imagePath": "http://47.100.59.75:80/jeeplus_zz/userfiles/1/images/pizza/产品目录/电动汽车/电动汽车驾驶版",
			"name": "电动汽车驾驶版",
			"subname": "Electric Vehicle",
			"subtitle": "",
			"type": "6a0c6e94d98e4a85b8c5e18e0c2d64fa"
		}
     */
    @OrderBy("sort ASC")//按照年龄升序
    private String sort;
    @Id(autoincrement = false)
    private String id;
    private String image;
    private String imagePath;
    private String name;
    private String price;
    private String size;
    private String subname;
    private String subtitle;
    private String type;

    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

    private int itemtype;

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    @Transient
    private boolean isLove;
    @Convert(converter = ModuleProductBeanConverter.class, columnType = String.class)
    private List<Description> description;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1274493795)
    private transient ModuleProductBeanDao myDao;
    @Generated(hash = 1865672478)
    public ModuleProductBean(String sort, String id, String image, String imagePath, String name, String price, String size, String subname, String subtitle, String type, int itemtype,
            List<Description> description) {
        this.sort = sort;
        this.id = id;
        this.image = image;
        this.imagePath = imagePath;
        this.name = name;
        this.price = price;
        this.size = size;
        this.subname = subname;
        this.subtitle = subtitle;
        this.type = type;
        this.itemtype = itemtype;
        this.description = description;
    }

    @Generated(hash = 1656123290)
    public ModuleProductBean() {
    }
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }



    public List<Description> getDescription() {
        return description;
    }

    public void setDescription(List<Description> description) {
        this.description = description;
    }



    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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
    @Generated(hash = 1975334720)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getModuleProductBeanDao() : null;
    }


    public static class Description {
        @Override
        public String toString() {
            return "Description{" +
                    "content='" + content + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        private String content;
        private String type;

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

    }
}