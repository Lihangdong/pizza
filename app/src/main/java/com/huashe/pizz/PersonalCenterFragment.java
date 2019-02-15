package com.huashe.pizz;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;
import com.huashe.pizz.bean.PersonalCenter.DataBean;
import com.huashe.pizz.bean.PersonalCenter.PersonalLikeListBean;
import com.huashe.pizz.bean.PersonalCenter.UpdateBean;
import com.huashe.pizz.bean.PersonalCenter.UserInfoBean;
import com.huashe.pizz.bean.greendao.DataBeanDaoUtil;
import com.huashe.pizz.bean.greendao.ModuleProductBeanDaoUtil;
import com.huashe.pizz.other.adapter.LikeDetailAdapter;
import com.huashe.pizz.other.entity.Adapter;
import com.huashe.pizz.other.entity.DataItem;
import com.huashe.pizz.other.entity.LikeDetailEntity;
import com.huashe.pizz.other.expandable.Group;
import com.huashe.pizz.other.expandable.Item;
import com.huashe.pizz.other.expandable.MyBaseExpandableListAdapter;
import com.huashe.pizz.other.service.LoginService;
import com.huashe.pizz.other.util.Constant;
import com.huashe.pizz.utils.Calback4Personal;
import com.huashe.pizz.utils.PersonRequestUtil;
import com.huashe.pizz.utils.PhotoUtils;
import com.huashe.pizz.utils.QRCodeUtil;
import com.huashe.pizz.utils.SPUtils;
import com.huashe.pizz.utils.ToastUtil;
import com.huashe.pizz.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class PersonalCenterFragment extends Fragment {


    @BindView(R.id.img1)
    ImageView img1;

    @BindView(R.id.zhuxiao)
    Button zhuxiao;
    @BindView(R.id.personal_center_content_Layout)
    LinearLayout personalCenterContentLayout;
    Unbinder unbinder;
    @BindView(R.id.menu_item1)
    RelativeLayout menuItem1;
    @BindView(R.id.menu_item2)
    RelativeLayout menuItem2;
    @BindView(R.id.menu_item3)
    RelativeLayout menuItem3;
    @BindView(R.id.menu_item4)
    RelativeLayout menuItem4;
    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    View menu_view_1, menu_view_2, menu_view_3, menu_view_4;
    MyBaseExpandableListAdapter myAdapter;
    int tagPosition = 1;
    ExpandableListView expandableListView;
    TextView gitLog4Update_tv;
    //个人信息界面
    CircleImageView user_image;
    TextView userName_txt, user_phone_txt, userEmail_txt, userDepart_txt, userStation_txt;
    PersonalCenterFragment personalCenterFragment;
    UserInfoBean userInfoBean;
    MyHandle myHandle;
    PersonalLikeListBean personalLikeListBean;
    String userid;
    UpdateBean updateBean;
    Uri newUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }


        myHandle = new MyHandle();
        adapter = new Adapter(listData, getActivity());
        userid = SPUtils.get(MyApplication.getInstance(), "UserId", "").toString();
