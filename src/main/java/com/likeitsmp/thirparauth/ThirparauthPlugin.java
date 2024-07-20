package com.likeitsmp.thirparauth;

import org.bukkit.plugin.java.JavaPlugin;

import com.likeitsmp.thirparauth.processes.UserAuthProcessInitiator;
import com.likeitsmp.thirparauth.userdata.ThirparauthUserDatabase;

public final class ThirparauthPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        var userDatabase = new ThirparauthUserDatabase(this);
        new UserAuthProcessInitiator(this, userDatabase);
    }
}
