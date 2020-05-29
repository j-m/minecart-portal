package dev.jonmarsh.minecartportal.event;

import dev.jonmarsh.minecartportal.Config;
import dev.jonmarsh.minecartportal.Output;
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
        teleport(vehicle, target);
    }

    @EventHandler
    public void onVehicleMove(final VehicleMoveEvent event) {
        final Location location = event.getTo();
        if (!isPortal(location)) {
            return;
        }
        Output.ServerLog("Detected a minecart in a portal");
        final Vehicle vehicle = event.getVehicle();
        final Vector priorVelocity = vehicle.getVelocity();
        final Location target = calculateNewLocation(location);
        teleport(vehicle, target);
    }

    private boolean isPortal(final Location location) {
        final Block portal = location.getBlock();
        return portal.getType() == Material.NETHER_PORTAL;
    }

    private Location calculateNewLocation(final Location location) {
        final World world = location.getWorld() == Config.Overworld ? Config.Nether : Config.Overworld;
        final double x = location.getX() / Config.Scale;
        final double y = Config.PortalY;
        final double z = location.getZ() / Config.Scale;
        return new Location(world, x, y, z);
    }

    public void teleport(final Entity entity, final Location location) {
        Output.ServerLog("Teleporting entity to " + location.getWorld().getName() + " " + location.toVector().toString());
        entity.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

}
