package dev.jonmarsh.minecartportal.event;

import dev.jonmarsh.minecartportal.Output;
import dev.jonmarsh.minecartportal.DismountRemount;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class VehicleInPortal implements Listener {

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Location location = event.getFrom();
        if (!isPortal(location)) {
            return;
        }
        Output.ServerLog("Detected a player in a portal through PlayerMoveEvent");
        final Player player = event.getPlayer();
        checkIsInVehicle(player);
        checkWasInVehicle(player);
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
        DismountRemount.dismount(vehicle);
    }

    private boolean isPortal(final Location location) {
        final Block portal = location.getBlock();
        return portal.getType() == Material.NETHER_PORTAL;
    }

    private void checkIsInVehicle(final Player player) {
        final Entity vehicle = player.getVehicle();
        if (vehicle == null) {
            return;
        }
        Output.ServerLog("That player /is/ in a vehicle");
        DismountRemount.dismount(vehicle);
    }

    private void checkWasInVehicle(final Player player) {
        if (DismountRemount.findVehicle(player) == null) {
            return;
        }
        Output.ServerLog("That player /was/ in a vehicle");
        DismountRemount.remount(player);
    }
}
