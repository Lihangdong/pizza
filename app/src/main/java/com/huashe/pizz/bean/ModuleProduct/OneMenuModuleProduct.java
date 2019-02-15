package com.huashe.pizz.bean.ModuleProduct;

import java.util.List;

public class OneMenuModuleProduct {
    private String name;
    private String id;
    private String sort;
    private String icon1;
    private String parentid;
    private List<ModuleProductMenu> content;

    public List<ModuleProductMenu> getContent() {
        return content;
    }

    public void setContent(List<ModuleProductMenu> content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

}
