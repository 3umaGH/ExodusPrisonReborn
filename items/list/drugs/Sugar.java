package items.list.drugs;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import items.Drug;

public class Sugar extends Drug {

	public Sugar() {
		super("Cocaine", Material.SUGAR);
		
		this.setPriority(true);
		this.setLegal(LegalityType.Illegal);
		this.setDescription("&7Looks like sugar, smells like sugar.");
		this.compile();
		
		this.addEffect(new PotionEffect(PotionEffectType.SPEED, 150, 2,false));
		
		this.compile();
		
	}

}
