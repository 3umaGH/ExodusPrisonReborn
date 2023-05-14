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
import sound.CustomSound;
import sound.CustomSound.SoundType;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class InventorySearcher extends Item {

	public InventorySearcher() {
		super("Inventory Searcher", Material.INK_SACK);

		this.setData(12);
		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&7Allows you to look at the prisoners inventory.", 60));
		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&6&lRight Click - View Inventory", 60));
		
		this.setLegal(LegalityType.GuardsOnly);
		this.setPriority(false);
		this.setDropOnDeath(false);

		compile();

	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		e.setCancelled(true);
	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		// todo

	}

	@Override
	public void onUseOnEntity(Player p, PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player clicked = (Player) e.getRightClicked();

			if (ExodusPlayer.get(p).isGuard() && !ExodusPlayer.get(clicked).isGuard()) {

				if(!ExodusPlayer.get(clicked).isArrested()){
					GUI.sendTitle(p, 0, 40, 5, "","§cYou can only search arrested players.");
					p.getLocation().getWorld().playSound(clicked.getLocation(), Sound.VILLAGER_NO, 1F, 0.02F);
					return;
				}
				
				GUI.sendTitle(clicked, 0, 40, 5, "§cYou are being searched!", "§cby " + p.getName()  + ".");
				GUI.sendTitle(p, 0, 40, 5, "§9Searching...", "§9" + clicked.getName() + ".");
				
				p.getLocation().getWorld().playSound(clicked.getLocation(), Sound.EAT, 1F, 0.02F);
				CustomSound.playSound(SoundType.INV_SEARCH, clicked.getLocation());
				
				clicked.closeInventory();
				p.performCommand("invsee " + clicked.getName());
				
				ServerStatistics.incrementKey("inventory_searches", 1);
				ExodusPlayer.get(clicked).getStats().incrementKey("got_inventory_searched", 1);
				ExodusPlayer.get(p).getStats().incrementKey("searched_inventories", 1);

			}

		}

	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub
		
	}

}
 