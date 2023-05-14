package menu.list;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import exodusplayer.ExodusPlayer;
import menu.Menu;
import permission.Permissions.Permission;
import util.GUI;
import util.Util;

public class WarpMenu extends Menu {

	private HashMap<Integer, Permission> slots = new HashMap<Integer, Permission>();

	public WarpMenu() {
		super("Warp Menu", 6);

	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(p);

		int slot = 0;

		for (Permission perm : Permission.values()) {

			if (!perm.isTeleportableTo())
				continue;

			if (perm.getWarpSlot() != 0)
				slot = perm.getWarpSlot();

			if (eP.getPermissions().contains(perm)) {
				getInventory().setItem(slot,
						Util.createItem(perm.getIcon(), 1, (short) 0, "&a" + perm.getName(), "&a" + perm.getName(), "&7Click to teleport to this farm."));
				getInventory().setItem(slot + 9, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 5,
						"&a" + perm.getName(), "&7Click to teleport to this farm."));
			} else {
				getInventory().setItem(slot,
						Util.createItem(perm.getIcon(), 1, (short) 0, "&c" + perm.getName(), "&7You do not have this farm purchased!", " ", "&eUse &6/rankup&e to purchase farms."));
				getInventory().setItem(slot + 9,
						Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 14, "&c" + perm.getName(),
								"&7You do not have this farm purchased!", " ", "&eUse &6/rankup&e to purchase farms."));
			}

			slots.put(slot, perm);
			slots.put(slot + 9, perm);

			if (slot == 8)
				slot += 29;
			else
				slot += 1;
		}

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		e.setCancelled(true);

		ExodusPlayer eP = ExodusPlayer.get(p);

		if (slots.containsKey(e.getRawSlot())) {
			Permission perm = slots.get(e.getRawSlot());

			if (!eP.getPermissions().contains(perm)) {
				GUI.sendTitle(p, 10, 60, 10, "&4You don't have this farm!", "&eUse &6/rankup&e to purchase farms.");
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 0.7F);
				p.closeInventory();
			} else if (eP.isArrested()) {
				GUI.sendTitle(p, 10, 60, 10, "", "4You are arrested!");
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 0.7F);
				p.closeInventory();
			} else {
				p.teleport(perm.getLocation());
			}

		}

	}
}
