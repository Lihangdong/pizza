package com.huashe.pizz;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huashe.pizz.VMBanner.MVideoView;
import com.huashe.pizz.adapter.CommonAdapter;
import com.huashe.pizz.adapter.CommonRecycleAdapter;
import com.huashe.pizz.adapter.CommonViewHolder;
import com.huashe.pizz.adapter.GridSpacingItemDecoration;
import com.huashe.pizz.adapter.ModuleProductAdapter;
import com.huashe.pizz.adapter.ViewHolder;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductMenu;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;
import com.huashe.pizz.bean.ThumbnailsBean;
import com.huashe.pizz.bean.greendao.ModuleProductBeanDaoUtil;
import com.huashe.pizz.bean.greendao.ModuleProductMenuDaoUtil;
import com.huashe.pizz.utils.ThumbnaiUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.view.View.GONE;


public class ModuleProductFragment extends Fragment {

    private List<ModuleProductMenu> MenuDatas;
    private int currItem = -1;
    private ListView listView;
    private CommonAdapter<ModuleProductMenu> commonAdapter;
    private View lastView;
    private TextView tvModuleProductTitle;
    private View layoutModuleproductDetailGirdlayout;
    private View layoutModuleproductDetail;
    public MyApplication myApplication;
    public int currModuleProductBean = 0;
    private RecyclerView rvModuleProductGirdlayout;
    private List<ModuleProductBean> BeanDatas;
    private ModuleProductAdapter moduleProductAdapter;
    MVideoView mVideoView;
    private Button btnModuleproductDetailBack;
    private Button btnModuleproductDetailLike;
    private Button btnModuleproductDetailChange;
    private RecyclerView rvModuleProductDetailBottom;
    private LinearLayout llModuleproductDetailGird;
    private LinearLayout llModuleproductDetailList;

    public ModuleProductFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_module_product, container, false);
        layoutModuleproductDetailGirdlayout = view.findViewById(R.id.layout_moduleproduct_girdlayout);
        layoutModuleproductDetail = view.findViewById(R.id.layout_moduleproduct_detail);
        btnModuleproductDetailBack = layoutModuleproductDetail.findViewById(R.id.btn_moduleproduct_detail_back);
        btnModuleproductDetailLike = layoutModuleproductDetail.findViewById(R.id.btn_moduleproduct_detail_like);
        btnModuleproductDetailChange = layoutModuleproductDetail.findViewById(R.id.btn_moduleproduct_detail_change);
        llModuleproductDetailGird = layoutModuleproductDetail.findViewById(R.id.ll_moduleproduct_detail_gird);
        llModuleproductDetailList = layoutModuleproductDetail.findViewById(R.id.ll_moduleproduct_detail_list);
        BeanDatas = ModuleProductBeanDaoUtil.queryAll();
        System.out.println(BeanDatas.size());
        myApplication = (MyApplication) getActivity().getApplication();
        initLeft(view);
        initRight(view);
        return view;
    }


    private void initRight(final View view) {
        mVideoView = (MVideoView) layoutModuleproductDetail.findViewById(R.id.mvVideoview);
        rvModuleProductGirdlayout = view.findViewById(R.id.rv_moduleproduct_girdlayout);

        btnModuleproductDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutModuleproductDetailGirdlayout.setVisibility(View.VISIBLE);
                layoutModuleproductDetail.setVisibility(View.GONE);
                if (mVideoView != null)
                    mVideoView.stopPlayback();
                mVideoView.setVisibility(View.GONE);
            }
        });

        btnModuleproductDetailChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llModuleproductDetailGird.getVisibility() == GONE) {
                    llModuleproductDetailGird.setVisibility(View.VISIBLE);
                    llModuleproductDetailList.setVisibility(GONE);
                    RecyclerView rvModuleproductDetailGird = llModuleproductDetailGird.findViewById(R.id.rv_moduleproduct_detail_gird);
                    rvModuleproductDetailGird.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));  //每次setAdapter()都重新设置布局管理器
                    List<String> strings = Arrays.asList(BeanDatas.get(currModuleProductBean).getImagePath().split(","));
                    rvModuleproductDetailGird.setAdapter(new CommonRecycleAdapter<String>(getActivity(), strings, R.layout.item_item_moduleproduct_detail_girdlayout) {
                        @Override
                        public void bindData(CommonViewHolder holder, final String s) {
                            if (s.endsWith("png")) {
                                holder.setImageResourceBitmap(R.id.iv_moduleproduct_detail_girdlayout, ThumbnaiUtil.getImageThumbnail(s, 600, 375));
                            } else if (s.endsWith("mp4")) {
                                holder.setImageResourceBitmap(R.id.iv_moduleproduct_detail_girdlayout, ThumbnaiUtil.getVideoThumbnail(s, 600, 375, MediaStore.Video.Thumbnails.MINI_KIND));
                            }
                            holder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
                                @Override
                                public void onItemClickListener(int position) {
                                    Intent intent = new Intent(getActivity(), PlayActivity.class);
                                    intent.putExtra("pos", position);
                                    intent.putExtra("path", BeanDatas.get(currModuleProductBean).getImagePath());
                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClickListener(int position) {

                                }
                            });
                        }
                    });

                    ((TextView) llModuleproductDetailGird.findViewById(R.id.tv_moduleproduct_detail_gird)).setText("共" + strings.size() + "个   注:外观设计已申请专利");
