package com.josaca.basicrtp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Basicrtp extends JavaPlugin {
    private HashMap<UUID, Long> cooldowns = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Random TP Plugin habilitado");
    }

    @Override
    public void onDisable() {
        getLogger().info("Random TP Plugin deshabilitado");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("rtp") && sender instanceof Player) {
            Player player = (Player) sender;
            FileConfiguration config = getConfig();
            String mensaje = config.getString("teleport_message", "You have been teleported!");
            int cooldownSegundos = config.getInt("cooldown_seconds", 10);

            UUID playerId = player.getUniqueId();
            long tiempoActual = System.currentTimeMillis();
            long tiempoUltimoUso = cooldowns.getOrDefault(playerId, 0L);

            if (tiempoActual - tiempoUltimoUso < cooldownSegundos * 1000) {
                player.sendMessage("⏳ You must wait before using /rtp again.");
                return true;
            }

            int initialX = config.getInt("initial_x", 0);
            int initialZ = config.getInt("initial_z", 0);
            int maxRange = config.getInt("max_range", 2000);

            Random rand = new Random();
            int x = initialX + rand.nextInt((maxRange * 2) + 1) - maxRange;
            int z = initialZ + rand.nextInt((maxRange * 2) + 1) - maxRange;
            int y = player.getWorld().getHighestBlockYAt(x, z);

            Location randomLocation = new Location(player.getWorld(), x, y+2, z);
            player.teleport(randomLocation);
            player.sendMessage(config.getString("teleport_message", "You have been teleported!"));

            cooldowns.put(playerId, tiempoActual);
            return true;
        }
        
        if (label.equalsIgnoreCase("setrtp") && sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("basicrtp.setrtp")) {
                player.sendMessage("❌ You don't have permission to use this command!");
                return true;
            }

            // Get player's current location
            Location loc = player.getLocation();
            int initialX = loc.getBlockX();
            int initialZ = loc.getBlockZ();

            // Save the coordinates to config.yml
            FileConfiguration config = getConfig();
            config.set("initial_x", initialX);
            config.set("initial_z", initialZ);
            saveConfig();

            player.sendMessage("✅ RTP initial coordinates set to your current location (X: " + initialX + ", Z: " + initialZ + ")");
            return true;
        }
        return false;
    }
}