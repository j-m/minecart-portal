package dev.jonmarsh.minecartportal.event;

import dev.jonmarsh.minecartportal.DismountRemount;
import dev.jonmarsh.minecartportal.Output;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;

public class VehicleOutPortal implements Listener {
    @EventHandler
    public void onEntityOutOfPortal(final EntityPortalExitEvent event) {
        Output.ServerLog("Detected an entity exit a portal through EntityPortalExitEvent");
        DismountRemount.remount(event.getEntity());
    }

    @EventHandler
    public void onEntityOutOfPortal(final EntityPortalEvent event) {
        Output.ServerLog("Detected an entity exit a portal through EntityPortalEvent");
        DismountRemount.remount(event.getEntity());
    }
}