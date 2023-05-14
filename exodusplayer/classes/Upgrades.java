package exodusplayer.classes;

import java.util.HashMap;

import items.ItemUpgrade;

public class Upgrades {

	private HashMap<ItemUpgrade, Integer> itemUpgrades = new HashMap<ItemUpgrade, Integer>();

	public int getLevel(ItemUpgrade upgrade) {

		if (itemUpgrades.containsKey(upgrade))
			return itemUpgrades.get(upgrade);
		else
			return -1;

	}
	
	public void addLevel(ItemUpgrade upgrade){
		itemUpgrades.put(upgrade, getLevel(upgrade) + 1);
	}
	
	public void setLevel(ItemUpgrade upgrade, int level){
		itemUpgrades.put(upgrade, level);
	}

	public HashMap<ItemUpgrade, Integer> getItemUpgrades() {
		return itemUpgrades;
	}
	
	

}
