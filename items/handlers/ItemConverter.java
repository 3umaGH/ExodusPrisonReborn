package items.handlers;

import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import items.Item;
import statistics.handlers.ServerStatistics;
import util.Util;

public class ItemConverter {

	@SuppressWarnings("deprecation")
	public static ItemStack convertItem(ItemStack i, Player p) {
		Item item = Item.getConvertibles().get(i.getType().toString() + ":" + i.getData().getData());

		if (item != null) {

			if (Item.getItemList().containsKey(Util.getDisplayName(i)) || item.getItemStack().isSimilar(i)) {
				return null;
			}

				Map<Enchantment, Integer> enchantments = i.getEnchantments();

				if (i.getData().getData() == item.getItemStackData()) {
					i.setItemMeta(item.getItemMeta());
					i.addEnchantments(enchantments);
					
					if(p != null)
						p.updateInventory();
				}
			
			
			

			ServerStatistics.incrementKey("items_converted", i.getAmount());
		}

		return i;

	}

}
