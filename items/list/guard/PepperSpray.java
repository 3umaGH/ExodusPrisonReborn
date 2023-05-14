package items.list.guard;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import exodusplayer.ExodusPlayer;
import items.Item;
import sound.CustomSound;
import sound.CustomSound.SoundType;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class PepperSpray extends Item {

	private int level;

	public PepperSpray(int level) {
		super("Pepper Spray [LV. " + level + "]", Material.INK_SACK);

		this.level = level;
		
		this.setData(9);
		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&7Make prisoners cry like little babies.", 60));
		this.addCustomLore(" ");
		this.addCustomLore(Util.center("&6&lRight Click - Spray", 60));

		this.setCooldown(60);
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

			if(this.isCooleddown(p)) {
				GUI.sendTitle(p, 0, 50, 10, "","§cWait a little bit!");
				return;
			}
			
			if (ExodusPlayer.get(p).isGuard() && !ExodusPlayer.get(clicked).isGuard()) {

				switch (level) {

				case 1:
					clicked.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					clicked.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 2));
					break;
				case 2:
					clicked.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 250, 2));
					clicked.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 500, 2));
					clicked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 250, 1));
					break;
				case 3:
					clicked.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 350, 3));
					clicked.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 5));
					clicked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1));
					break;

				}
				
				activateCooldown(p);
				
				clicked.getLocation().getWorld().playSound(clicked.getLocation(), Sound.CAT_HISS, 1F, 0.3F);
				CustomSound.playSound(SoundType.PEPPER_SPRAY, clicked.getLocation());
				
				GUI.sendTitle(clicked, 0, 50, 10, "§7You have been pepper sprayed", "§7by " + p.getName() + ".");
				GUI.sendTitle(p, 0, 50, 10, "","§eYou have pepper sprayed " + clicked.getName() + ".");
				
				/*if(uses <= 0){
					GUI.sendTitle(p, 0, 50, 10, "","§cPepper Spray is empty");
					p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
					Util.removeFromHand(p);
				}*/
				
				ServerStatistics.incrementKey("pepper_sprays", 1);
				ExodusPlayer.get(clicked).getStats().incrementKey("got_pepper_sprayed", 1);
				ExodusPlayer.get(p).getStats().incrementKey("pepper_sprays", 1);

			}

		}
	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub
		
	}

}