//                    if (rvModuleproductDetailGird.getOnFlingListener() == null) {
//                        PagerSnapHelper snapHelper = new PagerSnapHelper();
//                        snapHelper.attachToRecyclerView(rvModuleproductDetailGird);
//                    }
                } else {
                    llModuleproductDetailGird.setVisibility(View.GONE);
                    llModuleproductDetailList.setVisibility(View.VISIBLE);
                }

            }
        });


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvModuleProductGirdlayout.setLayoutManager(layoutManager);
        // 设置 RecyclerView的Adapter
        // 注意一定在设置了布局管理器之后调用
        moduleProductAdapter = new ModuleProductAdapter(getActivity(), BeanDatas, new CommonViewHolder.onItemCommonClickListener() {
            @Override
            public void onItemClickListener(final int position) {
                currModuleProductBean = position;
                layoutModuleproductDetailGirdlayout.setVisibility(View.GONE);
                layoutModuleproductDetail.setVisibility(View.VISIBLE);
                if (BeanDatas.get(position).isLove()) {
                    btnModuleproductDetailLike.setBackgroundResource(R.drawable.view_icon_like_sel);
                } else if (!BeanDatas.get(position).isLove()) {
                    btnModuleproductDetailLike.setBackgroundResource(R.drawable.view_icon_like_nor);
                }
                btnModuleproductDetailLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currModuleProductBean != -1) {
                            if (myApplication.userLoveLists.contains(BeanDatas.get(currModuleProductBean))) {
                                myApplication.userLoveLists.remove(BeanDatas.get(currModuleProductBean));
                                v.setBackgroundResource(R.drawable.view_icon_like_nor);
                                if (myApplication.userLoveLists.size() == 0) {
                                    ((MainActivity) getActivity()).rbListSave.setBackgroundResource(R.drawable.nav_icon_list_default);
                                }
                                BeanDatas.get(currModuleProductBean).setLove(false);
                                rvModuleProductGirdlayout.getChildAt(currModuleProductBean).findViewById(R.id.ib_moduleproduct_rv_love).setBackgroundResource(R.drawable.view_icon_like_nor);
                            } else {
                                ((MainActivity) getActivity()).rbListSave.setBackgroundResource(R.drawable.nav_icon_list_nor);
                                myApplication.userLoveLists.add(BeanDatas.get(currModuleProductBean));
                                v.setBackgroundResource(R.drawable.view_icon_like_sel);
                                BeanDatas.get(currModuleProductBean).setLove(true);
                                rvModuleProductGirdlayout.getChildAt(currModuleProductBean).findViewById(R.id.ib_moduleproduct_rv_love).setBackgroundResource(R.drawable.view_icon_like_sel);
                            }
                        }

                    }
                });
                if (!TextUtils.isEmpty(BeanDatas.get(position).getImage())) {
                    List<String> types = new ArrayList<>();
                    List<String> contents = new ArrayList<>();
                    if (BeanDatas.get(position).getDescription() != null) {
                        for (int i = 1; i < BeanDatas.get(position).getDescription().size(); i++) {
                            types.add(BeanDatas.get(position).getDescription().get(i).getType());
                            if (BeanDatas.get(position).getDescription().get(i).getContent().contains("。")) {
                                String[] split = BeanDatas.get(position).getDescription().get(i).getContent().split("。");
                                for (int m = 0; m < split.length; m++) {
                                    contents.add(split[m] + "。");
                                }
                            } else {
                                contents.add(BeanDatas.get(position).getDescription().get(i).getContent());

                            }
                        }
                        TextView type1 = layoutModuleproductDetail.findViewById(R.id.tv_type_item_moduleproduct_detail_list_1);
                        TextView type2 = layoutModuleproductDetail.findViewById(R.id.tv_type_item_moduleproduct_detail_list_2);
                        TextView type3 = layoutModuleproductDetail.findViewById(R.id.tv_type_item_moduleproduct_detail_list_3);
                        TextView type4 = layoutModuleproductDetail.findViewById(R.id.tv_type_item_moduleproduct_detail_list_4);
                        List<TextView> typeTVs = new ArrayList<>();
                        typeTVs.add(type1);
                        typeTVs.add(type2);
                        typeTVs.add(type3);
                        typeTVs.add(type4);
                        TextView content1 = layoutModuleproductDetail.findViewById(R.id.tv_content_item_moduleproduct_detail_list_1);
                        TextView content2 = layoutModuleproductDetail.findViewById(R.id.tv_content_item_moduleproduct_detail_list_2);
                        TextView content3 = layoutModuleproductDetail.findViewById(R.id.tv_content_item_moduleproduct_detail_list_3);
                        TextView content4 = layoutModuleproductDetail.findViewById(R.id.tv_content_item_moduleproduct_detail_list_4);
                        TextView content5 = layoutModuleproductDetail.findViewById(R.id.tv_content_item_moduleproduct_detail_list_5);
                        TextView content6 = layoutModuleproductDetail.findViewById(R.id.tv_content_item_moduleproduct_detail_list_6);
                        TextView content7 = layoutModuleproductDetail.findViewById(R.id.tv_content_item_moduleproduct_detail_list_7);

                        List<TextView> contentTVs = new ArrayList<>();
                        contentTVs.add(content1);
                        contentTVs.add(content2);
                        contentTVs.add(content3);
                        contentTVs.add(content4);
                        contentTVs.add(content5);
                        contentTVs.add(content6);
                        contentTVs.add(content7);

                        LinearLayout ll1 = layoutModuleproductDetail.findViewById(R.id.ll_item_moduleproduct_detail_list_1);
                        LinearLayout ll2 = layoutModuleproductDetail.findViewById(R.id.ll_item_moduleproduct_detail_list_2);
                        LinearLayout ll3 = layoutModuleproductDetail.findViewById(R.id.ll_item_moduleproduct_detail_list_3);
                        LinearLayout ll4 = layoutModuleproductDetail.findViewById(R.id.ll_item_moduleproduct_detail_list_4);
                        LinearLayout ll5 = layoutModuleproductDetail.findViewById(R.id.ll_item_moduleproduct_detail_list_5);
                        LinearLayout ll6 = layoutModuleproductDetail.findViewById(R.id.ll_item_moduleproduct_detail_list_6);
                        LinearLayout ll7 = layoutModuleproductDetail.findViewById(R.id.ll_item_moduleproduct_detail_list_7);
                        List<LinearLayout> llTVs = new ArrayList<>();
                        llTVs.add(ll1);
                        llTVs.add(ll2);
                        llTVs.add(ll3);
                        llTVs.add(ll4);
                        llTVs.add(ll5);
                        llTVs.add(ll6);
                        llTVs.add(ll7);

                        for (int m = 0; m < contents.size(); m++) {
                            if (m < types.size()) {
//                                        System.out.println(types.get(m));
                                typeTVs.get(m).setVisibility(View.VISIBLE);
                                typeTVs.get(m).setText(types.get(m));
                            }
//                                    System.out.println(contents.get(m));
                            llTVs.get(m).setVisibility(View.VISIBLE);
                            contentTVs.get(m).setText(contents.get(m));
                        }
                    }
                    final ImageView ivModuleproductDetailList = layoutModuleproductDetail.findViewById(R.id.iv_moduleproduct_detail_list);
                    rvModuleProductDetailBottom = layoutModuleproductDetail.findViewById(R.id.rv_moduleproduct_detail_bottom);
                    ImageView ivRightModuleProductDetailBottom = layoutModuleproductDetail.findViewById(R.id.iv_right_moduleproduct_detail_bottom);
                    ImageView ivLeftModuleProductDetailBottom = layoutModuleproductDetail.findViewById(R.id.iv_left_moduleproduct_detail_bottom);
//                    final List<ThumbnailsBean> thumbnailsBeans = getFiles(BeanDatas.get(position).getImagePath(), 120, 75);
                    final List<String> strings = Arrays.asList(BeanDatas.get(position).getImagePath().split(","));
                    if (strings.size() > 0) {
                        final RecyclerView.LayoutManager layoutManagerModuleProductDetailBottom = new GridLayoutManager(getActivity(), strings.size(), GridLayoutManager.VERTICAL, false);
                        rvModuleProductDetailBottom.setLayoutManager(layoutManagerModuleProductDetailBottom);
                        CommonRecycleAdapter<String> adapter = new CommonRecycleAdapter<String>(getActivity(), strings, R.layout.item_item_moduleproduct_detail_list) {
                            @Override
                            public void bindData(final CommonViewHolder commonViewHolder, final String s) {
                                Glide.with(getActivity()).load(BeanDatas.get(position).getImage()).into((ImageView) layoutModuleproductDetail.findViewById(R.id.iv_moduleproduct_detail_list));
                                layoutModuleproductDetail.findViewById(R.id.iv_moduleproduct_detail_list).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), PlayActivity.class);
                                        intent.putExtra("pos", 0);
                                        intent.putExtra("path", BeanDatas.get(position).getImagePath());
                                        startActivity(intent);
                                    }
                                });
                                ((ImageView) layoutModuleproductDetail.findViewById(R.id.iv_moduleproduct_detail_list)).setVisibility(View.VISIBLE);
                                if (s.endsWith("png")) {
                                    commonViewHolder.getView(R.id.iv_moduleproduct_detail_bottom_video).setVisibility(View.GONE);
                                    commonViewHolder.setImageResourceBitmap(R.id.iv_moduleproduct_detail_bottom, ThumbnaiUtil.getImageThumbnail(s, 120, 75));
                                } else if (s.endsWith("mp4")) {
                                    commonViewHolder.getView(R.id.iv_moduleproduct_detail_bottom_video).setVisibility(View.VISIBLE);
                                    commonViewHolder.setImageResourceBitmap(R.id.iv_moduleproduct_detail_bottom, ThumbnaiUtil.getVideoThumbnail(s, 120, 75, MediaStore.Video.Thumbnails.MINI_KIND));
                                }

                                commonViewHolder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
                                    @Override
                                    public void onItemClickListener(final int i) {
                                        if (s.endsWith("png")) {
                                            mVideoView.setVisibility(View.GONE);
                                            ivModuleproductDetailList.setVisibility(View.VISIBLE);
                                            Glide.with(getActivity()).load(s).into(ivModuleproductDetailList);
                                            ivModuleproductDetailList.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(getActivity(), PlayActivity.class);
                                                    intent.putExtra("pos", i);
                                                    intent.putExtra("path", BeanDatas.get(position).getImagePath());
                                                    startActivity(intent);
                                                }
                                            });
                                        } else if (s.endsWith("mp4")) {
                                            ivModuleproductDetailList.setVisibility(View.GONE);
                                            mVideoView.setVisibility(View.VISIBLE);
                                            mVideoView.setVideoURI(Uri.parse(s));
                                            mVideoView.start();
                                            //监听视频播放完的代码
                                            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                @Override
                                                public void onCompletion(MediaPlayer mPlayer) {
                                                    mPlayer.start();
                                                    mPlayer.setLooping(true);
                                                }
                                            });
                                            mVideoView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    mVideoView.pause();
