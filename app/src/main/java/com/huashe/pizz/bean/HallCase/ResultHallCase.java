package com.huashe.pizz.bean.HallCase;

import java.util.List;

public class ResultHallCase {
    private boolean success;
    private int code;

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

    public List<OneMenuHallCase> getData() {
        return data;
    }

    public void setData(List<OneMenuHallCase> data) {
        this.data = data;
    }

    private List<OneMenuHallCase> data;
}
