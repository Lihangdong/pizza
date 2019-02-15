package com.huashe.pizz.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

public class ThumbnaiUtil {
    /**
     * @param imagePath 文件路径
     * @param width     缩略图的宽度
     * @param height    缩略图的高度
     * @return
     * @Title: getImageThumbnail
     * @Description: 获取指定路径的图片的缩略图
     * @author: leobert.lan
     */
    public static  Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * inJustDecodeBounds: If set to true, the decoder will return null (no
         * bitmap), but the out... fields will still be set, allowing the caller
         * to query the bitmap without having to allocate the memory for its
         * pixels.
         */
        options.inJustDecodeBounds = true;
        // 获取图片的宽、高，但是：bitmap为null！可以看一下上面的内容体会一下
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        be = Math.min(beHeight, beWidth);
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;
        // 真正读入图片
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils 根据原来的图创建缩略图
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * @param videoPath 视频路径
     * @param width
     * @param height
     * @param kind      kind could be MINI_KIND or MICRO_KIND
     * @return
     * @Title: getVideoThumbnail
     * @Description: 获取指定路径的视频的缩略图
     * @author: leobert.lan
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                     int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        // kind could be MINI_KIND or MICRO_KIND
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
}
