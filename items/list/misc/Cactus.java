package items.list.misc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import items.Item;

public class Cactus extends Item {

	public Cactus() {
		super("Cactus", Material.CACTUS);
		
		this.setPriority(true);
		this.setDescription("&7A good chunk of spiky cactus. I'm pretty sure that it can sell for a good amount.");
		this.compile();
		
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
