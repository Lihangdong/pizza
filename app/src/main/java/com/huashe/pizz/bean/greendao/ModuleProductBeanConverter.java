package com.huashe.pizz.bean.greendao;

import com.google.gson.Gson;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleProductBeanConverter implements PropertyConverter<List<ModuleProductBean.Description>, String> {

    @Override
    public List<ModuleProductBean.Description> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        } else {
            System.out.println(databaseValue);
            List<ModuleProductBean.Description> beanList=new ArrayList<>();
            List<String> list = Arrays.asList(databaseValue.split("@"));
            System.out.println(list);
            for (String s:list){
                Gson gson = new Gson();
                ModuleProductBean.Description description = gson.fromJson(s, ModuleProductBean.Description.class);
                beanList.add(description);
            }

            return beanList;
        }
    }

    @Override
    public String convertToDatabaseValue(List<ModuleProductBean.Description> entityProperty) {
        if(entityProperty==null){
            return null;
        }
        else{
            Gson gson = new Gson();
            StringBuilder sb= new StringBuilder();
            for(ModuleProductBean.Description description:entityProperty){
                sb.append(gson.toJson(description,ModuleProductBean.Description.class));
                sb.append("@");
            }
            return sb.toString();
        }
    }
}
