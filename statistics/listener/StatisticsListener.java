package statistics.listener;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import exodusplayer.ExodusPlayer;
import statistics.handlers.ServerStatistics;

public class StatisticsListener implements Listener {
	
	//Which blocks to log
	private static Integer[] loggedBlocks = {
			1,4,6,13,14,15,16,17,21,73,81,83,86,87,89,103,127,129,153,155,
	};
	
	@EventHandler(priority = EventPriority.MONITOR)
	public static void onBlockBreak(BlockBreakEvent e){
		int blockId = e.getBlock().getType().getId();
		
		if(e.isCancelled()) 
			return;
		
		
		ExodusPlayer p = ExodusPlayer.get(e.getPlayer());
		
		if(ArrayUtils.contains(loggedBlocks, blockId)){ // Log only logged blocks
			p.getStats().incrementKey("blocks_" + blockId + "_broken", 1);
			ServerStatistics.incrementKey("blocks_" + blockId + "_broken", 1);
		}
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public static void onBlockPlace(BlockPlaceEvent e){
		int blockId = e.getBlock().getType().getId();
		
		if(e.isCancelled())
			return;
		
		ExodusPlayer p = ExodusPlayer.get(e.getPlayer());
		
		if(ArrayUtils.contains(loggedBlocks, blockId)){ // Log only logged blocks
			p.getStats().incrementKey("blocks_" + blockId + "_placed", 1);
			ServerStatistics.incrementKey("blocks_" + blockId + "_placed", 1);
		}
		
	}
	
	
	

}
