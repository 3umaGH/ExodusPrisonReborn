package items.list.other;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import items.Item;
import util.GUI;

public class ItemPermVoucher extends Item {
	public ItemPermVoucher() {
		super("&6[item] Permission Voucher", Material.PAPER);

		this.setPriority(false);
		this.setDescription("&7Allows you to showcase your item in chat by typing [item].");
		this.addCustomLore("&cDissapears after use.");
		this.setLegal(LegalityType.Legal);

		this.compile();
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		e.setCancelled(true);


		if(p.hasPermission("exodus.item")) {
			GUI.sendTitle(p, 10, 60, 10, "", "&cYou already have this permission.");
			p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 1.4F);
		} else {
			p.sendMessage("§6§l+1 [Item] Permission.");
			p.sendMessage("§7Type [item] in chat to show your item in hand to the public.");
			
			p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 0.98F, 1.4F);

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
