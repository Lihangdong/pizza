package com.huashe.pizz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.huashe.pizz.base.BaseApplication;
import com.huashe.pizz.base.Constants;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;
import com.huashe.pizz.bean.greendao.DaoMaster;
import com.huashe.pizz.bean.greendao.DaoSession;
import com.huashe.pizz.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.sms.SMSSDK;
public class MyApplication extends BaseApplication {
    public static IWXAPI api;
    public static Context context;
    public List<ModuleProductBean> getUserLoveLists() {
        return userLoveLists;
    }
    static MyApplication myApplication;
    public static MyApplication getInstance() {
        return myApplication;
    }
    public void setUserLoveLists(List<ModuleProductBean> userLoveLists) {
        this.userLoveLists = userLoveLists;
    }

    public List<ModuleProductBean> userLoveLists = new ArrayList<>();

    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        //配置数据库
        myApplication=this;
        setupDatabase();
        api = WXEntryActivity.initOpenWeChat(this, Constants.OPEN_WECHAT_APP_ID);
		 SMSSDK.getInstance().initSdk(this);
        //getData();
    }



    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
