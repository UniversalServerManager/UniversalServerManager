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

USM is a distributed system, and multiple servers in different physical locations can be managed in one main system. So, it's necessary to give some definitions.

A **Main Server** is the server that can control all servers. Admins send request to Main Server and then log in. The basic operations like stopping the server are

A **Partial Server** is a server manager by the Main Server. It provides file router, console forwarder, and some operations that cost a lot of network resources.

If your server is running at the **Single Server** mode, There will be a partial server and a main server.

```java
import com.github.universalservermanager.api.configurations.Configuration;
import com.github.universalservermanager.exceptions.NotImplementedException;
import lombok.Getter;
import lombok.Setter;

class Example {
   public Example() {
      throw new NotImplementedException();
   }
}

class Conf extends Configuration {
   @Getter
   @Setter
   @NonNull
   String arg;
}
```

## Command Line Arguments

1. `-mode`
2. `-generate-script`
3. `-stop`
4. `-setup`

## Plugin API

### General Introduction

You can write plugins for UniversalServerManager , and this module will help you build a USM plugin quickly.

> Although UniversalServerManager is an open-sourced project based on AGPL v3 license , API is under MIT license , so you can use it without worrying about any copyright problems.

### Setup Environment

First , you need an API to write a USM plugin , which provides basic listeners and interfaces . You can download USM API.jar in **Releases** , and then import it into your project to finish setting up your develop environments.

Then you can go to the next step :P

### Plugin Main Class and plugin.json

First, you'll need a main class which will be the entry point of the plugin .

All the plugins must implement the interface `Plugin`, and of course you can use the  `ExamplePlugin` class, which includes some basic methods.

Here's an example:

```java
package com.example.usm.plugin;

import com.github.universalservermanager.api.plugin.ExamplePlugin;

public class MyPlugin extends ExamplePlugin {
}
```

Then you need to edit `plugin.json`, it will tell USM basic information of your plugin.

The basic elements of `plugin.json` includes `mainClass`, `name`, `version` and `authors`, without these, your plugin will be seen as invalid and will not be loaded.

```json
{
   "mainClass":"com.example.usm.plugin.MyPlugin",
   "name": "TestPlugin",
   "version": "1.0.0",
   "authors": ["USM Team"]
}
```

Here's the other elements' example.

```json
{
    "commands":[
        {
            "name": "test",
            "aliases": ["te", "t"],
            "usage": "Error, type 'test help' for help"
        }
    ],
    "depends": [
        "another plugin, if it exists and is not loaded, load it first. If it dose not exist, this plugin will not be loaded."
    ]
}
```
| Field | Description | Note |
|-------|-------------|------|
| name  |command's name|if this field is the same as another, you must use namespace to select which command to use. e.g. MyPlugin::test|
| usage |error message|appears when onCommand() of the command executor returns false.|

### onEnable(), onDisable() and onLoad()

Now, it's time to write some code.

First, let's learn three methods:

`onEnable()` is called when your plugin enabled. This means your dependencies have enabled, and you can register your listeners, initializing you variables and so on.

`onDisable()` means the server is going to reload or stop soon, you can save your data into disks.

`onLoad()` means your plugin has just loaded by the `ClassLoader`, you usually don't need to do anything.

To use these methods, you should override them.

```java
package com.example.usm.plugin;

import com.github.universalservermanager.api.plugin.ExamplePlugin;

public class MyPlugin extends ExamplePlugin {
    @Override
    public void onEnable() {
        System.out.println("Hello");
    }
    @Override
    public void onDisable() {
        System.out.println("Goodbye");
    }
    @Override
    public void onLoad() {
        System.out.println("Loaded");
    }
}
```

### Register Listeners to Listen Events

**Event**s are the ways to tell your plugin that something happened such as a server's output stream changed, a user opened a server and so on. To handle the events you will need a **listener**.

**Listeners** implements the interface `Listener`, and add `@EventHandler` to your methods that will listen to an event.

Notice that the name or overloading will not change the behavior of the listening method. Feel free to use any name you want.

Here's a example.

```java
package com.example.usm.plugin;

import com.github.universalservermanager.api.events.EventHandler;
import com.github.universalservermanager.api.events.Listener;
import com.github.universalservermanager.api.events.ServerStartEvent;

public class ExampleListener implements Listener {
    @EventHandler
    public void onServerStart(ServerStartEvent event) {
		
    }
}
```

The last step is to register your listener. This tells USM to call it when the event happens.

Do you still remember your main class? We need it to register.

```java
package com.example.usm.plugin;

import com.github.universalservermanager.api.plugin.ExamplePlugin;

public class MyPlugin extends ExamplePlugin { 
    @Override
    public void onEnable() {
        getPluginManager().registerEvents(this, new ExampleListener());
    }
}
```

For more information about USM's events, see our [API Docs](../USMInterface/docs/overview.md).

Also, you can create your own event by extending Event or CancellableEvent.

```java
package com.example.usm.plugin;

import com.github.universalservermanager.api.events.Event;
import com.github.universalservermanager.api.events.CancellableEvent;
import lombok.Getter;
import lombok.Setter;

class MyEvent extends Event { // You can also use CancellableEvent.
   @Getter
   @Setter
   private String myEventArg;
}
```
```java
package com.example.usm.plugin;

import com.github.universalservermanager.api.plugin.ExamplePlugin;

public class MyPlugin extends ExamplePlugin { 
    public void callEvent() {
        MyEvent event = new MyPlugin();
        event.setMyEventArg("test");
        this.getPluginManager().callEvents(event);
    }
}

```



### Communicate with Another Plugin by Using Channel



### Send a Broadcast to Main Server

USM is a distributed system, so every partial server has an independent copy of your plugin, a broadcast can tell them something.

### Write a CommandExecutor to Execute Your Commands.

