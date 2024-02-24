package com.longterm.commands;

import com.longterm.innerFunc.shop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import java.util.ArrayList;
import java.util.List;

public class test implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        Player p = (Player) player;
        World world = p.getWorld();
        Location location = p.getLocation();


        if(cmd.getName().equalsIgnoreCase("test")) {
            if(args.length == 0) {
                new shop().shopCreate("Material",world, location);
            }
            else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("coin100")) {

                } else if(args[0].equalsIgnoreCase("coin1000")) {

                } else if(args[0].equalsIgnoreCase("coin10000")) {

                } else if(args[0].equalsIgnoreCase("판")) {
                    
                } else if(args[0].equalsIgnoreCase("구")) {

                }  else if(args[0].equalsIgnoreCase("매")) {

                }
            } else if(args.length == 2) {

            } else if(args.length == 3) {

            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender player, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if(cmd.getName().equalsIgnoreCase("test")) {
            if(args.length == 0) {
                list.add("test");
            } else if(args.length == 1) {
                list.add("test2");
            } else if(args.length == 2) {

            } else if(args.length == 3) {

            }
            return list;
        }

        return null;
    }
}
