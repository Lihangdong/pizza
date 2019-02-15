package com.huashe.pizz.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.huashe.pizz.mvp.IView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.lang.reflect.ParameterizedType;

public abstract class BaseActivity<V extends IView, T extends BasePresenter<V>> extends
        RxActivity implements IView {
    public T mPresenter;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);

        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
//        mImmersionBar = ImmersionBar.with(this)
//                //解决软键盘与底部输入框冲突问题
//                .keyboardEnable(true)
//                //状态栏字体是深色，不写默认为亮色
//                .statusBarDarkFont(true, 0.2f)
//                //状态栏颜色，不写默认透明色
////                .statusBarColor(R.color.status_bar)
//                //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
//                .fitsSystemWindows(true);
//        mImmersionBar.init();  //所有子类都将继承这些相同的属性

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public LifecycleTransformer bindLifecycle() {
        LifecycleTransformer objectLifecycleTransformer = bindToLifecycle();
        return objectLifecycleTransformer;
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
