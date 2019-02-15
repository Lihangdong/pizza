package com.huashe.pizz.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huashe.pizz.MainActivity;
import com.huashe.pizz.MyApplication;
import com.huashe.pizz.R;
import com.huashe.pizz.bean.ModuleProduct.ModuleProductBean;

import java.util.List;

public class ModuleProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CommonViewHolder.onItemCommonClickListener commonClickListener;
    private List<ModuleProductBean> datas;
    private Context context;
    private int TYPE_ME = 2;
    protected boolean isScrolling = false;
    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    public ModuleProductAdapter(Context context, List<ModuleProductBean> dataList, CommonViewHolder.onItemCommonClickListener commonClickListener) {
        this.commonClickListener = commonClickListener;
        myApplication = (MyApplication) ((Activity) context).getApplication();
        this.context = context;
        this.datas = dataList;
    }

    private MyApplication myApplication;

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getItemtype();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (viewType == TYPE_ME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module_product_title, parent, false);
            return new TypeViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module_product_rv, parent, false);
            return new ProductViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        System.out.println(datas.get(i).getName()+"   itemtype  " + datas.get(i).getImage());
        if (holder instanceof TypeViewHolder) {
            if (datas.get(i).getSubname()!=null){
                ((TypeViewHolder) holder).setText(R.id.tv_item_moduleproduct_title, datas.get(i).getSubname());
            }else {
                ((TypeViewHolder) holder).tvItemModuleproductTitle.setVisibility(View.GONE);
            }
        } else {
            if (datas.get(i).getImage()!=null && !isScrolling) {
                ((ProductViewHolder) holder).ivModuleproductRvBg.setVisibility(View.VISIBLE);
                ((ProductViewHolder) holder).ibModuleproductRvLove.setVisibility(View.VISIBLE);
                ((ProductViewHolder) holder).tvModuleproductRvName.setVisibility(View.VISIBLE);
                Glide.with(context).load(datas.get(i).getImage()).into(((ProductViewHolder) holder).ivModuleproductRvBg);
                if (datas.get(i).isLove()) {
                    ((ProductViewHolder) holder).ibModuleproductRvLove.setBackgroundResource(R.drawable.view_icon_like_sel);
                } else {
                    ((ProductViewHolder) holder).ibModuleproductRvLove.setBackgroundResource(R.drawable.view_icon_like_nor);
                }
                ((ProductViewHolder) holder).tvModuleproductRvName.setText(datas.get(i).getName());
                ((ProductViewHolder) holder).ibModuleproductRvLove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (myApplication.userLoveLists.contains(datas.get(i))) {
                            myApplication.userLoveLists.remove(datas.get(i));
                            view.setBackgroundResource(R.drawable.view_icon_like_nor);
                            if (myApplication.userLoveLists.size() == 0) {
                                ((MainActivity) context).rbListSave.setBackgroundResource(R.drawable.nav_icon_list_default);
                            }
                            datas.get(i).setLove(false);
                        } else {
                            myApplication.userLoveLists.add(datas.get(i));
                            view.setBackgroundResource(R.drawable.view_icon_like_sel);
                            datas.get(i).setLove(true);
                            ((MainActivity) context).rbListSave.setBackgroundResource(R.drawable.nav_icon_list_nor);
                        }


                    }
                });
                ((ProductViewHolder) holder).setCommonClickListener(commonClickListener);
            } else {
//            holder.avatarImg.setImageResource(占位图本地资源);
                ((ProductViewHolder) holder).ivModuleproductRvBg.setVisibility(View.GONE);
                ((ProductViewHolder) holder).ibModuleproductRvLove.setVisibility(View.GONE);
                ((ProductViewHolder) holder).tvModuleproductRvName.setVisibility(View.GONE);
            }

        }
    }


    private static class TypeViewHolder extends CommonViewHolder {
        TextView tvItemModuleproductTitle;

        public TypeViewHolder(View view) {
            super(view);
            tvItemModuleproductTitle = view.findViewById(R.id.tv_item_moduleproduct_title);
        }
    }

    private static class ProductViewHolder extends CommonViewHolder {
        ImageView ivModuleproductRvBg;
        ImageButton ibModuleproductRvLove;
        TextView tvModuleproductRvName;

        public ProductViewHolder(View view) {
            super(view);
            ivModuleproductRvBg = view.findViewById(R.id.iv_moduleproduct_rv_bg);
            ibModuleproductRvLove = view.findViewById(R.id.ib_moduleproduct_rv_love);
            tvModuleproductRvName = view.findViewById(R.id.tv_moduleproduct_rv_name);
        }
    }
}
