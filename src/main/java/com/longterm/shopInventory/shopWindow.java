package com.longterm.shopInventory;

import com.longterm.LongTerm;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class shopWindow {
    File file = new File(LongTerm.this_path().getDataFolder() + "/shopProduct");
    String filePath = file.getPath() + "/Products.yml";
    File sfile = new File(filePath);
    YamlConfiguration configFile =  YamlConfiguration.loadConfiguration(sfile);

    public void onPlayerOpen(Player p, String type) {
        if(type.equalsIgnoreCase("Material")) {
            Inventory inventory = Bukkit.createInventory(p, 9 * 4, ChatColor.GREEN + "Mateiral Shop");

            inventory.setItem(0, display_word("구"));
            inventory.setItem(1, display_word("매"));
            inventory.setItem(18, display_word("판"));
            inventory.setItem(19, display_word("매"));

            inventory.setItem(10, shopProducts("Material", "COPPER_INGOT", "buy"));
            inventory.setItem(11, shopProducts("Material", "COAL", "buy"));
            inventory.setItem(12, shopProducts("Material", "IRON_INGOT", "buy"));
            inventory.setItem(14, shopProducts("Material", "GOLD_INGOT", "buy"));
            inventory.setItem(15, shopProducts("Material", "LAPIS_LAZULI", "buy"));
            inventory.setItem(16, shopProducts("Material", "DIAMOND", "buy"));

            inventory.setItem(28, shopProducts("Material", "COPPER_INGOT", "sell"));
            inventory.setItem(29, shopProducts("Material", "COAL", "sell"));
            inventory.setItem(30, shopProducts("Material", "IRON_INGOT", "sell"));
            inventory.setItem(32, shopProducts("Material", "GOLD_INGOT", "sell"));
            inventory.setItem(33, shopProducts("Material", "LAPIS_LAZULI", "sell"));
            inventory.setItem(34, shopProducts("Material", "DIAMOND", "sell"));

            p.openInventory(inventory);
        } else if(type.equalsIgnoreCase("Equipment")) {
            Inventory inventory = Bukkit.createInventory(p, 9 * 4, ChatColor.GREEN + "Equipment Shop");

            inventory.setItem(0, display_word("구"));
            inventory.setItem(1, display_word("매"));

            inventory.setItem(10, shopProducts("Equipment", "DIAMOND_SWORD", "buy"));
            inventory.setItem(11, shopProducts("Equipment", "DIAMOND_PICKAXE", "buy"));
            inventory.setItem(12, shopProducts("Equipment", "DIAMOND_AXE", "buy"));
            inventory.setItem(13, shopProducts("Equipment", "DIAMOND_SHOVEL", "buy"));
            inventory.setItem(14, shopProducts("Equipment", "DIAMOND_HOE", "buy"));

            inventory.setItem(19, shopProducts("Equipment", "DIAMOND_HELMET", "buy"));
            inventory.setItem(20, shopProducts("Equipment", "DIAMOND_CHESTPLATE", "buy"));
            inventory.setItem(21, shopProducts("Equipment", "DIAMOND_LEGGINGS", "buy"));
            inventory.setItem(22, shopProducts("Equipment", "DIAMOND_BOOTS", "buy"));

            p.openInventory(inventory);
        }
    }

    //종류, 아이템 코드
    private ItemStack shopProducts(String type, String what, String how) {
        Integer price = Integer.parseInt(configFile.get("Products." + type + "." + what).toString());
        List<String> lore = new ArrayList<>();

        if(!(configFile.get("Products." + type + "." + what) == null)) {
            if(type.equalsIgnoreCase("Material")) {
                ItemStack result = new ItemStack(Material.getMaterial(what));
                ItemMeta resultMeta = result.getItemMeta();

                String Word = toKorean(what);
                if(Word != null) {
                    if(how.equalsIgnoreCase("buy")) {
                        resultMeta.setDisplayName("§4[구매] " + Word);
                    } else if(how.equalsIgnoreCase("sell")) {
                        resultMeta.setDisplayName("§3[판매] " + Word);
                    }
                    lore.add("§f" + price + "원");
                    resultMeta.setLore(lore);
                    result.setItemMeta(resultMeta);
                    return result;
                }
            }
            if(type.equalsIgnoreCase("Equipment")) {
                ItemStack result = new ItemStack(Material.getMaterial(what));
                ItemMeta resultMeta = result.getItemMeta();

                String Word = toKorean(what);
                if(Word != null) {
                    if(how.equalsIgnoreCase("buy")) {
                        resultMeta.setDisplayName("§4[구매] " + Word);
                    }
                    lore.add("§f" + price + "원");
                    if(what.equalsIgnoreCase("DIAMOND_SWORD") ||
                            what.equalsIgnoreCase("DIAMOND_AXE"))
                        lore.add("§b[랜덤]날카로움, 강타, 살충 중 랜덤 부여됩니다.");
                    else if(what.equalsIgnoreCase("DIAMOND_HELMET") ||
                            what.equalsIgnoreCase("DIAMOND_CHESTPLATE") ||
                            what.equalsIgnoreCase("DIAMOND_LEGGINGS") ||
                            what.equalsIgnoreCase("DIAMOND_BOOTS")) {
                        lore.add("§b[랜덤]보호, 화염 보호, 폭발 보호, 발사체 보호 중 랜덤 부여됩니다.");
                    }
                    else lore.add("Maybe Something");
                    resultMeta.setLore(lore);
                    result.setItemMeta(resultMeta);
                    return result;
                }
            }
        }
        return null;
    }

    private ItemStack display_word(String what) {
        ItemStack result = new ItemStack(Material.AIR);
        if(what.equalsIgnoreCase("판")) {
            result = new ItemStack(Material.PAPER);
            ItemMeta meta = result.getItemMeta();
            meta.setCustomModelData(111);
            result.setItemMeta(meta);
            return result;
        } else if(what.equalsIgnoreCase("구")) {
            result = new ItemStack(Material.PAPER);
            ItemMeta meta = result.getItemMeta();
            meta.setCustomModelData(112);
            result.setItemMeta(meta);
            return result;
        } else if(what.equalsIgnoreCase("매")) {
            result = new ItemStack(Material.PAPER);
            ItemMeta meta = result.getItemMeta();
            meta.setCustomModelData(113);
            result.setItemMeta(meta);
            return result;
        }
        return null;
    }

    public String toKorean(String s) {
        if(s.equalsIgnoreCase("COPPER_INGOT")) { return "구리 주괴"; }
        if(s.equalsIgnoreCase("COAL")) { return "석탄"; }
        if(s.equalsIgnoreCase("IRON_INGOT")) { return "철 주괴"; }
        if(s.equalsIgnoreCase("GOLD_INGOT")) { return "금 주괴"; }
        if(s.equalsIgnoreCase("LAPIS_LAZULI")) { return "청금석"; }
        if(s.equalsIgnoreCase("DIAMOND")) { return "다이아몬드"; }

        if(s.equalsIgnoreCase("DIAMOND_SWORD")) { return "다이아몬드 검(랜덤)"; }
        if(s.equalsIgnoreCase("DIAMOND_PICKAXE")) { return "다이아몬드 곡괭이"; }
        if(s.equalsIgnoreCase("DIAMOND_AXE")) { return "다이아몬드 도끼"; }
        if(s.equalsIgnoreCase("DIAMOND_SHOVEL")) { return "다이아몬드 삽"; }
        if(s.equalsIgnoreCase("DIAMOND_HOE")) { return "다이아몬드 괭이"; }

        if(s.equalsIgnoreCase("DIAMOND_HELMET")) { return "다이아몬드 헬멧"; }
        if(s.equalsIgnoreCase("DIAMOND_CHESTPLATE")) { return "다이아몬드 흉갑"; }
        if(s.equalsIgnoreCase("DIAMOND_LEGGINGS")) { return "다이아몬드 레깅스"; }
        if(s.equalsIgnoreCase("DIAMOND_BOOTS")) { return "다이아몬드 부츠"; }

        return null;
    }
}
