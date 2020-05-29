package dev.jonmarsh.minecartportal;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public final class Config {
    static public String OverworldName = "world";
    static public String NetherName = "world_nether";
    static public int Scale = 8;
    static public int PortalY = 64;

    static public World Overworld;
    static public World Nether;

    static public void initialise(JavaPlugin plugin) {
        final FileConfiguration configuration = plugin.getConfig();
        configuration.options().copyDefaults();
        plugin.saveDefaultConfig();
        OverworldName = configuration.getString("Overworld", OverworldName);
        NetherName = configuration.getString("Nether", NetherName);
        Overworld = getServer().getWorld(OverworldName);
        Nether = getServer().getWorld(NetherName);
        Scale = configuration.getInt("Scale", Scale);
        PortalY = configuration.getInt("PortalY", PortalY);
    }
}