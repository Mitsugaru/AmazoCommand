package com.nyverdenproduction.AmazoCommand.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

import com.nyverdenproduction.AmazoCommand.AmazoCommand;

public class RootConfig {
    private AmazoCommand plugin;
    public Map<String, HashSet<String>> disabled = new HashMap<String, HashSet<String>>();
    public boolean debugEvents;

    public RootConfig(AmazoCommand plugin) {
        this.plugin = plugin;
        // Grab config
        final ConfigurationSection config = plugin.getConfig();
        // Defaults
        final Map<String, Object> defaults = new LinkedHashMap<String, Object>();
        defaults.put("worlds", "");
        defaults.put("debug.events", false);
        defaults.put("version", plugin.getDescription().getVersion());
        // Insert defaults into config file if they're not present
        for (final Entry<String, Object> e : defaults.entrySet()) {
            if (!config.contains(e.getKey())) {
                config.set(e.getKey(), e.getValue());
            }
        }
        // Save config
        plugin.saveConfig();
        // load settings
        loadSettings(config);
        loadWorldPlugins(config);
    }

    public void reload() {
        // Initial relaod
        plugin.reloadConfig();
        // Clear old list
        disabled.clear();
        // Grab config
        final ConfigurationSection config = plugin.getConfig();
        // Load settings
        loadSettings(config);
        loadWorldPlugins(config);
    }

    private void loadSettings(ConfigurationSection config) {
        debugEvents = config.getBoolean("debug.events", false);
    }

    private void loadWorldPlugins(ConfigurationSection config) {
        final ConfigurationSection worldSection = config
                .getConfigurationSection("worlds");
        try {
            for (String world : worldSection.getKeys(false)) {
                final List<String> pluginsList = worldSection
                        .getStringList(world);
                if (pluginsList != null && !pluginsList.isEmpty()) {
                    final HashSet<String> plugins = new HashSet<String>();
                    for(String p : pluginsList)
                    {
                        plugins.add(p.toLowerCase());
                    }
                    disabled.put(world.toLowerCase(), plugins);
                }
            }
        } catch (NullPointerException npe) {
            plugin.getLogger().warning(AmazoCommand.TAG + " World list empty!");
        }
        for (Map.Entry<String, HashSet<String>> entry : disabled.entrySet()) {
            for (String plugins : entry.getValue()) {
                plugin.getLogger().info(entry.getKey() + " : " + plugins);
            }
        }
    }
}
