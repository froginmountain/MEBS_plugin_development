package com.longterm.innerFunc;

import com.longterm.LongTerm;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class hideChat {
    public void onPlayerHideChat(Player p) {
        for(int i = 0; i < 100; i++) {
            p.sendMessage("");
        }
    }
}
