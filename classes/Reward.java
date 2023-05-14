package classes;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import economy.Vault;
import exodusplayer.ExodusPlayer;
import items.Item;
import util.Util;

public class Reward {

	public static enum RewardType {
		ITEM, TOKEN, MONEY
	}

	private RewardType type;
	private Item item;

	private int amountMin;
	private int amountMax;

	private int lastAmount;

	public Reward() {
	}

	public Reward setItemReward(Item item, int min, int max) {
		this.item = item;

		this.amountMin = min;
		this.amountMax = max;
		this.lastAmount = min;

		this.type = RewardType.ITEM;
		
		return this;
	}

	public Reward setTokenReward(int min, int max) {
		this.item = null;

		this.amountMin = min;
		this.amountMax = max;
		this.lastAmount = min;
		
		this.type = RewardType.TOKEN;
		
		return this;
	}

	public Reward setMoneyReward(int min, int max) {
		this.item = null;

		this.amountMin = min;
		this.amountMax = max;
		this.lastAmount = min;

		this.type = RewardType.MONEY;
		
		return this;
	}

	public void giveReward(Player p) {
		int amount = Util.generateRandomBetween(amountMin, amountMax);

		lastAmount = amount;

		switch (type) {
		case MONEY:
			Vault.addMoney(p, amount, false);
			break;
		case TOKEN:
			ExodusPlayer.get(p).addTokens(amount);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 0.98F, 1.4F);
			break;
		case ITEM:
			Util.giveItem(p, item.getItemStack(), amount);
			break;

		}

	}

	public String getRewardLabel() {
		switch (type) {
		case MONEY:
			return "§a$" + lastAmount;
		case TOKEN:
			return "§6§l" + lastAmount + " Tokens";
		case ITEM:
			return Util.getDisplayName(item.getItemStack());
		default:
			return "Unknown";

		}
	}

	public RewardType getType() {
		return type;
	}

	public Item getItem() {
		return item;
	}

	public int getLastAmount() {
		return lastAmount;
	}
	
	
	

}
