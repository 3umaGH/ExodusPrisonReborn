package items.list.tools;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import items.Item;

public class WoodHoe extends Item {

	public WoodHoe() {
		super("Wooden Hoe", Material.WOOD_HOE);
		
		this.setPriority(true);
		this.setDescription("&7A tool made out of wood.");
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
