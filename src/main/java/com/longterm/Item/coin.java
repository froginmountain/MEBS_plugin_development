package com.longterm.Item;

import com.longterm.LongTerm;
import com.longterm.innerFunc.RunTaskTimer;
import com.longterm.innerFunc.about_log;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class coin implements Listener {
    RunTaskTimer tasktimer = LongTerm.tasktimer;
    ItemStack item = new ItemStack(Material.PAPER, 1);
    JavaPlugin config = LongTerm.getPlugin(LongTerm.class);
    ItemMeta meta = item.getItemMeta();
    List<String> lore = new ArrayList<>();

    private void set_coin(int value, Player player) {
        this.meta.setDisplayName(value+" 원");
        this.lore.add("From "+ player.getName());
        this.lore.add("Amount : " + value);
        meta.setLore(this.lore);
        if(value <= 100) {
            meta.setCustomModelData(100777);
        } else  if (value <= 1000) {
            meta.setCustomModelData(100077);
        } else  if (value <= 10000) {
            meta.setCustomModelData(100007);
        }

        this.item.setItemMeta(meta);
    }

    public boolean get_coin(int value ,Player player) {
        set_coin(value, player);
        Inventory inv = player.getInventory();
        inv.addItem(item);
        return true;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // 플레이어가 우클릭하면서 손에 들고 있는 아이템이 CustomItem인 경우
        if (event.getItem() != null && event.getItem().getItemMeta().hasCustomModelData()) {
            if(event.getItem().getType().equals(Material.PAPER) || event.getItem().getItemMeta().getDisplayName().contains("원")) {
                int value = Integer.parseInt(event.getItem().getItemMeta().getDisplayName().replace(" 원",""));
                if((event.getItem().getItemMeta().getCustomModelData() == 100777 ||
                        event.getItem().getItemMeta().getCustomModelData() == 100077 ||
                        event.getItem().getItemMeta().getCustomModelData() == 100007
                ) && value == Integer.parseInt(event.getItem().getItemMeta().getLore().get(1).replace("Amount : ", ""))) {
                    int origin = config.getConfig().getInt("users."+event.getPlayer().getUniqueId().toString()+".money");
                    config.getConfig().set("users."+event.getPlayer().getUniqueId().toString()+".money", origin + value);
                    config.saveConfig();
                    tasktimer.restartScoreboardTask();
                    int current_amount = event.getPlayer().getInventory().getItemInMainHand().getAmount();
                    event.getPlayer().getInventory().getItemInMainHand().setAmount(current_amount - 1);

                    String[] toLog = new String[4];
                    toLog[0] = Integer.toString(origin);
                    toLog[1] = Integer.toString(value);
                    toLog[2] = event.getItem().getItemMeta().getLore().get(0).replace("From ","");
                    toLog[3] = Integer.toString(origin + value);
                    new about_log().writePlayerLog(event.getPlayer(), "GetCoin", toLog);

                    event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(value+"원을 획득하셨습니다!"));
                }
            }
        }
    }
}
