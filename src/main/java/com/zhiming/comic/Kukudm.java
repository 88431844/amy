package com.zhiming.comic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.util.List;

/**
 * @author: mayuan
 * @desc: kukudm
 * @date: 2018/07/27
 */
public class Kukudm implements PageProcessor {

    public static void main(String[] args) {
        Spider.create(new Kukudm("./data"))
                .addUrl("http://comic.kukudm.com/comiclist/2467/index.htm")
                .thread(5)
                .run();
    }

    private String dirPath;

    private Site site = Site.me()
            .setDomain("comic.kukudm.com")
            .setTimeOut(30000)
            .setRetryTimes(3)
            .setCycleRetryTimes(5)
            .setSleepTime(5000)
            .setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0)");

    public Kukudm() {
    }

    public Kukudm(String dir) {
        this.dirPath = dir;
    }

    @Override
    public void process(Page page) {
        // 漫画名称
        String comicName = page.getHtml().xpath("//head/title/text()").regex("(.+)漫画在线_在线漫画").get().trim();

        // 获取所有漫画章节地址
        List<String> chaptersUrl = page.getHtml().xpath("dd/a[1]").links().all();

        Spider chaptersSpider = Spider.create(new KukudmChapter(dirPath + File.separator + comicName));
        for (String cUrl : chaptersUrl) {
            chaptersSpider.addUrl(cUrl);
        }
        chaptersSpider.thread(4).run();

    }

    @Override
    public Site getSite() {
        return site;
    }

}
