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
import menu.list.GuardUpgradeMenu;
import mobs.SmartMob;
import util.Util;

public class GuardChief extends SmartMob {

	public GuardChief(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Guard Chief", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzYyZjM2ODg4NGJjMzVmMTYyNjI0OTc0ZDZlYTgzNjg2MzJmMGU4MDlmZmMxNmNhNzYyZDE2ZTM3ZTI2ZGUifX19");

		this.setEquipment(head, new ItemStack(Material.DIAMOND_CHESTPLATE,1), new ItemStack(Material.CHAINMAIL_LEGGINGS),new ItemStack(Material.CHAINMAIL_BOOTS), new ItemStack(Material.DIAMOND_SWORD,1));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard())
			NPCSay(p, "Hello, partner! Hows your duty going?");
		else {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
					"ban " + p.getName() + " Free Cam trap.");
			return;
		}

		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_YES, 0.98F, 0.7F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				
				new GuardUpgradeMenu().openMenu(p);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.77F);

			}
		}, 20);
	}
}