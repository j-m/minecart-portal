package dev.jonmarsh.minecartportal;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

public class DismountRemount implements Runnable {
    public Entity vehicle;
    public Location destination;
    public Vector velocity;

    public DismountRemount(final Entity vehicle) {
        this.vehicle = vehicle;
        this.velocity = vehicle.getVelocity();
    }

    public void run() {
        List<Entity> passengers = vehicle.getPassengers();
        Output.ServerLog("Passenger list: " + passengers);

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
    }

}
