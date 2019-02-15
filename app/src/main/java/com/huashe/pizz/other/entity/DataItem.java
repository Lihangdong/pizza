package com.huashe.pizz.other.entity;

/**
 * Created by Jamil
 * Time  2019/1/17
 * Description
 */
public class DataItem {
        String bianhao;
        String baocunshijian;
        String kehu;
        String gongsimingcheng;
        String xiangmumingcheng;
        String shoujihao;


        public DataItem(String bianhao, String baocunshijian, String kehu, String gongsimingcheng, String xiangmumingcheng, String shoujihao) {
            this.bianhao = bianhao;
            this.baocunshijian = baocunshijian;
            this.kehu = kehu;
            this.gongsimingcheng = gongsimingcheng;
            this.xiangmumingcheng = xiangmumingcheng;
            this.shoujihao = shoujihao;
        }

        public String getXiangmumingcheng() {
            return xiangmumingcheng;
        }

        public void setXiangmumingcheng(String xiangmumingcheng) {
            this.xiangmumingcheng = xiangmumingcheng;
        }

        public String getBianhao() {
            return bianhao;
        }

        public void setBianhao(String bianhao) {
            this.bianhao = bianhao;
        }

        public String getBaocunshijian() {
            return baocunshijian;
        }

        public void setBaocunshijian(String baocunshijian) {
            this.baocunshijian = baocunshijian;
        }

        public String getKehu() {
            return kehu;
        }

        public void setKehu(String kehu) {
            this.kehu = kehu;
        }

        public String getGongsimingcheng() {
            return gongsimingcheng;
        }

        public void setGongsimingcheng(String gongsimingcheng) {
            this.gongsimingcheng = gongsimingcheng;
        }

        public String getShoujihao() {
            return shoujihao;
        }

        public void setShoujihao(String shoujihao) {
            this.shoujihao = shoujihao;
        }
}
