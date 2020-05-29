package dev.jonmarsh.minecartportal;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public final class Config {
    private static Config config = null;

    static public World Overworld;
    static public World Nether;
    static public int Scale;
    static public int PortalY;

    private Config(JavaPlugin plugin) {
        final FileConfiguration configuration = plugin.getConfig();
        configuration.options().copyDefaults();
        plugin.saveDefaultConfig();
        Overworld = getServer().getWorld(configuration.getString("Overworld", "world"));
        Nether = getServer().getWorld(configuration.getString("Nether", "world_nether"));
        Scale = plugin.getConfig().getInt("Scale", 8);
        PortalY = plugin.getConfig().getInt("PortalY", 64);
    }

    static public void initialise(JavaPlugin plugin) {
        if (config == null) {
            config = new Config(plugin);
        }
    }

    public static Config getInstance() {
        return config;
    }
}