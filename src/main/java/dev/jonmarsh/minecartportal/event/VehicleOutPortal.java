package dev.jonmarsh.minecartportal.event;

import dev.jonmarsh.minecartportal.DismountRemount;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class VehicleOutPortal implements Listener {

    @EventHandler
    public void onPlayerOutOfPortal(final PlayerPortalEvent event) {
        DismountRemount.remount(event.getPlayer());
    }

    @EventHandler
    public void onEntityOutOfPortal(final EntityPortalExitEvent event) {
        DismountRemount.remount(event.getEntity());
    }
}