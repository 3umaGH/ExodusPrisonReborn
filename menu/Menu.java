package menu;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;

public abstract class Menu {
	private static HashMap<Inventory, Menu> list = new HashMap<Inventory, Menu>();

	private Inventory inv;
	private boolean updating;

	public Menu(String name, int slotRows) {
		this.inv = Bukkit.createInventory(null, slotRows * 9, name);
		this.updating = false;

		list.put(inv, this);
	}

	public abstract void loadIcons(Player p);

	public abstract void onInventoryClick(Player p, InventoryClickEvent e);

	public Inventory getInventory() {
		return this.inv;
	}

	public boolean isUpdating() {
		return updating;
	}

	public void setUpdating(boolean updating) {
		this.updating = updating;
	}

	public void openMenu(Player p) {

		if (isUpdating()) {
			new BukkitRunnable() {
				public void run() {

					if (inv.getViewers().size() <= 0) {
						cancel();
						return;
					}

					loadIcons(p);

				}

			}.runTaskTimer(Main.getPlugin(), 0L, 10L);
		} else
			loadIcons(p);

		p.openInventory(inv);

	}

	public static HashMap<Inventory, Menu> getList() {
		return list;
	}

}
