package com.zhiming;

import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * @author: mayuan
 * @desc:
 * @date: 2018/07/24
 */
@Command(name = "amy", mixinStandardHelpOptions = true, version = "amy version 0.1", description = "A fast and elegant comic crawler -> (http://comic.kukudm.com/)")
public class Comic implements Runnable {

    @Option(names = {"-o", "--output"}, required = true, defaultValue = "./download", description = "存储位置")
    private String outputPath;

    @Parameters(index = "0", paramLabel = "URL", description = "漫画主页网址")
    private String startUrl;

    @Override
    public void run() {
        System.out.println("输出位置:" + outputPath);
        System.out.println("爬取页面为:" + startUrl);
    }

    public static void main(String[] args) {
        CommandLine.run(new Comic(), System.out, args);
    }
}
