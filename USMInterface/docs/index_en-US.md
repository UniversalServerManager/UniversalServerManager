**English** | [简体中文](index.md)

Welcome to UniversalServerManager Documents.

## Quick Start

To start, please download the latest `UniversalServerManager.jsr` in **Releases** page. After that, if you want to manage more than one servers, goto **Multi-Servers**, otherwise, see **Simgle Server**.

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

   

## Plugin API

