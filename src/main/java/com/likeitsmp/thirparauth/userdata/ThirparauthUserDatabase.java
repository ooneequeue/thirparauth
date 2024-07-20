package com.likeitsmp.thirparauth.userdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import com.likeitsmp.util.console.Console;
import com.likeitsmp.util.event.AutoListener;

public final class ThirparauthUserDatabase
{
    private final Map<UUID, ThirparauthUser> _userDataMap;
    private final Plugin _plugin;

    public ThirparauthUserDatabase(Plugin plugin)
    {
        _plugin = plugin;
        _userDataMap = loadUserDataMap();

        setupPeriodicAutosave();
        setupShutdownAutosave();
    }

    private Map<UUID, ThirparauthUser> loadUserDataMap()
    {
        try (
            var fileInput = new FileInputStream(databaseFilePath());
            var objectInput = new ObjectInputStream(fileInput);
        ) {
            @SuppressWarnings("unchecked")
            var loadedMap = (Map<UUID, ThirparauthUser>) objectInput.readObject();
            return loadedMap;
        }
        catch (Exception exception)
        {
            Console.logError(
                "FAILED TO LOAD THIRPARAUTH USER DATABASE"+
                "\nFROM : "+databaseFilePath()+
                "\nTHROWN : "+exception
            );
            return new HashMap<>();
        }
    }

    private File databaseFilePath()
    {
        return new File(_plugin.getDataFolder(), "user_database.sjo");
    }

    private void setupPeriodicAutosave()
    {
        final var TICKS_PER_SECOND = 20;
        final var FIVE_MINUTES = 5 * 20 * TICKS_PER_SECOND;
        var initialDelay = FIVE_MINUTES;
        var repetitionPeriod = FIVE_MINUTES;
        Runnable task = this::save;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(_plugin, task, initialDelay, repetitionPeriod);
    }

    private void setupShutdownAutosave()
    {
        new AutoListener(_plugin)
        {
            @EventHandler
            private void handle(PluginDisableEvent event)
            {
                if (event.getPlugin() == _plugin)
                {
                    save();
                }
            }
        };
    }

    private void save()
    {
        var databaseFilePath = databaseFilePath();
        try
        {
            var databaseParent = databaseFilePath.getParentFile();
            if (databaseParent.exists() == false)
            {
                databaseParent.mkdir();
            }
        }
        catch (Exception exception)
        {
            Console.logError(
                "FAILED TO SAVE THIRPARAUTH USER DATABASE"+
                "\nTO : "+databaseFilePath()+
                "\nTHROWN : "+exception
            );
        }

        try (
            var fileOutput = new FileOutputStream(databaseFilePath);
            var objectOutput = new ObjectOutputStream(fileOutput);
        ) {
            objectOutput.writeObject(_userDataMap);
        }
        catch (Exception exception)
        {
            Console.logError(
                "FAILED TO SAVE THIRPARAUTH USER DATABASE"+
                "\nTO : "+databaseFilePath()+
                "\nTHROWN : "+exception
            );
        }
    }

    public void register(OfflinePlayer user, String password)
    {
        var userData = dataOf(user);
        if (userData == null)
        {
            var userKey = user.getUniqueId();
            userData = new ThirparauthUser(password);
            _userDataMap.put(userKey, userData);
        }
        else throw new IllegalStateException("Must not register a player twice");
    }

    public ThirparauthUser dataOf(OfflinePlayer user)
    {
        var userKey = user.getUniqueId();
        return _userDataMap.get(userKey);
    }
}
