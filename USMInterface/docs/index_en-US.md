**English** | [简体中文](index.md)

[Join in our QQ group to participate in the discussion!](https://qm.qq.com/cgi-bin/qm/qr?k=EOGFe43mN5UYC0RWwi0Gen_eSceN-d64&jump_from=webapi)

Welcome to UniversalServerManager Documents.

## Quick Start

To start, please download the latest `UniversalServerManager.jsr` in **Releases** page. After that, if you want to manage more than one server, goto **Multi-Servers**, otherwise, see **Single Server**.

#### Single Server

Run `java -jar UniversalServerManager.jar -mode single -setup -generate-script -stop`to start UniversalServerManager, then, you only need to run `start.sh`(Linux) or `start.cmd`(Windows) to start USM quickly every time.

Notice that the command line argument `-setup` will ask you to input some basic configurations. you can remove it and then the server will use a default config to start. You can change these configurations later in `config` folder.

#### Multi-Servers

1. Main Server

   The steps are nearly the same as above. The only difference is the command to start. 

   ```bash
   java -jar UniversalServerManager.jar -mode main -setup -generate-script -stop
   ```

2. Partial Server

   ```bash
   java -jar UniversalServerManager.jar -mode main -setup -generate-script -stop
   ```


## Architecture

USM is a distributed system , and so multiple servers in different physical locations can be managed in one main system . Thus , if

## Command Line Arguments

1. `-mode`
2. `-generate-script`
3. `-stop`
4. `-setup`

## Plugin API

### General Introduction

You can write plugins for UniversalServerManager , and this module will help you build a USM plugin quickly.

> Although UniversalServerManager is an open-sourced project based on AGPL license , API is based on MIT license , so you can use it without worrying about any copyright problems.

### Environment Settings

First , you need an API to write a USM plugin , which provides basic <u>listener and action interfaces</u> . You can download USMAPI.jar in **Releases** , and then import it into your project to finish setting <u>develop environments</u>.

Then you can go to the next step :P

### Plugin Main Class and plugin.json

First , you'll need a main class which will be the entry point of the plugin .

All the plugins must implement the interface `Plugin` , and of course you can use the  `ExamplePlugin` class ,which includes some basic methods.

Here's an example:

```java
package com.example.usm.plugin;

import com.github.universalservermanager.api.plugin.ExamplePlugin

public class MyPlugin extends ExamplePlugin {
}
```

Then you need to edit `plugin.json` , it will tell USM basic informations of your plugin namely location of the main class , the developer name , the plugin name and the version , as well as some additional informations namely dependencies and commands .

```json
{
   "mainClass":"com.example.usm.plugin.MyPlugin",
   "name": "TestPlugin",
   "version": "1.0.0",
   "authors": ["USM Team"]
}
```
