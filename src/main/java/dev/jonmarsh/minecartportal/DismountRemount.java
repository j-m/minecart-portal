package dev.jonmarsh.minecartportal;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

import static java.lang.Math.signum;

public final class DismountRemount {
    public static HashMap<Entity, VehicleInfo> vehicleVehicleInfoPair = new HashMap<>();

    public static void vehicle(Entity vehicle) {
        if (vehicleVehicleInfoPair.get(vehicle) == null) {
            dismount(vehicle);
        } else {
            remount(vehicle);
        }
    }

    public static void dismount(Entity vehicle) {
        if (vehicleVehicleInfoPair.get(vehicle) != null) {
            Output.ServerLog("Vehicle already teleporting");
            return;
        }
        final VehicleInfo vehicleInfo = new VehicleInfo(vehicle);
        vehicleVehicleInfoPair.put(vehicle, vehicleInfo);

        Output.ServerLog("Vehicle's velocity before teleport: " + vehicleInfo.velocity.toString());
        Output.ServerLog("Passenger list: " + vehicleInfo.passengers);

        vehicle.setPortalCooldown(0);
        for (Entity passenger: vehicle.getPassengers()) {
            Output.ServerLog("Dismounting passenger: " + passenger.getName());
            passenger.setPortalCooldown(0);
            passenger.leaveVehicle();
        }
    }

    public static void remount(Entity vehicle) {
        final VehicleInfo vehicleInfo = vehicleVehicleInfoPair.get(vehicle);
        if (vehicleInfo == null) {
            Output.ServerLog("Vehicle not tracked");
            return;
        }
        if (vehicleInfo.from.getWorld() == vehicle.getLocation().getWorld()) {
            Output.ServerLog("Vehicle not yet teleported");
            return;
        }

        Output.ServerLog("Moving vehicle from: " + vehicle.getLocation().toString());
        vehicle.teleport(locationAddVelocity(vehicle.getLocation(), negateVelocity(velocityDirection(vehicleInfo.velocity))));
        Output.ServerLog("Moving vehicle to: " + vehicle.getLocation().toString());

        final Vector negatedVehicleVelocity = negateVelocity(vehicleInfo.velocity);
        Output.ServerLog("Setting vehicle velocity to: " + negatedVehicleVelocity);
        vehicle.setVelocity(negatedVehicleVelocity);

        for (Entity passenger: vehicleInfo.passengers) {
            Output.ServerLog("Remounting passenger: " + passenger.getName());
            Location to = vehicle.getLocation();
            to.setPitch(passenger.getLocation().getPitch());
            to.setYaw(passenger.getLocation().getYaw());
            passenger.teleport(to, PlayerTeleportEvent.TeleportCause.PLUGIN);
            vehicle.addPassenger(passenger);
            passenger.setPortalCooldown(10);
        }

        vehicleVehicleInfoPair.remove(vehicle);
    }

    private static Vector velocityDirection(final Vector velocity) {
        return new Vector(
                signum(velocity.getX()),
                0,
                signum(velocity.getZ())
        );
    }

    private static Vector negateVelocity(final Vector velocity) {
        return new Vector(
                velocity.getX() * -1,
                velocity.getY(),
                velocity.getZ() * -1
        );
    }

    private static Location locationAddVelocity(final Location location, final Vector velocity) {
        final double x = location.getX() + velocity.getX();
        final double y = location.getY() + velocity.getY();
        final double z = location.getZ() + velocity.getZ();
        return new Location(location.getWorld(), x, y, z);
    }
}
