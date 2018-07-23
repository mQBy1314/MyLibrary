package com.mqby.mqlibrary.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author MaQiang
 * @time 2018/6/11 0011 18:20
 * @QQ 1033785970
 * @class File工具类
 */
public class FileTool {

    public static final String SDPATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * 保存图片
     *
     * @param context
     * @param filePath
     * @param picName
     */
    public static void saveImage(Context context, String filePath, String picName) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        saveImage(context, bitmap, picName);
    }

    /**
     * 保存图片
     *
     * @param context
     * @param bitmap
     * @param picName
     */
    public static void saveImage(Context context, Bitmap bitmap, String picName) {
        File dir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = new File(SDPATH);
        } else {
            dir = new File(Environment.getDataDirectory().getPath());
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, picName + ".jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);     //用.JPEG压缩速度比较快......
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (file.exists()) {
            ToastTool.showShort(file.getParent() + "文件夹");
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);  //刷新图库
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }


    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            LogTool.i("createSDDir:" + dir.getAbsolutePath());
            LogTool.i("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    /**
     * 根据路径删除文件夹
     * @param path
     */
    public static void deleteDir(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(path); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

}
