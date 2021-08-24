package com.bluefighter2007.toastcontrol;

import org.bukkit.plugin.java.JavaPlugin;

public final class controlMain extends JavaPlugin {


    private static controlMain plugin;

    @Override
    public void onEnable() {
        getCommand("테스트").setExecutor(new Command());

        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static controlMain getInstance() {
        return plugin;
    }
}
