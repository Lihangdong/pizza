package com.huashe.pizz.bean.PersonalCenter;

/**
 * Created by Jamil
 * Time  2019/1/23
 * Description
 */
public class UserInfoBean {

    /**
     * success : true
     * code : 0
     * data : {"id":"9ede0be9156e462cb099013d08a0178e","name":"李hangdong","photo":"/jeeplus_zz/static/common/images/flat-avatar.png","mobile":"17754012599","email":"","department":"行政部","station":"普通用户"}
     */

    private String success;
    private int code;
    private DataBean data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


}
