package com.huashe.pizz.bean.ModuleProduct;

import java.util.List;

public class ResultModuleProduct {
    private boolean success;
    private int code;
    private List<OneMenuModuleProduct> data;


    public List<OneMenuModuleProduct> getData() {

        return data;
    }

    public void setData(List<OneMenuModuleProduct> data) {
        this.data = data;
    }



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

}
