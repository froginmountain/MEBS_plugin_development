package com.longterm.innerFunc;

import com.longterm.LongTerm;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.Yaml;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class inner_estate implements Listener {
    JavaPlugin config = LongTerm.getPlugin(LongTerm.class);
    File estateFolder = new File(LongTerm.this_path().getDataFolder() + "/Estate");

    public void InitPlayerFile() {
        if(!estateFolder.exists()) {
            boolean createFolder = estateFolder.mkdir();
            if (createFolder) {
                System.out.println("Estate : success to make folder!");
            } else {
                System.out.println("Estate : failed to make folder!");
            }
        }
        String filePath = estateFolder.getPath() + "/EstateData.yml";
        try {
            File file = new File(filePath);
            if(file.createNewFile()) {
                System.out.println("file generated~!");
            } else {
                System.out.println("already file exists");
            }
            YamlConfiguration temp = YamlConfiguration.loadConfiguration(file);
            List<String> blank = new ArrayList<>();
            temp.set("Estate.DefaultUser.Chunk_ID", blank);
            temp.save(file);
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
    }

    public void addPlayerEstate(Player player, int x, int y, int z) {
        String target_name = player.getName();
        if(estateFolder.exists()) {
            int have_money = new inner_money().getMoney(player);
            if(have_money >= 1000) {
                boolean alreadyExist = false;

                File estateFile = new File(estateFolder.getPath() + "/EstateData.yml");
                YamlConfiguration Econfig = YamlConfiguration.loadConfiguration(estateFile);

                List<String> ChunkList = Econfig.getStringList("Estate." + target_name + ".Chunk_ID");
                for (String temp : ChunkList) {
                    if(temp.equalsIgnoreCase(x + ", " + y + ", " + z)) {
                        alreadyExist = true;
                    }
                }

                try {
                    if(!alreadyExist) {
                        ChunkList.add(x + ", " + y + ", " + z);
                        Econfig.set("Estate." + target_name + ".Chunk_ID", ChunkList);
                        Econfig.save(estateFile);
                        new inner_money().minusMoney(player, 1000);
                        player.sendMessage("현재 청크를 구매하셨습니다.");
                    } else {
                        player.sendMessage("이미 해당 청크를 가지고 있습니다.");
                    }
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }
            } else {
                player.sendMessage("돈이 충분하지 않습니다.");
            }
        } else {
            InitPlayerFile();
            player.sendMessage("다시 시도해주십시오.");
        }
    }

    public void nAddPlayerEstate(Player player, int x, int y, int z) {
        String target_name = player.getName();
        if(estateFolder.exists()) {
            File estateFile = new File(estateFolder.getPath() + "/EstateData.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(estateFile);
            List<String> ChunkList = config.getStringList("Estate." + target_name + ".Chunk_ID");

            boolean isIn = false;

            Iterator<String> iterator = ChunkList.iterator();
            while(iterator.hasNext()) {
                String temp = iterator.next();
                if(temp.equalsIgnoreCase(x + ", " + y + ", " + z)) {
                    isIn = true;
                    iterator.remove();
                }
            }

            if(!isIn) {
                player.sendMessage("해당 구역을 소유하고 있지 않습니다.");
            }

            try {
                config.set("Estate." + target_name + ".Chunk_ID", ChunkList);
                config.save(estateFile);
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        } else {
            InitPlayerFile();
            player.sendMessage("다시 시도해주십시오.");
        }
    }

    public String estateWho(Chunk chunk, int y) {
        File estateFile = new File(estateFolder.getPath() + "/EstateData.yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(estateFile);
        ConfigurationSection section = config.getConfigurationSection("Estate");

        String result = "NULL";
        int y_chunk = (y+64) / 16;
        Set<String> keys = section.getKeys(false);
        for (String key : keys) {
            List<String> ChunkList = config.getStringList("Estate." + key + ".Chunk_ID");
            for (String checkLog : ChunkList) {
                if(checkLog.equalsIgnoreCase(chunk.getX() + ", " + y_chunk + ", " + chunk.getZ())) {
                    result = key;
                    break;
                }
            }
            if (!result.equalsIgnoreCase("NULL")) {
                break;
            }
        }
        if(result.equalsIgnoreCase("NULL")) result = "None";
        return result;
    }

    public void print_estate(Player player) {
        File estateFile = new File(estateFolder.getPath() + "/EstateData.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(estateFile);
        List<String> ChunkList = config.getStringList("Estate." + player.getName() + ".Chunk_ID");

        player.sendMessage("--현재 "+player.getName()+"님이 가지고 있는 땅 정보--");
        for (String checkLog : ChunkList) {
            TextComponent first = new TextComponent("( "+checkLog + " )");
            TextComponent seeThere = new TextComponent(new ComponentBuilder(" [보러가기] ").color(ChatColor.DARK_GREEN).create());
            TextComponent sellHere = new TextComponent(new ComponentBuilder(" [매각] ").color(ChatColor.RED).create());

            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("3초 뒤 이동합니다. 머리도 움직이지 마세요!").color(ChatColor.DARK_GREEN).create());
            HoverEvent hoverEvent1 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("현재 구역을 반환합니다.").color(ChatColor.DARK_RED).create());

            seeThere.setHoverEvent(hoverEvent);
            sellHere.setHoverEvent(hoverEvent1);

            String[] temp = checkLog.split(", ");

            seeThere.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/e seeThere " + temp[0] + " " + temp[1] + " " + temp[2]));
            sellHere.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/e real1l1l1_sell " + temp[0] + " " + temp[1] + " " + temp[2]));

            first.addExtra(seeThere);
            first.addExtra(sellHere);

            player.spigot().sendMessage(first);
        }
        player.sendMessage("--------------------------------");
    }

    public void teleport_toChunk(Player player, int x, int y, int z) {
        GameMode original = player.getGameMode();
        player.sendMessage("곧 이동합니다. 움직이지 마세요...");
        Location sendLocation = player.getLocation();
        World world = player.getWorld();
        Location tepoLocation = new Location(world, (x * 16) + 8, (y * 16) - 64 + 18 , (z * 16) + 8);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (sendLocation.equals(player.getLocation())) {
                    player.teleport(tepoLocation);
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage("움직이면 원래 자리로 돌아갑니다.");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location currentLocation = player.getLocation().clone();

                            if(tepoLocation.getX() != currentLocation.getX() ||
                            tepoLocation.getY() != currentLocation.getY() ||
                            tepoLocation.getZ() != currentLocation.getZ()) {
                                cancel();
                                player.setGameMode(original);
                                player.teleport(sendLocation);
                            }
                        }
                    }.runTaskTimer(LongTerm.this_path(), 20L, 10L);
                } else {
                    player.sendMessage("이동 하셨습니다. 고개도 움직이면 안됩니다.");
                }
            }
        }.runTaskLater(LongTerm.this_path(), 60L);
    }
}
