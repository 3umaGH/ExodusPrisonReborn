package items.list.other;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import items.Item;
import items.Title;
import util.Util;

public class RandomTitle extends Item {

	public RandomTitle() {
		super("&eRandom Title", Material.EMPTY_MAP);
		
		
		this.setPriority(false);
		this.setDescription("&7Right click to get a random item.");
		this.setLegal(LegalityType.Legal);
		this.setDropOnDeath(true);
		
		compile();
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {

		e.setCancelled(true);
		
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			Title randomTitle = (Title) Util.getListRandom(Title.getTitles());
			
			Util.giveItem(p, randomTitle.getItemStack(), 1);
			Util.removeFromHand(p);
			
			p.getWorld().playSound(p.getLocation(),Sound.ORB_PICKUP, 1F, 2F);
		}
		
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
