package items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import exodusplayer.ExodusPlayer;
import menu.list.TitleMenu;

public class Title extends Item {

	private static List<Title> titles = new ArrayList<Title>();
	
	String suffix;

	public Title(String suffix) {
		super("&6Title &8[" + suffix + "&8]", Material.PAPER);
		this.setDescription("&7Right Click to use this title.");
		this.suffix = suffix;

		compile();
		
		titles.add(this);
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {

		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ExodusPlayer eP = ExodusPlayer.get(p);

			if (eP.getTitles().contains(suffix)) {

				p.sendMessage("§4You already have this title.");
				p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.valueOf("VILLAGER_NO"), 0.98F, 1F);

				return;
			}

			e.setCancelled(true);

			TitleMenu.setTitle(p, suffix);
			eP.addTitle(suffix);

			removeFromHand(p);
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

	public static List<Title> getTitles() {
		return titles;
	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
