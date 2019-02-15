package com.huashe.pizz;

import android.Manifest;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huashe.pizz.adapter.CommonRecycleAdapter;
import com.huashe.pizz.adapter.CommonViewHolder;
import com.huashe.pizz.base.BaseActivity;
import com.huashe.pizz.bean.AboutUs.AboutUsBean;
import com.huashe.pizz.bean.AboutUs.ResultAboutUs;
import com.huashe.pizz.bean.HallCase.HallCaseBean;
import com.huashe.pizz.bean.HallCase.HallCaseMenu;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;
import com.huashe.pizz.bean.HallCase.OneMenuHallCase;
import com.huashe.pizz.bean.HallCase.ResultHallCase;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductMenu;
import com.huashe.pizz.bean.ModuleProduct.OneMenuModuleProduct;
import com.huashe.pizz.bean.ModuleProduct.ResultModuleProduct;
import com.huashe.pizz.bean.PersonalCenter.DataBean;
import com.huashe.pizz.bean.PersonalCenter.UserInfoBean;
import com.huashe.pizz.bean.Result;
import com.huashe.pizz.bean.greendao.AboutUsDaoUtil;
import com.huashe.pizz.bean.greendao.DataBeanDaoUtil;
import com.huashe.pizz.bean.greendao.HallCaseBeanDaoUtil;
import com.huashe.pizz.bean.greendao.HallCaseMenuDaoUtil;
import com.huashe.pizz.bean.greendao.ModuleProductBeanDaoUtil;
import com.huashe.pizz.bean.greendao.ModuleProductMenuDaoUtil;
import com.huashe.pizz.contract.MainActivityContract;
import com.huashe.pizz.presenter.MainActivityPresenter;
import com.huashe.pizz.utils.Calback4Personal;
import com.huashe.pizz.utils.FileEnDecryptManager;
import com.huashe.pizz.utils.HttpUtil;
import com.huashe.pizz.utils.PersonRequestUtil;
import com.huashe.pizz.utils.QRCodeUtil;
import com.huashe.pizz.utils.SPUtils;
import com.huashe.pizz.utils.ToastUtil;
import com.huashe.pizz.wxapi.WXEntryActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<MainActivityContract.View, MainActivityPresenter> implements MainActivityContract.View {
    Unbinder unbinder;

    @BindView(R.id.top_rg_tab)
    RadioGroup rgTopTab;
    @BindView(R.id.rb_listsaver)
    public RadioButton rbListSave;
    @BindView(R.id.rb_personalcenter)
    RadioButton rbPersonalCenter;
    @BindView(R.id.rb_setting)
    RadioButton rbSetting;
    @BindView(R.id.rb_usernme)
    RadioButton rbUserName;
    @BindView(R.id.ll_home_btn)
    LinearLayout llHomeBtn;
    @BindView(R.id.content_fragment)
    FrameLayout contentFragment;

    @BindView(R.id.iv_top_logo)
    ImageView ivTopLogo;

    @BindView(R.id.home_btn_aboutus)
    Button homeBtnAboutUs;
    @BindView(R.id.home_btn_hallcase)
    Button homeBtnHallCase;
    @BindView(R.id.home_btn_moduleproduct)
    Button homeBtnModuleproduct;
    private MyApplication myApplication;
    private List<ModuleProductBean> BeanDatas;
    private String urlString = null;
    private AsyncTask asyncTask;

    private boolean isWebchatAvaliable() {
        //检测手机上是否安装了微信
        try {
            getPackageManager().getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Subscribe
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Request4PersonalInfo();
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun) {
//            Toast.makeText(MainActivity.this, "第一次运行", Toast.LENGTH_SHORT).show();
            initData();

            editor.putBoolean("isFirstRun", false);
            editor.commit();
        } else {
//            Toast.makeText(MainActivity.this, "不是第一次运行", Toast.LENGTH_SHORT).show();
        }
//        urlString = "http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/uploadphoto";
//        initTask();
//        asyncTask.execute();

        new RxPermissions(MainActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //所有权限都开启aBoolean才为true，否则为false
                            ToastUtil.showToast("权限已开启");
                        } else {
                            ToastUtil.showToast("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                        }
                    }
                });
//        if (!isWebchatAvaliable()) {
//            Toast.makeText(this, "请先安装微信", Toast.LENGTH_LONG).show();
//            return;
//        }
//            WXEntryActivity.webpagSharing(MyApplication.api,"http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/getMenu","您喜欢的产品","aaaa",BitmapFactory.decodeFile("/sdcard/pizz/pizzamenu/图标/解决方案/icon_sidebar_jjfa_sel.png"));
//        DownLoadAndDecZip task = new DownLoadAndDecZip("http://116.62.191.214:8080/jeeplus_zz/pizza/downLoadUpdate/copyFileZIP",Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator);
//        task.execute();

//        final Dialog mDialog = new Dialog(MainActivity.this, R.style.Dialog_Fullscreen);
//        //mDialog = new Dialog(MainActivity.this);
//        Window mWindow = mDialog.getWindow();
//        WindowManager.LayoutParams mParams = mWindow.getAttributes();
//        mParams.alpha = 1f;
//        mParams.gravity = Gravity.CENTER_HORIZONTAL;
//        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//        mParams.y = -200;
//        mParams.x = 0;
//        mWindow.setBackgroundDrawableResource(android.R.color.transparent);
//        mWindow.setAttributes(mParams);
////                mWindow.getDecorView().setBackgroundColor(Color.MAGENTA);
//        mWindow.getDecorView().setPadding(0, 0, 0, 0);
//        mWindow.getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
//        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_download, null);
//
//        mDialog.setContentView(inflate);
//        mDialog.setCancelable(true);
//        mDialog.setCanceledOnTouchOutside(true);
//        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                Window mWindow = getWindow();
//                WindowManager.LayoutParams mParams = mWindow.getAttributes();
//                mParams.alpha = 1.0f;
//                mWindow.setAttributes(mParams);
//            }
//        });
//        mDialog.show();
//        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Aria.download(this)
//                        .load("http://192.168.14.72:8080/jeeplus_zz/userfiles/images/pizza1.320190124141352.zip")
//                        .setDownloadPath(Environment.getExternalStorageDirectory().getPath() + "/test.zip")
//                        .start();
//            }
//        });

        myApplication = (MyApplication) this.getApplication();
        if (myApplication.userLoveLists.size() != 0) {
            rbListSave.setBackgroundResource(R.drawable.nav_icon_list_nor);
        }
        initView();
//        getAllFiles("/sdcard/pizz");
//        mPresenter.getMenu(false,"3");
        rgTopTab.check(R.id.rb_home);

    }

    //个人信息
    public void Request4PersonalInfo() {
        String userid = SPUtils.get(MyApplication.getInstance(), "UserId", "").toString();
        PersonRequestUtil.getInstance().Request4PersonalInfo(userid, new Calback4Personal() {
            @Override
            public void success(Object object) {
                System.out.println(" UserId   "+object.toString());
                UserInfoBean userInfoBean = (UserInfoBean) object;
                 DataBeanDaoUtil.deleteAll();
                DataBeanDaoUtil.insertDataBean(userInfoBean.getData());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rbUserName.setText(userInfoBean.getData().getName());
                    }
                });
            }

            @Override
            public void failed() {
                Log.e("error个人信息", "个人信息请求失败");
            }
        });
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    Map<String, String> map5 = new HashMap<>();
                    map5.put("version", "1.0");//2 产品 3 案例 1 我们 116.62.191.214
