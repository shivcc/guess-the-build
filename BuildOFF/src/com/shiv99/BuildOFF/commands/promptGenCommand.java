package com.shiv99.BuildOFF.commands;
import com.shiv99.BuildOFF.Main_class;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class promptGenCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main_class.getPlugin(Main_class.class).getConfig();
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        // /prompt
        if (args.length == 0) {
            if (!player.hasPermission("BuildOFF.admin") || !player.hasPermission("BuildOFF.user")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            player.sendMessage(ChatColor.WHITE + "You may use \"/prompt get\" to get a prompt \nYou may use \"/prompt add <msg>\" to add prompts \nYou may use \"/prompt list\" to list the prompts \nYou may use \"/prompt rem <msg>\" to remove a prompt");
            return true;
        }

        // /prompt get
        if (Objects.equals(args[0], "get")) {

            List<String> PromtList = (List<String>) config.getList("ListOfPrompts");

            String GenPrompt = PromtList.get(getRandomNumber(0, PromtList.size()));
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
            BookMeta meta = (BookMeta) book.getItemMeta();
            meta.setTitle("Original Promt");
            meta.setAuthor("Guess The Build Game");
            meta.addPage((ChatColor.translateAlternateColorCodes('&', GenPrompt.toString())));
            book.setItemMeta(meta);
            player.getInventory().addItem(book);
            player.sendMessage(ChatColor.GREEN + "Generated, check inventory!");
            return true;

        }
        // /prompt add <msg>
        if (Objects.equals(args[0], "add") && args.length >= 2) {
            if (!player.hasPermission("BuildOFF.admin")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            List<String> promptList = config.getStringList("ListOfPrompts");

            StringBuilder sentence = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sentence.append(" ").append(args[i]);
            }
            promptList.add(sentence.toString().trim());
            config.set("ListOfPrompts", promptList);
            Main_class.getPlugin(Main_class.class).saveConfig();

            player.sendMessage(ChatColor.GREEN + "Added: " + sentence.toString().trim());
            return true;
        }

        // /prompt rem <msg>
        if (Objects.equals(args[0], "rem") && args.length >= 2) {
            if (!player.hasPermission("BuildOFF.admin")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            List<String> promptList = config.getStringList("ListOfPrompts");

            StringBuilder sentence = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sentence.append(" ").append(args[i]);
            }
            if (promptList.remove(sentence.toString().trim())) {
                config.set("ListOfPrompts", promptList);
                Main_class.getPlugin(Main_class.class).saveConfig();
                player.sendMessage(ChatColor.GREEN + "Removed: " + sentence.toString().trim());
            } else {
                player.sendMessage(ChatColor.RED + "Prompt not found.");
            }
            return true;
        }
        // /prompt list
        if (Objects.equals(args[0], "list")) {
            if (!player.hasPermission("BuildOFF.admin")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            List<String> promptList = config.getStringList("ListOfPrompts");
            if (promptList.isEmpty()) {
                player.sendMessage(ChatColor.RED + "There are no prompts in the list.");
            } else {
                player.sendMessage(ChatColor.GREEN + "List of Prompts:");
                for (String prompt : promptList) {
                    player.sendMessage(ChatColor.YELLOW + "- " + prompt);
                }
            }
            return true;
        }

        // /prompt start <minutes>
        if (Objects.equals(args[0], "start") && args.length == 2) {
            if (!player.hasPermission("BuildOFF.admin")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            try {
                int minutes = Integer.parseInt(args[1]);
                startCountdown(minutes);
                sender.sendMessage(ChatColor.GREEN + "Timer started for " + minutes + " minutes.");
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid number format.");
            }
            return true;
        }

        return true;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void startCountdown(int minutes) {
        new BukkitRunnable() {
            int secondsLeft = minutes * 60;

            public void run() {
                if (secondsLeft <= 0) {
                    Bukkit.broadcastMessage(ChatColor.RED + "Time up!");
                    cancel();
                    return;
                }

                if (secondsLeft % 60 == 0) {
                    // Broadcast message every minute
                    int minutesLeft = secondsLeft / 60;
                    Bukkit.broadcastMessage(ChatColor.YELLOW + String.valueOf(minutesLeft) + " minutes left");
                }

                secondsLeft--;
            }
        }.runTaskTimer(Main_class.getPlugin(Main_class.class), 0, 20); // Schedule task with 1 second interval
    }
}