//                                                    Glide.with(getActivity()).load(BeanDatas.get(position).getImage()).into(ivModuleproductDetailList);
                                                    Intent intent = new Intent(getActivity(), PlayActivity.class);
                                                    intent.putExtra("pos", i);
                                                    intent.putExtra("path", BeanDatas.get(position).getImagePath());
                                                    startActivity(intent);
//                                                    ((ImageView) layoutModuleproductDetail.findViewById(R.id.iv_moduleproduct_detail_list)).setVisibility(View.VISIBLE);
//                                                    (layoutModuleproductDetail.findViewById(R.id.rv_moduleproduct_detail_bottom)).setVisibility(View.VISIBLE);
//                                                    Glide.with(getActivity()).load(BeanDatas.get(position).getImage()).into((ImageView) layoutModuleproductDetail.findViewById(R.id.iv_moduleproduct_detail_list));
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onItemLongClickListener(int position) {

                                    }
                                });
                            }

                        };

                        rvModuleProductDetailBottom.setAdapter(adapter);
                        rvModuleProductDetailBottom.setRecyclerListener(new RecyclerView.RecyclerListener() {
                            @Override
                            public void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
//                                            if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
//                                                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
//                                            }
                                if (ivModuleproductDetailList != null) {
                                    Glide.with(getActivity()).load(BeanDatas.get(position).getImage()).into(ivModuleproductDetailList);
                                }
                            }
                        });
                        rvModuleProductDetailBottom.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
                                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                                    //判断是当前layoutManager是否为LinearLayoutManager
                                    // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                                    if (layoutManager instanceof LinearLayoutManager) {
                                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                                        //获取最后一个可见view的位置
                                        int lastItemPosition = linearManager.findLastVisibleItemPosition();
                                        //获取第一个可见view的位置
                                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();

                                        if (firstItemPosition < 0) {
                                            firstItemPosition = 0;
                                        }
                                        if (firstItemPosition != lastItemPosition) {
                                        }
                                    }