//                    Result result = gson.fromJson(HttpUtil.postRequest("http://192.168.14.72:8080/jeeplus_zz/pizza/downLoadUpdate/downloadName", map5), Result.class);
//                    System.out.println("qqqqqqqqqqqq " + result.isSuccess());

                    Map<String, String> map = new HashMap<>();
                    map.put("type", "2");//2 产品 3 案例 1 我们 116.62.191.214
                    ResultModuleProduct resultModuleProduct = gson.fromJson(HttpUtil.postRequest("http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/getMenu", map), ResultModuleProduct.class);
                    if (resultModuleProduct.isSuccess()) {
                        List<OneMenuModuleProduct> oneMenus = resultModuleProduct.getData();
                        List<ModuleProductMenu> productMenus = oneMenus.get(0).getContent();
                        for (ModuleProductMenu moduleProductMenu : productMenus) {
                            ModuleProductMenuDaoUtil.insertModuleProductMenu(moduleProductMenu);
                            List<ModuleProductBean> moduleProductBeans = moduleProductMenu.getProducts();
                            ModuleProductBean m = new ModuleProductBean();
                            m.setItemtype(2);
                            m.setId(moduleProductBeans.get(0).getId() + "+");
                            m.setSubname(moduleProductMenu.getName());
                            m.setType(moduleProductBeans.get(0).getType() + "+");
                            moduleProductBeans.add(0, m);
                            ModuleProductBean m1 = new ModuleProductBean();
                            m1.setItemtype(2);
                            m1.setId(moduleProductBeans.get(1).getId() + "++");
                            m1.setSubname("");
                            m1.setType(moduleProductBeans.get(1).getType() + "++");
                            moduleProductBeans.add(1, m1);
                            if (moduleProductBeans.size() % 2 == 1) {
                                ModuleProductBean m2 = new ModuleProductBean();
                                m2.setItemtype(1);
                                m2.setId(moduleProductBeans.get(2).getId() + "-");
                                m2.setType(moduleProductBeans.get(2).getType());
                                moduleProductBeans.add(moduleProductBeans.size(), m2);
                            }
                            for (ModuleProductBean moduleProductBean : moduleProductBeans) {
                                if (moduleProductBean.getItemtype() == 0) {
                                    moduleProductBean.setItemtype(1);
                                }
                                System.out.println("itemtype  " + moduleProductBean.getItemtype() + "      type  " + moduleProductBean.getId());
                                ModuleProductBeanDaoUtil.insertModuleProductBean(moduleProductBean);
                            }
                        }
                    }
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("type", "1");//2 产品 3 案例 1 我们  116. 62.191.214
                    ResultAboutUs resultAboutUs = gson.fromJson(HttpUtil.postRequest("http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/getMenu", map1), ResultAboutUs.class);
                    if (resultAboutUs.isSuccess()) {
                        List<AboutUsBean> aboutUsBeans = resultAboutUs.getData();
                        for (AboutUsBean aboutUsBean : aboutUsBeans) {
                            System.out.println("一级    " + aboutUsBean.getName());
                            AboutUsDaoUtil.insertAboutUsBean(aboutUsBean);
                            if (aboutUsBean.getContent() != null) {
                                for (AboutUsBean aboutUsBean1 : aboutUsBean.getContent()) {
                                    AboutUsDaoUtil.insertAboutUsBean(aboutUsBean1);
                                    System.out.println("二级    " + aboutUsBean1.getName());
                                    System.out.println(aboutUsBean1.getParentid());
                                    if (aboutUsBean1.getContent() != null) {
                                        for (AboutUsBean aboutUsBean2 : aboutUsBean1.getContent()) {
                                            AboutUsDaoUtil.insertAboutUsBean(aboutUsBean2);
                                            System.out.println("三级    " + aboutUsBean2.getName());
//                                            System.out.println(aboutUsBean2.getImages());
                                        }
                                    }

                                }
                            }
                        }
                    }
                    Map<String, String> map3 = new HashMap<>();
                    map3.put("type", "3");//2 产品 3 案例 1 我们 192.168.14.150
                    ResultHallCase resultHallCase = gson.fromJson(HttpUtil.postRequest("http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/getMenu", map3), ResultHallCase.class);
                    if (resultHallCase.isSuccess()) {
                        List<OneMenuHallCase> oneMenus = resultHallCase.getData();
                        List<HallCaseMenu> hallCaseMenus = oneMenus.get(0).getContent();
                        for (HallCaseMenu hallCaseMenu : hallCaseMenus) {
                            HallCaseMenuDaoUtil.insertHallCaseMenu(hallCaseMenu);
                            List<HallCaseBean> hallCaseBeans = hallCaseMenu.getDemo();
                            for (HallCaseBean hallCaseBean : hallCaseBeans) {
                                HallCaseBeanDaoUtil.insertHallCaseBean(hallCaseBean);
                            }
                        }
                    }

