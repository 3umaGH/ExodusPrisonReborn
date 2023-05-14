package shop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

import shop.Shop;

public class ShopListener implements Listener {
	
	@EventHandler
	public static void onInventoryClick(InventoryClickEvent e) {
		Shop shop = Shop.getShops().get(e.getWhoClicked().getOpenInventory().getTitle());
		
		
		if(shop != null) {
			Shop.shopClick(shop, e.getRawSlot(), e);
		}

	}
	


}
