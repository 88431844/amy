package com.zhiming.comic;

import com.zhiming.util.Downloader;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;

/**
 * @author: mayuan
 * @desc:
 * @date: 2018/07/27
 */
public class KukudmPic implements PageProcessor {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Spider.create(new KukudmPic())
                .addUrl("http://comic.kukudm.com/comiclist/3/11/2.htm")
                .thread(5)
                .run();
    }

    /**
     * 图片服务器 URL 前缀
     */
    private static final String PICPRE = "http://n5.1whour.com/";

    private String dirPath;

    private Site site = Site.me()
            .setDomain("comic.kukudm.com")
            .setTimeOut(30000)
            .setRetryTimes(3)
            .setCycleRetryTimes(5)
            .setSleepTime(5000)
            .setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0)");

    public KukudmPic() {
    }

    public KukudmPic(String dir) {
        this.dirPath = dir;
    }

    @Override
    public void process(Page page) {
        // 分章节保存图片
        String dir = dirPath + File.separator + page.getHtml().getDocument().title().trim();
        // 该图片需要重新按照编号命名, 确保图片编号为3位,不足3位,高位填充0, 看图时方便排序.
        int id = Integer.parseInt(page.getHtml().xpath("//td[@valign=\"top\"]/text()").regex("当前第(\\d+)页").get().trim());
        String picName = String.format("%03d", id) + ".jpg";
        // 图片真实地址
        String pictureUrl = PICPRE + page.getHtml().regex("\\+\"(.+)'><span").get();
        // 必须对 URL 字符串中的特殊字符进行替换,替换为其编码
        pictureUrl = Downloader.encodingURL(pictureUrl);

        Spider.create(new KukudmData(dir, picName))
                .addUrl(pictureUrl)
                .thread(4)
                .start();
    }

    @Override
    public Site getSite() {
        return site;
    }
}
