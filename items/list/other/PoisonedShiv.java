package items.list.other;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import items.Item;
import util.Util;

public class PoisonedShiv extends Item {

	public PoisonedShiv() {
		super("Poisoned Shiv", Material.STONE_SWORD);

		this.setPriority(false);
		this.addCustomLore("&7Severly poisons player upon hit.");
		this.addCustomLore("");
		this.addCustomLore("&cBreaks after one use.");
		this.setLegal(LegalityType.Illegal);

		compile();
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {

	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player){
			Player damaged = (Player) e.getEntity();
			
			damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 2));
			
			Util.removeFromHand(p);
			p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 0.98F, 1F);

		}

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
