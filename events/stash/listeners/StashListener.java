package events.stash.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import events.stash.Stash;

public class StashListener implements Listener {

	@EventHandler
	public static void onStashClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (Stash.getStashes().containsKey(e.getClickedBlock().getLocation())) {
				Stash stash = Stash.getStashes().get(e.getClickedBlock().getLocation());
				
				if(!stash.isOpened())
					stash.openStash(p);
				
			}
		}

	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onStashBreak(BlockBreakEvent e){
		
		if(Stash.getStashes().containsKey(e.getBlock().getLocation()))
			e.setCancelled(true);
		
	}

}
