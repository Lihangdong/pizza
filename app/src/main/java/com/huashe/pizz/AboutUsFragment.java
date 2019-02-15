package com.huashe.pizz;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huashe.pizz.bean.AboutUs.AboutUsBean;
import com.huashe.pizz.bean.greendao.AboutUsDaoUtil;
import com.huashe.pizz.other.util.VerticalViewPager;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment implements View.OnClickListener {

    float mPosX = 0;
    float mPosY = 0;
    float mCurPosY = 0;
    float mCurPosX = 0;

    static int menu_last_position = 0; //左侧菜单中，上一个被选中的位置

    int viewPager4Menu_CurrentPosition = 0;// 当前显示的是 第几页

    Unbinder unbinder;
    View FragmentView, SulutionView;
    Banner banner;
    VerticalViewPager viewPager;
    LinearLayout Switch_layout4Our;

    static RelativeLayout menu0_our, menu1_our, menu2_our, menu3_our, menu4_our, menu5_our;
    static View[] menu_our_buttons,dian_view_List;
    List<View> aList;
    View FangAnLiJieView, ChengGongAnLiView, MoKuaiHuaChanPinView, YiTingYiAnView;
    LayoutInflater layoutInflater;

    @BindView(R.id.img0)
    ImageView img0;
    @BindView(R.id.pMenu_txt0)
    TextView pMenuTxt0;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.pMenu_txt1)
    TextView pMenuTxt1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.pMenu_txt2)
    TextView pMenuTxt2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.pMenu_txt3)
    TextView pMenuTxt3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.pMenu_txt4)
    TextView pMenuTxt4;
    @BindView(R.id.img5)
    ImageView img5;
    @BindView(R.id.pMenu_txt5)
    TextView pMenuTxt5;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mkOryy)
    TextView mkOryy;
    @BindView(R.id.mkOryy_layout)
    LinearLayout mkOryyLayout;
    //  List<AboutUsBean> aboutUsBeans;
    String[] us_arry, falj_array, cgal_array, jjfa_array,mkhcp_array,yayt_array;
    AboutUsBean our_AboutusBean, falj_AboutusBean, cgal_AboutusBean, jjfa_AboutusBean, yTyA_AboutusBean, mKh_AboutusBean;
    @BindView(R.id.dian_layout)
    LinearLayout dianLayout;
    RelativeLayout  mkh_layout,ytya_layout;
    final int JJFA_jieshaoye_qrray=1;
    ImageView imageView;

    int maxArryNumber4Point=7;//设置最大点数，初始化点数根据菜单要显示内容页数而定

    boolean us_block=false,falj_block=false,cgal_block=false,mkh_block=false,ytya_block=false;// 在页面切换的时候减少不必要的代码执行，有些代码只执行一次

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentView = inflater.inflate(R.layout.fragment_about_us, container, false);
        viewPager = FragmentView.findViewById(R.id.viewPager);
        layoutInflater = getLayoutInflater();
        unbinder = ButterKnife.bind(this, FragmentView);

        List<AboutUsBean> aboutUsBeans = AboutUsDaoUtil.queryAll();
        for (AboutUsBean aboutUsBean : aboutUsBeans) {
            System.out.println("一条数据开始" + "=============");
            System.out.println("一级getName=" + aboutUsBean.getName());
            System.out.println("一级id=" + aboutUsBean.getId());
            System.out.println("一级getParentid=" + aboutUsBean.getParentid());
            System.out.println("一级getContent=" + aboutUsBean.getContent());
            System.out.println("一级getImages=" + aboutUsBean.getImages());
            System.out.println("一级getIcon1=" + aboutUsBean.getIcon1());
            System.out.println("一级getIco2=" + aboutUsBean.getIco2());
            System.out.println("一级getSort=" + aboutUsBean.getSort());
            System.out.println("一条数据结束" + "=============");
            System.out.println(" " + "  ");
            if (aboutUsBean.getContent() != null) {
                for (AboutUsBean aboutUsBean1 : aboutUsBean.getContent()) {
                    System.out.println("二级our" + aboutUsBean1.getName());
                    System.out.println(aboutUsBean1.getParentid());
                    if (aboutUsBean1.getContent() != null) {
                        for (AboutUsBean aboutUsBean2 : aboutUsBean1.getContent()) {
                            System.out.println("三级our" + aboutUsBean2.getName());
                            System.out.println("级our" + aboutUsBean2.getParentid());
                        }
                    }
                }
            }
            if (aboutUsBean.getParentid().equals("1") && aboutUsBean.getName().equals("关于我们") && our_AboutusBean == null) {
                our_AboutusBean = aboutUsBean;
            }

            if (aboutUsBean.getParentid().equals("1") && aboutUsBean.getName().equals("方案理解") && falj_AboutusBean == null) {
                falj_AboutusBean = aboutUsBean;
            }
            if (aboutUsBean.getParentid().equals("1") && aboutUsBean.getName().equals("成功案例") && cgal_AboutusBean == null) {
                cgal_AboutusBean = aboutUsBean;
            }
            if (aboutUsBean.getParentid().equals("1") && aboutUsBean.getName().equals("解决方案") && jjfa_AboutusBean == null) {
                jjfa_AboutusBean = aboutUsBean;
            }


            if (aboutUsBean.getName().equals("模块化产品") && jjfa_AboutusBean != null && aboutUsBean.getParentid().equals(jjfa_AboutusBean.getId())) {
                mKh_AboutusBean = aboutUsBean;
            }


            if (aboutUsBean.getName().equals("一厅一案") && jjfa_AboutusBean != null && aboutUsBean.getParentid().equals(jjfa_AboutusBean.getId())) {
                yTyA_AboutusBean = aboutUsBean;
            }

            if (jjfa_AboutusBean != null && our_AboutusBean != null && falj_AboutusBean != null && cgal_AboutusBean != null && yTyA_AboutusBean != null && mKh_AboutusBean != null) {
                break;
            }

        }


        Log.i("关于我们", our_AboutusBean.toString());
        Log.i("方案理解", falj_AboutusBean.toString());
        Log.i("cgal", cgal_AboutusBean.toString());
        Log.i("jjfa", jjfa_AboutusBean.toString());
        Log.i("mkh", mKh_AboutusBean.toString());
        Log.i("ytya", yTyA_AboutusBean.toString());


        us_arry = our_AboutusBean.getImages().split(",");
        falj_array = falj_AboutusBean.getImages().split(",");
        cgal_array = cgal_AboutusBean.getImages().split(",");

        if (jjfa_AboutusBean.getImages() != null) {
            jjfa_array = jjfa_AboutusBean.getImages().split(",");
        }

        //先初始化几张一厅一案的图片
        yayt_array=new String[3];
        for(int i=0;i<3;i++){
            yayt_array[i]=us_arry[i];
        }

        int[] compareCount =new int[]{us_arry.length,falj_array.length,cgal_array.length,1,yayt_array.length};


        maxArryNumber4Point=compareCount[0];
        for(int i=1;i<compareCount.length;i++){
            if (compareCount[i]>maxArryNumber4Point){
                maxArryNumber4Point=compareCount[i];
            }
        }

        initDianAndMenu(); //执行该方法前要决定maxArryNumber4Point的具体值
        initMenuButton();
        initViewPager();
        viewPagerListener4ChangeDian();


        return FragmentView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void initMenuButton() {
        pMenuTxt0.setText(our_AboutusBean.getName());
        pMenuTxt1.setText(falj_AboutusBean.getName());
        pMenuTxt2.setText(cgal_AboutusBean.getName());
        pMenuTxt3.setText(jjfa_AboutusBean.getName());
        pMenuTxt4.setText("模块化产品");
        pMenuTxt5.setText("一厅一案");

    }


    public void changeMenuStatemnet(int currentMenuPosition, int lastPosition) {

        //选中第一个按钮的时候
        if (currentMenuPosition == 0) {
            // img0.setImageBitmap(BitmapFactory.decodeFile(our_AboutusBean.getIcon1()));
            img0.setBackgroundResource(R.drawable.icon_sidebar_gywm_sel);
            pMenuTxt0.setTextColor(getResources().getColor(R.color.personal_menu_text_color));
            title.setText(our_AboutusBean.getName());
            if (mkOryyLayout.getVisibility() == View.VISIBLE) {
                mkOryyLayout.setVisibility(View.GONE);
            }
        } else if (currentMenuPosition == 1) {
            //img1.setImageBitmap(BitmapFactory.decodeFile(falj_AboutusBean.getIcon1()));
            img1.setBackgroundResource(R.drawable.icon_sidebar_falj_sel);
            pMenuTxt1.setTextColor(getResources().getColor(R.color.personal_menu_text_color));
            title.setText(falj_AboutusBean.getName());
            if (mkOryyLayout.getVisibility() == View.VISIBLE) {
                mkOryyLayout.setVisibility(View.GONE);
            }
        } else if (currentMenuPosition == 2) {
            //img2.setImageBitmap(BitmapFactory.decodeFile(cgal_AboutusBean.getIcon1()));
            img2.setBackgroundResource(R.drawable.icon_sidebar_cgal_sel);
            pMenuTxt2.setTextColor(getResources().getColor(R.color.personal_menu_text_color));
            title.setText(cgal_AboutusBean.getName());
            if (mkOryyLayout.getVisibility() == View.VISIBLE) {
                mkOryyLayout.setVisibility(View.GONE);
            }

        } else if (currentMenuPosition == 3) {
            //img3.setImageBitmap(BitmapFactory.decodeFile(jjfa_AboutusBean.getIcon1()));
            img3.setBackgroundResource(R.drawable.icon_sidebar_jjfa_sel);
            pMenuTxt3.setTextColor(getResources().getColor(R.color.personal_menu_text_color));
            title.setText(jjfa_AboutusBean.getName());
            mkOryyLayout.setVisibility(View.GONE);
        }


        if (lastPosition == 0 && menu_last_position != currentMenuPosition) {
            //   img0.setImageBitmap(BitmapFactory.decodeFile(our_AboutusBean.getIco2()));
            img0.setBackgroundResource(R.drawable.icon_sidebar_gywm_nor);
            pMenuTxt0.setTextColor(getResources().getColor(R.color.personal_text_color));
        } else if (lastPosition == 1 && lastPosition != currentMenuPosition) {
            //   img1.setImageBitmap(BitmapFactory.decodeFile(falj_AboutusBean.getIco2()));
            img1.setBackgroundResource(R.drawable.icon_sidebar_falj_nor);
            pMenuTxt1.setTextColor(getResources().getColor(R.color.personal_text_color));
        } else if (lastPosition == 2 && lastPosition != currentMenuPosition) {
            //img2.setImageBitmap(BitmapFactory.decodeFile(cgal_AboutusBean.getIco2()));
            img2.setBackgroundResource(R.drawable.icon_sidebar_cgal_nor);
            pMenuTxt2.setTextColor(getResources().getColor(R.color.personal_text_color));
        } else if (lastPosition == 3 && lastPosition != currentMenuPosition) {
            // img3.setImageBitmap(BitmapFactory.decodeFile(jjfa_AboutusBean.getIco2()));
            img3.setBackgroundResource(R.drawable.icon_sidebar_jjfa_nor);
            pMenuTxt3.setTextColor(getResources().getColor(R.color.personal_text_color));
        }
        else if(lastPosition==4&&lastPosition!=currentMenuPosition){
            pMenuTxt4.setTextColor(getResources().getColor(R.color.personal_text_color));
        }



        if (lastPosition != currentMenuPosition) {
            (menu_our_buttons[lastPosition]).setBackgroundResource(R.drawable.button_unselect);
        }


    }


    public void viewPagerListener4ChangeDian() {

        viewPager.setOnVerticalPageChangeListener(position -> {

            if(position<us_arry.length){
                /*关于我们**/

                if(!us_block){
                    //没有锁住
                    isShowPoint(us_arry.length);
                    //左侧菜单的变化
                    if(menu_last_position!=0){
                        menu_our_buttons[menu_last_position].setBackgroundResource(R.color.menu_black);
                    }
                    menu0_our.setBackgroundResource(R.color.menu_selected);
                    changeMenuStatemnet(0, menu_last_position);

                    menu5_our.setVisibility(View.GONE);
                    menu4_our.setVisibility(View.GONE);
                    menu_last_position = 0;
                    isBlock("关于我们");
                }


                // 切换点
                if(viewPager4Menu_CurrentPosition<us_arry.length){
                    dian_view_List[viewPager4Menu_CurrentPosition].setBackgroundResource(R.drawable.dian_hei);
                }
                if(position==0){
                    //把第一个变黑
                    dian_view_List[dian_view_List.length-1].setBackgroundResource(R.drawable.dian_hei);
                }
                dian_view_List[position].setBackgroundResource(R.drawable.dian);



            }

            else  if(position<(us_arry.length+falj_array.length)){
                /*
                  方案理解**/
                if(!falj_block){
                    isShowPoint(falj_array.length);

                    changeMenuStatemnet(1, menu_last_position);
                    menu_our_buttons[menu_last_position].setBackgroundResource(R.color.menu_black);
                    dian_view_List[0].setBackgroundResource(R.drawable.dian_hei);
                    menu1_our.setBackgroundResource(R.color.menu_selected);

                    menu5_our.setVisibility(View.GONE);
                    menu4_our.setVisibility(View.GONE);
                    menu_last_position = 1;
                }

                if(falj_array.length>1){
                    if(us_arry.length<=viewPager4Menu_CurrentPosition){
                        dian_view_List[viewPager4Menu_CurrentPosition-(us_arry.length)].setBackgroundResource(R.drawable.dian_hei);
                    }
                    dian_view_List[position-(us_arry.length)].setBackgroundResource(R.drawable.dian);
                }
                isBlock("方案理解");
            }

            else  if(us_arry.length+falj_array.length<=position&&position<us_arry.length+falj_array.length+cgal_array.length){
                /*这是成功案例**/
                if(!cgal_block){
                    isShowPoint(cgal_array.length);
                    changeMenuStatemnet(2, menu_last_position);
                    menu_our_buttons[menu_last_position].setBackgroundResource(R.color.menu_black);
                    //  menu1_our.setBackgroundResource(R.color.menu_black);
                    menu2_our.setBackgroundResource(R.color.menu_selected);
                    menu5_our.setVisibility(View.GONE);
                    menu4_our.setVisibility(View.GONE);
                    //  menu_last_position=3;
                    menu_last_position = 2;
                    isBlock("成功案例");
                }

                if(cgal_array.length>1){
                    if(us_arry.length+falj_array.length<=viewPager4Menu_CurrentPosition){
                        dian_view_List[viewPager4Menu_CurrentPosition-(us_arry.length+falj_array.length)].setBackgroundResource(R.drawable.dian_hei);
                    }
                    dian_view_List[position-(us_arry.length+falj_array.length)].setBackgroundResource(R.drawable.dian);
                }



            }

            else if(us_arry.length+falj_array.length+cgal_array.length<=position&&position<us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray){
                /*解决方案介绍页**/
                isShowPoint(1);
                changeMenuStatemnet(3, menu_last_position);
                menu_our_buttons[menu_last_position].setBackgroundResource(R.color.menu_black);
                menu3_our.setBackgroundResource(R.color.menu_selected);
                menu5_our.setVisibility(View.GONE);
                menu4_our.setVisibility(View.GONE);
                menu_last_position = 3;
                isBlock("解决方案介绍页");

            }
            //TODO 这里的 1要改成 模块化产品的页数
            else if(us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray<=position&&position<us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray+1){
                /**模块化产品*/

                if(!mkh_block){
                    isShowPoint(1);//TODO  1要改成 模块化产品对应的页数


                    //这个是解决方案
                    changeMenuStatemnet(3, menu_last_position);
                    menu_our_buttons[menu_last_position].setBackgroundResource(R.color.menu_black);

                    menu5_our.setVisibility(View.VISIBLE);
                    menu4_our.setVisibility(View.VISIBLE);
                    menu4_our.setBackgroundResource(R.color.menu_selected);

                    menu5_our.setBackgroundResource(R.color.menu_unselected);
                    pMenuTxt4.setTextColor(getResources().getColor(R.color.personal_menu_text_color));
                    pMenuTxt5.setTextColor(getResources().getColor(R.color.personal_text_color));
                    mkOryy.setText("模块化产品");
                    mkOryyLayout.setVisibility(View.VISIBLE);
                    menu_last_position = 3;
                    isBlock("模块化产品");
                }

                if(us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray<=viewPager4Menu_CurrentPosition){
                    dian_view_List[viewPager4Menu_CurrentPosition-(us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray)].setBackgroundResource(R.drawable.dian_hei);
                }
                dian_view_List[position-(us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray)].setBackgroundResource(R.drawable.dian);


            }
            //TODO 这里的 1要改成 模块化产品的页数，后面us_arry.length要改成一厅一案的页数
            else if(us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray+1<=position&&position<us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray+1+yayt_array.length){
                /**一厅一案*/

                if(!ytya_block){
                    isShowPoint(yayt_array.length); //TODO 这里的数组长度要改成 一厅一案的对应页数

                    menu4_our.setBackgroundResource(R.color.menu_unselected);

                    menu_our_buttons[menu_last_position].setBackgroundResource(R.color.menu_black);
                    menu5_our.setBackgroundResource(R.color.menu_selected);

                    pMenuTxt5.setTextColor(getResources().getColor(R.color.personal_menu_text_color));
                    pMenuTxt4.setTextColor(getResources().getColor(R.color.personal_text_color));
                    menu5_our.setVisibility(View.VISIBLE);
                    menu4_our.setVisibility(View.VISIBLE);
                    mkOryy.setText("一厅一案");
                    mkOryyLayout.setVisibility(View.VISIBLE);
                    menu_last_position = 3;

                    isBlock("一厅一案");
                }

                //指示器显示
                if(us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray+1<=viewPager4Menu_CurrentPosition){//TODO 要改的数字 1 是模块化产品
                    dian_view_List[viewPager4Menu_CurrentPosition-(us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray+1)].setBackgroundResource(R.drawable.dian_hei);
                }
                dian_view_List[position-(us_arry.length+falj_array.length+cgal_array.length+JJFA_jieshaoye_qrray+1)].setBackgroundResource(R.drawable.dian);

                if(menu_last_position!=3){
                    menu_our_buttons[menu_last_position].setBackgroundResource(R.color.menu_black);
                }



            }


            viewPager4Menu_CurrentPosition = position;

        });



    }

    // 在ViewPager中加载图片  ,如果要加载 方案理解介绍也的时候布尔参数为true ,这时添加的是一个固定的页面
    public void loadImagViewList(String[] strings, int MaxCount,boolean isFangAnLiJie) {
        if(isFangAnLiJie){
            /* 解决方案加载介绍页*/
            View JjfaView = layoutInflater.inflate(R.layout.solusion_conten, null, false);
            mkh_layout=  JjfaView.findViewById(R.id.mkh_relative);
            ytya_layout=JjfaView.findViewById(R.id.ytya_relative);
            mkh_layout.setOnClickListener(this);
            ytya_layout.setOnClickListener(this);
            aList.add(JjfaView);

        }else{
            if (strings.length == 0) {
                Toast.makeText(getActivity(), "出现空的图片数组", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i <strings.length; i++) {
                View ourView = layoutInflater.inflate(R.layout.about_our, null, false);
                ImageView imageView = ourView.findViewById(R.id.our_img);
                //   Glide.with(getActivity()).load(strings[i]).into(imageView);
                imageView.setImageBitmap(BitmapFactory.decodeFile(strings[i]));

                aList.add(ourView);
                if (MaxCount == i + 1) {
                    break;
                }
            }
        }



    }


    public void initViewPager() {
        aList = new ArrayList<>();

        loadImagViewList(us_arry, us_arry.length,false);

        loadImagViewList(falj_array, falj_array.length,false);

        loadImagViewList(cgal_array, cgal_array.length,false);

        loadImagViewList(falj_array, 1,true);

        loadImagViewList(falj_array, 1,false);//TODO 要改的数字 这里是解决方案啊=>模块化产品页

        loadImagViewList(yayt_array, yayt_array.length,false);//TODO 要改的数字 这里是解决方案啊=>一厅一案页

        viewPager.setViewList(aList);
    }




    private void initDianAndMenu() {

        menu0_our = FragmentView.findViewById(R.id.menu0_our);
        menu1_our = FragmentView.findViewById(R.id.menu1_our);
        menu2_our = FragmentView.findViewById(R.id.menu2_our);
        menu3_our = FragmentView.findViewById(R.id.menu3_our);
        menu4_our = FragmentView.findViewById(R.id.menu4_our);
        menu5_our = FragmentView.findViewById(R.id.menu5_our);

        // 点的数组
        dian_view_List=new View[maxArryNumber4Point];


        //现在打算动态添加点数 ，目前实例化数组页数最多的点，避免每次切换菜单的时候重新加载 new出不必要的ImageView;
        for(int i=0;i<maxArryNumber4Point;i++){

            imageView=new ImageView(getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(25, 25));
            //设置控件的padding属性
            imageView.setPadding(20, 0, 20, 0);
            dian_view_List[i]=imageView;

            //初始化第一个page页面的图片的原点为选中状态
            if (i == 0) {
                //表示当前图片
                dian_view_List[i].setBackgroundResource(R.drawable.dian);
                /*
                 * 在java代码中动态生成ImageView的时候
                 * 要设置其BackgroundResource属性才有效
                 * 设置ImageResource属性无效
                 */
            } else {
                dian_view_List[i].setBackgroundResource(R.drawable.dian_hei);
            }
            //把new出来的ImageView控件添加到线性布局中
            dianLayout.addView(dian_view_List[i]);

        }


        //菜单背景控件实例数组
        menu_our_buttons = new View[]{menu0_our, menu1_our, menu2_our, menu3_our, menu4_our, menu5_our};

        menu2_our.setOnClickListener(this);
        menu3_our.setOnClickListener(this);
        menu4_our.setOnClickListener(this);
        menu5_our.setOnClickListener(this);
        menu0_our.setOnClickListener(this);
        menu1_our.setOnClickListener(this);

    }


    //客户需求当菜单对应图片为一张时，不显示点
    private  void  isShowPoint(int CurrentMenuContentCount){
        if(CurrentMenuContentCount==1){
            dianLayout.setVisibility(View.GONE);
        }else{

            for(int i=0;i<maxArryNumber4Point;i++){
                if(i<CurrentMenuContentCount){
                    dian_view_List[i].setVisibility(View.VISIBLE);
                }else{
                    dian_view_List[i].setVisibility(View.GONE);
                }

            }
            dianLayout.setVisibility(View.VISIBLE);

        }

    }

    //设置菜单锁，让一个菜单选中时，某些代码只执行一次
    private  void isBlock(String arrayName){
        switch (arrayName)
        {
            case "关于我们":
                us_block =true;
                falj_block=false;
                cgal_block=false;
                mkh_block=false;
                ytya_block=false;
                break;
            case "方案理解":
                us_block =false;
                falj_block=true;
                cgal_block=false;
                mkh_block=false;
                ytya_block=false;
                break;
            case "成功案例":
                us_block =false;
                falj_block=false;
                cgal_block=true;
                mkh_block=false;
                ytya_block=false;
                break;
            case "模块化产品":
                us_block =false;
                falj_block=false;
                cgal_block=false;
                mkh_block=true;
                ytya_block=false;
                break;
            case "一厅一案":
                us_block =false;
                falj_block=false;
                cgal_block=false;
                mkh_block=false;
                ytya_block=true;
                break;
            case "解决方案介绍页":
                us_block =false;
                falj_block=false;
                cgal_block=false;
                mkh_block=false;
                ytya_block=false;
                break;
        }

    }

    private  void resertPointState(){
        dian_view_List[0].setBackgroundResource(R.drawable.dian);
        for(int i=1;i<dian_view_List.length;i++){
            dian_view_List[i].setBackgroundResource(R.drawable.dian_hei);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.mkh_relative:
                resertPointState();
                if (menu_last_position != 4) {
                    (menu_our_buttons[menu_last_position]).setBackgroundResource(R.color.menu_black);
                }
                viewPager.snapToScreen(falj_array.length+us_arry.length+JJFA_jieshaoye_qrray+1);//TODO 要改的数字
                break;
            case R.id.ytya_relative:
                resertPointState();
                if (menu_last_position != 5) {
                    (menu_our_buttons[menu_last_position]).setBackgroundResource(R.color.menu_black);
                }
                viewPager.snapToScreen(falj_array.length+us_arry.length+JJFA_jieshaoye_qrray+1+1);//TODO 要改的数字
                break;




            case R.id.menu0_our: /* 菜单点击事件 部分地方和 滑动到顶端相同*/
                resertPointState();
                changeMenuStatemnet(0, menu_last_position);
                viewPager.snapToScreen(0);
                break;
            case R.id.menu1_our:
                resertPointState();
                viewPager.snapToScreen(us_arry.length);

                break;
            case R.id.menu2_our:
                resertPointState();
                changeMenuStatemnet(2, menu_last_position);
                viewPager.snapToScreen(falj_array.length+us_arry.length);
                break;
            case R.id.menu3_our:
                resertPointState();
                if (menu_last_position != 3) {
                    (menu_our_buttons[menu_last_position]).setBackgroundResource(R.color.menu_black);
                }
                changeMenuStatemnet(3, menu_last_position);
                viewPager.snapToScreen(us_arry.length+cgal_array.length+falj_array.length);
                break;
            case R.id.menu4_our:
                //模块化产品
                resertPointState();
                if (menu_last_position != 4) {
                    (menu_our_buttons[menu_last_position]).setBackgroundResource(R.color.menu_black);
                }
                viewPager.snapToScreen(falj_array.length+us_arry.length+JJFA_jieshaoye_qrray+1);//TODO 要改的数字
                break;
            case R.id.menu5_our:
                //一案一厅
                resertPointState();
                if (menu_last_position != 5) {
                    (menu_our_buttons[menu_last_position]).setBackgroundResource(R.color.menu_black);
                }
                viewPager.snapToScreen(falj_array.length+us_arry.length+JJFA_jieshaoye_qrray+1+1);//TODO 要改的数字
                break;

        }
    }



    //当 Fragment活动的时候重置菜单位置
    @Override
    public void onStop() {
        super.onStop();
        menu_last_position=0;
    }
}
