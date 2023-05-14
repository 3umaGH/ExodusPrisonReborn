package items.list.drugs;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import items.Drug;

public class Meth extends Drug {

	public Meth() {
		super("Crystal meth", Material.PRISMARINE_CRYSTALS);
		this.setData(0);
		
		this.setPriority(true);
		this.setLegal(LegalityType.Illegal);
		this.setDescription("&7A strong central nervous system stimulant.");
		
		this.addEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 2,false));
		this.addEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1,false));
		this.addEffect(new PotionEffect(PotionEffectType.ABSORPTION, 700, 2,false));
		
		this.addEffect(new PotionEffect(PotionEffectType.CONFUSION, 800, 2,false));
		this.addEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1,false));
		
		
		this.compile();
		
	}

	

}
