package com.zhiming.util;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
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

    public static void storeBytes(byte[] data, String file) throws IOException {
        BufferedOutputStream bufferedOutputStream = null;

        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(data);
        } finally {
            bufferedOutputStream.close();
        }
    }

    public static void downloadUsingStream(String urlString, String file) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            URL url = new URL(urlString);
            bis = new BufferedInputStream(url.openStream());
            bos = new BufferedOutputStream(new FileOutputStream(file));

            byte[] buffer = new byte[1024];
            int count = 0;
            while (-1 != (count = bis.read(buffer))) {
                bos.write(buffer, 0, count);
            }
        } finally {
            bos.close();
            bis.close();
        }
    }

    public static void downloadUsingNIO(String urlString, String file) throws IOException {
        URL url = new URL(urlString);
        ReadableByteChannel fcin = Channels.newChannel(url.openStream());
        FileChannel fcout = null;
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        try {
            fcout = new FileOutputStream(file).getChannel();

            while (-1 != fcin.read(buffer)) {
                buffer.flip();
                fcout.write(buffer);
                buffer.clear();
            }

        } finally {
            fcin.close();
            fcout.close();
        }
    }
}
