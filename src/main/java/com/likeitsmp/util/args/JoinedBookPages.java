package com.likeitsmp.util.args;

import org.bukkit.inventory.meta.BookMeta;

public final class JoinedBookPages
{
    private static final String PAGE_DELIMITER = "\n\n\n\f\f\f\n\n\n";

    public static String of(BookMeta book)
    {
        var pages = book.getPages();
        return String.join(PAGE_DELIMITER, pages);
    }

    private JoinedBookPages()
    {
    }
}
