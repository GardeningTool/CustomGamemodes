package dev.gardeningtool.customgamemode.command;

import dev.gardeningtool.customgamemode.CustomGamemodes;
import dev.gardeningtool.customgamemode.api.CustomGamemodesAPI;
import dev.gardeningtool.customgamemode.gamemode.CustomGamemode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomGamemodeCommand implements CommandExecutor {

    private CustomGamemodes customGamemodes;

    public CustomGamemodeCommand(CustomGamemodes customGamemodes) {
        this.customGamemodes = customGamemodes;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("customgamemodes.command")) {
            sender.sendMessage(color(customGamemodes.getSettingsManager().getNoPermission()));
            return false;
        }

        switch(args.length) {

            case 1: {
                CustomGamemode target = null;
                for (CustomGamemode customGamemode : customGamemodes.getSettingsManager().getCustomGamemodes().values()) {
                    if (args[0].equalsIgnoreCase(customGamemode.getName())) {
                        target = customGamemode;
                    }
                }

                if (target == null) {

                    sender.sendMessage(color(customGamemodes.getSettingsManager().getInvalidGamemodeMessage()));
                    return false;
                }

                if (!sender.hasPermission("customgamemodes.gamemode." + target.getName())) {
                    sender.sendMessage(color(customGamemodes.getSettingsManager().getNoGamemodePermissionMessage()));
                    return false;
                }

                if (!(sender instanceof Player)) {

                    sender.sendMessage(color(customGamemodes.getSettingsManager().getNotAPlayer()));
                    return false;
                }

                Player player = (Player) sender;

                if (CustomGamemodesAPI.hasCustomGamemode(player) && CustomGamemodesAPI.getCustomGamemode(player) == target) {
                    CustomGamemodesAPI.removeCustomGamemode(player);
                    player.sendMessage(color(customGamemodes.getSettingsManager().getGamemodeUpdateMessage().replace("%gamemode%", player.getGameMode().name())));
                    return false;
                }

                CustomGamemodesAPI.setCustomGamemode(player, target);
                player.sendMessage(color(customGamemodes.getSettingsManager().getGamemodeUpdateMessage().replace("%gamemode%", target.getName())));
                break;
            }
            case 2: {

                CustomGamemode target = null;
                for (CustomGamemode customGamemode : customGamemodes.getSettingsManager().getCustomGamemodes().values()) {
                    if (args[0].equalsIgnoreCase(customGamemode.getName())) {
                        target = customGamemode;
                    }
                }

                if (target == null) {

                    sender.sendMessage(color(customGamemodes.getSettingsManager().getInvalidGamemodeMessage()));
                    return false;
                }

                if (!sender.hasPermission("customgamemodes.gamemode." + target.getName())) {

                    sender.sendMessage(color(customGamemodes.getSettingsManager().getNoGamemodePermissionMessage()));
                    return false;
                }

                Player targetPlayer = Bukkit.getPlayer(args[1]);
                if (targetPlayer == null) {

                    sender.sendMessage(color(customGamemodes.getSettingsManager().getSyntaxMessage()));
                    return false;
                }

                if (CustomGamemodesAPI.hasCustomGamemode(targetPlayer) && CustomGamemodesAPI.getCustomGamemode(targetPlayer) == target) {
                    CustomGamemodesAPI.removeCustomGamemode(targetPlayer);
                    targetPlayer.sendMessage(color(customGamemodes.getSettingsManager().getGamemodeUpdateMessage().replace("%gamemode%", targetPlayer.getGameMode().name())));
                    sender.sendMessage(color(customGamemodes.getSettingsManager().getUpdatedPlayerMessage().replace("%player%", targetPlayer.getName()).replace("%gamemode%", targetPlayer.getGameMode().name())));
                    return false;

                }

                CustomGamemodesAPI.setCustomGamemode(targetPlayer, target);
                targetPlayer.sendMessage(color(customGamemodes.getSettingsManager().getGamemodeUpdateMessage().replace("%gamemode%", target.getName())));
                sender.sendMessage(color(customGamemodes.getSettingsManager().getUpdatedPlayerMessage().replace("%player%", targetPlayer.getName()).replace("%gamemode%", target.getName())));
                return false;
            }
            default: {
                sender.sendMessage(color(customGamemodes.getSettingsManager().getSyntaxMessage()));
                break;
            }
        }
        return false;
    }

    private String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
