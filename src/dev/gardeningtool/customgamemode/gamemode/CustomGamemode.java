package dev.gardeningtool.customgamemode.gamemode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomGamemode {

    private byte key;
    private String name;
    private int maxHealth;
    private int flySpeed;
    private int walkSpeed;
    private boolean allowFly;
    private boolean fallDamage;
    private boolean allowAttackDamage;

}