//
                                }
                            }
                        });

                        if (rvModuleProductDetailBottom != null && rvModuleProductDetailBottom.getChildCount() > 0) {
                            try {
                                int currentPosition = ((RecyclerView.LayoutParams) rvModuleProductDetailBottom.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
                                System.out.println(currentPosition);
                            } catch (Exception e) {

                            }
                        }

                    }

                } else {
//            holder.avatarImg.setImageResource(占位图本地资源);
                }


            }

            @Override
            public void onItemLongClickListener(int position) {

            }
        });
        rvModuleProductGirdlayout.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        rvModuleProductGirdlayout.setAdapter(moduleProductAdapter);
        rvModuleProductGirdlayout.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
//                    moduleProductAdapter.setScrolling(false);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    //判断是当前layoutManager是否为LinearLayoutManager
                    // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        //获取最后一个可见view的位置
                        int lastItemPosition = linearManager.findLastVisibleItemPosition();
                        //获取第一个可见view的位置
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();

                        if (firstItemPosition < 0) {
                            firstItemPosition = 0;
                        }
                        if (firstItemPosition != lastItemPosition) {
//                            lastItemPosition = firstItemPosition; //得到要更新的item的view
                            RecyclerView.ViewHolder viewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(lastItemPosition);
                            String id = BeanDatas.get(lastItemPosition).getType().substring(0, 32);
                            System.out.println("ID    " + id);
                            if (isRightRefresh) {
                                isLeftRefresh = false;
                                for (int i = 0; i < MenuDatas.size(); i++) {
                                    if (id.equals(MenuDatas.get(i).getId())) {
                                        listView.performItemClick(listView.getChildAt(i), i, listView.getItemIdAtPosition(i));
                                        break;
                                    }
                                }
                            }
                            isRightRefresh = true;
                        }
                    }
