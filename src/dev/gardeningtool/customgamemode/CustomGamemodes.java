package dev.gardeningtool.customgamemode;

import dev.gardeningtool.customgamemode.api.CustomGamemodesAPI;
import dev.gardeningtool.customgamemode.command.CustomGamemodeCommand;
import dev.gardeningtool.customgamemode.listener.PlayerListener;
import dev.gardeningtool.customgamemode.listener.UpdateListener;
import dev.gardeningtool.customgamemode.settings.SettingsManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CustomGamemodes extends JavaPlugin {

    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        settingsManager = new SettingsManager(getConfig());

        getCommand("customgamemode").setExecutor(new CustomGamemodeCommand(this));
        CustomGamemodesAPI.setup(this, settingsManager);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        if (settingsManager.isAlertUpdates()) {

            Bukkit.getPluginManager().registerEvents(new UpdateListener(), this);
        }
    }


}
