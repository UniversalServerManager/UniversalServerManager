package com.github.universalservermanager;

import java.io.Console;

public class UniversalServerManager {
    static StartupType startupType = StartupType.UNKNOWN;

    public static void main(String[] args) {
        System.out.println("UniversalServerManager");
        System.out.println("copyright (c) 2022, UniversalServerManager organization and contributors.");
        System.out.println("This is a **FREE** software under GNU Affero General Public License v3.0.");
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "":
                    break;
                case "-setup": {
                    if (startupType == StartupType.SINGLE) {
                        System.out.println("Setup: ");
                        System.out.println("Main Server port:");
                    } else {
                    }
                    break;
                }
                case "-stop": {
                    return;
                }
                case "-mode": {
                    i++;
                    try {
                        startupType = StartupType.valueOf(args[i]);
                    } catch (IllegalArgumentException exception) {
                        System.out.println("[Error] Unknown start mode.");
                        System.out.println("[Warn] Reset start ode to UNKNOWN.");
                        startupType = StartupType.UNKNOWN;
                    }
                }
                default: {
                    System.out.printf("Invalid arg: [%d](%s)%n", i, args[i]);
                    break;
                }
            }
        }
        System.out.println("Setup finished! Starting server...");
        StartupSettings startupSettings=new StartupSettings();
        startupSettings.setType(startupType);
        USMServer.Instance = new USMServer(startupSettings);
        Console console = System.console();
        for(;;) {
            console.readLine();
        }
    }
}
