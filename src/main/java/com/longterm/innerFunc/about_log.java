package com.longterm.innerFunc;

import com.longterm.LongTerm;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class about_log {
    File logFolder = new File(LongTerm.this_path().getDataFolder() + "/User_Log");

    public File getPlayerLog(Player player) {
        String target_name = player.getName();
        File file = new File(logFolder.getPath() + "/" + target_name + ".yml");
        return file;
    }

    public void createPlayerLog(Player player) {
        String target_name = player.getName();
        if (!logFolder.exists()) {
            boolean createFolder = logFolder.mkdir();

            if (createFolder) {
                System.out.println("success to make folder!");
            } else {
                System.out.println("failed to make folder!");
            }
        }

        String filePath = LongTerm.this_path().getDataFolder() + "/User_Log/" + target_name + ".yml";
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("file generated~!");
            } else {
                System.out.println("already file exists");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter);
    }

    //누구의 로그, 로그 종류
    public void writePlayerLog(Player player, String Type, String[] args) {
        String finalWrite = "[" + this.getCurrentTime() + "]";
        switch (Type) {
            case "Join":
                finalWrite += "[--JOIN]";
                break;
            case "Left":
                finalWrite += "[LEFT--]";
                break;
            case "GetCoin": //player, type, before coin, amount, from, current coin
                finalWrite += ("[GET COIN][" + args[0] + "] - " + args[1] + " THAT FROM " + args[2] + " = [" + args[3] + "]");
                break;
            case "TakeCoin": //player, type, before coin, amount, current coin
                finalWrite += ("[Take COIN][" + args[0] + "] - " + args[1] + " = ["+args[2]+"]");
                break;
            case "UseCoin": //player, type, before coin, amount, Where, What
                finalWrite += ("[Use COIN] - ");
                break;
            case "BuyMaterial": //player, type, buyWhat, price, amount
                finalWrite += ("[BUY MATERIAL] - BUY " + args[0] + " " + args[1] + " - EA : " + args[2]);
                break;
            case "SellMaterial": //player, type, sellWhat, price, amount
                finalWrite += ("[SELL MATERIAL] - SELL " + args[0] + " " + args[1] + " - EA : " + args[2]);
                break;
            case "DoUpgrade": //player, type, what, price
                finalWrite += ("[DoUpgrade] - UPGRADE " + args[0] + " " + args[1]);
                break;
        }
        finalWrite += "\n";
        try {
            FileWriter writer = new FileWriter(this.getPlayerLog(player).getPath(), true);

            writer.write(finalWrite);
            writer.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}

