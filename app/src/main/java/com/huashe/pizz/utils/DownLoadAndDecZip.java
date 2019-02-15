package com.huashe.pizz.utils;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DownLoadAndDecZip extends AsyncTask<Void, Void, Boolean> {

    private String downUrl,decZipToFile;//下载地址、解压之后的地址
    public DownLoadAndDecZip(String in, String out){
        super();
        this.downUrl = in;
        this.decZipToFile = out;
    }
    @Override
    protected void onPostExecute(Boolean aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        File file1 = new File(decZipToFile);
        while (downUrl != null) {//如果下载并解压完成跳出循环
            try {
                URL url = new URL(downUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setReadTimeout(5 * 1000);
                conn.connect();
                InputStream is = conn.getInputStream();
                int availableLength = conn.getContentLength();
                File file = new File(decZipToFile + "mzh.zip");//你可以根据你的需要给成自己的zip名称
                if (file.exists()) {//压缩文件存在
                    upZipFile(file, file1.getAbsolutePath());
                    break;//解压完成跳出循环
                }
                //压缩文件不存在  下载文件并解压
                file.setReadable(true);
                file.setWritable(true);
                file.setExecutable(true);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[256];
                int size;
                while ((size = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, size);
                }
                is.close();
                fos.flush();
                fos.close();
                conn.disconnect();

                if (availableLength == file.length()) { // 如果出现未下载完成的情况，则继续下载
                    upZipFile(file, file1.getAbsolutePath());//下载完成解压文件
                } else {//文件不完整，删除文件重新下载
                    file.delete();
                    continue;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除整个文件夹 或者文件
     *
     * @param file
     */
    public void deleteDir(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
            }

            for (int index = 0; index < childFiles.length; index++) {
                deleteDir(childFiles[index]);
            }
        }
        file.delete();
    }

    /**
     * 解压缩功能.
     * 将zipFile文件解压到folderPath目录下.
     *
     * @throws Exception
     */
    public void upZipFile(File zipFile, String folderPath) throws IOException {
        File dirFile = new File(folderPath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        ZipFile zfile = new ZipFile(zipFile);
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                String dirstr = folderPath + ze.getName();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                File tmpFile = new File(dirstr);
                continue;
            }
            File file = getRealFileName(folderPath, ze.getName());
            if (file.exists()) {
                continue;
            }
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
        System.out.println("将zipFile文件解压到folderPath目录下."+folderPath);
        //解压完之后  删除压缩包
        //deleteDir(new File(out + "mzh.zip"));
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    substr = new String(substr.getBytes("8859_1"), "GB2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);
            }
            if (!ret.exists()) {
                ret.mkdirs();
            }
            substr = dirs[dirs.length - 1];
            try {
                substr = new String(substr.getBytes("8859_1"), "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            ret = new File(ret, substr);
            return ret;
        }
        return ret;
    }
}