package com.carlosferreras.chestgenerator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ChestGenerator extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Plugin startup logic
        this.getCommand("generatechest").setExecutor(this::onCommand);
        this.getCommand("generatechest").setPermission("generatechest.command");
        this.getCommand("generatechest").setPermissionMessage("You don't have permission to use this command.");

        // Load the generation time from the config file
        FileConfiguration config = this.getConfig();
        int generationTime = config.getInt("generation-time");
        if (generationTime == 0) {
            // Skip the scheduling step if the generation time is 0
            return;
        }
        // Schedule the generation of the chest at the specified time
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                // Generate the chest here
                GenerateChest generator = new GenerateChest();
                generator.generateChest();
            }
        }, generationTime * 20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("generatechest")) {
            GenerateChest generator = new GenerateChest();
            generator.generateChest();

            return true;
        }
        return false;
    }
}
