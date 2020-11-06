package dev.gardeningtool.customgamemode.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class UpdateListener implements Listener {

    private final String VERSION = "1.0.0";
    private String latestVersion;
    private URL url;
    private long lastChecked;

    public UpdateListener() {
        try {
            url = new URL("https://gardeningtool.dev/plugins/version/CustomGamemode.txt");
        } catch (MalformedURLException exc) {
            exc.printStackTrace();
        }
        latestVersion = checkForUpdates();
    }

    private String checkForUpdates() {
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            return bufferedReader.readLine();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        finally {
            lastChecked = System.currentTimeMillis();
        }
        return null;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("customgamemodes.updates")) return;
        long time = System.currentTimeMillis();
        if (time - lastChecked > 1000 * 14400) {
            new Thread(() -> {
                latestVersion = checkForUpdates();
                if (!latestVersion.equalsIgnoreCase(VERSION)) {
                    Arrays.asList("", "§7(§c§l!§7) A new update of §fCustomGamemodes §7is available!", "").forEach(player::sendMessage);
                }
            }).start();
            return;
        }
        if (!latestVersion.equalsIgnoreCase(VERSION)) {

            Arrays.asList("", "§7(§c§l!§7) A new update of §fCustomGamemodes §7is available!", "").forEach(player::sendMessage);
        }
    }
}
