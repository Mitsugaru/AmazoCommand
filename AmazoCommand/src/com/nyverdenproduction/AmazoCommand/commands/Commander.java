package com.nyverdenproduction.AmazoCommand.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.nyverdenproduction.AmazoCommand.AmazoCommand;
import com.nyverdenproduction.AmazoCommand.permissions.PermissionHandler;
import com.nyverdenproduction.AmazoCommand.permissions.PermissionNode;

public class Commander implements CommandExecutor
{
	// Class variables
	private final AmazoCommand plugin;
	private final static String bar = "======================";

	public Commander(AmazoCommand plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		// See if any arguments were given
		if (args.length == 0)
		{
			// Check if they have "karma" permission
			this.displayHelp(sender);
		}
		else
		{
			final String com = args[0].toLowerCase();
			if (com.equals("version") || com.equals("ver"))
			{
				// Version and author
				return showVersion(sender, args);
			}
			else if (com.equals("?") || com.equals("help"))
			{
				return displayHelp(sender);
			}
			else if (com.equals("reload"))
			{
				if (!PermissionHandler.checkPermission(sender, PermissionNode.ADMIN_RELOAD))
				{
					sender.sendMessage(ChatColor.RED + AmazoCommand.TAG
							+ " Lack permission: "
							+ PermissionNode.ADMIN_RELOAD);
					return true;
				}
				plugin.getRootConfig().reload();
				sender.sendMessage(ChatColor.GREEN + AmazoCommand.TAG
						+ " Reloaded config");
				return true;
			}
			else
			{
				sender.sendMessage(ChatColor.RED + AmazoCommand.TAG
						+ " Unknown command '" + ChatColor.AQUA + com
						+ ChatColor.RED + "'");
			}
		}
		return false;
	}

	private boolean showVersion(CommandSender sender, String[] args)
	{
		sender.sendMessage(ChatColor.BLUE + bar + "=====");
		sender.sendMessage(ChatColor.GREEN + "AmazoCommand v"
				+ plugin.getDescription().getVersion());
		sender.sendMessage(ChatColor.GREEN + "Coded by Mitsugaru");
		sender.sendMessage(ChatColor.BLUE + "===========" + ChatColor.GRAY
				+ "Config" + ChatColor.BLUE + "===========");
		return true;
	}

	/**
	 * Show the help menu, with commands and description
	 * 
	 * @param sender
	 *            to display to
	 */
	private boolean displayHelp(CommandSender sender)
	{
		sender.sendMessage(ChatColor.GREEN + "==========" + ChatColor.WHITE
				+ "AmazoCommand" + ChatColor.GREEN + "==========");
		if (PermissionHandler.checkPermission(sender, PermissionNode.ADMIN_RELOAD))
		{
			sender.sendMessage(ChatColor.BLUE + "/acom reload" + ChatColor.WHITE
					+ " : Reloads configs");
		}
		sender.sendMessage(ChatColor.BLUE + "/acom help" + ChatColor.WHITE
				+ " : Show help menu");
		sender.sendMessage(ChatColor.BLUE + "/acom version" + ChatColor.WHITE
				+ " : Show version and about");
		return true;
	}

}
