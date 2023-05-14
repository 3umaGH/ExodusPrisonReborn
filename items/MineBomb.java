package items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

import bukkit.exodusprison.spurkle.Main;
import logger.Log;
import logger.Log.LogType;
import util.Util;

public class MineBomb extends Item {
	private static List<Location> placedBombs = new ArrayList<Location>();

	private int explosionSize;
	private String headName;

	public MineBomb(String name, String headTexture, int explosionSize) {
		super(name, Material.LEGACY_SKULL);

		this.setData(3);
		this.setLegal(LegalityType.Illegal);
		this.addCustomLore("&7Explosive bomb, used in mines.");

		this.compile();

		Util.setHeadTexture(this.item, headTexture);

		this.explosionSize = explosionSize;
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {

	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUseOnEntity(Player p, PlayerInteractEntityEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {

		if (e.isCancelled())
			return;

		Block block = e.getBlock();

		placedBombs.add(block.getLocation());

		new BukkitRunnable() {
			public void run() {

				block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				block.getWorld().playSound(block.getLocation(), Sound.UI_BUTTON_CLICK, 0.98F, 1F);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					public void run() {
						cancel();
					}
				}, 9);

			}

		}.runTaskTimer(Main.getPlugin(), 0L, 3L);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			public void run() {

				block.setType(Material.AIR);

				for (int i = 1; (explosionSize + 1) > i; i++) {
					List<Block> blocks = Util.getNearbyBlocks(block.getLocation(), i);

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						public void run() {

							for (Block tempBlock : blocks) {

								if (Util.countChance(80) && !tempBlock.getType().equals(Material.LEGACY_SKULL)
										&& !tempBlock.getType().equals(Material.BEDROCK)) {

									/*ApplicableRegionSet regions = Main.getWGRegionManager()
											.getApplicableRegions(BukkitAdapter.adapt(tempBlock.getLocation()));

									if (regions.canBuild(Main.getWG().wrapPlayer(p))) {

										tempBlock.getWorld().playSound(tempBlock.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.98F,
												1F);

										tempBlock.breakNaturally();

									}*/

								}

							}

						}
					}, i);

				}

				placedBombs.remove(block.getLocation());

			}
		}, 12);

	}

	public static List<Location> getPlacedBombs() {
		return placedBombs;
	}

	public int getExplosionSize() {
		return explosionSize;
	}

	public String getHeadName() {
		return headName;
	}

	public static void removeBombs() {
		for (Location loc : placedBombs) {
			loc.getBlock().setType(Material.AIR);
		}
		
		Log.log(LogType.WARNING,"[STOPPING] [MINEBOMBS] Removed " + placedBombs.size() + " mine bombs.");
	}

}
