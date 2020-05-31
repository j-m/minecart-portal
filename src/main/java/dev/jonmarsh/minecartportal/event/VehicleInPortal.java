package dev.jonmarsh.minecartportal.event;

import dev.jonmarsh.minecartportal.Config;
import dev.jonmarsh.minecartportal.Output;
import dev.jonmarsh.minecartportal.VehicleTeleport;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class VehicleInPortal implements Listener {

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Entity vehicle = player.getVehicle();
        if (vehicle == null) {
            return;
        }
        final Location location = event.getFrom();
        if (!isPortal(location)) {
            return;
        }
        Output.ServerLog("Detected a player mount in a portal");
        final Location target = calculateNewLocation(location);
        new VehicleTeleport(vehicle, target).run();
    }

    @EventHandler
    public void onVehicleMove(final VehicleMoveEvent event) {
        final Vehicle vehicle = event.getVehicle();
        if (vehicle.isEmpty()) {
            return;
        }
        final Location location = event.getTo();
        if (!isPortal(location)) {
            return;
        }
        Output.ServerLog("Detected a vehicle with a passenger in a portal");
        final Location target = calculateNewLocation(location);
        new VehicleTeleport(vehicle, target).run();
    }

    private boolean isPortal(final Location location) {
        final Block portal = location.getBlock();
        return portal.getType() == Material.NETHER_PORTAL;
    }

    private Location calculateNewLocation(final Location location) {
        final World world = location.getWorld() == Config.Overworld ? Config.Nether : Config.Overworld;
        final double x = location.getWorld() == Config.Overworld ? location.getX() / Config.Scale : location.getX() * Config.Scale;
        final double y = calculateNewYCoordinate(location);
        final double z = location.getWorld() == Config.Overworld ? location.getZ() / Config.Scale : location.getZ() * Config.Scale;
        return new Location(world, x, y, z);
    }

    private double calculateNewYCoordinate(final Location location) {
        if (Config.MatchPortalY == true) {
            return location.getY();
        }
        if (Config.PortalY > 0) {
            return Config.PortalY;
        }
        if (location.getWorld() == Config.Overworld) {
            return location.getY() / Config.Scale;
        }
        return location.getY() * Config.Scale;
    }
}
