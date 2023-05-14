package items.list.guard;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import items.Item;
import jail.handlers.JailHandler;
import menu.list.ArrestMenu;
import sound.CustomSound;
import sound.CustomSound.SoundType;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class ArrestStick extends Item {
	private static HashMap<Player, Integer> hits = new HashMap<Player, Integer>();

	private int level;

	public ArrestStick(int level) {
		super("Arrest Baton [LV. " + level + "]", Material.INK_SACK);

		this.level = level;

		this.setData(13);
		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&7The most important tool for a guard.", 60));
		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&6&lLeft Click - Push", 60));
		this.addCustomLore(Util.center("&6&lRight Click - Arrest", 60));
		
		this.setLegal(LegalityType.GuardsOnly);
		this.setPriority(false);
		this.setDropOnDeath(false);

		compile();

		switch (level) {

		case 1:
			getItemStack().addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			break;
		case 2:
			getItemStack().addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			break;
		case 3:
			getItemStack().addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			break;
		case 4:
			getItemStack().addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			break;
		case 5:
			getItemStack().addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
			break;

		}

	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {

	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		// todo

	}

	@Override
	public void onUseOnEntity(Player p, PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player clicked = (Player) e.getRightClicked();

			if (ExodusPlayer.get(p).isGuard() && !ExodusPlayer.get(clicked).isGuard()) {
				addHits(p, clicked);
			}
		}
	}

	private void arrestPlayer(Player guard, Player p) {
		hits.remove(p);

		ExodusPlayer.get(p).arrestPlayer(JailHandler.ARREST_TIME);
		guard.getLocation().getWorld().playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
		
		GUI.sendTitle(guard, 0, 50, 5, "&aArrested" + ".", "");
		ServerStatistics.incrementKey("arrests_by_guards", 1);

	}

	private void addHits(Player guard, Player p) {
		ExodusPlayer victim = ExodusPlayer.get(p);
		
		if(victim.isArrested()){
			new ArrestMenu(victim).openMenu(guard);
			return;
		}
		
		if (hits.containsKey(p))
			hits.put(p, hits.get(p) + 1);
		else {
			hits.put(p, 1);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				public void run() {

					if(hits.containsKey(p))
						hits.remove(p);

				}
			}, 1200L);
			
		}
	
		p.closeInventory();
		
		guard.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
		CustomSound.playSound(SoundType.BATON_HIT, p.getLocation());
		
		GUI.sendTitle(guard, 0, 20, 0, "§cArresting...", getHitsGui(hits.get(p)));
		GUI.sendTitle(p, 0, 40, 5, "§cYou are getting arrested!", getHitsGui(hits.get(p)));
		
		if (hits.get(p) >= hitsRequiredFor()) {
			arrestPlayer(guard, p);
			
			ServerStatistics.incrementKey("total_arrests", 1);
			victim.getStats().incrementKey("times_arrested", 1);
			ExodusPlayer.get(guard).getStats().incrementKey("arrested_prisoners", 1);
		}
	
		
		ServerStatistics.incrementKey("arrest_baton_hits", 1);
		victim.getStats().incrementKey("arrest_baton_hits", 1);
		ExodusPlayer.get(guard).getStats().incrementKey("arrest_baton_hit_others", 1);

	}

	private int hitsRequiredFor() {

		switch (level) {
		case 1:
			return 10;
		case 2:
			return 8;
		case 3:
			return 6;
		case 4:
			return 4;
		case 5:
			return 3;
		default:
			return 20;
		}
	}

	private String getHitsGui(int hits) {
		int percent = 10 * hits / hitsRequiredFor();
		String bar = "§c[||||||||||||||||||||]";

		switch (percent) {
		case 1:
			bar = "§a[||§c||||||||||||||||||]";
			break;
		case 2:
			bar = "§a[||||§c||||||||||||||||]";
			break;
		case 3:
			bar = "§a[||||||§c||||||||||||||]";
			break;
		case 4:
			bar = "§a[||||||||§c||||||||||||]";
			break;
		case 5:
			bar = "§a[||||||||||§c||||||||||]";
			break;
		case 6:
			bar = "§a[||||||||||||§c||||||||]";
			break;
		case 7:
			bar = "§a[||||||||||||||§c||||||]";
			break;
		case 8:
			bar = "§a[||||||||||||||||§c||||]";
			break;
		case 9:
			bar = "§a[||||||||||||||||||§c||]";
			break;
		case 10:
			bar = "§a[||||||||||||||||||||]";
			break;
		}

		return bar;

	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub
		
	}

}
