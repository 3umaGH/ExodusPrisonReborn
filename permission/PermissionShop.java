package permission;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import economy.Vault;
import exodusplayer.ExodusPlayer;
import logger.Log;
import menu.Menu;
import permission.Permissions.Permission;
import statistics.handlers.ServerStatistics;
import util.Util;

public class PermissionShop extends Menu {
	
	HashMap<Integer, Permission> stock = new HashMap<Integer, Permission>();

	public PermissionShop(String name, Permission... perms) {
		super(name, 2);
		
		for(Permission perm : perms) 
			stock.put(stock.size(),perm);
		
	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(p);
		
		
		
		for(int slot = 0; slot < stock.size(); slot++){
			Permission perm = stock.get(slot);
			
			if(eP.getPermissions().contains(perm)) {
				getInventory().setItem(slot, Util.createItem(perm.getIcon(), 1, (short) 0, "&a" + perm.getName()));
				getInventory().setItem(slot+9, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 5, "&a" + perm.getName(), perm.getDescription(), " ", "&7You already own this permission :)"));
			}
			else {
				getInventory().setItem(slot, Util.createItem(perm.getIcon(), 1, (short) 0, "&a" + perm.getName(), perm.getDescription(), " ", Util.center("&lLeft click to buy for &e$" + perm.getPrice()+".",55)));
				getInventory().setItem(slot+9, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 14, "&a" + perm.getName(), perm.getDescription(), " ", Util.center("&lLeft click to buy for &e$" + perm.getPrice()+".",55)));
			}

		}
		
	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		ExodusPlayer eP = ExodusPlayer.get(p);
		int slot = e.getRawSlot();

		e.setCancelled(true);

		if (slot >= 0 && slot <= getInventory().getSize()) {
			ItemStack item = e.getCurrentItem();
			
			if(item == null) // clicked god knows what... lol
				return;
			
			Permission perm = null;
			
			for(Permission pp : stock.values())
				if(pp.getName().equals(ChatColor.stripColor(item.getItemMeta().getDisplayName()))){
					perm = pp;
					break;
				}
			
				
			if(perm != null && !eP.getPermissions().contains(perm)){
				
				if(Vault.takeMoney(p, perm.getPrice(), false)) {
					p.sendMessage("§aCongratulations! You have purchased §e" + perm.getName() + "!");
					p.playSound(p.getLocation(), Sound.VILLAGER_YES, 0.98F, 1F);
					
					Util.globalMessage("§eCongratulations! §6" + p.getName() + "§e has just purchased §6" + perm.getName() + "!");
					
					eP.addPermission(perm);
					
					p.closeInventory();
					openMenu(p);
					
					Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
					FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(Color.YELLOW,Color.ORANGE).withFade(Color.RED).with(Type.STAR).trail(true).build();
					FireworkMeta meta = fw.getFireworkMeta();
					
					meta.addEffect(effect);
					fw.setFireworkMeta(meta);
					
					Log.log(p.getName() + " has purchased " + perm.getName() + " permission.");
					ServerStatistics.incrementKey("permissions_purchased", 1);
				} else {
					p.sendMessage("§cYou don't have enough money. You need at least: $" + perm.getPrice() + ".");
					p.playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1F);

					return;
				}
				
			}
			
			
			
		}
		
	}

}
