package com.github.universalservermanager;

public class UniversalServerManager {
    static boolean single = true;

    public static void main(String[] args) {
        System.out.println("UniversalServerManager");
        System.out.println("copyright (c) 2022, UniversalServerManager organization and contributors.");
        System.out.println("This is a **FREE** software under GNU Affero General Public License v3.0.");
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "":
                    break;
                case "-setup": {
                    if (single) {
                        System.out.println("Setup: ");
                        System.out.println("Main Server port:");
                    } else {
                    }
                    break;
                }
                default: {
                    System.out.println(String.format("Invalid arg: [%d](%s)", i, args[i]));
                    break;
                }
            }
        }
    }
}
