package mobs.list;

import org.bukkit.Bukkit;
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

public class Butcher extends SmartMob {

	// item,type,buyprice,sellprice,def amnt,refill time

	Shop shop = new Shop("Meat Shop", 5, 
			new Shop.ShopItem(Item.rawBeef, ShopItemType.SELL_ONLY, 0, 2, 0, 20),
			new Shop.ShopItem(Item.leather, ShopItemType.SELL_ONLY, 0, 2.5, 0, 20));

	public Butcher(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Crazy Butcher", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBhMTQzMjNiYTkzNGE0N2Q5NzY4OWUxMzZkN2IzYzZhODI2ZTgzNDMxZjBkNzRkZDY5NThlNzY3NWQ1YTkifX19");
	

		this.setEquipment(head, new ItemStack(Material.LEATHER_CHESTPLATE), null, null, new ItemStack(Material.IRON_AXE,1));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {
		
		if(eP.isGuard()) 
			NPCSay(p,"Police! U meat have please? Meat!");
		else
			NPCSay(p,"I want meat. I like eat meat.");
		
		
		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.7F);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				shop.openShop(p);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.88F);
				
			}
		}, 20);
		
	}

}
