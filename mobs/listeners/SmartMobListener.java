package mobs.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import mobs.SmartMob;
import statistics.handlers.ServerStatistics;

public class SmartMobListener implements Listener {
	
	@EventHandler
	public static void playerInteract(PlayerInteractEntityEvent e) {
		Entity rightClicked = e.getRightClicked();
		final Player p = e.getPlayer();
		
		if(e.isCancelled())
			return;

		if (SmartMob.mobList_get(rightClicked) == null)
			return;

		if (SmartMob.cooldown_list.contains(p)) {
			e.setCancelled(true);
			return;
		}

		e.setCancelled(true);

		SmartMob.mobList_get(rightClicked).onClick(p, ExodusPlayer.get(p));

		SmartMob.cooldown_list.add(p);
		
		ServerStatistics.incrementKey("smartmobs_times_clicked",1);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			public void run() {
				SmartMob.cooldown_list.remove(p);
			}
		}, SmartMob.delay);
	}

	@EventHandler
	public static void entityDamageEntity(EntityDamageByEntityEvent e) {

		if (SmartMob.mobList_get(e.getDamager()) != null && !SmartMob.mobList_get(e.getDamager()).canDamage())
			e.setCancelled(true);

		if (SmartMob.mobList_get(e.getEntity()) != null && SmartMob.mobList_get(e.getEntity()).invincible())
			e.setCancelled(true);

	}

	@EventHandler
	public static void entityCombust(EntityCombustEvent e) {
		if (SmartMob.mobList_get(e.getEntity()) != null)
			e.setCancelled(true);
	}

}
