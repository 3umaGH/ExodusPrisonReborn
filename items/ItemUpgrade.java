package items;

import java.util.HashMap;
import java.util.Map.Entry;

public class ItemUpgrade {
	private static HashMap<Integer, ItemUpgrade> upgradesList = new HashMap<Integer, ItemUpgrade>();
	private HashMap<Integer, Upgrade> upgrades = new HashMap<Integer, Upgrade>();

	private String name;
	private int slot;

	public static class Upgrade {

		int upgradePrice;
		Item item;

		public Upgrade(Item item, int upgradePrice) {
			this.upgradePrice = upgradePrice;
			this.item = item;
		}

		public int getUpgradePrice() {
			return upgradePrice;
		}

		public Item getItem() {
			return item;
		}

	}

	public ItemUpgrade(String name, int slot, Upgrade... upgrd) {
		this.name = name;
		this.slot = slot;

		for (Upgrade item : upgrd)
			upgrades.put(upgrades.size(), item);

		upgradesList.put(slot, this);
	}

	public String getName() {
		return this.name;
	}

	public Item getUpgradedItem(int level) {
		return upgrades.get(level).item;
	}

	public Upgrade getUpgradeByLevel(int level) {
		return upgrades.get(level);
	}

	public int getMaxLevel() {
		return upgrades.size();
	}

	public static ItemUpgrade getByName(String name) {
		ItemUpgrade foundItem = null;

		for (Entry<Integer, ItemUpgrade> entry : upgradesList.entrySet())
			if (entry.getValue().getName().equals(name)) {
				foundItem = entry.getValue();
				break;
			}

		return foundItem;
	}

	public static ItemUpgrade getByItem(Item item) {
		ItemUpgrade found = null;

		for (Entry<Integer, ItemUpgrade> entry : upgradesList.entrySet())
			for (Upgrade upgrade : entry.getValue().upgrades.values())
				if (item.equals(upgrade.item))
					found = entry.getValue();

		return found;
	}

	public static HashMap<Integer, ItemUpgrade> getItemUpgrades() {
		return upgradesList;
	}

	public int getSlot() {
		return this.slot;
	}

}
