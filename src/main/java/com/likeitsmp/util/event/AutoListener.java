package com.likeitsmp.util.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class AutoListener implements Listener
{
    protected AutoListener()
    {
    }

    public AutoListener(Plugin plugin)
    {
        registerSelfUsing(plugin);
    }

    protected void registerSelfUsing(Plugin plugin)
    {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
