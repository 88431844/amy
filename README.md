# Amy

🔥 Amy 是一个快速优雅的漫画爬虫.

<p align="center">
    <img src="https://github.com/mayuanucas/amy/blob/master/img/demo.svg"/>
</p>

> Amy is a fast and elegant comic crawler.
> 
> 如果您觉得好用,请支持一个 **star** 和 **follow** 😘

## 目录

- [安装](#安装)
- [使用](#使用)
- [支持站点](#支持站点)
- [NOTE](#note)
- [License](#license)

## 安装

### 前提条件

- **[Java](https://jingyan.baidu.com/article/e75aca85b29c3b142edac6a8.html)**

> **Note**: 建议安装 Java 8 及以上版本.

### 获取程序

本程序无需安装,直接从 **[Releases](https://github.com/mayuanucas/amy/releases)** 页面下载最新的 **jar** 包,使用命令行运行即可.

> **Note:** Windows 用户推荐使用 PowerShell

## 使用

命令行运行:

> **java -jar amy.jar [-hV] [-o=<outputPath>] URL**

```bash
A fast and elegant comic crawler.
      URL         漫画主页网址
  -h, --help      Show this help message and exit.
  -o, --output=[outputPath]
                  存储位置
  -V, --version   Print version information and exit.
```

> **Note:** URL应是漫画主页面的地址

### 示例

以爬取 **[http://comic.kukudm.com/comiclist/2387/index.htm](http://comic.kukudm.com/comiclist/2387/index.htm) 漫画** 为例,命令行输入下面的命令:

```bash
java -jar amy.jar -o ~/Downloads/comic/ http://comic.kukudm.com/comiclist/2387/index.htm
```

下载完成后,漫画存储在指定的目录下: **~/Downloads/comic/**

<p align="center">
    <img src="/img/example.svg?raw=true"/>
</p>

## 支持站点

| Site | URL                      |
| ---- | ------------------------ |
| KuKu | http://comic.kukudm.com/ |

## NOTE

> 本项目仅供学习交流使用,不得用作非法用途.使用本程序的用户自身承担相关法律责任.如果有侵犯版本,请联系作者删除.

## License

Apache License
