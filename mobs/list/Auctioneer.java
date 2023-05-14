package mobs.list;

import org.bukkit.Bukkit;
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

public class Auctioneer extends SmartMob {

	public Auctioneer(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Auctioneer", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RhMGJjYzFiMmM4ODlmZjVhNGFiODM5NzhlMzQ4MmU2MmI1NDcyNmFhYTI0ZmQ1OTk2MjZlMmY1MjExZWUifX19");

		this.setEquipment(head, null, null, null, new ItemStack(Material.GOLD_INGOT));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard())
			NPCSay(p, "Uhh.... Hi?... Well, take a look I guess...");
		else if (eP.getAgeHours() < 24)
			NPCSay(p, "Hi. I'm Quentin. I am an auctioneer.");
		else
			NPCSay(p, "Hello! Want to see what others are selling?");

		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.9F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				p.performCommand("ah");
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.7F);

			}
		}, 20);

	}

}
