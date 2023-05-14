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
import permission.PermissionShop;
import permission.Permissions.Permission;
import util.Util;

public class PermShopBlockB extends SmartMob {

	public PermShopBlockB(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "[B] Permissions Shop", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWE4Y2ExMWQ4YjZmYmZmZjYwNDQyMmViOTg4ZGRiZjkyZmZlYTEwMTA5ZTYyYmNkNjM1NzA3ZWIzN2E4NCJ9fX0=");

		this.setEquipment(head, new ItemStack(Material.IRON_CHESTPLATE, 1), new ItemStack(Material.IRON_LEGGINGS), null,
				new ItemStack(Material.PAPER));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard())
			NPCSay(p, "Hi! Mr. Officer. How can i help you?");
		else
			NPCSay(p, "Welcome to the Block B, " + p.getName() + "!");
		

		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_YES, 0.98F, 0.9F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

					new PermissionShop("Block B Permissions", Permission.CHICKEN_FARM, Permission.GRAVEL_MINE,
							Permission.CELL_BLOCK_B, Permission.COAL_MINE, Permission.SHEEP_FARM,
							Permission.SPIDER_FARM, Permission.GOLD_MINE, Permission.BLOCK_C)
									.openMenu(p);

				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.7F);

			}
		}, 20);

	}

}
