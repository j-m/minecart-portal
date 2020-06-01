package dev.jonmarsh.minecartportal;

import dev.jonmarsh.minecartportal.event.VehicleInPortal;
import dev.jonmarsh.minecartportal.event.VehicleOutPortal;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecartPortal extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[MinecartPortal] Loading configuration...");
        Config.initialise(this);
        System.out.println("[MinecartPortal] Registering events...");
        getServer().getPluginManager().registerEvents(new VehicleInPortal(), this);
        getServer().getPluginManager().registerEvents(new VehicleOutPortal(), this);
        System.out.println("[MinecartPortal] Ready");
    }

    @Override
    public void onDisable() {
        System.out.println("[MinecartPortal] Disabled");
    }
}
