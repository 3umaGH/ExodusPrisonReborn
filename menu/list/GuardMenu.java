package menu.list;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import exodusplayer.ExodusPlayer;
import menu.Menu;
import statistics.handlers.ServerStatistics;
import util.Util;

public class GuardMenu extends Menu {

	public GuardMenu() {
		super("Guard", 3);

		this.setUpdating(false);
	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(p);

		getInventory().setItem(11, Util.createItem(Material.WOOD_PICKAXE, 1, (short) 0, "&aFree Kit",
				"&7You have got your kit " + eP.getLastKitMinutes() + " minutes ago."));
		
		getInventory().setItem(13, Util.createItem(Material.PAPER, 1, (short) 0, "&ePassport",
				"&7View detailed information about your profile."));
		
	
		getInventory().setItem(15, Util.createItem(Material.IRON_CHESTPLATE, 1, (short) 0, "&9PVP Protection",
				"&7Prevent people from attacking you for 60 minutes.","&7Only works if you have only Beginners Mine permission."));

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		ExodusPlayer eP = ExodusPlayer.get(p);
		e.setCancelled(true);
		
		if(e.getRawSlot() == 11){
			if(eP.getLastKitMinutes() < 10){
				p.sendMessage("§4You have got your kit less than 10 minutes ago.");
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
			} else {
				new GuardKitMenu().openMenu(p);
				eP.updateKitTime();
				
				ServerStatistics.incrementKey("newbie_kits_taken", 1);
			}
			
			
		}
		
		if(e.getRawSlot() == 13){
			new PlayerMenu(p,p,false);
		}
		
		if(e.getRawSlot() == 15){
			if(eP.getPermissions().size() > 1){
				p.sendMessage("§4You can only use this if you only have Beginners Mine permission.");
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
			} else {
				
				if(eP.getPvPTimerMinutes() > 0){
					p.sendMessage("§4You already have a PVP timer. Type /timer remove to remove it.");
					p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);
				} else  {
					eP.setPvPTimerMinutes(60);
					p.sendMessage("§aYou have got 60 minutes of Protection.");
					p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_YES, 0.98F, 1F);
					
					ServerStatistics.incrementKey("pvp_timers_taken", 1);
				}
			}
		}
		
		
	}

}
