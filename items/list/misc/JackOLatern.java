package items.list.misc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import items.Item;

public class JackOLatern extends Item {

	public JackOLatern() {
		super("Jack o'Lantern", Material.JACK_O_LANTERN);
		
		this.setPriority(true);
		this.setDescription("&7A spooky pumpkin. Why would I even craft that?");
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
