package com.huashe.pizz.contract;

import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductMenu;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    private static List<ModuleProductMenu> menuDatas;
    private static List<ModuleProductBean> beanDatas;

    public static List<ModuleProductMenu> getMenuDatas() {
//        "icon": "/jeeplus_zz/userfiles/1/files/pizz/pizzamenu/pizzaMenu/2019/01/u338920190107164803.png",
//                "id": "392a635f512743fca1ea4ef0fb4cfbfe",
//                "name": "智能家庭与国网商城",
//                "parentid": "2",
        menuDatas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ModuleProductMenu moduleProductMenu = new ModuleProductMenu();
            moduleProductMenu.setIcon1("/sdcard/Download/qdcf+gwscsc/icon/drawable/sidebar_icon_zhny_nor.png");
            moduleProductMenu.setId((int) (Math.random() * 10 + 1)+"aaaa");
            moduleProductMenu.setName("电动汽车"+i);
            moduleProductMenu.setParentid("2");
            moduleProductMenu.setSort((int) (Math.random() * 10 + 1)+"");
            menuDatas.add(moduleProductMenu);
        }
        return menuDatas;
    }

    public static  List<ModuleProductBean> getBeanDatas() {
//        {
//            "description": [{
//            "content": "12312",
//                    "type": "简短描述"
//        }, {
//            "content": "12312123",
//                    "type": "设计构思"
//        }],
//            "id": "00bd121d5d54408d897f4ad9c442f627",
//                "image": "",
//                "imagePath": "",
//                "name": "wooqwoewq",
//                "price": 1232.0,
//                "size": "qweqwe",
//                "subname": "qweqweqw",
//                "subtitle": "qweqwe",
//                "type": "5276000f20f344d3b4bda5f0916777c8"
//        }
        beanDatas = new ArrayList<>();
        for (int i = 0; i < 30  ; i++) {
            ModuleProductBean moduleProductBean = new ModuleProductBean();
            moduleProductBean.setSort((int) (Math.random() * 10 + 1)+"");
            moduleProductBean.setId(i+"ddsdsds");
            moduleProductBean.setImage("/sdcard/Download/qdcf+gwscsc/icon/drawable/sidebar_icon_zhny_nor.png");
            moduleProductBean.setImagePath("/sdcard/Download/智电家庭+全电厨房+国网商城/");
            moduleProductBean.setName("电动汽车"+i);
            moduleProductBean.setPrice("11"+i);
            moduleProductBean.setSize(i+"x"+i);
            moduleProductBean.setSubname("顶顶顶顶");
            moduleProductBean.setSubtitle("的点点滴滴多");
            moduleProductBean.setType((int) (Math.random() * 10 + 1)+"aaaa");
            List<ModuleProductBean.Description> descriptions=new ArrayList<>();
            for (int k=0;k<3;k++){
                ModuleProductBean.Description description=new ModuleProductBean.Description();
                description.setType(k+k+k+k+"吾谓");
                description.setContent(k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+k+"吾问无为谓");
                descriptions.add(description);
            }
            moduleProductBean.setDescription(descriptions);
            beanDatas.add(moduleProductBean);
        }
        return beanDatas;
    }

}
