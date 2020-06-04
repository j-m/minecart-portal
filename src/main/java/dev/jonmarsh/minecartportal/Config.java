package dev.jonmarsh.minecartportal;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Config {
    static public double MinimumSpeed = 1.5;

    static public void initialise(JavaPlugin plugin) {
        final FileConfiguration configuration = plugin.getConfig();
        configuration.options().copyDefaults();
        plugin.saveDefaultConfig();
        MinimumSpeed = configuration.getDouble("MinimumSpeed", MinimumSpeed);
    }
}