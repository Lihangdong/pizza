package com.huashe.pizz.other.rx;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * 通用工具类
 */
public class CommonUtil {

  /**
   * 检查是否有网络
   */
  public static boolean isNetworkAvailable(Context context) {

    NetworkInfo info = getNetworkInfo(context);
    return info != null && info.isAvailable();
  }


  /**
   * 检查是否是WIFI
   */
  public static boolean isWifi(Context context) {

    NetworkInfo info = getNetworkInfo(context);
    if (info != null) {
      if (info.getType() == ConnectivityManager.TYPE_WIFI) {
        return true;
      }
    }
    return false;
  }


  /**
   * 检查是否是移动网络
   */
  public static boolean isMobile(Context context) {

    NetworkInfo info = getNetworkInfo(context);
    if (info != null) {
      if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
        return true;
      }
    }
    return false;
  }


  private static NetworkInfo getNetworkInfo(Context context) {

    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
        Context.CONNECTIVITY_SERVICE);
    return cm.getActiveNetworkInfo();
  }


  /**
   * 检查SD卡是否存在
   */
  private static boolean checkSdCard() {

    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
  }


  /**
   * 获取手机SD卡总空间
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
  private static long getSDcardTotalSize() {

    if (checkSdCard()) {
      File path = Environment.getExternalStorageDirectory();
      StatFs mStatFs = new StatFs(path.getPath());
      long blockSizeLong = mStatFs.getBlockSizeLong();
      long blockCountLong = mStatFs.getBlockCountLong();

      return blockSizeLong * blockCountLong;
    } else {
      return 0;
    }
  }


  /**
   * 获取SDka可用空间
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
  private static long getSDcardAvailableSize() {

    if (checkSdCard()) {
      File path = Environment.getExternalStorageDirectory();
      StatFs mStatFs = new StatFs(path.getPath());
      long blockSizeLong = mStatFs.getBlockSizeLong();
      long availableBlocksLong = 0;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        availableBlocksLong = mStatFs.getAvailableBlocksLong();
      }
      return blockSizeLong * availableBlocksLong;
    } else {
      return 0;
    }
  }


  /**
   * 获取手机内部存储总空间
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
  public static long getPhoneTotalSize() {

    if (!checkSdCard()) {
      File path = Environment.getDataDirectory();
      StatFs mStatFs = new StatFs(path.getPath());
      long blockSizeLong = 0;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        blockSizeLong = mStatFs.getBlockSizeLong();
      }
      long blockCountLong = mStatFs.getBlockCountLong();
      return blockSizeLong * blockCountLong;
    } else {
      return getSDcardTotalSize();
    }
  }


  /**
   * 获取手机内存存储可用空间
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
  public static long getPhoneAvailableSize() {

    if (!checkSdCard()) {
      File path = Environment.getDataDirectory();
      StatFs mStatFs = new StatFs(path.getPath());
      long blockSizeLong = 0;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        blockSizeLong = mStatFs.getBlockSizeLong();
      }
      long availableBlocksLong = 0;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        availableBlocksLong = mStatFs.getAvailableBlocksLong();
      }
      return blockSizeLong * availableBlocksLong;
    } else
      return getSDcardAvailableSize();
  }

  /**
   * 判断某个界面是否在前台
   *
   * @param activity 要判断的Activity
   * @return 是否在前台显示
   */
  public static boolean isForeground(Activity activity) {
    return isForeground(activity, activity.getClass().getName());
  }

  /**
   * 判断某个界面是否在前台
   *
   * @param context
   * @param className
   *            某个界面名称
   */
  private static boolean isForeground(Context context, String className) {
    if (context == null || TextUtils.isEmpty(className)) {
      return false;
    }

    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
    if (list != null && list.size() > 0) {
      ComponentName cpn = list.get(0).topActivity;

      if (className.equals(cpn.getClassName())) {
        return true;
      }
    }

    return false;
  }
}