//                    Map<String, String> map4 = new HashMap<>();
//                    map4.put("name", "1");//2 产品 3 案例 1 我们
//                    map4.put("mobile","13838350239");
//                    map4.put("projectname","projectname1");
//                    map4.put("company","projectname");
//                    map4.put("products","asasaadsadafafsfdsaaaf");
//                    ResultHallCase resultHallCase = gson.fromJson(HttpUtil.postRequest("http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/setCustomLike", map4), ResultHallCase.class);
//                    System.out.println(resultHallCase);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                getAllFiles("/sdcard/pizz");
            }
        }).start();
    }

    /**
     * 获取指定目录内所有文件路径
     *
     * @param dirPath 需要查询的文件目录
     */
    public static void getAllFiles(String dirPath) {

        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return;
        }

        File[] files = f.listFiles();

        if (files == null) {//判断权限
            return;
        }
//        JSONArray fileList = new JSONArray();
        for (File _file : files) {//遍历目录
            if (_file.isFile()) {
                String _name = _file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                System.out.println(filePath);

                FileEnDecryptManager.getInstance().Initdecrypt(filePath);
                String fileName = _file.getName().substring(0, _name.length() - 4);//获取文件名
            } else if (_file.isDirectory()) {//查询子目录
                getAllFiles(_file.getAbsolutePath());
            }
        }
    }

    private void initView() {
        rgTopTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rbListSave.setChecked(false);
                rbPersonalCenter.setChecked(false);
                rbSetting.setChecked(false);
                rbUserName.setChecked(false);
                switch (i) {
                    case R.id.rb_home:
                        ivTopLogo.setImageResource(0);
                        contentFragment.setVisibility(View.GONE);
                        llHomeBtn.setVisibility(View.VISIBLE);
                        HomeFragment homeFragment = new HomeFragment();
                        //事务处理 fragment替换
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_fragment, homeFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.rb_aboutus:
                        ivTopLogo.setImageResource(R.drawable.logo_1);
                        contentFragment.setVisibility(View.VISIBLE);
                        llHomeBtn.setVisibility(View.GONE);
                        AboutUsFragment aboutUsFragment = new AboutUsFragment();
                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.content_fragment, aboutUsFragment);
                        fragmentTransaction2.commit();
                        break;
                    case R.id.rb_moduleproduct:
                        ivTopLogo.setImageResource(R.drawable.logo_1);
                        contentFragment.setVisibility(View.VISIBLE);
                        llHomeBtn.setVisibility(View.GONE);
                        ModuleProductFragment moduleProductFragment = new ModuleProductFragment();
                        FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.content_fragment, moduleProductFragment);
                        fragmentTransaction3.commit();
                        break;
                    case R.id.rb_hallcase:
                        ivTopLogo.setImageResource(R.drawable.logo_1);
                        contentFragment.setVisibility(View.VISIBLE);
                        llHomeBtn.setVisibility(View.GONE);
                        HallCaseFragment hallCaseFragment = new HallCaseFragment();
                        FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction4.replace(R.id.content_fragment, hallCaseFragment);
                        fragmentTransaction4.commit();
                        break;
                }
            }
        });
    }

    @OnClick({R.id.rb_listsaver, R.id.rb_personalcenter, R.id.rb_setting, R.id.rb_usernme, R.id.home_btn_hallcase, R.id.home_btn_aboutus, R.id.home_btn_moduleproduct})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_listsaver:
