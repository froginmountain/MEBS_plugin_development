package com.longterm.commands;

import com.longterm.LongTerm;
import com.longterm.innerFunc.about_log;
import com.longterm.innerFunc.upgrade;
import com.longterm.innerFunc.inner_money;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.joml.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class create_upgrade implements Listener {
    //인챈트 테이블 클릭 시 강화 창 열리기
    @EventHandler
    public void onPlayerInteractUpgrade(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(e.getClickedBlock() != null) {
            if(e.getClickedBlock().getType() == Material.ENCHANTING_TABLE && e.getAction().name().equalsIgnoreCase("RIGHT_CLICK_BLOCK")) {
                e.setCancelled(true);
                if(e.getItem() != null) {
                    if(CanIn(e.getItem().getType())) {
                        this.onPlayerOpen(p, "upgrade", e.getItem());
                        p.setMetadata("OpenMenu", new FixedMetadataValue(LongTerm.this_path(), "Upgrade"));
                    } else {
                        p.sendMessage("강화 불가능한 아이템입니다.");
                    }
                } else {
                    p.sendMessage("강화할 아이템을 손에 들고 클릭해주세요.");
                }
            }
        }
    }

    public void onPlayerOpen(Player p, String type, ItemStack handed) {
        if(type.equalsIgnoreCase("upgrade")) {
            Inventory upgradeWindow = Bukkit.createInventory(p, 9 * 6, ChatColor.RED + "" + ChatColor.BOLD + "강화");

            ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta blank_temp = blank.getItemMeta();
            blank_temp.setDisplayName(" ");
            blank.setItemMeta(blank_temp);

            ItemStack UpgradeSlot = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta UpgradeSlot_Meta = UpgradeSlot.getItemMeta();
            UpgradeSlot_Meta.setDisplayName(" ");
            UpgradeSlot.setItemMeta(UpgradeSlot_Meta);

            for(int i = 0; i < 54; i++) {
                upgradeWindow.setItem(i, blank);
            }

            upgradeWindow.setItem(12, UpgradeSlot);
            upgradeWindow.setItem(13, UpgradeSlot);
            upgradeWindow.setItem(14, UpgradeSlot);
            upgradeWindow.setItem(21, UpgradeSlot);
            upgradeWindow.setItem(23, UpgradeSlot);
            upgradeWindow.setItem(30, UpgradeSlot);
            upgradeWindow.setItem(31, UpgradeSlot);
            upgradeWindow.setItem(32, UpgradeSlot);

            upgradeWindow.setItem(22, handed);

            if(handed.getType().toString().contains("SWORD")) {
                Map<Enchantment, Integer> enchant_list = handed.getEnchantments();
                int temp;
                ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                ItemMeta bookMeta = book.getItemMeta();
                List<String> lore = new ArrayList<>();

                if(enchant_list.get(Enchantment.DAMAGE_ALL) != null) {
                    temp = enchant_list.get(Enchantment.DAMAGE_ALL);
                    bookMeta.addEnchant(Enchantment.DAMAGE_ALL, temp + 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "날카로움 " + (temp + 1) + "레벨 강화");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "DAMAGE_ALL", temp + 1));
                    bookMeta.setLore(lore);
                } else if(enchant_list.get(Enchantment.DAMAGE_UNDEAD) != null) {
                    temp = enchant_list.get(Enchantment.DAMAGE_UNDEAD);
                    bookMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, temp + 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "강타 " + (temp + 1) + "레벨 강화");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "DAMAGE_UNDEAD", temp + 1));
                    bookMeta.setLore(lore);
                } else if(enchant_list.get(Enchantment.DAMAGE_ARTHROPODS) != null) {
                    temp = enchant_list.get(Enchantment.DAMAGE_ARTHROPODS);
                    bookMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, temp + 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "살충 " + (temp + 1) + "레벨 강화");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "DAMAGE_ARTHROPODS", temp + 1));
                    bookMeta.setLore(lore);
                } else {
                    temp = 0;
                    bookMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, temp + 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "살충 " + (temp + 1) + "레벨 강화");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "DAMAGE_ARTHROPODS", 1));
                    bookMeta.setLore(lore);
                }
                book.setItemMeta(bookMeta);
                if(temp != 5) upgradeWindow.setItem(37, book);
                else upgradeWindow.setItem(37, new ItemStack(Material.BARRIER));
                lore.clear();

                //내구성
                bookMeta.getEnchants().keySet().forEach(bookMeta::removeEnchant);
                if(enchant_list.get(Enchantment.DURABILITY) != null) {
                    temp = enchant_list.get(Enchantment.DURABILITY);
                    bookMeta.addEnchant(Enchantment.DURABILITY, temp + 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "내구성 " + (temp + 1) + "레벨 강화");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "DURABILITY", temp + 1));
                    bookMeta.setLore(lore);
                } else {
                    temp = 0;
                    bookMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "내구성 " + (1) + "레벨 부여");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "DURABILITY", 1));
                    bookMeta.setLore(lore);
                }
                book.setItemMeta(bookMeta);
                if(temp != 3) upgradeWindow.setItem(38, book);
                else upgradeWindow.setItem(38, new ItemStack(Material.BARRIER));
                lore.clear();

                //밀치기
                bookMeta.getEnchants().keySet().forEach(bookMeta::removeEnchant);
                if(enchant_list.get(Enchantment.KNOCKBACK) != null) {
                    temp = enchant_list.get(Enchantment.KNOCKBACK);
                    bookMeta.addEnchant(Enchantment.KNOCKBACK, temp + 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "밀치기 " + (temp + 1) + "레벨 강화");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "KNOCKBACK", temp + 1));
                    bookMeta.setLore(lore);
                } else {
                    temp = 0;
                    bookMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "밀치기 " + (1) + "레벨 부여");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "KNOCKBACK", 1));
                    bookMeta.setLore(lore);
                }
                book.setItemMeta(bookMeta);
                if(temp != 2) upgradeWindow.setItem(39, book);
                else upgradeWindow.setItem(39, new ItemStack(Material.BARRIER));
                lore.clear();

                //발화
                bookMeta.getEnchants().keySet().forEach(bookMeta::removeEnchant);
                if(enchant_list.get(Enchantment.FIRE_ASPECT) != null) {
                    temp = enchant_list.get(Enchantment.FIRE_ASPECT);
                    bookMeta.addEnchant(Enchantment.FIRE_ASPECT, temp + 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "화염 " + (temp + 1) + "레벨 강화");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "FIRE_ASPECT", temp + 1));
                    bookMeta.setLore(lore);
                } else {
                    temp = 0;
                    bookMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "화염 " + (1) + "레벨 부여");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "FIRE_ASPECT", 1));
                    bookMeta.setLore(lore);
                }
                book.setItemMeta(bookMeta);
                if(temp != 2) upgradeWindow.setItem(41, book);
                else upgradeWindow.setItem(41, new ItemStack(Material.BARRIER));
                lore.clear();

                //휩쓸기
                bookMeta.getEnchants().keySet().forEach(bookMeta::removeEnchant);
                if(enchant_list.get(Enchantment.SWEEPING_EDGE) != null) {
                    temp = enchant_list.get(Enchantment.SWEEPING_EDGE);
                    bookMeta.addEnchant(Enchantment.SWEEPING_EDGE, temp + 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "휩쓸기 " + (temp + 1) + "레벨 강화");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "SWEEPING_EDGE", temp + 1));
                    bookMeta.setLore(lore);
                } else {
                    temp = 0;
                    bookMeta.addEnchant(Enchantment.SWEEPING_EDGE, 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "휩쓸기 " + (1) + "레벨 부여");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "SWEEPING_EDGE", 1));
                    bookMeta.setLore(lore);
                }
                book.setItemMeta(bookMeta);
                if(temp != 3) upgradeWindow.setItem(42, book);
                else upgradeWindow.setItem(42, new ItemStack(Material.BARRIER));
                lore.clear();

                //약탈
                bookMeta.getEnchants().keySet().forEach(bookMeta::removeEnchant);
                if(enchant_list.get(Enchantment.LOOT_BONUS_MOBS) != null) {
                    temp = enchant_list.get(Enchantment.LOOT_BONUS_MOBS);
                    bookMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, temp + 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "약탈 " + (temp + 1) + "레벨 강화");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "LOOT_BONUS_MOBS", temp + 1));
                    bookMeta.setLore(lore);
                } else {
                    temp = 0;
                    bookMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true);
                    bookMeta.setDisplayName(ChatColor.RED + "약탈 " + (1) + "레벨 부여");
                    lore.add("§f[비용] " + new upgrade().howPrice("Sword", "LOOT_BONUS_MOBS", 1));
                    bookMeta.setLore(lore);
                }
                book.setItemMeta(bookMeta);
                if(temp != 3) upgradeWindow.setItem(43, book);
                else upgradeWindow.setItem(43, new ItemStack(Material.BARRIER));
                lore.clear();
            }
            p.openInventory(upgradeWindow);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        String Type = p.getInventory().getItemInMainHand().getType().toString();

        int have_money = new inner_money().getMoney(p);

        if(p.hasMetadata("OpenMenu")) {
            if(p.getMetadata("OpenMenu").get(0).asString().equalsIgnoreCase("Upgrade")) {
                e.setCancelled(true);
                if(clickedItem != null) {
                    if(clickedItem.getItemMeta().getLore() != null) {
                        int price = Integer.parseInt(clickedItem.getItemMeta().getLore().get(0).replace("§f[비용] ", ""));
                        if(have_money >= price) {
                            String[] toLog = new String[2];
                            toLog[0] = "";
                            toLog[1] = Integer.toString(price);
                            new about_log().writePlayerLog(p, "DoUpgrade", toLog);

                            new inner_money().minusMoney(p, price);

                            ItemStack main_hand = p.getInventory().getItemInMainHand();
                            ItemStack onDisplay = e.getInventory().getItem(22);

                            Enchantment fromBook = null;
                            Integer Level = 0;

                            Map<Enchantment, Integer> enchantments = clickedItem.getEnchantments(); // 인챈트북에 있는 인챈트 목록 가져오기
                            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                                fromBook = entry.getKey();
                                Level = entry.getValue();
                            }

                            double random = new Random().nextFloat();
                            double temp = new upgrade().howPercentage("Sword", fromBook.getName(), Level);
                            p.sendMessage(random + " : " + temp);
                            //p.sendMessage(temp + " : " + fromBook.getName());
                            if(random < temp) {
                                p.sendMessage("강화에 성공하였습니다!");
                                main_hand.addEnchantment(fromBook, Level); // 메인 핸드 아이템에 인챈트 부여
                                onDisplay.addEnchantment(fromBook, Level);
                                p.closeInventory();
                            } else {
                                p.sendMessage("강화에 실패하였습니다...");
                            }
                        } else {
                            p.sendMessage("돈이 충분치 않습니다.");
                        }
                    }
                }
            }
        }
    }

    private boolean CanIn(Material type) {
        switch (type) {
            case WOODEN_SWORD:
            case WOODEN_PICKAXE:
            case WOODEN_AXE:
            case WOODEN_SHOVEL:
            case WOODEN_HOE:
            case STONE_SWORD:
            case STONE_PICKAXE:
            case STONE_AXE:
            case STONE_SHOVEL:
            case STONE_HOE:
            case IRON_SWORD:
            case IRON_PICKAXE:
            case IRON_AXE:
            case IRON_SHOVEL:
            case IRON_HOE:
            case GOLDEN_SWORD:
            case GOLDEN_PICKAXE:
            case GOLDEN_AXE:
            case GOLDEN_SHOVEL:
            case GOLDEN_HOE:
            case DIAMOND_SWORD:
            case DIAMOND_PICKAXE:
            case DIAMOND_AXE:
            case DIAMOND_SHOVEL:
            case DIAMOND_HOE:
            case NETHERITE_SWORD:
            case NETHERITE_PICKAXE:
            case NETHERITE_AXE:
            case NETHERITE_SHOVEL:
            case NETHERITE_HOE:

            case IRON_HELMET:
            case IRON_CHESTPLATE:
            case IRON_LEGGINGS:
            case IRON_BOOTS:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_BOOTS:
            case GOLDEN_HELMET:
            case GOLDEN_CHESTPLATE:
            case GOLDEN_LEGGINGS:
            case GOLDEN_BOOTS:
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
            case NETHERITE_HELMET:
            case NETHERITE_CHESTPLATE:
            case NETHERITE_LEGGINGS:
            case NETHERITE_BOOTS:
            case TURTLE_HELMET:

            case TRIDENT:
            case BOW:
            case CROSSBOW:

                return true;
            default:
                return false;
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();

        if(player.hasMetadata("OpenMenu")) {
            player.removeMetadata("OpenMenu", LongTerm.this_path());
        }
    }

}
