package com.zhiming.comic;

import com.zhiming.util.Downloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;

/**
 * @author: mayuan
 * @desc: 获取图片的字节流,保存到指定路径文件中.
 * @date: 2018/07/27
 */
public class KukudmData implements PageProcessor {

    private String dirPath;

    private String fileName;

    private Site site = Site.me()
            .setDomain("comic.kukudm.com")
            .setTimeOut(30000)
            .setRetryTimes(3)
            .setCycleRetryTimes(5)
            .setSleepTime(5000)
            .setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0)");

    public KukudmData() {
    }

    public KukudmData(String dir, String fileName) {
        this.dirPath = dir;
        this.fileName = fileName;
    }

    @Override
    public void process(Page page) {
        Downloader.ensureDir(this.dirPath);

        Downloader.storeData(page.getBytes(), dirPath + File.separator + fileName);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
