package com.huashe.pizz.other.expandable;

/**
 * Created by Jay on 2015/9/25 0025.
 */
public class Item {

    private int iId;
    private String iName;
    private String zuijingengxin;
    public Item() {
    }

    public Item(int iId, String iName, String zuijingengxin) {
        this.iId = iId;
        this.iName = iName;
        this.zuijingengxin = zuijingengxin;
    }

    public String getZuijingengxin() {
        return zuijingengxin;
    }

    public void setZuijingengxin(String zuijingengxin) {
        this.zuijingengxin = zuijingengxin;
    }

    public Item(int iId, String iName) {
        this.iId = iId;
        this.iName = iName;
    }

    public int getiId() {
        return iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }
}
