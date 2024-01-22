package com.longterm.commands;

import com.longterm.LongTerm;
import com.longterm.innerFunc.about_log;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ADMIN implements Listener, CommandExecutor {
    JavaPlugin config = LongTerm.getPlugin(LongTerm.class);

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("admin") || args.length == 2) {
            if(args[0].equalsIgnoreCase("money")
            || args[0].equalsIgnoreCase("m")
            || args[0].equalsIgnoreCase("돈")) {
                Player target_player = Bukkit.getPlayer(args[1]);
                player.sendMessage(target_player.getName() + " : " + config.getConfig().getInt("users."+target_player.getUniqueId().toString()+".money"));
            }
            else if(args[0].equalsIgnoreCase("log")
                    || args[0].equalsIgnoreCase("l")
                    || args[0].equalsIgnoreCase("로그")) {
                Player target_player = Bukkit.getPlayer(args[1]);
                target_player.sendMessage(new about_log().getPlayerLog(target_player).toString());
                new about_log().createPlayerLog(target_player);
            } else if(args[0].equalsIgnoreCase("HideName")) {
                Player target_player = Bukkit.getPlayer(args[1]);
                target_player.sendMessage("hidename : ");
            }
        }
        return true;
    }
}
