package org.bebr0.bebr0zavr;

import org.bebr0.bebr0zavr.events.JumpEvent;
import org.bebr0.bebr0zavr.events.SneakEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new Commands(this);

        Bukkit.getPluginManager().registerEvents(new JumpEvent(), this);
        Bukkit.getPluginManager().registerEvents(new SneakEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
