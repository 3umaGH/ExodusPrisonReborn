package menu.list;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import economy.Vault;
import exodusplayer.ExodusPlayer;
import items.ItemUpgrade;
import items.ItemUpgrade.Upgrade;
import menu.Menu;
import util.Util;

public class GuardUpgradeMenu extends Menu {
	private HashMap<Integer, ItemUpgrade> upgrades = new HashMap<Integer, ItemUpgrade>();

	public GuardUpgradeMenu() {
		super("Guard Upgrade Menu", 1);

	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(p);

		for (int slot : ItemUpgrade.getItemUpgrades().keySet()) {
			ItemUpgrade upgrade = ItemUpgrade.getItemUpgrades().get(slot);
			
			int playerLevel = eP.getUpgrades().getLevel(upgrade);

			if (playerLevel + 1 >= upgrade.getMaxLevel())
				getInventory().setItem(slot, Util.createItem(Material.BARRIER, 1, (short) 0, "&a" + upgrade.getName(),
						"&7You have reached the maximum level for this upgrade."));
			else {
				Upgrade itemUpgrade = upgrade.getUpgradeByLevel(playerLevel + 1);
				ItemStack upgradeItemStack = itemUpgrade.getItem().getNewItemStack(1);

				ItemMeta meta = upgradeItemStack.getItemMeta();
				ArrayList<String> loreList = new ArrayList<String>();

				loreList.addAll(upgradeItemStack.getItemMeta().getLore());

				loreList.add(" ");
				loreList.add(Util.center("§7Your level: §6§l" + (playerLevel+1), 65));
				loreList.add(Util.center("§7Upgrade price: §a§l$" + itemUpgrade.getUpgradePrice(), 60));

				meta.setLore(loreList);
				upgradeItemStack.setItemMeta(meta);
				
				getInventory().setItem(slot, upgradeItemStack);

			}

			upgrades.put(slot, upgrade);
			slot++;
		}

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		e.setCancelled(true);

		if (e.getRawSlot() >= 0 && e.getRawSlot() <= getInventory().getSize()) {

			if (!(e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType().equals(Material.BARRIER))) {

				ExodusPlayer eP = ExodusPlayer.get(p);
				ItemUpgrade clickedUpgrade = upgrades.get(e.getRawSlot());
				int playerLevel = eP.getUpgrades().getLevel(clickedUpgrade);

				if (Vault.takeMoney(p,
						clickedUpgrade.getUpgradeByLevel(playerLevel+1).getUpgradePrice(),
						false)) {

					ExodusPlayer.get(p).getUpgrades().addLevel(upgrades.get(e.getRawSlot()));
					
					p.sendMessage("§aUpgraded " + clickedUpgrade.getName() + " to the level " + (playerLevel + 2) + ".");
					p.playSound(p.getLocation(), Sound.VILLAGER_YES, 0.98F, 1F);
					
					p.closeInventory();
					openMenu(p);

				} else {
					p.sendMessage("§cYou don't have enough money.");
					p.playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
				}

			}
		}

	}

}