package org.infamousmc.sneakers;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("sneakers")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Error! This command must be run by a player!");
                return true;
            }
            Player player = (Player) sender;
            if (player.getInventory().firstEmpty() == -1) {
                // If inventory is full
                Location loc = player.getLocation();
                World world = player.getWorld();

                world.dropItemNaturally(loc, getItem());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4!&8] &7Your inventory was full so the item was dropped at your feet!"));
                return true;
            }
            player.getInventory().addItem(getItem());
            return true;
        }

        return false;
    }

    public ItemStack getItem() {

        Material type;
        ItemStack sneakers = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta m = sneakers.getItemMeta();
        LeatherArmorMeta meta = (LeatherArmorMeta) m;

        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sneakers");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shiny new kicks!");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Press shift and see what happens!");
        meta.setLore(lore);

        meta.setColor(Color.fromRGB(250, 250, 250));

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);

        sneakers.setItemMeta(meta);

        return sneakers;
    }

    //Check if a player is jumping
//    @EventHandler
//    public void onJump(PlayerMoveEvent event) {
//        Player player = (Player) event.getPlayer();
//        if (player.getInventory().getBoots() != null)
//            if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Sneakers"))
//                if (player.getInventory().getBoots().getItemMeta().hasLore())
//                    if (event.getFrom().getY() < event.getTo().getY() &&
//                    player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
//
//                    }
//    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = (Player) event.getPlayer();
        boolean isSneaking = player.isSneaking();
        if (player.getInventory().getBoots() != null)
            if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Sneakers"))
                if (player.getInventory().getBoots().getItemMeta().hasLore())
                    if (player.getInventory().getBoots().getItemMeta().hasEnchant(Enchantment.ARROW_INFINITE))
                        if (isSneaking) {
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                        } else {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0, false, false, false));
                        }
    }
}
