package items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class RewardChest extends Item {

	public static enum ChestTier {
		SIMPLE("&fSimple Chest"), RARE("&dRare Chest"), LEGENDARY("&6&lLegendary Chest");

		String display;

		ChestTier(String display) {
			this.display = display;
		}

	}

	public static class Reward {
		String item;
		int amount;
		double chance;

		public Reward(Item item, int amount, double chance) {
			this.item = item.getName();
			this.amount = amount;
			this.chance = chance;
		}

	}

	ChestTier tier;

	public RewardChest(ChestTier tier) {
		super(tier.display, Material.ENDER_CHEST);
		this.tier = tier;

	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		
	}

}
