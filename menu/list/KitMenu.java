package menu.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import classes.Reward;
import classes.Reward.RewardType;
import exodusplayer.ExodusPlayer;
import kit.handlers.KitHandler;
import kit.handlers.KitHandler.Kit;
import menu.Menu;
import util.Util;

public class KitMenu extends Menu {

	private HashMap<Integer, Kit> slots = new HashMap<Integer, Kit>();

	public KitMenu() {
		super("Kit Menu", 6);

		this.setUpdating(true);

	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(p);

		for (int i = 0; i < 9; i++) {
			getInventory().setItem(i, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 3, "Border"));
			getInventory().setItem(i + 45, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 3, "Border"));
		}

		for (int i = 0; i < 6; i++) {
			getInventory().setItem(i * 9, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 3, "Border"));
			getInventory().setItem((i * 9) + 8, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 3, "Border"));
		}

		getInventory().setItem(13, Util.createItem(Material.BARRIER, 1, (short) 0, "",""));
		
		getInventory().setItem(49, Util.createItem(Material.PAINTING, 1, (short) 0, "&6Token Shop", "&7Spend your tokens in this shop."));
		
		for (Kit kit : KitHandler.Kit.getKits().values()) {

			if (kit.getSlot() != -1) {
				slots.put(kit.getSlot(), kit);

				List<String> kitLore = new ArrayList<String>();

				kitLore.add(" ");
				kitLore.add("&7Kit Contents: ");

				for (Reward reward : kit.getRewards()) {
					int amount = reward.getLastAmount();

					if (reward.getType().equals(RewardType.MONEY) || reward.getType().equals(RewardType.TOKEN))
						amount = 1;

					kitLore.add("&7" + amount + "x " + reward.getRewardLabel());
				}

				kitLore.add(" ");

				if (!p.hasPermission(kit.getPermission())) {

					kitLore.add("&4Type &6/donate&4 to visit our webstore.");

					getInventory().setItem(kit.getSlot(), Util.createItem(Material.BARRIER, 1, (short) 0,
							"&6" + kit.getName(), kitLore.toArray(new String[0])));

					continue;
				}

				if (kit.isCooledDown(eP) && !kit.isVotingKit()) {
					kitLore.add("&7Click to claim!");
					getInventory().setItem(kit.getSlot(), Util.createItem(Material.STORAGE_MINECART, 1, (short) 0,
							"&6" + kit.getName(), kitLore.toArray(new String[0])));
				} else {

					if (kit.getCooldown(eP) >= 10000000)
						kitLore.add("&7You have used your one time kit!");
					else if(!kit.isVotingKit())
						kitLore.add("&7You can claim this kit in &6" + kit.getCooldown(eP) + " minutes.");

					if (kit.isVotingKit()) {
						int kits = kit.getKitsAmount(eP);

						if (kits <= 0) {

							kitLore.add("&aType &6&l/vote&a to receive this kit!");

							getInventory().setItem(kit.getSlot(), Util.createItem(Material.COAL_BLOCK, 1, (short) 0,
									"&6" + kit.getName(), kitLore.toArray(new String[0])));

						} else {
							kitLore.add("&7Click to claim!");
							getInventory().setItem(kit.getSlot(), Util.createItem(Material.GOLD_BLOCK, 1, (short) 0,
									"&6" + kits + "x " + kit.getName(), kitLore.toArray(new String[0])));
						}

					} else {

						getInventory().setItem(kit.getSlot(), Util.createItem(Material.MINECART, 1, (short) 0,
								"&6" + kit.getName(), kitLore.toArray(new String[0])));

					}
				}

			}

		}

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		e.setCancelled(true);
		ExodusPlayer eP = ExodusPlayer.get(p);

		if (slots.containsKey(e.getRawSlot())) {
			Kit kit = slots.get(e.getRawSlot());

			if (kit.isCooledDown(eP) && p.hasPermission(kit.getPermission())) {
				
				if(kit.isVotingKit() && kit.getKitsAmount(eP) <= 0) {
					p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
					return;
				}

				kit.giveKit(eP);
				p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 1F, 0.7F);

			} else {
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
			}
		}
		
		if(e.getRawSlot() == 49)
			new TokenShop().openMenu(p);

	}

}
