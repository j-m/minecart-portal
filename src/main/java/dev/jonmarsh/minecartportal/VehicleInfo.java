package dev.jonmarsh.minecartportal;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

public class VehicleInfo {
    public Location from;
    public Vector velocity;
    public List<Entity> passengers;

    public VehicleInfo(Entity vehicle) {
        from = vehicle.getLocation();
        velocity = vehicle.getVelocity();
        passengers = vehicle.getPassengers();
    }
}
