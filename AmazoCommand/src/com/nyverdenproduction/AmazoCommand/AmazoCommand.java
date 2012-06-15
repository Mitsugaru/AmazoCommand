package com.nyverdenproduction.AmazoCommand;

import org.bukkit.plugin.java.JavaPlugin;

import com.nyverdenproduction.AmazoCommand.commands.Commander;
import com.nyverdenproduction.AmazoCommand.config.RootConfig;
import com.nyverdenproduction.AmazoCommand.listeners.CommandListener;
import com.nyverdenproduction.AmazoCommand.permissions.PermissionHandler;

public class AmazoCommand extends JavaPlugin {

    public static final String TAG = "[AmazoCommand]";
    public RootConfig config;

    @Override
    public void onEnable() {
        config = new RootConfig(this);
        PermissionHandler.init(this);
        this.getServer().getPluginManager()
                .registerEvents(new CommandListener(this), this);
        this.getCommand("acom").setExecutor(new Commander(this));
    }

    public RootConfig getRootConfig() {
        return config;
    }
}
