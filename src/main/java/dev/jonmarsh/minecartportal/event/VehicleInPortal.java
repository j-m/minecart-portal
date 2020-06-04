package dev.jonmarsh.minecartportal.event;

import dev.jonmarsh.minecartportal.DismountRemount;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
        DismountRemount.inPortal(event.getVehicle());
    }
}
