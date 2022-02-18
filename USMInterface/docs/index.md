[English](index_en-US.md) | **简体中文**

[加入我们的QQ群参与更多讨论！](https://qm.qq.com/cgi-bin/qm/qr?k=EOGFe43mN5UYC0RWwi0Gen_eSceN-d64&jump_from=webapi)

## Quick Start
在**Release**中下载UniversalServerManager.jar，然后，如果您想同时管理多个服务器，选择多服务器模式，否则，选单服务器模式。
##### 单服务器模式
使用`java -jar UniversalServerManager.jar -mode single -setup -generate-script -stop`启动USM，然后每次只需要运行`start.sh`或`start.cmd`即可快速启动。

由于有`-setup`命令，服务器将要求您输入一些基本配置。您可可以选择去掉它，这将使用默认配置，您可以在config文件夹修改配置。

##### 多服务器模式

1. 主服务器

   与上面基本相同，但是命令如下：

   `java -jar UniversalServerManager.jar -mode main -setup -generate-script -stop`

   

2. 分服务器

## 架构

USM是分布式系统，可以通过一个主系统管理多个处于不同物理位置的服务器。因此，如果

## 命令行参数

1. `-mode`
2. `-generate-script`
3. `-stop`
4. `-setup`

