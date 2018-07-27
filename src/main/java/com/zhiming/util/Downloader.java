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
