package com.nyverdenproduction.AmazoCommand.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.nyverdenproduction.AmazoCommand.AmazoCommand;
import com.nyverdenproduction.AmazoCommand.permissions.PermissionHandler;
import com.nyverdenproduction.AmazoCommand.permissions.PermissionNode;

public class CommandListener implements Listener {

    private AmazoCommand plugin;

    public CommandListener(AmazoCommand plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            if (plugin.getRootConfig().debugEvents) {
                plugin.getLogger().info("cancelled");
            }
            return;
        }
        if (event.getPlayer() == null) {
            if (plugin.getRootConfig().debugEvents) {
                plugin.getLogger().info("player null");
            }
            return;
        }
        final Player player = event.getPlayer();
        if (PermissionHandler.checkPermission(player, PermissionNode.EXEMPT)) {
            if (plugin.getRootConfig().debugEvents) {
                plugin.getLogger().info("exempt");
            }
            return;
        }
        if (player.getWorld() == null) {
            if (plugin.getRootConfig().debugEvents) {
                plugin.getLogger().info("world null");
            }
            return;
        }
        final String world = player.getWorld().getName().toLowerCase();
        if (plugin.getRootConfig().debugEvents) {
            plugin.getLogger().info("World: " + world);
        }
        if (!plugin.getRootConfig().disabled.containsKey(world)) {
            if (plugin.getRootConfig().debugEvents) {
                plugin.getLogger().info("Non-applicable world");
            }
            return;
        }
        String com = "";
        try {
            com = event.getMessage().split(" ")[0];
            com = com.replace("/", "");
            if (plugin.getRootConfig().debugEvents) {
                plugin.getLogger().info(com);
            }
        } catch (ArrayIndexOutOfBoundsException aioob) {
            if (plugin.getRootConfig().debugEvents) {
                aioob.printStackTrace();
            }
            return;
        }
        try {
            final PluginCommand pluginCom = Bukkit.getPluginCommand(com);
            final String pluginName = pluginCom.getPlugin().getName();
            if (plugin.getRootConfig().debugEvents) {
                plugin.getLogger().info("Plugin: " + pluginName);
            }
            if (plugin.getRootConfig().disabled.get(world).contains(
                    pluginName.toLowerCase())) {
                player.sendMessage(ChatColor.RED + AmazoCommand.TAG
                        + " Plugin commands for " + ChatColor.GOLD + pluginName
                        + ChatColor.RED + " are disabled in " + ChatColor.AQUA
                        + world);
                event.setCancelled(true);
            }
        } catch (NullPointerException npe) {
            if (plugin.getRootConfig().debugEvents) {
                npe.printStackTrace();
            }
        }
    }
}
