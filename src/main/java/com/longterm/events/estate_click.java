package com.longterm.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import com.longterm.innerFunc.inner_estate;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;

public class estate_click implements Listener {
    @EventHandler
    //누구 땅인지 확인하게 하기
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(!e.getAction().name().equalsIgnoreCase("RIGHT_CLICK_AIR") && !e.getAction().name().equalsIgnoreCase("LEFT_CLICK_AIR")) {
            String estate_owner = new inner_estate().estateWho(e.getClickedBlock().getChunk(), e.getClickedBlock().getY());
            //e.getPlayer().sendMessage(e.getPlayer().getInventory().getItemInMainHand().toString());
            if(!estate_owner.equalsIgnoreCase(e.getPlayer().getName()) && !estate_owner.equalsIgnoreCase("None")) {

                if(e.getAction().name().equalsIgnoreCase("PHYSICAL") || e.getAction().name().equalsIgnoreCase("RIGHT_CLICK_BLOCK")) {
                    if(e.getPlayer().getInventory().getItemInMainHand().toString().contains("AIR")) {
                        e.setCancelled(true);
                    } else if(e.getClickedBlock().getType().toString().contains("DOOR") || e.getItem().getType().toString().contains("BUCKET") || e.getItem().getType().toString().contains("END_CRYSTAL") || e.getItem().getType().toString().contains("ENDER_PEARL")) {
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c권한이 없습니다."));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        String who = new inner_estate().estateWho(e.getBlock().getChunk(), e.getBlock().getY());
        if(p.getName().equalsIgnoreCase(who) || who.equalsIgnoreCase("None")) {

        }
        else {e.setCancelled(true);}
    }

    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        String who = new inner_estate().estateWho(e.getBlock().getChunk(), e.getBlock().getY());

        if(p.getName().equalsIgnoreCase(who) || who.equalsIgnoreCase("None")) {
            if(e.getBlock().getType().equals(Material.RESPAWN_ANCHOR) && e.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
                e.setCancelled(true);
            }
        }
        else {e.setCancelled(true);}
    }

    //TNT 폭발 방지
    @EventHandler
    public void onTNTBlowup(TNTPrimeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerEnterChunk(PlayerMoveEvent e) {
        Chunk currentChunk = e.getTo().getChunk();
        Chunk beforeChunk = e.getFrom().getChunk();
        int y = (int) e.getTo().getY();
        int before_y = (int) e.getFrom().getY();

        String estate_owner = new inner_estate().estateWho(currentChunk, y);
        String estate_before = new inner_estate().estateWho(beforeChunk, before_y);

        if(!estate_before.equalsIgnoreCase(estate_owner)) {
            if(!estate_owner.equalsIgnoreCase(e.getPlayer().getName()) && !estate_owner.equalsIgnoreCase("None")) {
                if(!e.getFrom().getChunk().equals(e.getTo().getChunk())) {
                    e.getPlayer().sendTitle(estate_owner, "에 입장하셨습니다.", 20, 10, 20);
                }
            }
        }
    }

    public void removeTNTCartRecipe() {
        Iterator<Recipe> recipeIterator = Bukkit.getServer().recipeIterator();
        while(recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            ItemStack result = recipe.getResult();
            if(result != null && result.getType() == Material.TNT_MINECART) {
                recipeIterator.remove();
            }
        }
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        if(e.getBlock().getState() instanceof Dispenser) {
            if(e.getItem().getType() == Material.TNT) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMinecartSpawn(VehicleCreateEvent e) {
        if(e.getVehicle().getType() == EntityType.MINECART_TNT) {
            e.getVehicle().remove();
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        World world = e.getEntity().getWorld();
        if(e.getEntity() instanceof EnderCrystal) {
            if(world.getName().equalsIgnoreCase("world")) {
                e.setCancelled(true);
            }
        }
    }
}
