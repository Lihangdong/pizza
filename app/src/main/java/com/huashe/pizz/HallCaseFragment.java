package com.huashe.pizz;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huashe.pizz.adapter.CommonAdapter;
import com.huashe.pizz.adapter.CommonRecycleAdapter;
import com.huashe.pizz.adapter.CommonViewHolder;
import com.huashe.pizz.adapter.ViewHolder;
import com.huashe.pizz.bean.HallCase.HallCaseBean;
import com.huashe.pizz.bean.HallCase.HallCaseMenu;
import com.huashe.pizz.bean.greendao.HallCaseBeanDaoUtil;
import com.huashe.pizz.bean.greendao.HallCaseMenuDaoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HallCaseFragment extends Fragment {


    private int currItem = -1;
    private ListView listView;
    private CommonAdapter<HallCaseMenu> commonAdapter;
    private View lastView;
    private List<HallCaseMenu> hallCaseMenus;
    private RecyclerView rvHallCase;
    private List<HallCaseBean> hallCaseBeans;
    private TextView tvHallCaseTitle;
    private boolean isRightFresh = true;
    private boolean isFresh = true;
    private RelativeLayout rlHallCase;
    private RecyclerView rvHallCaseDetail;
    private View inflate;
    private List<String> imagePaths;

    public HallCaseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hall_case, container, false);
        tvHallCaseTitle = view.findViewById(R.id.tv_hall_case_title);
        rlHallCase = view.findViewById(R.id.rv_hall_case);

        initLeft(view);
        initRight(view);
        return view;
    }

    private void initRight(View view) {
        rvHallCase = view.findViewById(R.id.right_rv_hall_case);
        hallCaseBeans = HallCaseBeanDaoUtil.queryAll();
        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        rvHallCase.setLayoutManager(layoutManager);
        // 设置 RecyclerView的Adapter
        // 注意一定在设置了布局管理器之后调用
        rvHallCase.setAdapter(new CommonRecycleAdapter<HallCaseBean>(getActivity(), hallCaseBeans, R.layout.item_hall_case) {
            @Override
            public void bindData(CommonViewHolder holder, final HallCaseBean data) {
                holder.setText(R.id.tv_hall_case_name, data.getName());
                Glide.with(getActivity()).load(data.getImage()).into((ImageView) holder.getView(R.id.iv_hall_case_pic));
                holder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
                    @Override
                    public void onItemClickListener(final int position) {
                        inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_hall_case_detail, null);
                        rlHallCase.removeAllViews();
                        rlHallCase.addView(inflate);
                        System.out.println(data.getImagePath());

                        Button btnModuleproductDetailBack = inflate.findViewById(R.id.btn_hall_case_detail_back);
                        btnModuleproductDetailBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rlHallCase.removeAllViews();
                                rlHallCase.addView(rvHallCase);
                            }
                        });
                        RecyclerView rvHallCaseDetail = inflate.findViewById(R.id.rv_hall_case_detail);
                        final TextView tvHallCaseDetailTitle = inflate.findViewById(R.id.tv_hall_case_detail_title);
                        imagePaths =Arrays.asList( data.getImagePath().split(","));
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        rvHallCaseDetail.setLayoutManager(linearLayoutManager);
                        rvHallCaseDetail.setAdapter(new CommonRecycleAdapter<String>(getActivity(), imagePaths, R.layout.item_rv_hall_case_detail) {
                            @Override
                            public void bindData(CommonViewHolder holder, final String s) {
                                tvHallCaseDetailTitle.setText(data.getName());
                                Glide.with(getActivity()).load(s).into((ImageView) holder.getView(R.id.iv_item_hallcase_detail));
                                ((ImageView) holder.getView(R.id.iv_item_hallcase_detail)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), PlayPicActivity.class);
                                        intent.putExtra("path", s);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onBindViewHolder(CommonViewHolder holder, int position) {
                                super.onBindViewHolder(holder, position);
                            }
                        });

                        rvHallCaseDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            int mScrollThreshold;

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                                boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
                            }

                            public void setScrollThreshold(int scrollThreshold) {
                                mScrollThreshold = scrollThreshold;
                            }
                        });
                        if (rvHallCaseDetail.getOnFlingListener() == null) {
                            PagerSnapHelper snapHelper = new PagerSnapHelper();
                            snapHelper.attachToRecyclerView(rvHallCaseDetail);
                        }

                    }

                    @Override
                    public void onItemLongClickListener(int position) {

                    }
                });
            }
        });
        rvHallCase.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
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
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && firstItemPosition != lastItemPosition) {
                        lastItemPosition = firstItemPosition; //得到要更新的item的view
                        RecyclerView.ViewHolder viewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(lastItemPosition);
//                        viewHolderForAdapterPosition.itemView.findViewById(R.id.tv_hall_case_title).setBackgroundColor(getResources().getColor(R.color.left_mkhcp_sel_text_color));
                        String id = hallCaseBeans.get(lastItemPosition).getType();
                        isFresh = false;
                        if (isRightFresh) {
                            for (int i = 0; i < hallCaseMenus.size(); i++) {
                                if (id.equals(hallCaseMenus.get(i).getId())) {
                                    System.out.println(i);
                                    listView.performItemClick(listView.getChildAt(i), i, listView.getItemIdAtPosition(i));
                                    isRightFresh = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void initLeft(View view) {
        hallCaseMenus = HallCaseMenuDaoUtil.queryAll();
        listView = view.findViewById(R.id.left_lv_hall_case);
        commonAdapter = new CommonAdapter<HallCaseMenu>(getActivity(), hallCaseMenus, R.layout.item_left_menu) {
            @Override
            public void convert(ViewHolder holder, HallCaseMenu hallCaseMenu, int i) {
                if (i == 0) {
                    holder.setText(R.id.tv_lv_menu_title, hallCaseMenu.getName());
                    Glide.with(getActivity()).load(hallCaseMenu.getIcon2()).into((ImageView) holder.getView(R.id.iv_lv_menu_icon));
                    ((TextView) holder.getView(R.id.tv_lv_menu_title)).setTextColor(getResources().getColor(R.color.left_mkhcp_sel_text_color));
                    currItem = 0;
                    lastView = holder.getConvertView();
                } else {
                    holder.setText(R.id.tv_lv_menu_title, hallCaseMenu.getName());
                    Glide.with(getActivity()).load(hallCaseMenu.getIcon1()).into((ImageView) holder.getView(R.id.iv_lv_menu_icon));
                }

            }
        };
//        listView.post(new Runnable() {
//            @Override
//            public void run() {
//                listView.performItemClick(listView.getChildAt(0), 0, listView.getItemIdAtPosition(0));
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (lastView != null && currItem != -1) {
                    ((TextView) lastView.findViewById(R.id.tv_lv_menu_title)).setTextColor(getResources().getColor(R.color.left_mkhcp_nor_text_color));
                    Glide.with(getActivity()).load(hallCaseMenus.get(currItem).getIcon1()).into((ImageView) lastView.findViewById(R.id.iv_lv_menu_icon));
//                    ((ImageView) lastView.findViewById(R.id.iv_lv_menu_icon)).setBackgroundResource(getIcon1());
                }
                ((TextView) view.findViewById(R.id.tv_lv_menu_title)).setTextColor(getResources().getColor(R.color.left_mkhcp_sel_text_color));
                Glide.with(getActivity()).load(hallCaseMenus.get(i).getIcon2()).into((ImageView) view.findViewById(R.id.iv_lv_menu_icon));
//                ((ImageView) view.findViewById(R.id.iv_left_lv_3)).setBackgroundResource(imagesSelect[i]);
                tvHallCaseTitle.setText(hallCaseMenus.get(i).getName());
                for (int m = 0; m < hallCaseBeans.size(); m++) {
                    isRightFresh = false;
                    if (isFresh) {
                        if (hallCaseMenus.get(i).getId().equals(hallCaseBeans.get(m).getType())) {
                            smoothMoveToPosition(rvHallCase, m);
                            break;
                        }
                    }
                    isFresh = true;


                }
                lastView = view;
                currItem = i;
            }
        });
        listView.setAdapter(commonAdapter);

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

    private List<String> getImagePathFromSD(String path) {
        //  图片列表
        final List<String> imagePathList = new ArrayList<>();
        imagePathList.clear();
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(path);
        File[] files = fileAll.listFiles();
        List fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (File file1 : files) {
            imagePathList.add("file://" + file1.getAbsolutePath());
        }

        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
//        for (int i = 0; i < files.length; i++) {
//            File file = files[i];
//            if (file.isFile()) {
//                if (file.getName().endsWith("png") && file.getName().endsWith("jpg") && file.getName().endsWith("gif") && file.getName().endsWith("jpeg") && file.getName().endsWith("bmp")) {
//                    //imagePathList.add(file.getPath());
//                    imagePathList.add("file://" + file.getAbsolutePath()+file.getName());
//                }
//
//            }
//        }
        // 返回得到的图片列表
        return imagePathList;
    }

}
