package menu.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import items.Item;

public class AdminItemList {

	public static void openMenu(Player p, int page) {

		Inventory inv = Bukkit.createInventory(null, 54, "Item List Page: " + page);
		{

			int start_from = (54 * page) - 54;
			int show_amount = 54 * page;

			if (show_amount > Item.itemIds.size())
				show_amount = Item.itemIds.size();

			List<Item> old_list = new ArrayList<Item>();
			List<Item> new_list = new ArrayList<Item>();

			for (Entry<Integer, String> entry : Item.itemIds.entrySet())
				old_list.add(Item.getItemList().get(Item.itemIds.get(entry.getKey())));

			for (int i = start_from; i < show_amount; i++)
				new_list.add(old_list.get(i));

			int slot = 0;
			for (Item result : new_list) {

				inv.setItem(slot, result.getItemStack());

				slot += 1;
			}
			p.openInventory(inv);
		}
	}

}
