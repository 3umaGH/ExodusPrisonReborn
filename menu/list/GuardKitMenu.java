package menu.list;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import items.Item;
import menu.Menu;

public class GuardKitMenu extends Menu {

	public GuardKitMenu() {
		super("Kit Menu", 1);
		
		this.setUpdating(false);
	}

	@Override
	public void loadIcons(Player p) {
		
		getInventory().setItem(1,Item.woodPickaxe.getNewItemStack(1));
		getInventory().setItem(2,Item.woodPickaxe.getNewItemStack(1));
		getInventory().setItem(3,Item.woodPickaxe.getNewItemStack(1));
		getInventory().setItem(4,Item.smallMineBomb.getNewItemStack(3));
		getInventory().setItem(5,Item.steak.getNewItemStack(8));
		getInventory().setItem(6,Item.torch.getNewItemStack(16));
		
	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		
	}

}
