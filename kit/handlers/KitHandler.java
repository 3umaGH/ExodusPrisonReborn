package kit.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import classes.Reward;
import exodusplayer.ExodusPlayer;
import items.Item;
import logger.Log;
import logger.Log.LogType;
import util.Util;

public class KitHandler { /// id name perm delay slot

	
	
	// DO NOT CHANGE IDS! THEY ARE STORED IN DATABASE TO COUNT THE TIME. new Kit(0 <-------------------- this number
	public static Kit miningKit = new Kit(0, "Starter Kit", "exodus.prisoner", 10, 12);
	public static Kit votingKit = new Kit(9, "&6Daily Voting Kit", "exodus.prisoner", 0, 22);
	public static Kit fbKit = new Kit(1, "&6Facebook Kit (type /fb)", "exodus.fb", 100000000, 14);

	public static Kit juniorKit = new Kit(3, "&aJunior Kit", "exodus.junior", 1440, 10);
	public static Kit savageKit = new Kit(4, "&eSavage Kit", "exodus.savage", 1440, 11);
	public static Kit exodusKit = new Kit(5, "&3Exodus Kit", "exodus.exodus", 1440, 15);
	public static Kit kingpinKit = new Kit(6, "&dKingpin Kit", "exodus.kingpin", 1440, 16);

	public static Kit juniorKitOneTime = new Kit(7, "&aJunior One Time Gift", "exodus.junior", 100000000, 19);
	public static Kit savageKitOneTime = new Kit(8, "&eSavage One Time Gift", "exodus.savage", 100000000, 20);

	public static Kit exodusKitOneTime = new Kit(10, "&3Exodus One Time Gift", "exodus.exodus", 100000000, 24);
	public static Kit kingpinKitOneTime = new Kit(11, "&dKingpin One Time Gift", "exodus.kingpin", 100000000, 25);

	public static void initalize() {

		miningKit.addReward(new Reward().setItemReward(Item.woodPickaxe, 3, 3));
		miningKit.addReward(new Reward().setItemReward(Item.smallMineBomb, 3, 3));
		miningKit.addReward(new Reward().setItemReward(Item.steak, 8, 8));
		miningKit.addReward(new Reward().setItemReward(Item.torch, 16, 16));

		fbKit.addReward(new Reward().setItemReward(Item.legendaryStash, 3, 3));

		votingKit.addReward(new Reward().setMoneyReward(500, 500));
		votingKit.addReward(new Reward().setTokenReward(10, 10));
		votingKit.addReward(new Reward().setItemReward(Item.prisonersStash, 3, 3));
		votingKit.addReward(new Reward().setItemReward(Item.mediumMineBomb, 3, 3));
		votingKit.setVotingKit(true);

		juniorKit.addReward(new Reward().setItemReward(Item.woodSword, 1, 1));
		juniorKit.addReward(new Reward().setItemReward(Item.prisonersStash, 1, 1));
		juniorKit.addReward(new Reward().setItemReward(Item.smallMineBomb, 4, 4));
		juniorKit.addReward(new Reward().setItemReward(Item.leatherHelmet, 1, 1));
		juniorKit.addReward(new Reward().setItemReward(Item.leatherChestplate, 1, 1));
		juniorKit.addReward(new Reward().setItemReward(Item.leatherLeggings, 1, 1));
		juniorKit.addReward(new Reward().setItemReward(Item.leatherBoots, 1, 1));

		savageKit.addReward(new Reward().setItemReward(Item.stoneSword, 1, 1));
		savageKit.addReward(new Reward().setItemReward(Item.stonePickaxe, 3, 3));
		juniorKit.addReward(new Reward().setItemReward(Item.prisonersStash, 2, 2));
		savageKit.addReward(new Reward().setItemReward(Item.smallMineBomb, 4, 4));
		savageKit.addReward(new Reward().setItemReward(Item.mediumMineBomb, 2, 2));

		exodusKit.addReward(new Reward().setItemReward(Item.ironSword, 1, 1));
		exodusKit.addReward(new Reward().setItemReward(Item.ironPickaxe, 1, 1));
		exodusKit.addReward(new Reward().setItemReward(Item.prisonersStash, 2, 2));
		exodusKit.addReward(new Reward().setItemReward(Item.guardsStash, 1, 1));
		exodusKit.addReward(new Reward().setItemReward(Item.smallMineBomb, 4, 4));
		exodusKit.addReward(new Reward().setItemReward(Item.mediumMineBomb, 2, 2));

		kingpinKit.addReward(new Reward().setItemReward(Item.ironSword, 1, 1));
		kingpinKit.addReward(new Reward().setItemReward(Item.ironPickaxe, 3, 3));
		kingpinKit.addReward(new Reward().setItemReward(Item.prisonersStash, 3, 3));
		kingpinKit.addReward(new Reward().setItemReward(Item.guardsStash, 1, 1));
		kingpinKit.addReward(new Reward().setItemReward(Item.smallMineBomb, 8, 8));
		kingpinKit.addReward(new Reward().setItemReward(Item.mediumMineBomb, 8, 8));

		juniorKitOneTime.addReward(new Reward().setItemReward(Item.mediumMineBomb, 8, 8));
		juniorKitOneTime.addReward(new Reward().setItemReward(Item.bigMineBomb, 4, 4));
		juniorKitOneTime.addReward(new Reward().setItemReward(Item.prisonersStash, 3, 3));
		juniorKitOneTime.addReward(new Reward().setItemReward(Item.guardsStash, 1, 1));
		juniorKitOneTime.addReward(new Reward().setItemReward(Item.cactusGreen, 32, 32));
		juniorKitOneTime.addReward(new Reward().setItemReward(Item.ironHelmet, 1, 1));
		juniorKitOneTime.addReward(new Reward().setItemReward(Item.ironChestplate, 1, 1));
		juniorKitOneTime.addReward(new Reward().setItemReward(Item.ironLeggings, 1, 1));
		juniorKitOneTime.addReward(new Reward().setItemReward(Item.ironBoots, 1, 1));

		savageKitOneTime.addReward(new Reward().setItemReward(Item.lockpick, 1, 1));
		savageKitOneTime.addReward(new Reward().setItemReward(Item.prisonersStash, 3, 3));
		savageKitOneTime.addReward(new Reward().setItemReward(Item.legendaryStash, 1, 1));
		savageKitOneTime.addReward(new Reward().setItemReward(Item.meth, 1, 1));

		exodusKitOneTime.addReward(new Reward().setItemReward(Item.randomTitle, 1, 1));
		exodusKitOneTime.addReward(new Reward().setItemReward(Item.legendaryStash, 1, 1));
		exodusKitOneTime.addReward(new Reward().setItemReward(Item.LSD, 3, 3));

		kingpinKitOneTime.addReward(new Reward().setItemReward(Item.lockpick, 1, 1));
		kingpinKitOneTime.addReward(new Reward().setItemReward(Item.legendaryStash, 1, 1));
		kingpinKitOneTime.addReward(new Reward().setItemReward(Item.meth, 1, 1));
		kingpinKitOneTime.addReward(new Reward().setItemReward(Item.superWoodPickaxe, 1, 1));

		Log.log(LogType.NOTIFY,"[INITALIZING] [KITS] Kits module initalized successfully.");
	}

