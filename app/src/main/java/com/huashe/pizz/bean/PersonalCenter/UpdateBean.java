package com.huashe.pizz.bean.PersonalCenter;

/**
 * Created by Jamil
 * Time  2019/1/24
 * Description
 */
public class UpdateBean {

    /**
     * success : true
     * code : 0
     * data : {"id":"1.3","name":"1.3","content":"王企鹅无群","href":"http://116.62.191.214:8080/jeeplus_zz/userfiles/images/pizza1.320190124150117.zip"}
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

    public static class DataBean {
        /**
         * id : 1.3
         * name : 1.3
         * content : 王企鹅无群
         * href : http://116.62.191.214:8080/jeeplus_zz/userfiles/images/pizza1.320190124150117.zip
         */

        private String id;
        private String name;
        private String content;
        private String href;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}