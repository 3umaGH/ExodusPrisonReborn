package items.list.drugs;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import items.Drug;

public class CactusGreen extends Drug {

	public CactusGreen() {
		super("Marijuana", Material.INK_SACK);
		this.setData(2);
		
		this.setPriority(true);
		this.setLegal(LegalityType.Illegal);
		this.setDescription("&7Cannabis is psychoactive drug.");
		
		this.addEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 1,false));
		this.addEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1,false));
		this.addEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 3,false));
		
		
		this.compile();
		
	}

	

}
