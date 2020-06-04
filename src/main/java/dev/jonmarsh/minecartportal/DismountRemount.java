package dev.jonmarsh.minecartportal;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

import static java.lang.StrictMath.signum;

public final class DismountRemount {
    private final static HashMap<Entity, VehicleInfo> vehicleVehicleInfoPair = new HashMap<>();

    public static void inPortal(final Entity vehicle) {
        if (vehicle.isEmpty()) {
            final VehicleInfo vehicleInfo = vehicleVehicleInfoPair.get(vehicle);
            if (vehicleInfo == null) {
                Output.ServerLog("Detected a vehicle with NO passenger(s) in a portal via VehicleMoveEvent");
                DismountRemount.dismount(vehicle);
            } else {
                Output.ServerLog("Detected a vehicle that HAD passenger(s) in a portal via VehicleMoveEvent");
                DismountRemount.remount(vehicle);
            }
        } else {
            Output.ServerLog("Detected a vehicle with passenger(s) in a portal via VehicleMoveEvent");
            DismountRemount.dismount(vehicle);
        }
    }

    private static void dismount(Entity vehicle) {
        if (vehicleVehicleInfoPair.get(vehicle) != null) {
            Output.ServerLog("Vehicle already teleporting");
            return;
        }

        final VehicleInfo vehicleInfo = new VehicleInfo(vehicle);
        vehicleVehicleInfoPair.put(vehicle, vehicleInfo);

        vehicle.setPortalCooldown(0);
        for (Entity passenger: vehicle.getPassengers()) {
            Output.ServerLog("Dismounting passenger: " + passenger.getName());
            passenger.setPortalCooldown(0);
            passenger.leaveVehicle();
        }
    }

    private static void remount(Entity vehicle) {
        final VehicleInfo vehicleInfo = vehicleVehicleInfoPair.get(vehicle);
        if (vehicleInfo.from.getWorld() == vehicle.getLocation().getWorld()) {
            Output.ServerLog("Vehicle not yet teleported. Original: " + vehicleInfo.from.getWorld().getName() + ". Current: " + vehicle.getLocation().getWorld().getName());
            Output.ServerLog("Setting vehicle velocity to: " + vehicleInfo.velocity);
            vehicle.setVelocity(vehicleInfo.velocity);
            return;
        }
        Output.ServerLog("Vehicle has now teleported");
        Output.ServerLog("Setting vehicle velocity to: " + vehicleInfo.velocity);
        vehicle.setVelocity(vehicleInfo.velocity);
        adjustVehiclePosition(vehicle);

        for (Entity passenger: vehicleInfo.passengers) {
            Output.ServerLog("Remounting passenger: " + passenger.getName());
            Location to = vehicle.getLocation();
            to.setPitch(passenger.getLocation().getPitch());
            to.setYaw(passenger.getLocation().getYaw() + 180);
            passenger.teleport(to, PlayerTeleportEvent.TeleportCause.PLUGIN);
            vehicle.addPassenger(passenger);
        }

        vehicleVehicleInfoPair.remove(vehicle);
    }

    private static void adjustVehiclePosition(final Entity vehicle) {
        final VehicleInfo vehicleInfo = vehicleVehicleInfoPair.get(vehicle);
        final double signX = signum(vehicleInfo.velocity.getX());
        final double signZ = signum(vehicleInfo.velocity.getZ());
        final Vector adjustment = new Vector(signX, 0, signZ);
        Output.ServerLog("Adjusting vehicle position by: " + adjustment);
        vehicle.teleport(locationAddVelocity(vehicle.getLocation(), adjustment));
    }

    private static Location locationAddVelocity(final Location location, final Vector velocity) {
        final double x = location.getX() + velocity.getX();
        final double y = location.getY() + velocity.getY();
        final double z = location.getZ() + velocity.getZ();
        return new Location(location.getWorld(), x, y, z, location.getYaw(), location.getPitch());
    }
}
