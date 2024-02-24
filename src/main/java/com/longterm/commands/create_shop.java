package com.longterm.commands;

import com.longterm.innerFunc.shop;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class create_shop implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        Player p = (Player) player;
        World createWorld = p.getWorld();
        Location createPosition = p.getLocation();
        if(label.equalsIgnoreCase("shop")
        || label.equalsIgnoreCase("s")) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("create")) {
                    if(args[1].equalsIgnoreCase("material")) {
                        new shop().shopCreate("Material", createWorld, createPosition);
                        return true;
                    }
                    if(args[1].equalsIgnoreCase("equipment")) {
                        new shop().shopCreate("Equipment", createWorld, createPosition);
                        return true;
                    }
                }
            }

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender player, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if(cmd.getName().equalsIgnoreCase("s") || cmd.getName().equalsIgnoreCase("shop")) {
            if(args.length == 0) {
                list.add("shop");
            } else if(args.length == 1) {
                list.add("create");
            } else if(args.length == 2) {
                list.add("equipment");
                list.add("material");
            } else if(args.length == 3) {

            }
            return list;
        }

        return null;
    }
}
