package com.longterm.events;

import com.longterm.LongTerm;
import com.longterm.customEntity.Keys;
import com.longterm.innerFunc.about_log;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import com.longterm.shopInventory.shopWindow;
import com.longterm.innerFunc.inner_money;

import java.util.Random;

public class shop_click implements Listener {
    @EventHandler
    public void onPlayerInteractShop(PlayerInteractAtEntityEvent e) {
        Entity clickedEntity = e.getRightClicked();
        if(clickedEntity.getType() == EntityType.VILLAGER && clickedEntity.getCustomName() != null) {
            //이름이 상점일 경우
            if(clickedEntity.getPersistentDataContainer().has(Keys.Is_Material, PersistentDataType.BYTE)) {
                if(clickedEntity.getPersistentDataContainer().get(Keys.Is_Material, PersistentDataType.BYTE) == (byte) 1) {
                    new shopWindow().onPlayerOpen(e.getPlayer(), "Material");
                    e.getPlayer().setMetadata("OpenMenu", new FixedMetadataValue(LongTerm.this_path(), "Material"));
                }
            } else if(clickedEntity.getPersistentDataContainer().has(Keys.Is_Equipment, PersistentDataType.BYTE)){
                if(clickedEntity.getPersistentDataContainer().get(Keys.Is_Equipment, PersistentDataType.BYTE) == (byte) 1) {
                    new shopWindow().onPlayerOpen(e.getPlayer(), "Equipment");
                    e.getPlayer().setMetadata("OpenMenu", new FixedMetadataValue(LongTerm.this_path(), "Equipment"));
                }
            }
        } else if(clickedEntity.getType() == EntityType.VILLAGER) {
            e.getPlayer().closeInventory();
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.VILLAGER)
            return;
        Villager villager = (Villager) event.getRightClicked();
        event.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if(p.hasMetadata("OpenMenu")) {
            if(p.getMetadata("OpenMenu").get(0).asString().equalsIgnoreCase("Material")) {
                e.setCancelled(true);
                if((e.getAction().equals(InventoryAction.PICKUP_ALL) || e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY))) {
                    if(e.getClickedInventory() != null) {
                        if(!e.getClickedInventory().equals(e.getWhoClicked().getInventory())) {
                            if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.PAPER)) {
                                String BuyOrSell = e.getCurrentItem().getItemMeta().getDisplayName().toString().substring(3,5);
                                String material = e.getCurrentItem().getType().toString();
                                int price = Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(0).replace("원","").substring(2));
                                int have_money = new inner_money().getMoney(p);

                                if(!e.isShiftClick()) { //단일 매매
                                    if(BuyOrSell.equalsIgnoreCase("구매")) {
                                        if(p.getInventory().firstEmpty() != -1) {
                                            if(have_money >= price) {
                                                //구매
                                                String[] toLog = new String[3];
                                                toLog[0] = material;
                                                toLog[1] = Integer.toString(price);
                                                toLog[2] = "1";
                                                new about_log().writePlayerLog(p, "BuyMaterial", toLog);

                                                new inner_money().minusMoney(p, price);
                                                p.getInventory().addItem(new ItemStack(Material.getMaterial(material), 1));
                                            } else {
                                                p.sendMessage("돈이 충분치 않습니다.");
                                            }
                                        } else {
                                            p.sendMessage("인벤토리 한 칸을 비워주세요.");
                                        }
                                    } else if(BuyOrSell.equalsIgnoreCase("판매")) {
                                        for (ItemStack item : p.getInventory().getContents()) {
                                            if (item != null && item.isSimilar(new ItemStack(Material.getMaterial(material)))) {
                                                // 특정 아이템이 인벤토리에 존재함
                                                item.setAmount(item.getAmount() - 1);
                                                String[] toLog = new String[3];
                                                toLog[0] = material;
                                                toLog[1] = Integer.toString(price);
                                                toLog[2] = "1";
                                                new about_log().writePlayerLog(p, "SellMaterial", toLog);
                                                new inner_money().addMoney(p, price);
                                                break;
                                            }
                                        }
                                    }
                                } else if(e.isShiftClick()) { //복수 매매
                                    if(BuyOrSell.equalsIgnoreCase("구매")) {
                                        if(have_money >= price) {
                                            boolean canBuy = false;
                                            int i = 0;
                                            for(i = 64; i > 0; i--) {
                                                if(have_money >= price * i) {
                                                    canBuy = true;
                                                    break;
                                                }
                                            }
                                            if(canBuy) {
                                                String[] toLog = new String[3];
                                                toLog[0] = material;
                                                toLog[1] = Integer.toString(price);
                                                toLog[2] = Integer.toString(i);
                                                new about_log().writePlayerLog(p, "BuyMaterial", toLog);

                                                new inner_money().minusMoney(p, price * i);
                                                p.getInventory().addItem(new ItemStack(Material.getMaterial(material), i));
                                            } else if(!canBuy) {
                                                p.sendMessage("돈이 충분치 않습니다.");
                                            }
                                        } else {
                                            p.sendMessage("돈이 충분치 않습니다.");
                                        }
                                    } else if(BuyOrSell.equalsIgnoreCase("판매")) {
                                        int to_sell = 64;
                                        for (ItemStack item : p.getInventory().getContents()) {
                                            if (item != null && item.isSimilar(new ItemStack(Material.getMaterial(material)))) {
                                                if(to_sell == 0) {
                                                    break;
                                                } else if(item.getAmount() <= to_sell) { //가지고 있는 아이템이 팔 아이템의 개수보다 작을 때
                                                    to_sell -= item.getAmount();
                                                    item.setAmount(0);
                                                } else if(item.getAmount() > to_sell) {
                                                    item.setAmount(item.getAmount() - to_sell);
                                                    to_sell = 0;
                                                    break;
                                                }
                                            }
                                        }

                                        String[] toLog = new String[3];
                                        toLog[0] = material;
                                        toLog[1] = Integer.toString(price);
                                        toLog[2] = Integer.toString(64 - to_sell);
                                        new about_log().writePlayerLog(p, "SellMaterial", toLog);

                                        new inner_money().addMoney(p, (64 - to_sell) * price);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if(p.getMetadata("OpenMenu").get(0).asString().equalsIgnoreCase("Equipment")) {
                e.setCancelled(true);
                if((e.getAction().equals(InventoryAction.PICKUP_ALL) || e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY))) {
                    if(e.getClickedInventory() != null) {
                        if(!e.getClickedInventory().equals(e.getWhoClicked().getInventory())) {
                            if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.PAPER)) {
                                String BuyOrSell = e.getCurrentItem().getItemMeta().getDisplayName().toString().substring(3,5);
                                String material = e.getCurrentItem().getType().toString();
                                int price = Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(0).replace("원","").substring(2));
                                boolean isRandom = e.getCurrentItem().getItemMeta().getLore().get(1).substring(3,5).equalsIgnoreCase("랜덤");
                                int have_money = new inner_money().getMoney(p);

                                if(!e.isShiftClick()) { //단일 매매
                                    if(BuyOrSell.equalsIgnoreCase("구매")) {
                                        if(p.getInventory().firstEmpty() != -1) {
                                            if(have_money >= price) {

                                                //구매
                                                String[] toLog = new String[3];
                                                toLog[0] = material;
                                                toLog[1] = Integer.toString(price);
                                                toLog[2] = "1";
                                                new about_log().writePlayerLog(p, "BuyMaterial", toLog);

                                                new inner_money().minusMoney(p, price);

                                                ItemStack toGive = new ItemStack(Material.getMaterial(material));
                                                ItemMeta toGiveMeta = toGive.getItemMeta();
                                                if(isRandom) {
                                                    double random = Math.random();
                                                    if(material.equalsIgnoreCase("DIAMOND_SWORD") || material.equalsIgnoreCase("DIAMOND_AXE")) {
                                                        if(random < 0.3) {
                                                            toGiveMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                                                        } else if(random >= 0.3 && random < 0.6) {
                                                            toGiveMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, true);
                                                        } else {
                                                            toGiveMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
                                                        }
                                                    } else if (material.equalsIgnoreCase("DIAMOND_HELMET") ||
                                                            material.equalsIgnoreCase("DIAMOND_CHESTPLATE") ||
                                                            material.equalsIgnoreCase("DIAMOND_LEGGINGS") ||
                                                            material.equalsIgnoreCase("DIAMOND_BOOTS")) {
                                                        if(random < 0.25) {
                                                            toGiveMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                                                        } else if (random >= 0.25 && random < 0.5) {
                                                            toGiveMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
                                                        } else if (random >= 0.5 && random < 0.75) {
                                                            toGiveMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
                                                        } else {
                                                            toGiveMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
                                                        }
                                                    }
                                                }
                                                toGive.setItemMeta(toGiveMeta);

                                                p.getInventory().addItem(toGive);
                                            } else {
                                                p.sendMessage("돈이 충분치 않습니다.");
                                            }
                                        } else {
                                            p.sendMessage("인벤토리 한 칸을 비워주세요.");
                                        }
                                    } else if(BuyOrSell.equalsIgnoreCase("판매")) {
                                        for (ItemStack item : p.getInventory().getContents()) {
                                            if (item != null && item.isSimilar(new ItemStack(Material.getMaterial(material)))) {
                                                // 특정 아이템이 인벤토리에 존재함
                                                item.setAmount(item.getAmount() - 1);
                                                String[] toLog = new String[3];
                                                toLog[0] = material;
                                                toLog[1] = Integer.toString(price);
                                                toLog[2] = "1";
                                                new about_log().writePlayerLog(p, "SellMaterial", toLog);
                                                new inner_money().addMoney(p, price);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
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
