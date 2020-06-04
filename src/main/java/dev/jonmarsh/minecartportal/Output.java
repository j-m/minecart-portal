package dev.jonmarsh.minecartportal;

public class Output {
    public static void ServerLog(String message) {
        if (Config.Log == true) {
            System.out.println("[MinecartPortal] " + message);
        }
    }
}
