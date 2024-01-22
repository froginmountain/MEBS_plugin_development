package com.longterm.innerFunc;

import com.longterm.LongTerm;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class inner_money {
    RunTaskTimer taskTimer = LongTerm.tasktimer;
    JavaPlugin config = LongTerm.getPlugin(LongTerm.class);

    public int getMoney(Player p) {
        int result = 0;
        if(!config.getConfig().contains("users."+p.getPlayer().getUniqueId().toString())) {
            result = 100;
            config.getConfig().set("users." + p.getPlayer().getUniqueId().toString() + ".money", result);
            config.saveConfig();
            new about_log().createPlayerLog(p);
            taskTimer.restartScoreboardTask();
            return result;
        } else {
            result = config.getConfig().getInt("users."+p.getPlayer().getUniqueId().toString()+".money");
            taskTimer.restartScoreboardTask();
            return result;
        }
    }

    public void addMoney(Player p, int amount) {
        int currentMoney = this.getMoney(p);
        config.getConfig().set("users."+p.getPlayer().getUniqueId().toString()+".money", currentMoney + amount);
        config.saveConfig();
    }

    public boolean minusMoney(Player p, int amount) {
        int currentMoney = this.getMoney(p);
        if(currentMoney >= amount) {
            config.getConfig().set("users." + p.getPlayer().getUniqueId().toString() + ".money", this.getMoney(p) - amount);
            config.saveConfig();
            taskTimer.restartScoreboardTask();
            return true;
        } else {
            return false;
        }
    }
}
