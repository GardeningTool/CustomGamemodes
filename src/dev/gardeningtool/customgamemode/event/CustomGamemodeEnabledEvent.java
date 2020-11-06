package dev.gardeningtool.customgamemode.event;

import dev.gardeningtool.customgamemode.gamemode.CustomGamemode;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class CustomGamemodeEnabledEvent extends Event {

    private static HandlerList handlers = new HandlerList();
    private Player player;
    private CustomGamemode customGamemode;

    public CustomGamemodeEnabledEvent(Player player, CustomGamemode customGamemode) {
        this.player = player;
        this.customGamemode = customGamemode;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

