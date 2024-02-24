package com.longterm.innerFunc;

import com.longterm.LongTerm;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class upgrade {
    File upgradeProductFolder = new File(LongTerm.this_path().getDataFolder() + "/upgradeProduct");

    File FilePath = new File(upgradeProductFolder + "/Upgrade.yml");
    YamlConfiguration uFile = YamlConfiguration.loadConfiguration(FilePath);
    public void init_upgradeProduct() {
        if(!upgradeProductFolder.exists()) {
            boolean createFolder = upgradeProductFolder.mkdir();
            if(createFolder) {
                System.out.println("upgradeProduct : success to make folder!");
            } else {
                System.out.println("upgradeProduct : failed to make folder!");
            }
        }
        String filePath = upgradeProductFolder.getPath() + "/Upgrade.yml";
        try {
            File file = new File(filePath);
            if(file.createNewFile()) {
                System.out.println("upgradeProduct : success to make file!");
            } else {
                System.out.println("upgradeProduct : failed to make file!");
            }
            YamlConfiguration temp = YamlConfiguration.loadConfiguration(file);
            whatInIt(temp);
            temp.save(file);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void whatInIt(YamlConfiguration temp) {
        List<String> sort = new ArrayList<>();
        List<Integer> price = new ArrayList<>();
        List<Double> percentage = new ArrayList<>();
        int idx = 0;
        {
            {
                sort.add("DAMAGE_ALL1");
                price.add(1000);
                sort.add("DAMAGE_ALL2");
                price.add(2000);
                sort.add("DAMAGE_ALL3");
                price.add(3000);
                sort.add("DAMAGE_ALL4");
                price.add(4000);
                sort.add("DAMAGE_ALL5");
                price.add(5000);
            }
            {
                sort.add("DAMAGE_UNDEAD1");
                price.add(1000);
                sort.add("DAMAGE_UNDEAD2");
                price.add(2000);
                sort.add("DAMAGE_UNDEAD3");
                price.add(3000);
                sort.add("DAMAGE_UNDEAD4");
                price.add(4000);
                sort.add("DAMAGE_UNDEAD5");
                price.add(5000);
            }
            {
                sort.add("DAMAGE_ARTHROPODS1");
                price.add(1000);
                sort.add("DAMAGE_ARTHROPODS2");
                price.add(2000);
                sort.add("DAMAGE_ARTHROPODS3");
                price.add(3000);
                sort.add("DAMAGE_ARTHROPODS4");
                price.add(4000);
                sort.add("DAMAGE_ARTHROPODS5");
                price.add(5000);
            }
            {
                sort.add("DURABILITY1");
                price.add(1000);
                sort.add("DURABILITY2");
                price.add(2000);
                sort.add("DURABILITY3");
                price.add(3000);
            }
            {
                sort.add("KNOCKBACK1");
                price.add(1000);
                sort.add("KNOCKBACK2");
                price.add(2000);
            }
            {
                sort.add("SWEEPING_EDGE1");
                price.add(1000);
                sort.add("SWEEPING_EDGE2");
                price.add(2000);
                sort.add("SWEEPING_EDGE3");
                price.add(3000);
            }
            {
                sort.add("FIRE_ASPECT1");
                price.add(1000);
                sort.add("FIRE_ASPECT2");
                price.add(2000);
                sort.add("FIRE_ASPECT3");
                price.add(3000);
            }
            {
                sort.add("LOOT_BONUS_MOBS1");
                price.add(1000);
                sort.add("LOOT_BONUS_MOBS2");
                price.add(2000);
            }

            for (String setting : sort) {
                temp.set("Upgrade.Enchantment.Sword."+setting, price.get(idx));
                idx++;
            }

            sort.clear();
            idx = 0;

            {
                sort.add("DAMAGE_ALL1");
                percentage.add(0.7);
                sort.add("DAMAGE_ALL2");
                percentage.add(0.55);
                sort.add("DAMAGE_ALL3");
                percentage.add(0.4);
                sort.add("DAMAGE_ALL4");
                percentage.add(0.3);
                sort.add("DAMAGE_ALL5");
                percentage.add(0.1);
            }
            {
                sort.add("DAMAGE_UNDEAD1");
                percentage.add(0.8);
                sort.add("DAMAGE_UNDEAD2");
                percentage.add(0.7);
                sort.add("DAMAGE_UNDEAD3");
                percentage.add(0.6);
                sort.add("DAMAGE_UNDEAD4");
                percentage.add(0.5);
                sort.add("DAMAGE_UNDEAD5");
                percentage.add(0.4);
            }
            {
                sort.add("DAMAGE_ARTHROPODS1");
                percentage.add(0.8);
                sort.add("DAMAGE_ARTHROPODS2");
                percentage.add(0.7);
                sort.add("DAMAGE_ARTHROPODS3");
                percentage.add(0.6);
                sort.add("DAMAGE_ARTHROPODS4");
                percentage.add(0.5);
                sort.add("DAMAGE_ARTHROPODS5");
                percentage.add(0.4);
            }
            {
                sort.add("DURABILITY1");
                percentage.add(0.8);
                sort.add("DURABILITY2");
                percentage.add(0.6);
                sort.add("DURABILITY3");
                percentage.add(0.3);
            }
            {
                sort.add("KNOCKBACK1");
                percentage.add(0.7);
                sort.add("KNOCKBACK2");
                percentage.add(0.6);
            }
            {
                sort.add("SWEEPING_EDGE1");
                percentage.add(0.6);
                sort.add("SWEEPING_EDGE2");
                percentage.add(0.5);
                sort.add("SWEEPING_EDGE3");
                percentage.add(0.4);
            }
            {
                sort.add("FIRE_ASPECT1");
                percentage.add(0.5);
                sort.add("FIRE_ASPECT2");
                percentage.add(0.4);
            }
            {
                sort.add("LOOT_BONUS_MOBS1");
                percentage.add(0.3);
                sort.add("LOOT_BONUS_MOBS2");
                percentage.add(0.1);
            }

            for (String setting : sort) {
                temp.set("Upgrade.Percentage.Sword."+setting, percentage.get(idx));
                idx++;
            }
        }
    }

    public int howPrice(String type, String enchant, int lv) {
        if((uFile.get("Upgrade.Enchantment." + type + "." + enchant + lv)) != null) {
            return Integer.parseInt(uFile.get("Upgrade.Enchantment." + type + "." + enchant + lv).toString());
        }
        return 0;
    }

    public double howPercentage(String type, String enchant, int lv) {
        if((uFile.get("Upgrade.Percentage." + type + "." + enchant + lv)) != null) {
            return Double.parseDouble(uFile.get("Upgrade.Percentage." + type + "." + enchant + lv).toString());
        }
        return 0;
    }
}
