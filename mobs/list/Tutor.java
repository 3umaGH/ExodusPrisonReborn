package mobs.list;

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
import menu.list.TutorMenu;
import mobs.SmartMob;
import util.Util;

public class Tutor extends SmartMob {

	public Tutor(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Tutor", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTAzZjllZTVkOTRkY2Q1ZmRhZjZiMjg4NTVhNGMzYzI1YzY3OGExZDRiZTMwYjQyOTM5NGJkOWFkMzNhNDcyIn19fQ==");

		ItemStack robe = Util.createItem(Material.LEATHER_CHESTPLATE, 1, (short) 0, "");
		ItemStack leggings = Util.createItem(Material.LEATHER_LEGGINGS, 1, (short) 0, "");
		ItemStack boots = Util.createItem(Material.LEATHER_BOOTS, 1, (short) 0, "");

		Util.leatherSetColor(robe, Color.WHITE);
		Util.leatherSetColor(leggings, Color.WHITE);
		Util.leatherSetColor(boots, Color.WHITE);

		this.setEquipment(head, robe, leggings, boots,  new ItemStack(Material.BOOK,1));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if(eP.isGuard()) 
			NPCSay(p, "Hello, Officer. Hows your day been?");
		 else 
			NPCSay(p, "Hi. I've been in this prison for more than 30 years. Ask me anything!");
		
		
		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_YES, 0.98F, 0.4F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				
				new TutorMenu().openMenu(p);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.45F);

			}
		}, 20);
		
	}

}
