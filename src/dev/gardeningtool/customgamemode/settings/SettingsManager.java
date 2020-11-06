package dev.gardeningtool.customgamemode.settings;

import dev.gardeningtool.customgamemode.gamemode.CustomGamemode;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

@Getter
public class SettingsManager {

    private HashMap<Byte, CustomGamemode> customGamemodes;
    private boolean alertUpdates;
    private String noPermission;
    private String syntaxMessage;
    private String noGamemodePermissionMessage;
    private String invalidGamemodeMessage;
    private String notAPlayer;
    private String gamemodeUpdateMessage;
    private String updatedPlayerMessage;

    public SettingsManager(FileConfiguration fileConfiguration) {
        customGamemodes = new HashMap<>();

        alertUpdates = fileConfiguration.getBoolean("alert-updates");
        
        updatedPlayerMessage = fileConfiguration.getString("Messages.updated-player-message");
        gamemodeUpdateMessage = fileConfiguration.getString("Messages.updated-gamemode-message");
        notAPlayer = fileConfiguration.getString("Messages.not-a-player");
        invalidGamemodeMessage = fileConfiguration.getString("Messages.invalid-gamemode-message");
        noPermission = fileConfiguration.getString("Messages.no-permission");
        syntaxMessage = fileConfiguration.getString("Messages.syntax-message");
        noGamemodePermissionMessage = fileConfiguration.getString("Messages.no-gamemode-permission-message");

        byte index = 0;
        for (String key : fileConfiguration.getConfigurationSection("Gamemodes").getKeys(false)) {
            String path = "Gamemodes." + key + ".";
            if (Arrays.asList("creative", "survival", "adventure", "spectator").contains(key)) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occured while loading in custom gamemodes: You can't create a gamemode using a default gamemode name.");
                continue;
            }
            int maxHealth = fileConfiguration.getInt(path + "max-health");
            int flySpeed = fileConfiguration.getInt(path + "fly-speed");
            int walkSpeed = fileConfiguration.getInt(path + "walk-speed");
            boolean fallDamage = fileConfiguration.getBoolean(path + "invulnerable-to-fall-damage");
            boolean allowFly = fileConfiguration.getBoolean(path + "allow-fly");
            boolean allowAttackDamage = fileConfiguration.getBoolean(path + "invulnerable-to-attack-damage");
            customGamemodes.put(index, new CustomGamemode(index, key, maxHealth, flySpeed, walkSpeed, allowFly, fallDamage, allowAttackDamage));
            index++;
        }
    }
}
