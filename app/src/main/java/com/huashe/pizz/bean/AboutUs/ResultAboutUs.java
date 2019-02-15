package com.huashe.pizz.bean.AboutUs;

import java.util.List;

public class ResultAboutUs {
    private boolean success;
    private int code;
    private List<AboutUsBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AboutUsBean> getData() {
        return data;
    }

    public void setData(List<AboutUsBean> data) {
        this.data = data;
    }
}
