package items.list.other;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import items.Item;
import util.GUI;
import util.Util;

public class EnchantingVoucher extends Item {

	private int tier;

	public EnchantingVoucher(String prefix, int tier) {
		super("&6" + prefix + " Enchanting Voucher", Material.PAPER);

		this.setPriority(false);
		this.addCustomLore("&7Right Click in to open an enchanting table.");

		if (tier == 1)
			this.addCustomLore("&7Offers 1-8 level enchantments.");
		else if (tier == 2)
			this.addCustomLore("&7Offers 8-18 level enchantments.");
		else if (tier == 3)
			this.addCustomLore("&7Offers 18-30 level enchantments.");

		this.addCustomLore("");

		this.addCustomLore("&cDissapears after use.");
		this.setLegal(LegalityType.Legal);

		this.tier = tier;

		this.compile();
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		e.setCancelled(true);

		ExodusPlayer eP = ExodusPlayer.get(p);

		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			if (!eP.isArrested()) {
				p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 0.98F, 1.4F);
				Util.removeFromHand(p);

				switch (tier) {
				case 1:
					p.openEnchanting(new Location(Main.getWorld(), 40, 83, 8), true);
					break;
				case 2:
					p.openEnchanting(new Location(Main.getWorld(), 40, 83, 3), true);
					break;
				case 3:
					p.openEnchanting(new Location(Main.getWorld(), 45, 83, 5), true);
					break;
				}
			} else {
				GUI.sendTitle(p, 10, 60, 10, "", "&4You cannot use this when you are arrested.");
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
