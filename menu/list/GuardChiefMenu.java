package menu.list;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import exodusplayer.ExodusPlayer;
import exodusplayer.classes.Offenses.Offense;
import exodusplayer.classes.OnlineDay;
import items.ItemUpgrade;
import menu.Menu;
import permission.Permissions.Permission;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class GuardChiefMenu extends Menu {

	public static HashMap<Player, String> selectedPlayers = new HashMap<Player, String>();

	public GuardChiefMenu() {
		super("Guards Chief Menu", 6);

		this.setUpdating(true);

	}

	@Override
	public void loadIcons(Player p) {

		int slot = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			ExodusPlayer eP = ExodusPlayer.get(player);
			String lore = "";

			if (eP.isGuard())
				lore = "&9GUARD";
			else
				lore = "&fPLAYER";

			ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "&f" + player.getName(), lore);

			SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
			skullMeta.setOwner(player.getName());

			head.setItemMeta(skullMeta);

			getInventory().setItem(slot, head);

			slot++;

			if (slot >= 45)
				break;
		}

		if (selectedPlayers.containsKey(p)) {
			Player selectedPlayer = Bukkit.getPlayer(selectedPlayers.get(p));

			if (selectedPlayer != null) {
				ExodusPlayer eP = ExodusPlayer.get(selectedPlayer);

				String permissions = "";
				String offenses = "";
				String statistics = "";
				String serverStatistics = "";
				String guardUpgrades = "";
				String data = "";
				String onlineDays = "";

				for (Permission perm : Permission.values()) {

					if (eP.getPermissions().contains(perm))
						permissions = permissions + "&a" + perm.getName() + "/n";
					else
						permissions = permissions + "&c" + perm.getName() + "/n";

				}

				for (Offense offense : eP.getOffenses().getOffenses().values()) {
					offenses = offenses + "&7" + offense.getFormattedTime() + " : " + offense.getDetails() + "/n";
				}

				for (Entry<String, Long> entry : eP.getStats().getStatistics().entrySet()) {
					statistics = statistics + "&7" + entry.getKey() + " : &f" + entry.getValue() + "/n";
				}

				for (Entry<String, Long> entry : ServerStatistics.getStatistics().entrySet()) {
					serverStatistics = serverStatistics + "&7" + entry.getKey() + " : &f" + entry.getValue() + "/n";
				}

				for (Entry<ItemUpgrade, Integer> entry : eP.getUpgrades().getItemUpgrades().entrySet()) {
					if (entry.getValue() >= 0)
						guardUpgrades = guardUpgrades
								+ entry.getKey().getUpgradeByLevel(entry.getValue()).getItem().getName() + "/n";
				}

				for (Entry<String, String> entry : eP.getData().getData().entrySet()) {
					String value = entry.getValue();

					try {
						if (Long.parseLong(value) > Integer.MAX_VALUE)
							value = Util.formatTimeMillis(Long.parseLong(value), "yyyy/MM/dd HH:mm:ss");
					} catch (Exception ex) {

					}

					data = data + "&7" + entry.getKey() + " : &f" + value + "/n";
				}

				for (OnlineDay day : eP.getOnlineTime()) {
					onlineDays = onlineDays + "&7" + day.getDate() + " : &f" + day.getTime() + "/n";
				}

				data = data + "&7" + "guard : &f" + eP.isGuard() + "/n";
				data = data + "&7" + "farmBypass : &f" + eP.isFarmBypass() + "/n";
				data = data + "&7" + "frozen : &f" + eP.isFrozen() + "/n";

				ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "&aSelected: &e" + selectedPlayer.getName());

				SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
				skullMeta.setOwner(selectedPlayer.getName());

				head.setItemMeta(skullMeta);

				getInventory().setItem(45, head);
				getInventory().setItem(47, Util.createItem(Material.MAP, 1, (short) 0, "&ePlayer Data", data));
				getInventory().setItem(48, Util.createItem(Material.PAPER, 1, (short) 0, "&ePermissions", permissions));
				getInventory().setItem(49, Util.createItem(Material.FEATHER, 1, (short) 0, "&eStatistics", statistics));
				getInventory().setItem(50,
						Util.createItem(Material.DIAMOND_SWORD, 1, (short) 0, "&eGuard Upgrades", guardUpgrades));
				getInventory().setItem(51, Util.createItem(Material.WATCH, 1, (short) 0, "&eOnline Time", onlineDays));
				getInventory().setItem(52, Util.createItem(Material.BARRIER, 1, (short) 0, "&cOffenses", offenses));

				if (eP.isGuard())
					getInventory().setItem(53, Util.createItem(Material.WOOL, 1, (short) 14, "&c&lDEMOTE PLAYER",
							"&cThis player is a guard. RIGHT + SHIFT CLICK this to DEMOTE HIM."));
				else
					getInventory().setItem(53, Util.createItem(Material.WOOL, 1, (short) 13, "&c&lPROMOTE PLAYER",
							"&aThis player is &cNOT&a a guard. RIGHT + SHIFT CLICK this to PROMOTE HIM."));

			}

		}

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		e.setCancelled(true);

		ItemStack clickedItem = e.getCurrentItem();

		if (clickedItem == null || clickedItem.getType().equals(Material.AIR))
			return;

		if (clickedItem.getType().equals(Material.SKULL_ITEM))
			selectedPlayers.put(p, ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()));

		if (e.getRawSlot() == 53 && e.isShiftClick() && e.isRightClick() && selectedPlayers.containsKey(p)) {
			Player selectedPlayer = Bukkit.getPlayer(selectedPlayers.get(p));

			if (selectedPlayer != null) {
				ExodusPlayer eP = ExodusPlayer.get(selectedPlayer);

				if (selectedPlayer.equals(p)) {
					p.closeInventory();
					p.sendMessage("§4Are you retarded? Why are you trying to demote yourself?...");
					p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
					return;
				}

				if (selectedPlayer.hasPermission("exodus.helper")) {
					p.closeInventory();
					p.sendMessage("§4This guy isn't even a guard, what the hell are you trying to do?");
					p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
					return;
				}

				if (eP.isGuard()) {
					GUI.sendTitle(selectedPlayer, 20, 100, 20, "&4You have been DEMOTED.",
							"&cYou have been demoted by guard chief " + p.getName() + ".");
					selectedPlayer.getWorld().playSound(selectedPlayer.getLocation(), Sound.WITHER_DEATH, 0.98F, 1F);

					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + selectedPlayer.getName() + " group remove guard");
				
					ServerStatistics.incrementKey("guard_demotions_by_chief", 1);
				} else {
					GUI.sendTitle(selectedPlayer, 20, 100, 20, "&aYou have been PROMOTED.",
							"&eYou have been promoted by guard chief " + p.getName() + " to a guard.");
					selectedPlayer.getWorld().playSound(selectedPlayer.getLocation(), Sound.WITHER_DEATH, 0.98F, 1F);

					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + selectedPlayer.getName() + " group add guard");
					
					ServerStatistics.incrementKey("guard_promotions_by_chief", 1);
				}

			}

		}

	}
}
