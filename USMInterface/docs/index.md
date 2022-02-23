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
   
   ```bash
   java -jar UniversalServerManager.jar -mode main -setup -generate-script -stop
   ```

2. 分服务器

   ```bash
   java -jar UniversalServerManager.jar -mode main -setup -generate-script -stop
   ```


## 架构

USM是分布式系统，可以通过一个主系统管理多个处于不同物理位置的服务器。因此，如果

## 命令行参数

1. `-mode`
2. `-generate-script`
3. `-stop`
4. `-setup`

## API接口

### 概述

您可以为UniversalServerManager编写插件，而这个模块将带您快速建立一个USM插件！

> 虽然UniversalServerManager基于AGPL协议开源，但是API是基于MIT协议的，请放心使用，无需任何后顾之忧。

### 环境配置

首先，一个USM插件的编写需要一个API，它提供基本的监听器和操作接口。您可以下载**Releases**中的USMAPI.jar，并将其导入您的项目，即可快速配置完成开发环境。

然后，您可以开始下一步了~

### 插件主类和plugin.json

首先，您需要一个主类，它将作为插件运行的入口点。

所有插件必须实现`Plugin`接口，当然，您可以使用`ExamplePlugin`类，这包含了一些基本的方法。

比如这样：
```javaa
package com.example.usm.plugin;

import com.github.universalservermanager.api.plugin.ExamplePlugin

public class MyPlugin extends ExamplePlugin {
}
```

然后，您需要编辑`plugin.json`，这将告诉USM你的插件的主类位置、作者、名称、版本等基本信息以及依赖、命令等附加信息。

