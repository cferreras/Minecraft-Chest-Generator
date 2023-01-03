# Minecraft-Chest-Generator
A chest generation plugin. Compatible with Spigot 1.19.3

## How to use

- Put the `.jar` in your '/plugins` forder.
- Start up the server and edit the `config.yml` inside ´/ChestGenerator´.

## Config

```# Configuration for chest contents
items:
  # Each set of loot has a unique key
  basic_loot:
    # The weight of the set (required)
    weight: 50
    # The items in the set
    items:
      iron_ingot:
        type: IRON_INGOT
        amount: 10
      bread:
        type: BREAD
        amount: 5
  rare_loot:
    weight: 10
    items:
      diamond:
        type: DIAMOND
        amount: 1
      enchanted_gold_pickaxe:
        type: GOLD_PICKAXE
        amount: 1
        enchantments:
          - EFFICIENCY
          - UNBREAKING
  legendary_loot:
    weight: 1
    items:
      nether_star:
        type: NETHER_STAR
      enchanted_diamond_chestplate:
        type: DIAMOND_CHESTPLATE
        enchantments:
          - PROTECTION
          - THORNS

# radious
radius: 100
# Configuration file for chest generator

# The time in seconds at which the chest should be generated
generation-time: 0
```

## Permissions and commands

`/generatechet` - `generatechest.command`  Gerates the chest at a random location in the radious

