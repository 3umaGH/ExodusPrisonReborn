package items.list.other;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import items.Item;
import util.GUI;

public class GuardTracker extends Item {

	public GuardTracker() {
		super("Guard Tracker", Material.BEACON);

		this.setPriority(false);
		this.addCustomLore("&7Tells you if there is a guard within 50 blocks.");
		this.addCustomLore("&7Right Click to track.");
		this.setLegal(LegalityType.Illegal);
		this.setCooldown(200);

		compile();
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {

		e.setCancelled(true);

		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			if (this.isCooleddown(p))
				return;

			this.activateCooldown(p);

			new BukkitRunnable() {
				public void run() {

					p.getWorld().playSound(p.getLocation(), Sound.NOTE_PLING, 0.98F, 3F);
					GUI.sendTitle(p, 0, 60, 5, "", "&4Searching...");
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						public void run() {
							cancel();
						}
					}, 30);

				}

			}.runTaskTimer(Main.getPlugin(), 0L, 3L);

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				public void run() {

					List<Entity> near = p.getNearbyEntities(50.0D, 50.0D, 50.0D);

					for (Entity entity : near) {
						if (entity instanceof Player) {
							Player nearPlayer = (Player) entity;

							if (ExodusPlayer.isLoaded(nearPlayer) && ExodusPlayer.get(nearPlayer).isGuard()) {
								GUI.sendTitle(p, 10, 60, 10, "",
										"&aClosest guard " + nearPlayer.getName() + " is &6"
												+ Math.round(p.getLocation().distance(nearPlayer.getLocation()))
												+ "&a blocks away from you.");
								p.getWorld().playSound(p.getLocation(), Sound.NOTE_PLING, 0.98F, 5F);
								return;
							}
						}
					}

					GUI.sendTitle(p, 10, 60, 10, "", "&4No guards within 50 blocks.");
					p.getWorld().playSound(p.getLocation(), Sound.NOTE_PLING, 0.98F, 0.5F);
					
				}
			}, 35);

		}

	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUseOnEntity(Player p, PlayerInteractEntityEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub

	}

}
