package items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import bukkit.exodusprison.spurkle.Main;
import classes.Reward;
import classes.Reward.RewardType;
import events.stash.Stash.StashType;
import util.Util;

public class StashItem extends Item {

	private final int REWARDS_AMOUNT_MIN = 3;
	private final int REWARDS_AMOUNT_MAX = 6;

	private List<PotionEffect> effects = new ArrayList<PotionEffect>();

	StashType type;

	public StashItem(String name, StashType type) {
		super(name, Material.LEGACY_SKULL_ITEM);

		this.setData(3);
		this.setPriority(false);
		this.addCustomLore("&7Contains items that were hidden by someone.");

		this.type = type;
		
		switch(type) {
		
		case PRISONERS:
			this.setLegal(LegalityType.Illegal);
			break;
			
		case GUARDS:
			this.setLegal(LegalityType.GuardsOnly);
			break;
			
		case LEGENDARY:
			this.setLegal(LegalityType.Legal);
			break;
		
		}
		
		this.compile();

		SkullMeta meta = (SkullMeta) this.item.getItemMeta();
		meta.setOwner(type.getTexture());
		this.item.setItemMeta(meta);
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		e.setCancelled(true);
		
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Location loc = p.getLocation();
			
			loc.getWorld().playSound(loc, Sound.DIG_STONE, 1F, 1F);
			
			if (type.equals(StashType.LEGENDARY)) {
				loc.getWorld().playSound(loc, Sound.WITHER_DEATH, 0.98F, 1F);
				Util.globalMessage("§ePlayer §6" + p.getName() + " §ehas opened a §6" + Util.makeFirstCapital(type.toString()) + " Stash!");
			}
			if (type.equals(StashType.GUARDS))
				loc.getWorld().playSound(loc, Sound.LEVEL_UP, 0.98F, 0.3F);
				
			
			int rewards = Util.generateRandomBetween(REWARDS_AMOUNT_MIN, REWARDS_AMOUNT_MAX);

			for (int i = 0; i < rewards; i++) { // give rewards loop

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					public void run() {

						Reward reward = (Reward) Util.getListRandom(type.getRewards());

						reward.giveReward(p);

						if (reward.getType().equals(RewardType.ITEM))
							Util.privateItemMessage(p,
									"§6" + Util.makeFirstCapital(type.toString()) + " Stash Loot: [item]",
									reward.getItem().getNewItemStack(reward.getLastAmount()));
						else
							p.sendMessage("§6" + Util.makeFirstCapital(type.toString()) + " Stash Loot: "
									+ reward.getRewardLabel() + ".");

					}
				}, i * 5);

			}
		}
		
		Util.removeFromHand(p);
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
