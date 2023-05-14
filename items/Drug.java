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
import org.bukkit.potion.PotionEffect;

import exodusplayer.ExodusPlayer;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class Drug extends Item {

	private List<PotionEffect> effects = new ArrayList<PotionEffect>();

	public Drug(String name, Material material) {
		super(name, material);

		this.setCooldown(60);
		this.addCustomLore("");
		this.addCustomLore(Util.center("&7[Shift + Right Click]", 60));
		this.addCustomLore("");

		this.compile();
	}

	public void addEffect(PotionEffect eff) {
		this.effects.add(eff);

		this.addCustomLore(Util.center("&7" + Util.makeFirstCapital(eff.getType().getName()).replace("_", " ")
				+ " " + Util.arabicToRoman(eff.getAmplifier()) + " : &f" + eff.getDuration() / 20
				+ " seconds.", 60));
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {

		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			if (!p.isSneaking())
				return;
			
			if(this.isCooleddown(p)) {
				GUI.sendTitle(p, 0, 50, 10, "","§cWait a little bit!");
				return;
			}

			for (PotionEffect eff : effects)
				p.addPotionEffect(eff);

			GUI.sendTitle(p, 5, 20, 5, "", this.getName() + " consumed.");
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 0.98F, 4F);

			removeFromHand(p);

			ExodusPlayer.get(p).getStats().incrementKey("drugs_used", 1);
			ServerStatistics.incrementKey("drugs_used", 1);
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
