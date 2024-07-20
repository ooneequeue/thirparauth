package com.likeitsmp.thirparauth;

import org.bukkit.plugin.java.JavaPlugin;

import com.likeitsmp.thirparauth.processes.UserAuthProcessInitiator;

public final class ThirparauthPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        new UserAuthProcessInitiator();
    }
}
