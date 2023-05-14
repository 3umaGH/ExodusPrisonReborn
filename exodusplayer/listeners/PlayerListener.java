package exodusplayer.listeners;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import exodusplayer.ExodusPlayer;
import exodusplayer.PlayerLoader;

public class PlayerListener implements Listener {

	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent e) {

		PlayerLoader.loadPlayer(e.getPlayer());

	}

	@EventHandler
	public static void onPlayerDisconnect(PlayerQuitEvent e) {

		PlayerLoader.savePlayer(ExodusPlayer.get(e.getPlayer()), true, true, true);

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public static void onPlayerInteract(PlayerInteractEvent e) {
		
		if (!ExodusPlayer.isLoaded(e.getPlayer()))
			e.setCancelled(true);
		
		ExodusPlayer eP = ExodusPlayer.get(e.getPlayer());
		
		if(eP.isFrozen()){
			e.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public static void onPlayerDamage(EntityDamageEvent e) {
		if (e.getEntityType().equals(EntityType.PLAYER)) {
			Player p = (Player) e.getEntity();

			if (!ExodusPlayer.isLoaded(p)) {
				e.setCancelled(true);
				return;
			}
			
			ExodusPlayer eP = ExodusPlayer.get(p);
			
			if(eP.isFrozen() || eP.isNoPVP()){
				e.setCancelled(true);
				return;
			}
			

		}
	}
	
	@EventHandler
	public static void onPlayerHit(EntityDamageByEntityEvent e){
		if(e.getEntityType().equals(EntityType.PLAYER)){
			Player p = (Player) e.getEntity();

			if (!ExodusPlayer.isLoaded(p)) {
				e.setCancelled(true);
				return;
			}
			
			ExodusPlayer eP = ExodusPlayer.get(p);
			
			if(eP.isFrozen() || eP.isNoPVP()){
				e.setCancelled(true);
				return;
			}
		}
		
		if(e.getDamager().getType().equals(EntityType.PLAYER)){
			Player p = (Player) e.getDamager();

			if (!ExodusPlayer.isLoaded(p)) {
				e.setCancelled(true);
				return;
			}
			
			ExodusPlayer eP = ExodusPlayer.get(p);
			
			if(eP.isFrozen() || eP.isNoPVP()){
				e.setCancelled(true);
				return;
			}
		}
		
	}

	@EventHandler
	public static void onMove(PlayerMoveEvent e) {
		ExodusPlayer eP = ExodusPlayer.get(e.getPlayer());

		if (eP == null || eP.isFrozen()) {
			Location to = e.getFrom();
			to.setPitch(e.getTo().getPitch());
			to.setYaw(e.getTo().getYaw());
			e.setTo(to);
		}
	}

	@EventHandler
	public static void onPlayerDeath(PlayerDeathEvent e) {
		ExodusPlayer.get(e.getEntity()).getStats().incrementKey("player_deaths", 1);

		if (e.getEntity().getKiller() instanceof Player)
			ExodusPlayer.get(e.getEntity().getKiller()).getStats().incrementKey("player_kills", 1);

	}

}
