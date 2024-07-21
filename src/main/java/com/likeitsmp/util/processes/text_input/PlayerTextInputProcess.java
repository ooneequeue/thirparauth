package com.likeitsmp.util.processes.text_input;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

public abstract class PlayerTextInputProcess
{
    protected final Player player;

    public PlayerTextInputProcess(Player player)
    {
        this.player = player;
        throw new UnsupportedOperationException("Unimplemented method 'new PlayerTextInputProcess'");
    }

    protected void setupBookMeta(BookMeta inputField)
    {
        throw new UnsupportedOperationException("Unimplemented method 'setupBookMeta'");
    }

    protected abstract void onTextInput(BookMeta inputField);

    public final void endProcess()
    {
        throw new UnsupportedOperationException("Unimplemented method 'endProcess'");
    }
}
