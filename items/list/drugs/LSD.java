package items.list.drugs;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import items.Drug;

public class LSD extends Drug {

	public LSD() {
		super("LSD", Material.INK_SACK);
		this.setData(14);
		
		this.setPriority(true);
		this.setLegal(LegalityType.Illegal);
		this.setDescription("&7Psychedelic drug.");
		
		this.addEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 1,false));
		this.addEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1,false));
		this.addEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 3,false));
		
		
		this.compile();
		
	}

	

}
