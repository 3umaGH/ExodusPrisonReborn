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
import exodusplayer.PlayerLoader;
import mobs.SmartMob;
import util.Util;

public class CorruptGuard extends SmartMob {

	public CorruptGuard(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Corrupt Guard", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBkYzQzOWU4MTkxNzEwNzY0NTU4NmMyMzg3MzRkODU3OTBlYWVmYTViMTI5MDM3MTc5NGQzNGE2ZjI2NGMifX19");

		this.setEquipment(head, new ItemStack(Material.CHAINMAIL_CHESTPLATE,1), new ItemStack(Material.CHAINMAIL_LEGGINGS),new ItemStack(Material.CHAINMAIL_BOOTS), new ItemStack(Material.IRON_SWORD,1));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard())
			NPCSay(p, "*covers his face and runs away*");
		else
			NPCSay(p, "Follow me, boy.");

		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.88F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				p.teleport(PlayerLoader.spawn);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.7F);

			}
		}, 40);

	}

}
