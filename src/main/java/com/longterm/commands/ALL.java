package com.longterm.commands;

import com.longterm.LongTerm;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ALL implements Listener, CommandExecutor {
    JavaPlugin config = LongTerm.getPlugin(LongTerm.class);
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if(!config.getConfig().contains("users."+p.getPlayer().getUniqueId().toString())) {
            config.getConfig().set("users." + p.getPlayer().getUniqueId().toString() + ".money", 100);
            config.saveConfig();
        }
        if(label.equalsIgnoreCase("money")) {
            if(args.length == 0) {
                p.sendMessage(sender.getName() + " : " + config.getConfig().getInt("users."+p.getPlayer().getUniqueId().toString()+".money"));
            }
        }
        return true;
    }

}
