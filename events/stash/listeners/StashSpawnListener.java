package events.stash.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import events.stash.Stash;
import events.stash.Stash.StashType;
import util.Util;

public class StashSpawnListener implements Listener {
	
	private final static int CHANCE = 2;
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onBlockMine(BlockBreakEvent e){
		
		if(e.isCancelled())
			return;
		
		if(e.getBlock().getType().equals(Material.STONE) && Util.countChance(CHANCE) && Util.countChance(25)){
			
			e.setCancelled(true);
			
			new Stash(e.getBlock().getLocation(), StashType.MINING);
			
			e.getPlayer().sendMessage("§eYou have found a hidden §6stash§e in a rock! Pick it up quick!");
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_WITHER_SPAWN, 0.98F, 3F);
		}
		
	}
	
}
