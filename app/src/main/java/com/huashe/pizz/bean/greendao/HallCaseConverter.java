package com.huashe.pizz.bean.greendao;

import com.google.gson.Gson;
import com.huashe.pizz.bean.HallCase.HallCaseBean;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HallCaseConverter implements PropertyConverter<List<HallCaseBean>, String> {
    @Override
    public List<HallCaseBean> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        } else {
            System.out.println(databaseValue);
            List<HallCaseBean> beanList=new ArrayList<>();
            List<String> list = Arrays.asList(databaseValue.split("@"));
            System.out.println(list);
            for (String s:list){
                Gson gson = new Gson();
                HallCaseBean description = gson.fromJson(s, HallCaseBean.class);
                beanList.add(description);
            }

            return beanList;
        }
    }

    @Override
    public String convertToDatabaseValue(List<HallCaseBean> entityProperty) {
        return null;
    }
}
