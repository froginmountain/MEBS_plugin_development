package com.longterm.innerFunc;

import com.longterm.LongTerm;
import com.longterm.customEntity.Keys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class shop {

    File shopProductFolder = new File(LongTerm.this_path().getDataFolder() + "/shopProduct");
    public void init_shopProduct() {
        if(!shopProductFolder.exists()) {
            boolean createFolder = shopProductFolder.mkdir();
            if(createFolder) {
                System.out.println("shopProduct : success to make folder!");
            } else {
                System.out.println("shopProduct : failed to make folder!");
            }
        }
        String filePath = shopProductFolder.getPath() + "/Products.yml";
        try {
            File file = new File(filePath);
            if(file.createNewFile()) {
                System.out.println("shopProduct : success to make file!");
            } else {
                System.out.println("shopProduct : failed to make file!");
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
        int idx = 0;
        {
            sort.add("COPPER_INGOT");
            price.add(50);
            sort.add("COAL");
            price.add(80);
            sort.add("IRON_INGOT");
            price.add(150);
            sort.add("GOLD_INGOT");
            price.add(300);
            sort.add("LAPIS_LAZULI");
            price.add(230);
            sort.add("DIAMOND");
            price.add(1000);

            for (String setting : sort) {
                temp.set("Products.Material."+setting, price.get(idx));
                idx++;
            }
        }
        sort = new ArrayList<>();
        price = new ArrayList<>();
        idx = 0;
        {
            sort.add("IRON_HELMETS");
            price.add(50);
            sort.add("GOLDEN_HELMETS");
            price.add(80);
            sort.add("IRON_INGOT");
            price.add(150);
            sort.add("GOLD_INGOT");
            price.add(300);
            sort.add("LAPIS_LAZULI");
            price.add(230);
            sort.add("DIAMOND");
            price.add(1000);

            for (String setting : sort) {
                temp.set("Products.Enchant."+setting, price.get(idx));
                idx++;
            }
        }
    }

    public void shopCreate(String type, World world, Location loc) {
        if(type.equalsIgnoreCase("Material")) {

            LivingEntity entity = (LivingEntity) world.spawnEntity(loc, EntityType.VILLAGER);

            entity.getPersistentDataContainer().set(Keys.Is_Material, PersistentDataType.BYTE, (byte) 1);
            entity.setCustomName(ChatColor.BLUE + "광물 상인");
            entity.setCustomNameVisible(true);
            Villager villager = (Villager) entity;
            villager.setAI(false);
            villager.setProfession(Villager.Profession.MASON);
            villager.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, PotionEffect.INFINITE_DURATION, 10));

        }
        if(type.equalsIgnoreCase("Equipment")) {
            LivingEntity entity = (LivingEntity) world.spawnEntity(loc, EntityType.VILLAGER);

            entity.getPersistentDataContainer().set(Keys.Is_Equipment, PersistentDataType.BYTE, (byte) 1);
            entity.setCustomName(ChatColor.BLUE + "장비 및 보호구 상인");
            entity.setCustomNameVisible(true);
            Villager villager = (Villager) entity;
            villager.setAI(false);
            villager.setProfession(Villager.Profession.LIBRARIAN);
            villager.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, PotionEffect.INFINITE_DURATION, 10));
        }
    }
}