//        new Thread(() -> Request4PersonalInfo()).start();
        userInfoBean=new UserInfoBean();
        userInfoBean.setSuccess("true");
        userInfoBean.setCode(0);
        userInfoBean.setData(DataBeanDaoUtil.queryAll().get(0));
        myHandle.sendEmptyMessageAtTime(1, 0);
        Request4KeHuLikeList();
        updateNetRequest();

        unbinder = ButterKnife.bind(this, view);
        personalCenterFragment = this;
        initView();
        return view;
    }

    public void initView() {
        menu_view_1 = LayoutInflater.from(getActivity()).inflate(R.layout.personal_info_view, null);
        user_image = menu_view_1.findViewById(R.id.user_image);
        userName_txt = menu_view_1.findViewById(R.id.userName_txt);
        user_phone_txt = menu_view_1.findViewById(R.id.user_phone_txt);
        userEmail_txt = menu_view_1.findViewById(R.id.userEmail_txt);
        userDepart_txt = menu_view_1.findViewById(R.id.userDepart_txt);
        userStation_txt = menu_view_1.findViewById(R.id.userStation_txt);
        user_image.setOnClickListener(view -> showUserImageChooseDialog());
        menu_view_1.findViewById(R.id.qr_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View DialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_imag_layout, null);
                ImageView imageView = DialogView.findViewById(R.id.dialog_img);
                imageView.setBackgroundResource(R.drawable.erweima);
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setView(DialogView)
                        .create();

                alertDialog.show();

                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
                android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
                p.height = 400;   //高度设置为屏幕的0.3
                p.width = 400;
                alertDialog.getWindow().setAttributes(p);
                imageView.setOnClickListener(view1 -> alertDialog.dismiss());
            }
        });
        personalCenterContentLayout.addView(menu_view_1);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myHandle.removeCallbacksAndMessages(null);
        unbinder.unbind();

    }

    @OnClick(R.id.zhuxiao)
    public void onViewClicked() {
        //DialogMargin();
        // Toast.makeText(getActivity(), "注销", Toast.LENGTH_SHORT).show();


    }


    //这里是dialog调试
    public void DialogMargin() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.margin_dialog_layout, null);
        AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialog)
                .setView(view)
                .create();
        dialog.show();

    }


    Adapter adapter;
    List<PersonalLikeListBean.DataBean> listData = new ArrayList<>();

    @OnClick({R.id.menu_item1, R.id.menu_item2, R.id.menu_item3, R.id.menu_item4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_item1:
                if (menu_view_1 == null) {
                    menu_view_1 = LayoutInflater.from(getActivity()).inflate(R.layout.personal_info_view, null);
                }

                personalCenterContentLayout.removeAllViews();
                personalCenterContentLayout.addView(menu_view_1);
                menuItem1.setBackgroundResource(R.color.menu_selected);
                menuItem1.findViewById(R.id.img1).setBackgroundResource(R.drawable.icon_login_user_sel);
                ((TextView) menuItem1.findViewById(R.id.pMenu_txt1)).setTextColor(getResources().getColor(R.color.personal_menu_text_color));
                if (tagPosition != 1) {
                    changeMenu_Color();
                }
                tagPosition = 1;
                break;
            case R.id.menu_item2:
                if (menu_view_2 == null) {
                    menu_view_2 = LayoutInflater.from(getActivity()).inflate(R.layout.personal_like_view, null);
                }
                ListView listView = menu_view_2.findViewById(R.id.likeList);


                final List<ModuleProductBean> productBeanList = new ArrayList<>();
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((adapterView, view12, i, l) -> {

                    if (listData != null && listData.get(i) != null) {
                        String[] stringArray = listData.get(i).getProducts().split(",");
                        for (String id : stringArray) {
                            ModuleProductBean queryModuleProductBean = ModuleProductBeanDaoUtil.queryModuleProductBeanById(id);
                            if (queryModuleProductBean != null) {
                                productBeanList.add(queryModuleProductBean);
                            }
                        }
                    }
                    if (productBeanList.size() > 0) {
                        showLikeDetailDialog(productBeanList);
                    } else {
                        Toast.makeText(getActivity(), "暂无相关信息", Toast.LENGTH_SHORT).show();
                    }


                });
                personalCenterContentLayout.removeAllViews();
                personalCenterContentLayout.addView(menu_view_2);
                menuItem2.setBackgroundResource(R.color.menu_selected);
                menuItem2.findViewById(R.id.img2).setBackgroundResource(R.drawable.personalcenter_icon_userlike_sel);
                ((TextView) menuItem2.findViewById(R.id.pMenu_txt2)).setTextColor(getResources().getColor(R.color.personal_menu_text_color));
                if (tagPosition != 2) {
                    changeMenu_Color();
                }
                tagPosition = 2;
                break;
            case R.id.menu_item3:
                if (menu_view_3 == null) {
                    menu_view_3 = LayoutInflater.from(getActivity()).inflate(R.layout.personal_update_view, null);
                }

                expandableListView = menu_view_3.findViewById(R.id.expandableList);

                gitLog4Update_tv = menu_view_3.findViewById(R.id.git_log);
                gitLog4Update_tv.setOnClickListener(view1 -> {
                    View logsView = LayoutInflater.from(getActivity()).inflate(R.layout.update_detail_logs, null);
                    personalCenterContentLayout.removeAllViews();
                    personalCenterContentLayout.addView(logsView);

                    logsView.findViewById(R.id.back_update_log_tv).setOnClickListener(view11 -> {
                        personalCenterContentLayout.removeAllViews();
                        personalCenterContentLayout.addView(menu_view_3);
                    });
                });

                gData = new ArrayList<>();
                iData = new ArrayList<>();

                //TODO
                gData.add(new Group("当前系统版本："));
                gData.add(new Group("当前内容版本："));

                lData = new ArrayList<>();

                //这个是子菜单数据
                lData.add(new Item(R.drawable.albicocca, getString(R.string.neirong_update), updateBean.getData().getContent()));

                iData.add(lData);

                lData = new ArrayList<>();
                lData.add(new Item(R.drawable.banana, getString(R.string.neirong_update), ""));
//                lData.add(new Item(R.mipmap.cocco, "安妮"));
//                lData.add(new Item(R.mipmap.arancia, "天使"));

                iData.add(lData);
                //TANK组


                myAdapter = new MyBaseExpandableListAdapter(gData, iData, getActivity());
                expandableListView.setAdapter(myAdapter);
                expandableListView.setIndicatorBounds(expandableListView.getWidth() - 140, expandableListView.getWidth() - 10);

                personalCenterContentLayout.removeAllViews();
                personalCenterContentLayout.addView(menu_view_3);
                menuItem3.setBackgroundResource(R.color.menu_selected);
                menuItem3.findViewById(R.id.img3).setBackgroundResource(R.drawable.personalcenter_icon_update_sel);
                ((TextView) menuItem3.findViewById(R.id.pMenu_txt3)).setTextColor(getResources().getColor(R.color.personal_menu_text_color));
                if (tagPosition != 3) {
                    changeMenu_Color();
                }
                tagPosition = 3;

                break;
            case R.id.menu_item4:
                if (menu_view_4 == null) {
                    menu_view_4 = LayoutInflater.from(getActivity()).inflate(R.layout.personal_setting_view, null);
                }
                personalCenterContentLayout.removeAllViews();
                personalCenterContentLayout.addView(menu_view_4);
                menuItem4.setBackgroundResource(R.color.menu_selected);
                menuItem4.findViewById(R.id.img4).setBackgroundResource(R.drawable.personalcenter_icon_set_sel);
                ((TextView) menuItem4.findViewById(R.id.pMenu_txt4)).setTextColor(getResources().getColor(R.color.personal_menu_text_color));
                if (tagPosition != 4) {
                    changeMenu_Color();
                }
                tagPosition = 4;
                break;
        }
    }

    public void changeMenu_Color() {

        if (tagPosition == 1) {
            menuItem1.setBackgroundResource(R.color.menu_black);
            menuItem1.findViewById(R.id.img1).setBackgroundResource(R.drawable.icon_login_user_nor);
            ((TextView) menuItem1.findViewById(R.id.pMenu_txt1)).setTextColor(getResources().getColor(R.color.personal_text_color));

        } else if (tagPosition == 2) {
            menuItem2.setBackgroundResource(R.color.menu_black);
            menuItem2.findViewById(R.id.img2).setBackgroundResource(R.drawable.personalcenter_icon_userlike_nor);
            ((TextView) menuItem2.findViewById(R.id.pMenu_txt2)).setTextColor(getResources().getColor(R.color.personal_text_color));
        } else if (tagPosition == 3) {
            menuItem3.setBackgroundResource(R.color.menu_black);
            menuItem3.findViewById(R.id.img3).setBackgroundResource(R.drawable.personalcenter_icon_update_nor);
            ((TextView) menuItem3.findViewById(R.id.pMenu_txt3)).setTextColor(getResources().getColor(R.color.personal_text_color));
        } else {
            menuItem4.setBackgroundResource(R.color.menu_black);
            menuItem4.findViewById(R.id.img4).setBackgroundResource(R.drawable.personalcenter_icon_set_nor);

            ((TextView) menuItem4.findViewById(R.id.pMenu_txt4)).setTextColor(getResources().getColor(R.color.personal_text_color));
        }

    }


    private void showUserImageChooseDialog() {

        View CamerachooseView = LayoutInflater.from(getActivity()).inflate(R.layout.camera_choose_layout, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialog)
                .setView(CamerachooseView)
                .create();
        alertDialog.show();
        CamerachooseView.findViewById(R.id.close).setOnClickListener(view -> alertDialog.dismiss());
        CamerachooseView.findViewById(R.id.tuku).setOnClickListener(view -> {
            autoObtainStoragePermission();
            alertDialog.dismiss();
        });
        CamerachooseView.findViewById(R.id.paizhao).setOnClickListener(view -> {
            autoObtainCameraPermission();
            alertDialog.dismiss();
        });
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = Objects.requireNonNull(alertDialog.getWindow()).getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.5);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.5);
//        p.x=30;
//        p.y=30;

        alertDialog.getWindow().setAttributes(p);

    }

    private void showLikeDetailDialog(List<ModuleProductBean> moduleProductBeanList) {


        LikeDetailAdapter likeDetailAdapter = new LikeDetailAdapter(moduleProductBeanList, getActivity());

        View likeDetailView = LayoutInflater.from(getActivity()).inflate(R.layout.like_dialog, null);

        ListView detailListView = likeDetailView.findViewById(R.id.detail_list);
        TextView textView = likeDetailView.findViewById(R.id.save_button_txt);


        detailListView.setAdapter(likeDetailAdapter);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialog)

                .setView(likeDetailView)
                .create();

        alertDialog.show();


        likeDetailView.findViewById(R.id.close_likedetail_img).setOnClickListener(view -> alertDialog.dismiss());

        textView.setOnClickListener(view -> {
            MyApplication myApplication = (MyApplication) getActivity().getApplication();
            if (myApplication.userLoveLists.size() != 0) {
                ToastUtil.showToast("当前清单中尚有未保存数据");
            }else {
                myApplication.userLoveLists=moduleProductBeanList;
                ((MainActivity)getActivity()).rbListSave.setBackgroundResource(R.drawable.nav_icon_list_nor);
            }
        });


    }


    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;
    private static final String TAG = "PersonalCenterFragment";

    /**
     * 动态申请sdcard读写权限
     */
    private void autoObtainStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                PhotoUtils.openPic(personalCenterFragment, CODE_GALLERY_REQUEST);
            }
        } else {
            PhotoUtils.openPic(personalCenterFragment, CODE_GALLERY_REQUEST);
        }
    }

    /**
     * 申请访问相机权限
     */
    private void autoObtainCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    ToastUtils.showShort(getActivity(), "您已经拒绝过一次");
                }
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
            } else {//有权限直接调用系统相机拍照
                if (hasSdcard()) {
                    imageUri = Uri.fromFile(fileUri);
                    //通过FileProvider创建一个content类型的Uri
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(getActivity(), "com.huashe.pizza", fileUri);
                    }
                    PhotoUtils.takePicture(personalCenterFragment, imageUri, CODE_CAMERA_REQUEST);
                } else {
                    ToastUtils.showShort(getActivity(), "设备没有SD卡！");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //通过FileProvider创建一个content类型的Uri
                            imageUri = FileProvider.getUriForFile(getActivity(), "com.huashe.pizza", fileUri);
                        }
                        PhotoUtils.takePicture(personalCenterFragment, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(getActivity(), "设备没有SD卡！");
                    }
                } else {
                    ToastUtils.showShort(getActivity(), "请允许打开相机！！");
                }
                break;
            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(personalCenterFragment, CODE_GALLERY_REQUEST);
                } else {
                    ToastUtils.showShort(getActivity(), "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode + "  resultCode:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "onActivityResult: resultCode!=RESULT_OK");
            return;
        }
        switch (requestCode) {
            //相机返回
            case CODE_CAMERA_REQUEST:
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(personalCenterFragment, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                break;
            //相册返回
            case CODE_GALLERY_REQUEST:

                if (hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    newUri = Uri.parse(PhotoUtils.getPath(getActivity(), data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(getActivity(), "com.huashe.pizza", new File(newUri.getPath()));
                    }
                    PhotoUtils.cropImageUri(personalCenterFragment, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                } else {
                    ToastUtils.showShort(getActivity(), "设备没有SD卡！");
                }
                break;
            //裁剪返回
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, getActivity());
                if (bitmap != null) {
                    showImages(bitmap);
                    new Thread(() -> upLoad2Server(cropImageUri)).start();
                }
                break;
            default:
        }
    }

    private void showImages(Bitmap bitmap) {
        user_image.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    //喜好产品
    public void Request4Product(String likeid) {
        // String likeid= "ad4096eeabc04f689af0802d3b4ea7d4";
        PersonRequestUtil.getInstance().Request4KeHuLikeProductInfo(likeid, new Calback4Personal() {
            @Override
            public void success(Object object) {
                //TODO  这里取得 喜好详情数据然后在dialog中显示

                Message message = new Message();
                message.obj = object;
                message.what = 3;
                myHandle.sendMessageAtTime(message, 0);
            }

            @Override
            public void failed() {
                Log.e("喜好产品", "个人信息请求失败");
            }
        });
    }

    //喜好列表
    public void Request4KeHuLikeList() {
        String userid = SPUtils.get(MyApplication.getInstance(), "UserId", "").toString();

        PersonRequestUtil.getInstance().Request4KeHuLikeList(userid, new Calback4Personal() {
            @Override
            public void success(Object object) {

                Gson gson = new Gson();//jsonObject.get("data")
                personalLikeListBean = gson.fromJson(object.toString(), PersonalLikeListBean.class);
                myHandle.sendEmptyMessage(2);
            }

            @Override
            public void failed() {
                Log.e("喜好列表", "个人信息请求失败");
            }
        });
    }

//    //个人信息
//    public void Request4PersonalInfo() {
//        String userid = SPUtils.get(MyApplication.getInstance(), "UserId", "").toString();
//        PersonRequestUtil.getInstance().Request4PersonalInfo(userid, new Calback4Personal() {
//            @Override
//            public void success(Object object) {
//                userInfoBean = (UserInfoBean) object;
//                myHandle.sendEmptyMessageAtTime(1, 0);
//            }
//
//            @Override
//            public void failed() {
//                Log.e("error个人信息", "个人信息请求失败");
//            }
//        });
//    }


    public void saveCustomLikeNetRequest(Map<String, String> saveMap) {

        PersonRequestUtil.getInstance().saveCustomLikeNetRequest(saveMap, new Calback4Personal() {
            @Override
            public void success(Object object) {
                userInfoBean = (UserInfoBean) object;
                // myHandle.sendEmptyMessageAtTime(1,0);
            }

            @Override
            public void failed() {
                Log.e("error个人信息", "个人信息请求失败");
            }
        });
    }

    //版本更新请求
    public void updateNetRequest() {
        String version = "1.0";
        PersonRequestUtil.getInstance().UpdateNetRequest(version, new Calback4Personal() {
            @Override
            public void success(Object object) {
                updateBean = (UpdateBean) object;
                Message message = new Message();
                message.what = 4;
                message.obj = updateBean;
                myHandle.sendEmptyMessageAtTime(4, 0);
            }

            @Override
            public void failed() {
                Log.e("error版本更新", "版本更新失败");
            }
        });
    }


    public void loadViewData4UserInfo() {
        try {
            //TODO 在这里加载二维码
//            String url = "http://116.62.191.214:8080/jeeplus_zz/pizza/pizzaInterface/yourInterestedCommodity?" + sb.substring(0, sb.length() - 1).toString();
//            Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(url, 480, 480);
            user_image.setImageBitmap(BitmapFactory.decodeFile("/sdcard/crop_photo.jpg"));
            user_phone_txt.setText(userInfoBean.getData().getMobile());
            userDepart_txt.setText(userInfoBean.getData().getDepartment());
            userName_txt.setText(userInfoBean.getData().getName());
            userStation_txt.setText(userInfoBean.getData().getStation());
            userEmail_txt.setText(userInfoBean.getData().getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadViewData4KehuLikeInfo() {
        if (personalLikeListBean.getSuccess().equals("true")) {
            if (personalLikeListBean.getData().size() > 0) {
                listData.addAll(personalLikeListBean.getData());
                adapter.notifyDataSetChanged();
            }

        }

    }


    public void loadUPdateView() {
        //TODO
    }

    class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {//通知更新 个人信息界面
                loadViewData4UserInfo();
            } else if (msg.what == 2) {// 更新客户喜好列表
                loadViewData4KehuLikeInfo();
            } else if (msg.what == 3) {//这里显示喜好详情 dialog

            } else if (msg.what == 4) {//版本更新
                loadUPdateView();
            }
        }
    }


    //图片上传
    private void upLoad2Server(Uri uri) {
        File file = null;
        try {
            file = new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        PersonRequestUtil.getInstance()
                .upload(SPUtils.get(MyApplication.getInstance(), "mobile", "").toString(), body, new Calback4Personal() {
                    @Override
                    public void success(Object object) {
                        try {
                            JSONObject jsonObject = new JSONObject(object.toString());
                            if (jsonObject.getString("success").equals("true")) {

                                Toast.makeText(getActivity(), "图片已上传", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getActivity(), "传递数据存在异常", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failed() {
                        Toast.makeText(getActivity(), "图片上传异常", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //文件下载
    public void downLoadFile() {


    }


}
