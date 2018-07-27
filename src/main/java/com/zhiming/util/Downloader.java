package com.zhiming.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author: mayuan
 * @desc:
 * @date: 2018/07/27
 */
public class Downloader {

    /**
     * 对原始 URL 进行编码,转换字符串中特殊字符
     *
     * @param url
     * @return
     */
    public static String encodingURL(String url) {
        if (null == url || 0 >= url.length()) {
            return url;
        }

        String newString = url.replace("[", "%5B");
        newString = newString.replace("]", "%5D");

        return newString;
    }

    /**
     * 确保目录存在,不存在则创建目录(包含父目录)
     *
     * @param dirPath
     * @return
     */
    public static boolean ensureDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            return dir.mkdirs();
        }
        return true;
    }

    public static void storeData(byte[] data, String file) {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(data);
            bufferedOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void downloadUsingStream(String urlString, String file) {
        try {
            URL url = new URL(urlString);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fos = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int count = 0;
            while (-1 != (count = bis.read(buffer, 0, 1024))) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            bis.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadUsingNIO(String urlString, String file) {
        try {
            URL url = new URL(urlString);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
