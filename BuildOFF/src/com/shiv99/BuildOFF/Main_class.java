package com.shiv99.BuildOFF;
import com.shiv99.BuildOFF.commands.promptGenCommand;
import com.shiv99.BuildOFF.events.MainEvents;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;



public class Main_class extends JavaPlugin {


    @Override
    public void onEnable(){

        getServer().getPluginManager().registerEvents(new MainEvents(), this);
        getCommand("prompt").setExecutor(new promptGenCommand());
        saveDefaultConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "BuildOFF plugin is enabled");
    }
    
    @Override
    public void onDisable(){

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "BuildOFF plugin is disabled");
    }



}
