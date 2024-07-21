package com.likeitsmp.thirparauth.processes;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;
import com.likeitsmp.thirparauth.userdata.ThirparauthUser;
import com.likeitsmp.util.args.JoinedBookPages;
import com.likeitsmp.util.processes.text_input.PlayerTextInputProcess;

public final class UserAuthProcess extends PlayerTextInputProcess
{
    private final ThirparauthUser _user;

    public UserAuthProcess(Player player, ThirparauthUser user, Plugin plugin)
    {
        super(player);

        Preconditions.checkArgument(user != null, "user must not be null");
        Preconditions.checkArgument(plugin != null, "plugin must not be null");

        _user = user;
    }

    @Override
    protected void setupBookMeta(BookMeta inputField)
    {
        inputField.setDisplayName("Enter The Password");
        inputField.setLore(List.of(
            "to be let on the server",
            "you have to enter your password here",
            "and then hit \"done\""
        ));
    }

    @Override
    protected void onTextInput(BookMeta inputField)
    {
        var enteredPassword = JoinedBookPages.of(inputField);
        inputField.setPages("");

        if (_user.verifies(enteredPassword))
        {
            player.sendMessage("§2Welcome back!");
            endProcess();
        }
        else
        {
            player.sendMessage("§4Wrong.");
        }
    }
}
