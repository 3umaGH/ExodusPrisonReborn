package menu.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import menu.Menu;
import statistics.handlers.ServerStatistics;

public class InventoryListener implements Listener {

	@EventHandler
	public static void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Menu menu = Menu.getList().get(e.getInventory());

		if (menu == null)
			return;

		menu.onInventoryClick(p, e);
		p.playSound(p.getLocation(), Sound.CLICK, 0.98F, 1F);
		
		ServerStatistics.incrementKey("inventory_clicks", 1);

	}

	@EventHandler
	public static void onInventoryClose(InventoryCloseEvent e) {
		Menu menu = Menu.getList().get(e.getInventory());

		if (menu == null)
			return;

		Menu.getList().remove(menu);
	}

}
