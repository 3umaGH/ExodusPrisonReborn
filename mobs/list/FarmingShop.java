package mobs.list;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import items.Item;
import mobs.SmartMob;
import shop.Shop;
import shop.Shop.ShopItemType;
import util.Util;

public class FarmingShop extends SmartMob {

	// item,type,buyprice,sellprice,def amnt,refill time

	Shop shop = new Shop("Farming Shop", 5, new Shop.ShopItem(Item.sugarcane, ShopItemType.SELL_ONLY, 0, 1.5, 0, 10),
			new Shop.ShopItem(Item.pumpkin, ShopItemType.SELL_ONLY, 0, 15, 0, 50),
			new Shop.ShopItem(Item.cactus, ShopItemType.SELL_ONLY, 0, 6, 0, 50),
			new Shop.ShopItem(Item.melonslice, ShopItemType.SELL_ONLY, 0, 4.5, 0, 50),
			new Shop.ShopItem(Item.birchWood, ShopItemType.SELL_ONLY, 0, 11.5, 0, 50),
			new Shop.ShopItem(Item.oakWood, ShopItemType.SELL_ONLY, 0, 11.5, 0, 50),
			new Shop.ShopItem(Item.birchSapling, ShopItemType.SELL_ONLY, 0, 12, 0, 50),
			new Shop.ShopItem(Item.oakSapling, ShopItemType.SELL_ONLY, 0, 12, 0, 50),
			new Shop.ShopItem(Item.wool, ShopItemType.SELL_ONLY, 0, 50, 0, 50),
			new Shop.ShopItem(Item.egg, ShopItemType.SELL_ONLY, 0, 80, 0, 50)
			
			);

	public FarmingShop(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Farming Shop", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTg4NjE2ZjBiZmYxMjU3NWUyM2ZiZDI4ZGZjNDhiNzMxNjQ2ODJiYjE1OWIxNmIyNTZhMTA5YWRjOWIwNzEifX19");
		
		ItemStack body = Util.createItem(Material.LEATHER_CHESTPLATE, 1, (short) 0, "", "");
		Util.leatherSetColor(body, Color.PURPLE);

		this.setEquipment(head, body, null, null, new ItemStack(Material.RED_ROSE,1));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {
		
		if(eP.isGuard()) 
			NPCSay(p,"Hello, officer " + p.getName() + ".");
		else {
			if(eP.getAgeHours() < 24) 	
				NPCSay(p,"Hello, new guy! You can sell all kind of plants to me.");
			else
			NPCSay(p,"Hello? Would you like to sell something to me?");
		}
		
		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 1.5F);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				shop.openShop(p);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 1F);
				
			}
		}, 20);
		
	}

}
