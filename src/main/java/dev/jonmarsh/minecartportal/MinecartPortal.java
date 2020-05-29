package dev.jonmarsh.minecartportal;

import org.bukkit.plugin.java.JavaPlugin;

public final class MinecartPortal extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[MinecartPortal] Enabled");
    }

    @Override
    public void onDisable() {
        System.out.println("[MinecartPortal] Disabled");
    }
}