	public static class Kit {
		private static HashMap<Integer, Kit> kits = new HashMap<Integer, Kit>();

		private int id, delayMinutes, slot;
		private String name, permission;
		private boolean votingKit;

		private List<Reward> rewards = new ArrayList<Reward>();

		public Kit(int id, String name, String permission, int delayMinutes, int slot) {
			this.id = id;
			this.name = name;
			this.permission = permission;
			this.delayMinutes = delayMinutes;
			this.slot = slot;
			this.votingKit = false;

			kits.put(id, this);
		}

		public void giveKit(ExodusPlayer eP) {
			for (Reward reward : rewards)
				reward.giveReward(eP.getPlayer());

			eP.getData().setData("kit_" + id, System.currentTimeMillis());
			eP.getStats().incrementKey("kits_taken", 1);
			
			if(votingKit)
			eP.getData().setDataInt("kit_stored_" + id, getKitsAmount(eP) -1);
		}

		public boolean isCooledDown(ExodusPlayer eP) {
			Long time = eP.getData().getData("kit_" + id);

			if (time == -1)
				return true;

			if (TimeUnit.MINUTES.toMillis(Util.LongToInt(delayMinutes)) < (System.currentTimeMillis() - time))
				return true;

			return false;
		}

		public long getCooldown(ExodusPlayer eP) {
			return delayMinutes
					- TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - eP.getData().getData("kit_" + id));
		}

		public static HashMap<Integer, Kit> getKits() {
			return kits;
		}

		public int getId() {
			return id;
		}

		public int getDelayMinutes() {
			return delayMinutes;
		}

		public int getSlot() {
			return slot;
		}

		public String getName() {
			return name;
		}

		public String getPermission() {
			return permission;
		}

		public List<Reward> getRewards() {
			return rewards;
		}

		public void addReward(Reward reward) {
			this.rewards.add(reward);
		}

		public boolean isVotingKit() {
			return votingKit;
		}

		public void setVotingKit(boolean votingKit) {
			this.votingKit = votingKit;
		}

		public int getKitsAmount(ExodusPlayer eP) {
			return eP.getData().getDataInt("kit_stored_" + id);
		}

		public void addKitsAmount(ExodusPlayer eP, int amount) {
			eP.getData().setDataInt("kit_stored_" + id, eP.getData().getDataInt("kit_stored_" + id) + amount);
		}

	}

}
