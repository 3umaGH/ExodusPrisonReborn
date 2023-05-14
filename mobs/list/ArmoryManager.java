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
import menu.list.ArmoryMenu;
import mobs.SmartMob;
import util.Util;

public class ArmoryManager extends SmartMob {

	public ArmoryManager(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Armory Manager", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmFlMDlmOGVkMjk3YjRjNDVkNDQ3MTgzYzlhMTE3NWFmMjg3NWM0YTY0ZDM1ZDRjZGU2NDNiYzViN2FhN2IifX19");

		this.setEquipment(head, new ItemStack(Material.IRON_CHESTPLATE, 1), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS),
				null);

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard()) {
			NPCSay(p,"Hello, partner!");
		} else {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
					"ban " + p.getName() + " Free Cam trap.");
			return;
		}

		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.9F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				new ArmoryMenu().openMenu(p);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.7F);

			}
		}, 20);

	}

}
