package dev.jonmarsh.minecartportal.event;

import dev.jonmarsh.minecartportal.DismountRemount;
import dev.jonmarsh.minecartportal.Output;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class VehicleInPortal implements Listener {

    @EventHandler
    public void onVehicleMove(final VehicleMoveEvent event) {
        final Location location = event.getTo();
        final Block portal = location.getBlock();
        if (portal.getType() != Material.NETHER_PORTAL) {
            return;
        }
        final Vehicle vehicle = event.getVehicle();
        if (vehicle.isEmpty()) {
            Output.ServerLog("Detected a vehicle with NO passenger(s) in a portal via VehicleMoveEvent");
            DismountRemount.remount(vehicle);
        } else {
            Output.ServerLog("Detected a vehicle with passenger(s) in a portal via VehicleMoveEvent");
            DismountRemount.dismount(vehicle);
        }
    }
}
