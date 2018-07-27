package com.zhiming.comic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author: mayuan
 * @desc: 解析漫画的每一章节, 获取该章节漫画图片总数, 按照规则计算出每张图片的链接地址.
 * @date: 2018/07/27
 */
public class KukudmChapter implements PageProcessor {

    public static void main(String[] args) {
        Spider.create(new KukudmChapter("./data/test"))
                .addUrl("http://comic.kukudm.com/comiclist/2036/64453/1.htm")
                .thread(4)
                .run();
    }

    private String dirPath;

    private Site site = Site.me()
            .setDomain("comic.kukudm.com")
            .setTimeOut(30000)
            .setRetryTimes(3)
            .setCycleRetryTimes(5)
            .setSleepTime(5000)
            .setCharset("gbk")
            .setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0)");

    public KukudmChapter() {
    }

    public KukudmChapter(String dir) {
        this.dirPath = dir;
    }

    @Override
    public void process(Page page) {
        // 本章节图片总数
        int pageNumber = Integer.parseInt(page.getHtml().xpath("//td[@valign=\"top\"]/text()")
                .regex("共(\\d+)页").get());

        Spider thirdPageSpider = Spider.create(new KukudmPic(dirPath));

        // 存储所有的3级页面,即每张图片的虚假 URL 链接.三级爬虫从该链接解析 js 获取真实图片链接地址
        String currentPageUrl = page.getUrl().get().trim();
        String urlPrefix = currentPageUrl.substring(0, currentPageUrl.length() - 5);
        String urlSuffix = ".htm";

        thirdPageSpider.addUrl(currentPageUrl);
        for (int i = 2; i <= pageNumber; i++) {
            thirdPageSpider.addUrl(urlPrefix + i + urlSuffix);
        }

        // 三级页面爬虫
        thirdPageSpider.thread(4).run();
    }

    @Override
    public Site getSite() {
        return site;
    }
}
