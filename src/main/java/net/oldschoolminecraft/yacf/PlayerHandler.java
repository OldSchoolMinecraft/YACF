package net.oldschoolminecraft.yacf;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerHandler implements Listener
{
    private YACF plugin;

    public PlayerHandler(YACF plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event)
    {
        KeywordManager keywordManager = plugin.getKeywordManager();
        event.setMessage(keywordManager.process(event.getMessage()));
    }
}
