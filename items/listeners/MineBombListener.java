package items.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import items.MineBomb;

public class MineBombListener implements Listener {
	
	@EventHandler 
	public static void onBombBreak(BlockBreakEvent e){
		if(MineBomb.getPlacedBombs().contains(e.getBlock().getLocation()))
			e.setCancelled(true);
	}

}
