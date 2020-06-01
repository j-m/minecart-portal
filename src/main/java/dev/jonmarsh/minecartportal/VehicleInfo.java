package dev.jonmarsh.minecartportal;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

public final class VehicleInfo {
    public Entity vehicle;
    public Vector velocity;
    public List<Entity> passengers;

    public VehicleInfo(final Entity vehicle) {
        this.vehicle = vehicle;
        this.velocity = vehicle.getVelocity();
        this.passengers = vehicle.getPassengers();
    }
}
