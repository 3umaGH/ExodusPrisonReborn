package mobs.list;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import mobs.SmartMob;
import util.Util;

public class Prisoner_64 extends SmartMob {

	HashMap<Player, Integer> steps = new HashMap<Player, Integer>();

	public Prisoner_64(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Prisoner #64", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE3MjUyYzljMTU2YmFiNTU2ZDIyNjhiZTZiMjdlZDJmNTRmY2RlYWZjMmNjNDZlZTU5NDc2OTVlZWY4ZWZmIn19fQ==");

		ItemStack robe = Util.createItem(Material.LEATHER_CHESTPLATE, 1, (short) 0, "");
		ItemStack leggings = Util.createItem(Material.LEATHER_LEGGINGS, 1, (short) 0, "");
		ItemStack boots = Util.createItem(Material.LEATHER_BOOTS, 1, (short) 0, "");

		Util.leatherSetColor(robe, Color.ORANGE);
		Util.leatherSetColor(leggings, Color.ORANGE);
		Util.leatherSetColor(boots, Color.ORANGE);

		this.setEquipment(head, robe, leggings, boots, new ItemStack(Material.STICK));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {
		
		if (eP.isGuard()) {
			NPCSay(p, "...");
			return;
		}
			

		if (steps.containsKey(p)) {
			steps.put(p, steps.get(p) + 1);
		} else {
			steps.put(p, 0);
		}

		switch (steps.get(p)) {

		case 0:
			NPCSay(p, "Mind your own business!");

			p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_NO, 0.98F, 0.8F);
			break;
		case 1:
			NPCSay(p, "Are you dumb, kid? Get out of here!");

			p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_NO, 0.98F, 0.6F);
			break;

		case 2:
			NPCSay(p, "Fuck off!");

			p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_DEATH, 0.98F, 0.6F);
			p.damage(8.0);

			steps.put(p, 1);
			return;

		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				steps.remove(p);

			}
		}, 200L);

	}

}
