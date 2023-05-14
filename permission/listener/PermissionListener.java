package permission.listener;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import bukkit.exodusprison.spurkle.Main;
import classes.Cuboid;
import exodusplayer.ExodusPlayer;
import logger.Log;
import logger.Log.LogType;
import permission.Permissions.Permission;
import statistics.handlers.ServerStatistics;

public class PermissionListener implements Listener {

	public static void startLoop() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			public void run() {

				for (ExodusPlayer p : ExodusPlayer.getOnlinePlayers()) {

					if (p.isFarmBypass())
						continue;

					for (Permission perm : Permission.values()) {
						
						if(p.isGuard() && perm.isGuardsEnterDefault())
							continue;

						if (p.getPermissions().contains(perm))
							continue;

						if (perm.isInside(p.getPlayer())) {
							p.getPlayer().teleport(perm.getEntrance());

							if(p.isGuard() && perm.isGuardsEnterDefault())
								p.getPlayer().sendMessage("§4Guards are not allowed to enter " + perm.getName() + "! Buy it.");
							else
							p.getPlayer().sendMessage("§4Uh oh! You haven't purchased access to " + perm.getName() + "!");
							
							p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.valueOf("VILLAGER_NO"), 0.98F,
									1F);

							ServerStatistics.incrementKey("no_access_farm_enter_attempts", 1);

							break;
						}

					}

				}

			}
		}, 0L, 10L);

		Log.log(LogType.NOTIFY, "[INITALIZING] [PERMISSION] Permission loop started successfully.");

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onWrongBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		ExodusPlayer eP = ExodusPlayer.get(p);

		if (eP.isFarmBypass())
			return;

		for (Permission perm : Permission.values()) {

			if (eP.getPermissions().contains(perm))
				continue;

			for (Cuboid cuboid : perm.getCuboids()) {
				if (cuboid.isIn(e.getBlock().getLocation())) {

					p.sendMessage("§4You don't have access to this farm.");
					e.setCancelled(true);

					break;
				}
			}

		}

	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onItemPickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		ExodusPlayer eP = ExodusPlayer.get(p);

		if (eP.isFarmBypass())
			return;

		for (Permission perm : Permission.values()) {

			if (eP.getPermissions().contains(perm))
				continue;

			for (Cuboid cuboid : perm.getCuboids()) {
				if (cuboid.isIn(e.getItem().getLocation())) {

					e.setCancelled(true);

					break;
				}
			}

		}

	}
	

	@EventHandler
	public static void onPlayerDisconnectInMine(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		for (Permission perm : Permission.values()) {

			if (perm.isInside(p))
				if (perm.getTeleportOnDisconnect())
					p.teleport(perm.getEntrance());

		}

	}

}
