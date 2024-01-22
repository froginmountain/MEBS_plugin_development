package com.longterm.commands;

import com.longterm.innerFunc.inner_estate;

import com.longterm.innerFunc.hideChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class estate implements Listener, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        Player p = (Player) player;
        if(label.equalsIgnoreCase("estate")
        || label.equalsIgnoreCase("e")) {
            Chunk currentChunk = p.getLocation().getChunk();

            new hideChat().onPlayerHideChat(p);

            if(args.length == 0) {
                //소유 토지 정보 출력
                new inner_estate().print_estate(p);
            } else if (args.length == 1) {
                if(args[0].equalsIgnoreCase("buy")) {
                    int y = (int) p.getLocation().getY();
                    String estateWho = new inner_estate().estateWho(currentChunk, y);
                    if(!estateWho.equalsIgnoreCase("None")) {
                        p.sendMessage("현재 여기는 " + estateWho + "님의 땅입니다.");
                    } else {
                        TextComponent current = new TextComponent("현재 지목한 청크 위치 : (" + currentChunk.getX() + ", " + ((y + 64) / 16) + ", " + currentChunk.getZ() + ")");
                        TextComponent msg = new TextComponent(new ComponentBuilder(" [구매] ").color(ChatColor.DARK_AQUA).create());
                        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("현재 위치한 청크 구매").color(ChatColor.BLUE).create());
                        msg.setHoverEvent(hoverEvent);
                        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/e real1l1l1_buy " + currentChunk.getX() + " " + ((y + 64) / 16) + " " + currentChunk.getZ()));
                        current.addExtra(msg);
                        p.spigot().sendMessage(current);
                    }
                } else if(args[0].equalsIgnoreCase("tax")) {

                } else {
                    p.sendMessage("명령어를 잘못 입력하셨습니다.");
                }
            } else if(args.length == 4) {
                if(args[0].equalsIgnoreCase("seeThere")) {
                    new inner_estate().teleport_toChunk(p, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                } else if(args[0].equalsIgnoreCase("real1l1l1_sell")) {
                    new inner_estate().nAddPlayerEstate(p, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                    p.sendMessage("매각하였습니다.");
                } else if(args[0].equalsIgnoreCase("real1l1l1_buy")) {
                    new inner_estate().addPlayerEstate(p, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                }
            }
        }
        return true;
    }
}
