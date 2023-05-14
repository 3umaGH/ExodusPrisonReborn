package items.list.guard;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import exodusplayer.ExodusPlayer;
import items.Item;
import menu.list.GuardChiefMenu;
import util.Util;

public class ChiefsPanel  extends Item {

	public ChiefsPanel() {
		super("Guard Chiefs Panel", Material.PAPER);

		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&7Allows guard chief to control guards.", 60));
		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&6&lRight Click - Open Menu", 60));
		
		this.setLegal(LegalityType.GuardsOnly);
		this.setGlow(true);
		this.setPriority(false);
		this.setDropOnDeath(false);

		compile();

	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		e.setCancelled(true);
		
		if(ExodusPlayer.get(p).isGuardChief()){
			new GuardChiefMenu().openMenu(p);
		} else {
			p.sendMessage("§4The text is written using some encryption which you are unable to understand.");
			p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
		}
	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		// todo

	}

	@Override
	public void onUseOnEntity(Player p, PlayerInteractEntityEvent e) {


	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub
		
	}

}
 