//                rgTopTab.clearCheck();
//                rbListSave.setChecked(true);
//                rbPersonalCenter.setChecked(false);
//                rbSetting.setChecked(false);
//                rbUserName.setChecked(false);
//                contentFragment.setVisibility(View.VISIBLE);
//                llHomeBtn.setVisibility(View.GONE);
//                ivTopLogo.setImageResource(R.drawable.logo_1);
                DialogGuestLove();
                break;
            case R.id.rb_personalcenter:
                rgTopTab.clearCheck();
                rbListSave.setChecked(false);
                rbPersonalCenter.setChecked(true);
                rbSetting.setChecked(false);
                rbUserName.setChecked(false);
                contentFragment.setVisibility(View.VISIBLE);
                llHomeBtn.setVisibility(View.GONE);
                ivTopLogo.setImageResource(R.drawable.logo_1);
                PersonalCenterFragment personalCenterFragment = new PersonalCenterFragment();
                FragmentTransaction fragmentTransaction7 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction7.replace(R.id.content_fragment, personalCenterFragment);
                fragmentTransaction7.commit();
                break;
            case R.id.rb_setting:
                rgTopTab.clearCheck();
                rbListSave.setChecked(false);
                rbPersonalCenter.setChecked(false);
                rbSetting.setChecked(true);
                rbUserName.setChecked(false);
                contentFragment.setVisibility(View.VISIBLE);
                llHomeBtn.setVisibility(View.GONE);
                ivTopLogo.setImageResource(R.drawable.logo_1);
                PersonalCenterFragment personalCenterFragment2 = new PersonalCenterFragment();
                FragmentTransaction fragmentTransaction8 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction8.replace(R.id.content_fragment, personalCenterFragment2);
                fragmentTransaction8.commit();
                break;
            case R.id.rb_usernme:
                rgTopTab.clearCheck();
                rbListSave.setChecked(false);
                rbPersonalCenter.setChecked(false);
                rbSetting.setChecked(false);
                rbUserName.setChecked(true);
                contentFragment.setVisibility(View.VISIBLE);
                llHomeBtn.setVisibility(View.GONE);
                ivTopLogo.setImageResource(R.drawable.logo_1);
                PersonalCenterFragment personalCenterFragment3 = new PersonalCenterFragment();
                FragmentTransaction fragmentTransaction9 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction9.replace(R.id.content_fragment, personalCenterFragment3);
                fragmentTransaction9.commit();
                break;
            case R.id.home_btn_aboutus:
                rgTopTab.check(R.id.rb_aboutus);
                contentFragment.setVisibility(View.VISIBLE);
                llHomeBtn.setVisibility(View.GONE);
                ivTopLogo.setImageResource(R.drawable.logo_1);
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.content_fragment, aboutUsFragment);
                fragmentTransaction2.commit();
                break;
            case R.id.home_btn_hallcase:
                rgTopTab.check(R.id.rb_hallcase);
                contentFragment.setVisibility(View.VISIBLE);
                llHomeBtn.setVisibility(View.GONE);
                ivTopLogo.setImageResource(R.drawable.logo_1);
                HallCaseFragment hallCaseFragment = new HallCaseFragment();
                FragmentTransaction fragmentTransaction5 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction5.replace(R.id.content_fragment, hallCaseFragment);
                fragmentTransaction5.commit();
                break;
            case R.id.home_btn_moduleproduct:
                rgTopTab.check(R.id.rb_moduleproduct);
                contentFragment.setVisibility(View.VISIBLE);
                llHomeBtn.setVisibility(View.GONE);
                ivTopLogo.setImageResource(R.drawable.logo_1);
                ModuleProductFragment moduleProductFragment = new ModuleProductFragment();
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.content_fragment, moduleProductFragment);
                fragmentTransaction3.commit();
                break;
        }
    }

    private void DialogGuestLove() {
        final Dialog mDialog = new Dialog(MainActivity.this, R.style.Dialog_Fullscreen);
        //mDialog = new Dialog(MainActivity.this);
        Window mWindow = mDialog.getWindow();
        WindowManager.LayoutParams mParams = mWindow.getAttributes();
        mParams.gravity = Gravity.TOP;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.y = 120;
        mWindow.setBackgroundDrawableResource(android.R.color.transparent);
        mWindow.setAttributes(mParams);
//                mWindow.getDecorView().setBackgroundColor(Color.MAGENTA);
        mWindow.getDecorView().setPadding(0, 0, 0, 0);
        mWindow.getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_guest_love, null);
        mDialog.setContentView(inflate);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
        BeanDatas = myApplication.userLoveLists;
        TextView tvGuestLove = inflate.findViewById(R.id.tv_guest_love);
        RecyclerView rvGuestLove = inflate.findViewById(R.id.rv_guest_love);
        if (BeanDatas.size() == 0) {
            tvGuestLove.setVisibility(View.VISIBLE);
            rvGuestLove.setVisibility(View.GONE);
        } else {
            tvGuestLove.setVisibility(View.GONE);
            rvGuestLove.setVisibility(View.VISIBLE);
            rvGuestLove.setLayoutManager(new LinearLayoutManager(this));
            rvGuestLove.setAdapter(new CommonRecycleAdapter<ModuleProductBean>(this, BeanDatas, R.layout.item_guest_love) {
                @Override
                public void bindData(CommonViewHolder holder, final ModuleProductBean data) {
                    if (data.getDescription().size() != 0) {
                        holder.setText(R.id.tv_guest_love_title, data.getName())
                                .setText(R.id.tv_guest_love_content, data.getDescription().get(0).getContent());
                        holder.setImageResourceBitmap(R.id.iv_guest_love, BitmapFactory.decodeFile(data.getImage()));
                        holder.getView(R.id.iv_guest_love_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BeanDatas.remove(data);
                                EventBus.getDefault().post("refresh");
                                if (BeanDatas.size() == 0) {
                                    rbListSave.setBackgroundResource(R.drawable.nav_icon_list_default);
                                }
                                notifyDataSetChanged();
                                data.setLove(false);
                            }
                        });
                    }

                }
            });
        }
        Button btnGuestLoveBack = inflate.findViewById(R.id.btn_guest_love_back);
        Button btnGuestLoveQrcode = inflate.findViewById(R.id.btn_guest_love_qrcode);
        Button btnGuestLoveBSave = inflate.findViewById(R.id.btn_guest_love_save);
        Button btnGuestLoveShare = inflate.findViewById(R.id.btn_guest_love_share);
        btnGuestLoveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        btnGuestLoveQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BeanDatas.size() != 0) {
                    StringBuffer sb = new StringBuffer("ids=");
                    for (ModuleProductBean moduleProductBean : BeanDatas) {
                        sb.append(moduleProductBean.getId() + ",");
                    }//116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/yourInterestedCommodity?ids=
                    String url = "http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/yourInterestedCommodity?" + sb.substring(0, sb.length() - 1).toString();
                    Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(url, 480, 480);

                    final Dialog shareDialog = new Dialog(MainActivity.this, R.style.Dialog_Fullscreen);
                    Window shareWindow = shareDialog.getWindow();
                    WindowManager.LayoutParams mParams = shareWindow.getAttributes();
                    mParams.gravity = Gravity.TOP;
                    mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    mParams.y = 120;
                    shareWindow.setBackgroundDrawableResource(android.R.color.transparent);
                    shareWindow.setAttributes(mParams);
                    shareWindow.getDecorView().setPadding(0, 0, 0, 0);
                    shareWindow.getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
                    final View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_guest_love_qrcode, null);
                    shareDialog.setContentView(inflate);
                    shareDialog.setCanceledOnTouchOutside(true);
                    shareDialog.show();
                    ((ImageView) inflate.findViewById(R.id.iv_qrcode)).setImageBitmap(mBitmap);
                    Button btnQRcodeClose = inflate.findViewById(R.id.btn_qrcode_close);
                    btnQRcodeClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareDialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "暂无任何数据可以分享", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnGuestLoveBSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(BeanDatas);
                if (BeanDatas.size() != 0) {
                    final Dialog saveDialog = new Dialog(MainActivity.this, R.style.Dialog_Fullscreen);
                    Window saveWindow = saveDialog.getWindow();
                    WindowManager.LayoutParams mParams = saveWindow.getAttributes();
                    mParams.gravity = Gravity.TOP;
                    mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    mParams.y = 120;
                    saveWindow.setBackgroundDrawableResource(android.R.color.transparent);
                    saveWindow.setAttributes(mParams);
                    saveWindow.getDecorView().setPadding(0, 0, 0, 0);
                    saveWindow.getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
                    final View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_guest_love_save, null);
                    saveDialog.setContentView(inflate);
                    saveDialog.setCanceledOnTouchOutside(true);
                    saveDialog.show();
                    Button btnSaveCancle = inflate.findViewById(R.id.btn_guest_love_save_cancle);
                    btnSaveCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveDialog.dismiss();
                        }
                    });
                    Button btnSaveSave = inflate.findViewById(R.id.btn_guest_love_save_save);
                    btnSaveSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!"".equals(((EditText) inflate.findViewById(R.id.et_guest_name)).getText())) {
                                if (!"".equals(((EditText) inflate.findViewById(R.id.et_company_name)).getText())) {
                                    if (!"".equals(((EditText) inflate.findViewById(R.id.et_project_name)).getText())) {
                                        if (!"".equals(((EditText) inflate.findViewById(R.id.et_guest_phone)).getText())) {

                                            try {
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            Map<String, String> map = new HashMap();
                                                            String userid = SPUtils.get(MyApplication.getInstance(), "UserId", "").toString();
                                                            map.put("userid", userid);
                                                            map.put("name", ((EditText) inflate.findViewById(R.id.et_guest_name)).getText().toString());
                                                            map.put("company", ((EditText) inflate.findViewById(R.id.et_company_name)).getText().toString());
                                                            map.put("projectname", ((EditText) inflate.findViewById(R.id.et_project_name)).getText().toString());
                                                            map.put("mobile", ((EditText) inflate.findViewById(R.id.et_guest_phone)).getText().toString());
                                                            StringBuffer sb = new StringBuffer();
                                                            for (ModuleProductBean moduleProductBean : BeanDatas) {
                                                                sb.append(moduleProductBean.getId() + ",");
                                                            }
                                                            map.put("products", sb.substring(0, sb.length() - 1).toString());
                                                            // TODO 为了方便测试， 这里先改成58的，后面需要修改成 116.62.191.214
                                                            Result result = new Gson().fromJson(HttpUtil.postRequest("http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/setCustomLikeVal", map), Result.class);
                                                            if (result.isSuccess()) {
                                                                saveDialog.dismiss();
                                                                mDialog.dismiss();
                                                                BeanDatas.clear();
                                                                EventBus.getDefault().post("refresh");
                                                                rbListSave.setBackgroundResource(R.drawable.nav_icon_list_default);

                                                            } else {
                                                                ToastUtil.showToast("服务异常请稍后再试");
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).start();
//                                                Toast.makeText(MainActivity.this, result.getErrorCode(), Toast.LENGTH_LONG).show();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(MainActivity.this, "手机号码不能为空", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "项目名称不能为空", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "公司名称不能为空", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "客户名称不能为空", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "暂无任何数据需要提交", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnGuestLoveShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BeanDatas.size() != 0) {
                    StringBuffer sb = new StringBuffer("ids=");
                    for (ModuleProductBean moduleProductBean : BeanDatas) {
                        sb.append(moduleProductBean.getId() + ",");
                    }//116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/yourInterestedCommodity?ids=
                    String url = "http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/yourInterestedCommodity?" + sb.substring(0, sb.length() - 1).toString();
                    WXEntryActivity.webpagSharing(MyApplication.api, url, "我喜欢", "收藏产品列表", BitmapFactory.decodeResource(getResources(), R.drawable.arrow_left));
//                final Dialog qrcodeDialog = new Dialog(MainActivity.this, R.style.Dialog_Fullscreen);
//                Window qrcodeWindow = qrcodeDialog.getWindow();
//                WindowManager.LayoutParams mParams = qrcodeWindow.getAttributes();
//                mParams.gravity = Gravity.TOP;
//                mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//                mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                mParams.y = 120;
//                qrcodeWindow.setBackgroundDrawableResource(android.R.color.transparent);
//                qrcodeWindow.setAttributes(mParams);
//                qrcodeWindow.getDecorView().setPadding(0, 0, 0, 0);
//                qrcodeWindow.getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
//                View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_guest_love_qrcode, null);
//                qrcodeDialog.setContentView(inflate);
//                qrcodeDialog.setCanceledOnTouchOutside(true);
//                qrcodeDialog.show();
//                Button btnQRcodeClose = inflate.findViewById(R.id.btn_qrcode_close);
//                btnQRcodeClose.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        qrcodeDialog.dismiss();
//                    }
//                });
                } else {
                    Toast.makeText(MainActivity.this, "暂无任何数据可以分享", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void getMenuSucess(List<AboutUsBean> list) {
        System.out.println(list);
    }

    @Override
    public void onFail(String err) {
        System.out.println(err);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }


}
