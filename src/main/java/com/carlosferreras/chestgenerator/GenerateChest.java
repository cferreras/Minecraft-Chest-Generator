package com.carlosferreras.chestgenerator;

import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateChest {

    public void generateChest() {

        // Load the chest contents from the config file
        FileConfiguration config = Bukkit.getServer().getPluginManager().getPlugin("ChestGenerator").getConfig();

        ConfigurationSection lootSets = config.getConfigurationSection("items");

        // Get the name of the world from the configuration file
        String worldName = config.getString("chest.world");

        // Get the World object for the specified world
        World world = Bukkit.getWorld(worldName);
        // Find a random location above the ground
        int radius = config.getInt("radius");
        Random rand = new Random();
        int x = rand.nextInt(radius * 2) - radius;
        int z = rand.nextInt(radius * 2) - radius;
        int y = world.getHighestBlockYAt(x, z) + 1;
        // Send a message to all players with the coordinates of the chest
        Bukkit.getServer().broadcastMessage(
                ChatColor.GREEN + "A chest has been generated at " + ChatColor.BLUE + x + ChatColor.GREEN + ", " + ChatColor.BLUE + y + ChatColor.GREEN + ", " + ChatColor.BLUE + z);

        Location chestLocation = new Location(world, x, y, z);

        // Place the chest at the location
        world.getBlockAt(chestLocation).setType(Material.CHEST);


        // Create a new chest block at the location
        Chest chest = (Chest) chestLocation.getBlock().getState();

        // Get the chest's inventory
        Inventory chestInventory = chest.getInventory();

        // Calculate the total weight of all loot sets
        int totalWeight = 0;
        for (String key : lootSets.getKeys(false)) {
            totalWeight += lootSets.getInt(key + ".weight");
        }

        // Select a random loot set
        Random random = new Random();
        int randomWeight = random.nextInt(totalWeight);

        for (String key : lootSets.getKeys(false)) {
            ConfigurationSection lootSet = lootSets.getConfigurationSection(key);
            randomWeight -= lootSet.getInt("weight");
            if (randomWeight <= 0) {
                // Add the items from the selected loot set to the chest
                for (String itemKey : lootSet.getConfigurationSection("items").getKeys(false)) {
                    Material material = Material.valueOf(lootSet.getString("items." + itemKey + ".type"));
                    int amount = lootSet.getInt("items." + itemKey + ".amount", 1);
                    short durability = (short) lootSet.getInt("items." + itemKey + ".durability", 0);
                    ItemStack item = new ItemStack(material, amount, durability);
                    chestInventory.addItem(item);
                }
                break;
            }
        }
    }
}

