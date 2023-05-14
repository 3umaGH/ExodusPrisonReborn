package listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import bukkit.exodusprison.spurkle.Main;
import classes.Cuboid;
import permission.Permissions;
import statistics.handlers.ServerStatistics;

public class TreeFarmListener implements Listener {

	//static Cuboid treeFarm = new Cuboid(new Location(Main.getWorld(), -165, 70, -54),
	//		new Location(Main.getWorld(), -138, 124, -8));

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void replaceSaplingOnTreeBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block block = e.getBlock();
		Block blockBelow = block.getLocation().subtract(0.0, 1, 0.0).getBlock();

		if (e.isCancelled() || p.getGameMode().equals(GameMode.CREATIVE))
			return;

		if (!isInTreeFarm(blockBelow.getLocation()))
			return;

		if (block.getType().equals(Material.LOG)
				&& (blockBelow.getType().equals(Material.DIRT) || blockBelow.getType().equals(Material.GRASS))) {
			byte blockType = block.getData();

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				public void run() {

					if (!block.getType().equals(Material.AIR))
						return;

					block.setType(Material.SAPLING);
					block.setData(blockType);

					ServerStatistics.incrementKey("trees_planted", 1);

				}
			}, 20L);

		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onWrongBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block block = e.getBlock();

		if (!isInTreeFarm(block.getLocation()))
			return;

		if (e.isCancelled() || p.getGameMode().equals(GameMode.CREATIVE))
			return;

		if (!(block.getType().equals(Material.LOG) || block.getType().equals(Material.LEAVES))) {
			e.setCancelled(true);

			p.sendMessage("§4I do not think that I should touch that.");
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onTreeFarmBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block block = e.getBlock();

		if (e.isCancelled() || p.getGameMode().equals(GameMode.CREATIVE))
			return;

		if (isInTreeFarm(block.getLocation())) {
			e.setCancelled(true);
			p.sendMessage("§4I do not think that I should do that.");
		}
	}

	@EventHandler
	public static void onMobSpawn(CreatureSpawnEvent e) {

		if (!isInTreeFarm(e.getLocation()))
			return;

		if (e.getSpawnReason().equals(SpawnReason.NATURAL))
			e.getEntity().remove();

	}
	
	private static boolean isInTreeFarm(Location loc){
		for(Cuboid c : Permissions.Permission.TREE_FARM.getCuboids()) 
			if(c.isIn(loc)) return true;
		return false;
	}

}
