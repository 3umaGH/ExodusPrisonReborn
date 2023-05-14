package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import exodusplayer.ExodusPlayer;
import statistics.handlers.ServerStatistics;
import util.GUI;

public class PVPTimerListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerDamage(EntityDamageByEntityEvent e){
		
		if(e.isCancelled())
			return;
		
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(!ExodusPlayer.get(p).canBeDamaged()){
				e.setCancelled(true);
				
				if(e.getDamager() instanceof Player) {
					GUI.sendTitle((Player) e.getDamager(), 0, 20, 0, "", "&aNo need to hit newbies!");
					ServerStatistics.incrementKey("pvp_timer_attacked_attempts", 1);
				}
				
			}
			
		}
		
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			
			if(!ExodusPlayer.get(p).canBeDamaged()){
				e.setCancelled(true);
				
				GUI.sendTitle(p, 0, 20, 0, "&aYou have a PVP Timer!", "&7Type &6/timer remove&7 to remove it.");
				ServerStatistics.incrementKey("pvp_timer_attack_attempts", 1);
				
			}
			
		}
		
		
	}
	


}
