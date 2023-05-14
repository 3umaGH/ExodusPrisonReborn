package menu.list;

import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import exodusplayer.ExodusPlayer;
import items.Item;
import items.ItemUpgrade;
import menu.Menu;

public class ArmoryMenu extends Menu {

	public ArmoryMenu() {
		super("Armory", 2);

	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(p);
		
		int slot = 0;
		for (Entry<ItemUpgrade, Integer> entry : eP.getUpgrades().getItemUpgrades().entrySet()) {
			slot = entry.getKey().getSlot();
			
			if (!(entry.getValue() < 0)) {
				getInventory().setItem(slot, entry.getKey().getUpgradedItem(entry.getValue()).getItemStack());
				slot++;
			}

		}
		
		if(eP.isGuardChief())
			getInventory().setItem(getInventory().getSize() - 1, Item.chiefsPanel.getItemStack());

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		// TODO Auto-generated method stub

	}

}
