package dev.jonmarsh.minecartportal;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.HashMap;

public final class DismountRemount {
    public static HashMap<Entity, VehicleInfo> passengerVehiclePair = new HashMap<>();

    public static void dismount(Entity vehicle) {
        if (vehicle.getPortalCooldown() != 0) {
            Output.ServerLog("Vehicle portal cooldown not reached");
            return;
        }

        VehicleInfo vehicleInfo = new VehicleInfo(vehicle);
        Output.ServerLog("Passenger list: " + vehicleInfo.passengers);

        for (Entity passenger: vehicleInfo.passengers) {
            Output.ServerLog("Removing passenger: " + passenger.getName());
            passenger.leaveVehicle();
            passengerVehiclePair.put(passenger, vehicleInfo);
        }
        Output.ServerLog("Velocity: " + vehicleInfo.vehicle.getVelocity());
        vehicleInfo.vehicle.setVelocity(vehicleInfo.velocity);
        Output.ServerLog("Velocity: " + vehicleInfo.vehicle.getVelocity());
    }

    public static void remount(Entity passenger) {
        final VehicleInfo vehicleInfo = passengerVehiclePair.get(passenger);
        if (vehicleInfo == null) {
            Output.ServerLog("Did not find a vehicle for: " + passenger);
            return;
        }
        vehicleInfo.vehicle.setPortalCooldown(20);
        Output.ServerLog("Remounting passenger: " + passenger.getName());
        passengerVehiclePair.remove(passenger);
        //passenger.teleport(vehicleInfo.vehicle.getLocation());
        vehicleInfo.vehicle.addPassenger(passenger);

    }
}
