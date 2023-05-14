package achievements;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import exodusplayer.ExodusPlayer;
import util.Util;

public abstract class Achievement {
	private static HashMap<String, Achievement> list = new HashMap<String, Achievement>();
	
	public enum Category {
		//FARMING("Farming", Material.RED_ROSE, false),
		COMBAT("Combat", Material.IRON_SWORD, false),
		MINING("Mining", Material.IRON_PICKAXE, false),
		
		GUARDING("Guarding", Material.STICK, true);
		
		private String name;
		private ItemStack icon;
		private boolean guards;
		
		Category(String name, Material icon, boolean guards){
			this.name = name;
			this.icon = Util.createItem(icon, 1, (short) 0, name); 
			this.guards = guards;
		}
		
	}
	
	
	private String name;
	private String description;
	private int points;
	private boolean guards;
	private Category category;
	
	public Achievement(String name, Category category){
		this.name = name;
		this.description = description;
		this.points = 0;
		this.guards = this.category.guards;
		this.category = category;
		
		list.put(name,this);
	}
	
	public abstract void checkRequirements(ExodusPlayer p);
	
	public void completed(ExodusPlayer p){
		p.addAchievement(this);
	}
	

}
