package dev.jonmarsh.minecartportal;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

public class VehicleTeleport implements Runnable {
    public Entity vehicle;
    public Location destination;
    public Vector velocity;

    public VehicleTeleport(final Entity vehicle, final Location destination) {
        this.vehicle = vehicle;
        this.destination = destination;
        this.velocity = vehicle.getVelocity();
    }

    public void run() {
        Output.ServerLog("Attempting to teleport entity to '" + destination.getWorld().getName() + "' " + destination.toVector().toString());

        final int cooldown = vehicle.getPortalCooldown();
        vehicle.setPortalCooldown(20);

        List<Entity> passengers = vehicle.getPassengers();
        Output.ServerLog("Passenger list: " + passengers);

//        Output.ServerLog("Ejecting passengers");
//        vehicle.eject();

        for (Entity passenger: vehicle.getPassengers()) {
            Output.ServerLog("Removing passenger: " + passenger.getName());
            passenger.leaveVehicle();
        }

        // wait for vehicle and passenger teleport

        /*for (Entity passenger: passengers) {
            Output.ServerLog("Adding passenger: " + passenger.getName());
            vehicle.addPassenger(passenger);
        }*/

        vehicle.setVelocity(velocity);
        vehicle.setPortalCooldown(cooldown);
    }

}
