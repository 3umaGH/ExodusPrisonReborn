package guardwatcher.handlers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import exodusplayer.ExodusPlayer;
import exodusplayer.classes.Offenses.OffenseType;
import logger.Log;
import statistics.handlers.ServerStatistics;
import util.Util;

public class GuardWatcher {
	static HashMap<UUID, String> drops = new HashMap<UUID, String>();

	public static void logItemDrop(UUID id, String guardName, Item item) { // Logs
																			// all
																			// illegally
																			// dropped
																			// items
																			// by
																			// guards.
		drops.put(id, guardName);
		ServerStatistics.incrementKey("guard_illegal_drops", 1);
	}

	public static void compareItem(Item item, Player p) { // Checks all illegal
															// items dropped by
															// guards, and logs
															// it if somebody
															// picks them up.

		if (drops.containsKey(item.getUniqueId())) {
			Location loc = item.getLocation();
			String guardName = drops.get(item.getUniqueId());

			if (p.getName() == guardName)
				return;

			Log.notifyStaff("§8[§eGuardWatcher§8]§c Player §7" + p.getName() + "§c has picked up illegal item (§7"
					+ item.getItemStack().getAmount() + "x §c" + items.Item.getItem(item.getItemStack()).getName()
					+ "§c) that was dropped by guard §7" + guardName + "§c at " + Util.readableLocation(loc));

			Player guard = Bukkit.getPlayer(guardName);

			if (guard != null) {
				ExodusPlayer.get(guard).getOffenses().addOffense(OffenseType.GUARD_ILLEGAL_DROP,
						"§e" + item.getItemStack().getAmount() + " x §r"
								+ item.getItemStack().getItemMeta().getDisplayName() + " §7picked up by §e"
								+ p.getName());

			}

			drops.remove(item.getUniqueId());

			ServerStatistics.incrementKey("guard_illegal_pickups", 1);
		}

	}

}
