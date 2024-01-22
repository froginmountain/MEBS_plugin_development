package com.longterm;

import com.longterm.commands.money;
import com.longterm.commands.ADMIN;
import com.longterm.commands.estate;

import com.longterm.events.estate_click;

import com.longterm.innerFunc.RunTaskTimer;
import com.longterm.innerFunc.about_log;
import com.longterm.innerFunc.inner_estate;

import com.longterm.Item.coin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LongTerm extends JavaPlugin implements Listener {
    private static LongTerm instance;
    static public RunTaskTimer tasktimer = new RunTaskTimer();

    @Override
    public void onEnable() {
        instance = this;

        //플레이어 입장, 퇴장
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().registerEvents(new coin(), this);
        getServer().getPluginManager().registerEvents(new estate_click(), this);

        getServer().getOnlinePlayers().forEach(player -> {
            player.sendMessage("LongTerm Reload Complete!");
        });

        this.saveDefaultConfig();

        tasktimer.startScoreboardTask();
        //관리 명령어(조회와 같은 것들)
        getCommand("admin").setExecutor(new ADMIN());

        //coin 관련 명령어
        getCommand("money").setExecutor(new money());
        
        //땅 부여 관련 명령어
        getCommand("estate").setExecutor(new estate());

        new estate_click().removeTNTCartRecipe();
        new inner_estate().InitPlayerFile();
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        p.sendMessage("Welcome To Time Machine!");
        tasktimer.restartScoreboardTask();
        new about_log().writePlayerLog(p, "Join", new String[0]);
    }

    @EventHandler
    public void PlayerLeft(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        new about_log().writePlayerLog(p, "Left", new String[0]);
    }

    public static LongTerm this_path() {
        return instance;
    }
}
