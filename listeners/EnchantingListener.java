package listeners;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public class EnchantingListener implements Listener {

	@EventHandler
	public void fillEnchantingTable(InventoryOpenEvent e) {
		if (e.getInventory() instanceof EnchantingInventory) {
			EnchantingInventory inv = (EnchantingInventory) e.getInventory();

			Dye d = new Dye();
			d.setColor(DyeColor.BLUE);
			ItemStack i = d.toItemStack();

			i.setAmount(30);
			inv.setItem(1, i);
		}
	}

	@EventHandler
	public void cleanEnchantingTable(InventoryCloseEvent e) {
		if (e.getInventory() instanceof EnchantingInventory) {
			EnchantingInventory inv = (EnchantingInventory) e.getInventory();
			try {
				inv.setItem(1, null);
			} catch (Exception ex) {

			}
		}
	}

	@EventHandler
	public void onLapisClick(InventoryClickEvent e) {
		if (e.getInventory() instanceof EnchantingInventory) {

			if (e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.INK_SACK))
				e.setCancelled(true);

		}
	}

}
