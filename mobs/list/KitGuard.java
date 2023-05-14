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
import menu.list.GuardMenu;
import mobs.SmartMob;
import util.Util;

public class KitGuard extends SmartMob {

	public KitGuard(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Guard", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjcxNTdjZmZiMDYwNjNiMzUyZGM2ODQ3OGY0NzZlN2QyMDJjM2JhNmU3Y2JmMjk3MjQxYmU4MTY4MTA3NGJmIn19fQ==");

		this.setEquipment(head, new ItemStack(Material.CHAINMAIL_CHESTPLATE,1), new ItemStack(Material.CHAINMAIL_LEGGINGS),new ItemStack(Material.CHAINMAIL_BOOTS), new ItemStack(Material.IRON_SWORD,1));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard())
			NPCSay(p, "Hi. Be safe.");
		else {
			NPCSay(p, "Hello, Prisoner. You need some help?");
		}

		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_YES, 0.98F, 0.8F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				
				new GuardMenu().openMenu(p);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.84F);

			}
		}, 20);
	}
}