package com.likeitsmp.util.console;

import org.bukkit.Bukkit;

import com.google.common.base.Preconditions;

public final class Console
{
    public static void log(Object message)
    {
        var logger = Bukkit.getLogger();
        var messageLines = (" "+message).split("\n");
        for (var messageLine : messageLines)
        {
            logger.info(messageLine);
        }
    }

    public static void logWarning(Object message)
    {
        var logger = Bukkit.getLogger();
        var messageLines = (" "+message).split("\n");
        for (var messageLine : messageLines)
        {
            logger.warning(messageLine);
        }
    }

    public static void logError(Object message)
    {
        var logger = Bukkit.getLogger();
        var messageLines = (" "+message).split("\n");
        for (var messageLine : messageLines)
        {
            logger.severe(messageLine);
        }
    }

    public static void debugRunAll(Runnable... tasks)
    {
        for (var i = 0; i < tasks.length; i++)
        {
            var task = tasks[i];
            debugRun(task);
        }
    }

    public static void debugRun(Runnable task)
    {
        Preconditions.checkArgument(task != null, "task must not be null");

        try
        {
            task.run();
        }
        catch (Exception exception)
        {
            logError(exception);
        }
    }

    private Console()
    {
    }
}