//
                } else
//                    moduleProductAdapter.setScrolling(true);
                    super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String s) {
        if ("refresh".equals(s)) {
            moduleProductAdapter.notifyDataSetChanged();
        }
        if (myApplication.userLoveLists.contains(BeanDatas.get(currModuleProductBean))) {
            btnModuleproductDetailLike.setBackgroundResource(R.drawable.view_icon_like_sel);
        } else {
            btnModuleproductDetailLike.setBackgroundResource(R.drawable.view_icon_like_nor);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            moduleProductAdapter.notifyDataSetChanged();
        }
    }

    private boolean isRightRefresh = true;
    private boolean isLeftRefresh = true;

    private void initLeft(View view) {
        tvModuleProductTitle = view.findViewById(R.id.tv_moduleproduct_title);
        MenuDatas = ModuleProductMenuDaoUtil.queryAll();
        listView = view.findViewById(R.id.lv_moduleproduct_menu);
        commonAdapter = new CommonAdapter<ModuleProductMenu>(getActivity(), MenuDatas, R.layout.item_left_menu) {
            @Override
            public void convert(ViewHolder viewHolder, ModuleProductMenu moduleProductMenu, int i) {
                if (i == 0) {
                    System.out.println(moduleProductMenu.getName());
                    viewHolder.setText(R.id.tv_lv_menu_title, moduleProductMenu.getName());
                    ((TextView) viewHolder.getView(R.id.tv_lv_menu_title)).setTextColor(getResources().getColor(R.color.left_mkhcp_sel_text_color));
//                    System.out.println(moduleProductMenu.getIcon1());
                    viewHolder.setImageFile(R.id.iv_lv_menu_icon, moduleProductMenu.getIcon2());
                    lastView = viewHolder.getConvertView();
                    currItem = 0;
                } else {
                    viewHolder.setText(R.id.tv_lv_menu_title, moduleProductMenu.getName());
//                    System.out.println(moduleProductMenu.getIcon1());
                    viewHolder.setImageFile(R.id.iv_lv_menu_icon, moduleProductMenu.getIcon1());
                }

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                System.out.println(listView.getFirstVisiblePosition() + "   " + listView.getLastVisiblePosition());
                if (lastView != null && currItem != -1) {
                    ((TextView) lastView.findViewById(R.id.tv_lv_menu_title)).setTextColor(getResources().getColor(R.color.left_mkhcp_nor_text_color));
                    ((ImageView) lastView.findViewById(R.id.iv_lv_menu_icon)).setImageBitmap(BitmapFactory.decodeFile(MenuDatas.get(currItem).getIcon1()));
                }
//                if (i >= listView.getFirstVisiblePosition() && i <= listView.getLastVisiblePosition()) {
                System.out.println(listView.getFirstVisiblePosition() + "   " + listView.getLastVisiblePosition());
                ((TextView) adapterView.getChildAt(i - listView.getFirstVisiblePosition()).findViewById(R.id.tv_lv_menu_title)).setTextColor(getResources().getColor(R.color.left_mkhcp_sel_text_color));
                ((ImageView) view.findViewById(R.id.iv_lv_menu_icon)).setImageBitmap(BitmapFactory.decodeFile(MenuDatas.get(i).getIcon2()));
//                }else {
//                    System.out.println(listView.getFirstVisiblePosition() + "   " + listView.getLastVisiblePosition());
//                    ((TextView) listView.getChildAt(i-listView.getFirstVisiblePosition()).findViewById(R.id.tv_lv_menu_title)).setTextColor(getResources().getColor(R.color.left_mkhcp_sel_text_color));
//                    ((ImageView) listView.getChildAt(i-listView.getFirstVisiblePosition()).findViewById(R.id.iv_lv_menu_icon)).setImageBitmap(BitmapFactory.decodeFile(MenuDatas.get(i).getIcon2()));
//                }

                tvModuleProductTitle.setText(MenuDatas.get(i).getName());
//                System.out.println(isLeftRefresh + "   " + isRightRefresh);
                if (isLeftRefresh) {
                    isRightRefresh = false;
                    for (int m = 0; m < BeanDatas.size(); m++) {
//                        System.out.println(BeanDatas.get(m).getType()+"----------"+MenuDatas.get(i).getId()+"++");
                        if ((MenuDatas.get(i).getId() + "++").equals(BeanDatas.get(m).getType())) {
                            layoutModuleproductDetailGirdlayout.setVisibility(View.VISIBLE);
                            layoutModuleproductDetail.setVisibility(View.GONE);
                            smoothMoveToPosition(rvModuleProductGirdlayout, m + 2);
                            break;
                        }
                    }
                }
                isLeftRefresh = true;

                lastView = view;
                currItem = i;
            }
        });
        listView.setAdapter(commonAdapter);
