package com.longterm.innerFunc;

import com.longterm.LongTerm;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;

public class RunTaskTimer {
    public BukkitRunnable scoreboardTask;

    public void startScoreboardTask() {
        scoreboardTask = new BukkitRunnable() {
            @Override
            public void run() {
                scoreboardMethod();
            }
        };
        scoreboardTask.runTaskTimer(LongTerm.getPlugin(LongTerm.class), 0, 20 * 60);
    }

    public void restartScoreboardTask() {
        if (scoreboardTask != null) {
            scoreboardTask.cancel();
        }
        startScoreboardTask();
    }

    public void scoreboardMethod() {
        ShowScoreboard temp = new ShowScoreboard();
        temp.updateEachPlayer();
    }
}
