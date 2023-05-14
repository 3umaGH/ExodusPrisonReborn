package mobs.list;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import menu.list.EscortMenu;
import mobs.SmartMob;
import util.Util;

public class EscortPrisoner extends SmartMob {

	private List<Location> tpLocations = new ArrayList<Location>();
	private boolean busy = false;
	
	public EscortPrisoner(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Gigi Smuggla Crackaface", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjE5YmJmYzdiZWMyM2MyOTM5NWI1Yjk4OGQ5MzliMmE2OGIyYmEyMDdjMzU2NmQzMjJmNDg2OTM2ZWI5In19fQ==");

		this.setEquipment(head, new ItemStack(Material.IRON_CHESTPLATE, 1),
				new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS),
				new ItemStack(Material.IRON_SWORD, 1));

		for (int i = 0; i < 105; i++)
			tpLocations.add(new Location(Main.getWorld(), -150 + i, 81, 0.5));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if(busy) {
			NPCSay(p, "Don't you see that I am busy?");
			p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_NO, 0.98F, 0.88F);
			return;
		}
			
		if (eP.isGuard()) {
			NPCSay(p, "I can't help you, buddy.");
			p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_NO, 0.98F, 0.88F);
			//return;
		}
		else {
			NPCSay(p, "Hello, Prisoner. You need some help?");
			new EscortMenu(this,eP).openMenu(p);
			p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.88F);
		}

		
	}

	public List<Location> getTpLocations() {
		return tpLocations;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}
	
	
}