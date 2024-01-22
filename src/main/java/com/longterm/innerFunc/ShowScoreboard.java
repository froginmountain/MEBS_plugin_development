package com.longterm.innerFunc;

import com.longterm.LongTerm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class ShowScoreboard {

    @EventHandler
    public void updateEachPlayer() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            updateScoreboard(player);
        }
    }

    private void updateScoreboard(Player player) {
        JavaPlugin config = LongTerm.getPlugin(LongTerm.class);
        // 기존에 표시되어 있던 스코어보드 삭제
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

        // 새로운 스코어보드 생성
        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("money", "dummy", "돈");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // 각 플레이어의 개인 변수를 스코어로 추가
        Score score = objective.getScore("현재 소지금 :");
        score.setScore(config.getConfig().getInt("users."+player.getUniqueId().toString()+".money")); // 스코어 값

        // 플레이어에게 적용
        player.setScoreboard(scoreboard);
    }
}
