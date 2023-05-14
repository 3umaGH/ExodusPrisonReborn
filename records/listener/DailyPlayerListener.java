package records.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import statistics.handlers.DailyStatistics;
import statistics.handlers.ServerStatistics;
import util.Util;

public class DailyPlayerListener implements Listener {
	
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent e){ // player record counter
		int players = Bukkit.getOnlinePlayers().size();
		
		if(players > DailyStatistics.getKey("max_players")) {
			DailyStatistics.setKey("max_players", players);
			
			Util.globalMessage("§4§lRecord §7: > " + e.getPlayer().getName() + " has beat todays record of " + players + " online players!");
		}
		
		if(players > ServerStatistics.getKey("max_players")) {
			ServerStatistics.setKey("max_players", players);
			
			Util.globalMessage("§4§lRecord §7: > " + e.getPlayer().getName() + " has beat all time record of " + players + " online players!");
		}
		
		
	}

}
