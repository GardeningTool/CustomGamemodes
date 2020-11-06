package dev.gardeningtool.customgamemode.api;

import dev.gardeningtool.customgamemode.CustomGamemodes;
import dev.gardeningtool.customgamemode.event.CustomGamemodeDisabledEvent;
import dev.gardeningtool.customgamemode.event.CustomGamemodeEnabledEvent;
import dev.gardeningtool.customgamemode.gamemode.CustomGamemode;
import dev.gardeningtool.customgamemode.settings.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomGamemodesAPI {

    private static SettingsManager settingsManager;
    private static CustomGamemodes customGamemodes;
    private static List<Player> canFly;

    public static void setup(CustomGamemodes customGamemodes, SettingsManager settingsManager) {
        CustomGamemodesAPI.customGamemodes = customGamemodes;
        CustomGamemodesAPI.settingsManager = settingsManager;
        canFly = new ArrayList<>();
    }

    public static boolean hasCustomGamemode(Player player) {
        return player.hasMetadata("customgamemode-key");
    }

    public static Collection<CustomGamemode> getCustomGamemodes() {
        return settingsManager.getCustomGamemodes().values();
    }

    public static boolean isCustomGamemode(String name) {
        return getCustomGamemode(name) != null;
    }

    public static CustomGamemode getCustomGamemode(String name) {
        return settingsManager.getCustomGamemodes().values().stream().filter(gamemode -> gamemode.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);
    }

    public static void setCustomGamemode(Player player, CustomGamemode customGamemode) {
        player.setMetadata("customgamemode-key", new FixedMetadataValue(customGamemodes, customGamemode.getKey()));
        if (player.getAllowFlight())
            canFly.add(player);
        if (customGamemode.isAllowFly()) {
            player.setAllowFlight(true);
        }
        player.setFlySpeed((float) customGamemode.getFlySpeed() / 10);
        player.setWalkSpeed((float) customGamemode.getWalkSpeed() / 10);
        player.setMaxHealth(customGamemode.getMaxHealth());
        player.setHealth(player.getMaxHealth());
        Bukkit.getPluginManager().callEvent(new CustomGamemodeEnabledEvent(player, customGamemode));

    }

    public static CustomGamemode getCustomGamemode(Player player) {
        return settingsManager.getCustomGamemodes().get(player.getMetadata("customgamemode-key").get(0).value());
    }

    public static void removeCustomGamemode(Player player) {
        CustomGamemode customGamemode = getCustomGamemode(player);

        player.removeMetadata("customgamemode-key", customGamemodes);

        player.setMaxHealth(20D);
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(1F);
        player.setAllowFlight(canFly.contains(player));

        Bukkit.getPluginManager().callEvent(new CustomGamemodeDisabledEvent(player, customGamemode));
    }


}
