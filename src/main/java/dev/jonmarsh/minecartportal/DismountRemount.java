package dev.jonmarsh.minecartportal;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.HashMap;

public final class DismountRemount {
    public static HashMap<Entity, Entity> passengerVehiclePair = new HashMap<>();
    public static HashMap<Entity, Vector> vehicleVelocityPair = new HashMap<>();

    public static void dismount(Entity vehicle) {
        if (vehicle.getPortalCooldown() != 0) {
            Output.ServerLog("Vehicle portal cooldown not reached");
            return;
        }

        Output.ServerLog("Prior dismounts, vehicle has velocity: " + vehicle.getVelocity().toString());
        vehicleVelocityPair.put(vehicle, vehicle.getVelocity());

        Output.ServerLog("Passenger list: " + vehicle.getPassengers());

        for (Entity passenger: vehicle.getPassengers()) {
            Output.ServerLog("Removing passenger: " + passenger.getName());
            passengerVehiclePair.put(passenger, vehicle);
            passenger.setPortalCooldown(0);
            passenger.leaveVehicle();
        }
    }

    public static void remount(Entity passenger) {
        Output.ServerLog("Attempt to remount passenger: " + passenger.getName());
        final Entity vehicle = passengerVehiclePair.get(passenger);
        if (vehicle == null) {
            Output.ServerLog("Did not find a vehicle for: " + passenger);
            return;
        }

        moveVehicleOutOfPortal(vehicle);

        Output.ServerLog("Remounting passenger: " + passenger.getName());
        vehicle.addPassenger(passenger);
        passengerVehiclePair.remove(passenger);
        passenger.setPortalCooldown(0);
    }

    private static void moveVehicleOutOfPortal(final Entity vehicle) {
        final Vector vehicleVelocity = vehicleVelocityPair.get(vehicle);
        if(vehicleVelocity == null) {
            Output.ServerLog("Did not find a vehicle velocity for: " + vehicle);
            return;
        }

        final Vector negatedVehicleVelocity = negateVelocity(vehicleVelocity);

        Output.ServerLog("Moving vehicle from: " + vehicle.getLocation().toString());
        vehicle.teleport(locationAddVelocity(vehicle.getLocation(), negatedVehicleVelocity));
        Output.ServerLog("Moving vehicle to: " + vehicle.getLocation().toString());

        Output.ServerLog("Setting vehicle velocity to: " + negatedVehicleVelocity);
        vehicle.setVelocity(negatedVehicleVelocity);
        vehicleVelocityPair.remove(vehicle);
        vehicle.setPortalCooldown(0);
    }

    private static Vector negateVelocity(final Vector velocity) {
        return new Vector(velocity.getX() * -1, velocity.getY(), velocity.getZ() * -1);
    }

    private static Location locationAddVelocity(final Location location, final Vector velocity) {
        final double x = location.getX() + velocity.getX();
        final double y = location.getY() + velocity.getY();
        final double z = location.getZ() + velocity.getZ();
        return new Location(location.getWorld(), x, y, z);
    }
}
