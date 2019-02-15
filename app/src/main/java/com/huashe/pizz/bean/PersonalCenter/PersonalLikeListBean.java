package com.huashe.pizz.bean.PersonalCenter;

import java.util.List;

/**
 * Created by Jamil
 * Time  2019/1/23
 * Description
 */
public class PersonalLikeListBean {

    /**
     * success : true
     * code : 0
     * data : [{"time":"2019-01-24 14:31:23.0","name":"李hangdong","mobile":"17754012599","projectname":"智电家庭+全电厨房+国网商城","company":"uuuuu","products":"04328b353d95499a9d41ce4392329e4d,ba7d1c3a581d4dca99be86f37e7f43d0","likeid":"20190124001"},{"time":"2019-01-24 14:33:17.0","name":"李hangdong","mobile":"17754012599","projectname":"全电厨房+国网商城","company":"uuuuu","products":"ba7d1c3a581d4dca99be86f37e7f43d0,04328b353d95499a9d41ce4392329e4d","likeid":"20190124002"},{"time":"2019-01-24 14:07:13.0","name":"李hangdong","mobile":"17754012599","projectname":"全电厨房+国网商城","company":"uuuuu","products":"ba7d1c3a581d4dca99be86f37e7f43d0","likeid":"20190124004"}]
     */

    private String success;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2019-01-24 14:31:23.0
         * name : 李hangdong
         * mobile : 17754012599
         * projectname : 智电家庭+全电厨房+国网商城
         * company : uuuuu
         * products : 04328b353d95499a9d41ce4392329e4d,ba7d1c3a581d4dca99be86f37e7f43d0
         * likeid : 20190124001
         */

        private String time;
        private String name;
        private String mobile;
        private String projectname;
        private String company;
        private String products;
        private String likeid;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProjectname() {
            return projectname;
        }

        public void setProjectname(String projectname) {
            this.projectname = projectname;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getProducts() {
            return products;
        }

        public void setProducts(String products) {
            this.products = products;
        }

        public String getLikeid() {
            return likeid;
        }

        public void setLikeid(String likeid) {
            this.likeid = likeid;
        }
    }
}