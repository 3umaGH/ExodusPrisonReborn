package menu.list;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import bukkit.exodusprison.spurkle.Main;
import cells.Cell;
import exodusplayer.PlayerLoader;
import menu.Menu;
import util.GUI;
import util.Util;

public class CellSettingsMenu extends Menu {

	private Cell c;
	private ProtectedRegion r;

	private boolean locked;

	public CellSettingsMenu(Cell c) {
		super("Cell " + c.getId() + " Settings", 1);

		this.setUpdating(true);

		this.c = c;
		this.r = Main.getWGRegionManager().getRegion(c.getRegion());
	}

	@Override
	public void loadIcons(Player p) {

		if (r.getFlag(DefaultFlag.ENTRY).equals(State.ALLOW)) {
			getInventory().setItem(0, Util.createItem(Material.IRON_DOOR, 1, (short) 0, "&fEntrance: &aALLOWED",
					"&7Any player can enter your cell."));
			locked = false;
		} else {
			getInventory().setItem(0, Util.createItem(Material.BARRIER, 1, (short) 0, "&fEntrance: &cNOT ALLOWED",
					"&7Nobody except you can enter your cell."));
			locked = true;
		}

		getInventory().setItem(1, Util.createItem(Material.BARRIER, 1, (short) 0, "&cKick everyone from your cell.",
				"&7Teleports everyone out of your cell except you."));

		String info = "";

		info = info + "&7Cell ID: &6" + c.getId();
		info = info + "/n&7Owner: &6" + c.getOwnerName();
		info = info + "/n&7Daily rent price: &6$" + c.getPrice();
		if (c.isPermanent())
			info = info + "/n&7Expires: &6Never";
		else
			info = info + "/n&7Expires: &6" + Util.formatTimeMillis(c.getExpiry(), "MM/dd HH:mm");

		getInventory().setItem(8, Util.createItem(Material.SIGN, 1, (short) 0, "&fInformation",
				info));

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {

		e.setCancelled(true);

		if (e.getRawSlot() == 0) {
			if (locked) {
				r.setFlag(DefaultFlag.ENTRY, State.ALLOW);
			} else {
				r.setFlag(DefaultFlag.ENTRY, State.DENY);
				kickAll();
			}
		}

		try {
			Main.getWGRegionManager().save();
		} catch (StorageException e1) {
			e1.printStackTrace();
		}

		if (e.getRawSlot() == 1) {
			kickAll();
		}

	}

	private void kickAll() {
		for (Player p : Bukkit.getOnlinePlayers())
			if (Util.inRegion(r, p.getLocation()) && !p.getName().equals(c.getOwnerName())) {
				GUI.sendTitle(p, 10, 60, 10, "&4Kicked", "&fYou have been kicked by the cell owner.");
				p.teleport(PlayerLoader.spawn);
			}
	}
}
