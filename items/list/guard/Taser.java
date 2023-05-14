package items.list.guard;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import items.Item;
import sound.CustomSound;
import sound.CustomSound.SoundType;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class Taser extends Item {

	private int level;
	private int failChance;
	private int duration; // seconds

	public Taser(int level) {
		super("Taser [LV. " + level + "]", Material.INK_SACK);

		this.level = level;

		switch (level) {
		case 1:
			failChance = 30;
			duration = 3;
			break;

		case 2:
			failChance = 20;
			duration = 5;
			break;

		}
		
		this.setData(10);
		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&7Immobilizes Prisoners.", 60));
		this.addCustomLore(Util.center("&7Miss chance: " + failChance + "%", 60));
		this.addCustomLore(Util.center("&7Duration: " + duration + " seconds", 60));
		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&6&lRight Click - Shock", 60));

		this.setCooldown(400);
		this.setLegal(LegalityType.GuardsOnly);
		this.setPriority(false);
		this.setDropOnDeath(false);

		compile();

	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		e.setCancelled(true);
	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		// todo

	}

	@Override
	public void onUseOnEntity(Player p, PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player clicked = (Player) e.getRightClicked();

			if (this.isCooleddown(p)) {
				GUI.sendTitle(p, 0, 50, 10, "", "§cIt takes a while to reload!");
				return;
			}

			if (ExodusPlayer.get(p).isGuard() && !ExodusPlayer.get(clicked).isGuard()) {

				activateCooldown(p);

				if (Util.countChance(failChance)) {
					GUI.sendTitle(p, 0, 50, 10, "", "§cFailed to hit!");
					p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
					return;
				}

				clicked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration * 20, 10));
				clicked.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration * 20, -5));

				clicked.getLocation().getWorld().playSound(clicked.getLocation(), Sound.BLAZE_BREATH, 1F, 10F);
				CustomSound.playSound(SoundType.TASER, clicked.getLocation());
				
				GUI.sendTitle(clicked, 0, 100, 10, "§bYou have been tased!", "§bby " + p.getName());
				GUI.sendTitle(p, 0, 50, 10, "", "§bYou have tased " + clicked.getName());

				new BukkitRunnable() {
					public void run() {

						if (!p.isOnline()) {
							cancel();
							return;

						}

						if (clicked.hasPotionEffect(PotionEffectType.SLOW)) {

							org.bukkit.Location oldLoc = clicked.getLocation();
							oldLoc.setPitch((float) Util.generateRandomBetween(-20, 20));
							
							int newYaw = (int) oldLoc.getYaw() + Util.generateRandomBetween(-20, 20);
							
							if(newYaw > 180)
								newYaw = 180;
							else if(newYaw < -180)
								newYaw = -180;
							
							oldLoc.setYaw((float) newYaw);

							clicked.teleport(oldLoc);
							clicked.getWorld().playSound(clicked.getLocation(), Sound.CLICK, 1F, 3F);
							
						} else {
							cancel();
						}

					}

				}.runTaskTimer(Main.getPlugin(), 0L, 2L);

				ServerStatistics.incrementKey("pepper_sprays", 1);
				ExodusPlayer.get(clicked).getStats().incrementKey("got_pepper_sprayed", 1);
				ExodusPlayer.get(p).getStats().incrementKey("pepper_sprays", 1);

			}

		}
	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub
		
	}

}
