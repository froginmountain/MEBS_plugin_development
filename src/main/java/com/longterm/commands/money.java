package com.longterm.commands;

import com.longterm.Item.coin;
import com.longterm.innerFunc.about_log;
import com.longterm.innerFunc.inner_money;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class money implements Listener, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        int p_money = new inner_money().getMoney(p);

        if(label.equalsIgnoreCase("money") || label.equalsIgnoreCase("돈") || label.equalsIgnoreCase("m")) {
            if(args.length == 0) {
                //p.sendMessage(sender.getName() + " : " + p_money);
            }
            else if(args.length == 1 && Integer.parseInt(args[0]) > 0) {
                boolean canExecute = new inner_money().minusMoney(p, Integer.parseInt(args[0]));
                if(p.getInventory().firstEmpty() != -1) {
                    if(canExecute) {
                        String[] toLog = new String[3];
                        toLog[0] = Integer.toString(p_money);
                        toLog[1] = args[0];
                        toLog[2] = Integer.toString(p_money + Integer.parseInt(args[0]));
                        new about_log().writePlayerLog(p, "TakeCoin", toLog);

                        new coin().get_coin(Integer.parseInt(args[0]), p);

                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR , TextComponent.fromLegacyText(args[0] + "원이 발행되었습니다."));
                    } else {
                        p.sendMessage("액수를 재확인 해주세요. 현재 소지금 : " + p_money);
                    }
                } else {
                    new inner_money().addMoney(p, Integer.parseInt(args[0]));
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("인벤토리가 가득 찼습니다."));
                }
            } else {
                p.sendMessage("나 하나만 이라는 생각이...");
            }
        }
        return true;
    }
}