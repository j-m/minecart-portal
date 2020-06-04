package dev.jonmarsh.minecartportal;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.signum;
import static org.bukkit.entity.EntityType.MINECART;

public class VehicleInfo {
    public Location from;
    public Vector velocity;
    public List<Entity> passengers;

    public VehicleInfo(Entity vehicle) {
        from = vehicle.getLocation();
        velocity = vehicle.getVelocity();
        passengers = vehicle.getPassengers();

        Output.ServerLog("Vehicle's velocity: " + velocity);
        Output.ServerLog("Vehicle's passengers: " + passengers);

        if (vehicle.getType() == MINECART) {
            final double x = abs(velocity.getX());
            final double z = abs(velocity.getZ());
            if (x < Config.MinimumSpeed && x >= z) {
                final double signX = signum(velocity.getX());
                Output.ServerLog("Minecart velocity insufficient and will likely derail. Increasing X to " + Config.MinimumSpeed * signX);
                velocity.setX(Config.MinimumSpeed * signX);
            }
            if (z < Config.MinimumSpeed && z > x) {
                final double signZ = signum(velocity.getZ());
                Output.ServerLog("Minecart velocity insufficient and will likely derail. Increasing Z to " + Config.MinimumSpeed * signZ);
                velocity.setZ(Config.MinimumSpeed * signZ);
            }
        }
    }
}