//        listView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                listView.performItemClick(listView.getChildAt(0), 0, listView.getItemIdAtPosition(0));
//            }
//        }, 2000);
    }

    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    // 遍历文件
    private List<ThumbnailsBean> getFiles(String filePath, int width, int height) {
        List<ThumbnailsBean> thumbnailsBeans = new ArrayList<>();
        File[] allFiles = new File(filePath).listFiles();
        if (allFiles != null) { // 若文件不为空，则遍历文件长度
            for (int i = 0; i < allFiles.length; i++) {
                File file = allFiles[i];
                if (file.isFile()) {
                    if (file.getName().endsWith("mp4")) {
                        ThumbnailsBean thumbnailsBean = new ThumbnailsBean();
                        thumbnailsBean.setType("1");
//                        System.out.println(filePath + "/" + file.getName());
                        thumbnailsBean.setTitle(file.getName());
                        thumbnailsBean.setPath(file.getAbsolutePath());
                        thumbnailsBean.setBitmap(ThumbnaiUtil.getVideoThumbnail(file.getAbsolutePath(), width, height, MediaStore.Video.Thumbnails.MINI_KIND));
                        thumbnailsBeans.add(thumbnailsBean);
                    } else if (file.getName().endsWith("png") || file.getName().endsWith("jpg")) {
                        ThumbnailsBean thumbnailsBean = new ThumbnailsBean();
                        thumbnailsBean.setType("0");
//                        System.out.println(filePath + "/" + file.getName());
                        thumbnailsBean.setTitle(file.getName());
                        thumbnailsBean.setPath(file.getAbsolutePath());
                        thumbnailsBean.setBitmap(ThumbnaiUtil.getImageThumbnail(file.getAbsolutePath(), width, height));
                        thumbnailsBeans.add(thumbnailsBean);
                    }
                }
            }
        }
        return thumbnailsBeans;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
