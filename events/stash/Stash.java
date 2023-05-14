package events.stash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;
import classes.Reward;
import classes.Reward.RewardType;
import exodusplayer.ExodusPlayer;
import statistics.handlers.ServerStatistics;
import util.Util;

public class Stash {
	private final int REWARDS_AMOUNT_MIN = 3;
	private final int REWARDS_AMOUNT_MAX = 6;

	private static HashMap<Location, Stash> stashes = new HashMap<Location, Stash>();

	public static enum StashType {
		PRISONERS("MHF_Chest"), LEGENDARY("Spurkle"), GUARDS("Freshmuffin"), MINING("TheSkidz");

		private List<Reward> rewards = new ArrayList<Reward>();
		private String texture;

		private StashType(String texture) {
			this.texture = texture;
		}

		public String getTexture() {
			return texture;
		}

		public void addReward(Reward reward, int chance) {
			for (int i = 0; i < chance; i++)
				rewards.add(reward);
		}

		public List<Reward> getRewards() {
			return rewards;
		}

	}

	Location loc;
	StashType type;

	boolean opened;

	public Stash(Location location, StashType type) {

		location.getBlock().setType(Material.LEGACY_SKULL);
		
		Skull s = (Skull) location.getBlock().getState();
		
		s.setSkullType(SkullType.PLAYER);
		s.setOwner(type.getTexture());
		s.update();

		this.loc = location.getBlock().getLocation();
		this.type = type;
		this.opened = false;

		stashes.put(this.loc, this);
		
		new BukkitRunnable() {
			public void run() {

				if (opened || !stashes.containsKey(loc)) {
					cancel();
					return;
				}

				loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);

			}

		}.runTaskTimer(Main.getPlugin(), 0L, 40L);

	
	}

	public void openStash(Player p) {
		this.opened = true;
		
		Util.globalMessage("§ePlayer §6" + p.getName() + " §ehas opened a §6" + Util.makeFirstCapital(type.toString()) + " Stash!");
		
		loc.getWorld().playSound(loc, Sound.BLOCK_STONE_BREAK, 1F, 1F);
		
		if (type.equals(StashType.LEGENDARY))
			loc.getWorld().playSound(loc, Sound.ENTITY_WITHER_DEATH, 0.98F, 1F);
		if (type.equals(StashType.GUARDS))
			loc.getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 0.98F, 0.3F);
		
		int rewards = Util.generateRandomBetween(REWARDS_AMOUNT_MIN, REWARDS_AMOUNT_MAX);

		for (int i = 0; i < rewards; i++) { // give rewards loop

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				public void run() {
					
					Reward reward = (Reward) Util.getListRandom(type.getRewards());

					loc.getWorld().playEffect(loc, Effect.EXTINGUISH, 1);
					loc.getWorld().playSound(loc, Sound.ENTITY_CHICKEN_EGG, 0.98F, 1F);
					
					reward.giveReward(p);
					
					if(reward.getType().equals(RewardType.ITEM))
						Util.globalItemMessage("§6" + Util.makeFirstCapital(type.toString()) + " Stash Loot: [item]" , reward.getItem().getNewItemStack(reward.getLastAmount()));
					else
						Util.globalMessage("§6" + Util.makeFirstCapital(type.toString()) + " Stash Loot: " + reward.getRewardLabel() + ".");
						

				}
			}, i * 5);

		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() { // remove
																							// chest
			public void run() {

				loc.getBlock().setType(Material.AIR);
				stashes.remove(loc);

			}
		}, rewards * 5);
		
		ExodusPlayer.get(p).getStats().incrementKey(type.toString().toLowerCase() + "_stashes_opened", 1);
		ServerStatistics.incrementKey("prisoner_stashes_opened", 1);
	}
	
	

	public Location getLoc() {
		return loc;
	}

	public StashType getType() {
		return type;
	}

	public boolean isOpened() {
		return opened;
	}

	public static HashMap<Location, Stash> getStashes() {
		return stashes;
	}
}
