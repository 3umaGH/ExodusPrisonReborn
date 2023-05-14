package items.list.other;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import exodusplayer.ExodusPlayer;
import items.Item;
import util.GUI;
import util.Util;

public class Lockpick extends Item {

	public Lockpick() {
		super("Lockpick", Material.TRIPWIRE_HOOK);
		
		
		this.setPriority(false);
		this.addCustomLore("&7Right Click in jail to escape. Breaks after use.");
		this.setLegal(LegalityType.Illegal);
		
		this.compile();
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		e.setCancelled(true);
		
		ExodusPlayer eP = ExodusPlayer.get(p);
		
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
		
			if(eP.isArrested()) {
				eP.unarrestPlayer();
				
				p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 0.98F, 1F);
				p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 0.98F, 1F);

				GUI.sendTitle(p, 5, 60, 5, "", "&cYou have escaped the jail.");
				Util.globalMessage("§4§lWARNING §7: > §4Player §c" + p.getName() + "§4 has used a lockpick to escape the jail!");
				

			} else {
				GUI.sendTitle(p, 5, 60, 5, "", "&cThis item only works when you are arrested.");
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
			}
			
			
		}
		
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
