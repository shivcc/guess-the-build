package com.shiv99.BuildOFF.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MainEvents implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent PlayerJoin){
        Player player = PlayerJoin.getPlayer();



    }
}
