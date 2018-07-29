package com.zhiming;

import com.zhiming.comic.Kukudm;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import us.codecraft.webmagic.Spider;

/**
 * @author: mayuan
 * @desc:
 * @date: 2018/07/24
 */
@Command(name = "amy", mixinStandardHelpOptions = true,
        version = "amy version 0.1",
        description = "A fast and elegant comic crawler.")
public class Comic implements Runnable {

    @Option(names = {"-o", "--output"}, required = true, defaultValue = "./download", description = "存储位置")
    private String outputPath;

    @Parameters(index = "0", paramLabel = "URL", description = "漫画主页网址")
    private String startUrl;

    @Override
    public void run() {
        Spider.create(new Kukudm(outputPath))
                .addUrl(startUrl)
                .thread(2)
                .run();

        System.out.println("所有章节采集完毕,准备退出...");
    }

    public static void main(String[] args) {
        CommandLine.run(new Comic(), System.out, args);
    }
}
