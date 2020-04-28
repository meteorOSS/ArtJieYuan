package com.meteor.artjieyuan.Events;

import com.meteor.artjieyuan.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private Main plugin;
    public PlayerJoin(Main plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent join){
        if(!Main.playerdata.containsKey(join.getPlayer().getName())){
            plugin.getMySql().loadPlayerData(join.getPlayer().getName());
        }
    }
}
