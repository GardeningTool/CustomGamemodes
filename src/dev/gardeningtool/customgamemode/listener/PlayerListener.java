package dev.gardeningtool.customgamemode.listener;

import dev.gardeningtool.customgamemode.api.CustomGamemodesAPI;
import dev.gardeningtool.customgamemode.gamemode.CustomGamemode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (!CustomGamemodesAPI.hasCustomGamemode(player)) return;

        CustomGamemode customGamemode = CustomGamemodesAPI.getCustomGamemode(player);

        if (customGamemode.isFallDamage() && event.getCause() == EntityDamageEvent.DamageCause.FALL) {

            event.setCancelled(true);
            return;
        }

        if (customGamemode.isAllowAttackDamage() && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {

            event.setCancelled(true);
        }
    }
}
