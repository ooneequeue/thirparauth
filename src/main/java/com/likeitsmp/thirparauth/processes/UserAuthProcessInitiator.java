package com.likeitsmp.thirparauth.processes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.likeitsmp.thirparauth.userdata.ThirparauthUserDatabase;
import com.likeitsmp.util.console.Console;
import com.likeitsmp.util.event.AutoListener;

public final class UserAuthProcessInitiator
{
    private final Plugin _plugin;
    private final ThirparauthUserDatabase _userDatabase;

    public UserAuthProcessInitiator(Plugin plugin, ThirparauthUserDatabase userDatabase)
    {
        _plugin = plugin;
        _userDatabase = userDatabase;
        
        tryPromptAlreadyOnlinePlayers();
        tryPromptNewPlayersWhenTheyJoin();
    }

    private void tryPromptAlreadyOnlinePlayers()
    {
        for (var player : Bukkit.getOnlinePlayers())
        {
            tryPromptForPassword(player);
        }
    }

    private void tryPromptForPassword(Player player)
    {
        var user = _userDatabase.dataOf(player);
        if (user == null)
        {
            Console.logWarning(
                "LETTING "+player.getName()+" IN WITHOUT PROMPTING THEM FOR PASSWORD\n"+
                "BECAUSE THEY HAVE NO 3PA AND THEREFORE NO PASSWORD TO BE PROMPTED FOR"
            );
            player.addAttachment(_plugin, "thirparauth", true);
            return;
        }

        if (user.isEnabled() == false)
        {
            Console.logWarning(
                "LETTING "+player.getName()+" IN WITHOUT PROMPTING THEM FOR PASSWORD\n"+
                "BECAUSE THEIR 3PA IS DISABLED"
            );
            player.addAttachment(_plugin, "thirparauth", true);
            return;
        }

        var currentLoginLocation = player.getAddress().getAddress();
        if (user.doesTrust(currentLoginLocation))
        {
            Console.logWarning(
                "LETTING "+player.getName()+" IN WITHOUT PROMPTING THEM FOR PASSWORD\n"+
                "BECAUSE THEY LOGGED IN FROM A TRUSTED IP ADDRESS ("+currentLoginLocation+")"
            );
            player.addAttachment(_plugin, "thirparauth", true);
            return;
        }

        new UserAuthProcess(player, user, _plugin);
        Console.logWarning("PROMPTED "+player.getName()+" FOR PASSWORD");
    }

    private void tryPromptNewPlayersWhenTheyJoin()
    {
        new AutoListener(_plugin)
        {
            @EventHandler(priority = EventPriority.LOWEST)
            private void handle(PlayerJoinEvent event)
            {
                var joinedPlayer = event.getPlayer();
                tryPromptForPassword(joinedPlayer);
            }
        };
    }
}
