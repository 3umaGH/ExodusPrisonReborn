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

public class Cook extends SmartMob {

	// item,type,buyprice,sellprice,def amnt,refill time

	Shop shop = new Shop("Food Shop", 5, 
			new Shop.ShopItem(Item.apple, ShopItemType.BUY_SELL, 15, 10, 16, 50),
			new Shop.ShopItem(Item.steak, ShopItemType.BUY_ONLY, 20, 0, 8, 50),
			new Shop.ShopItem(Item.bread, ShopItemType.BUY_ONLY, 15, 0, 32, 50),
			new Shop.ShopItem(Item.cookedfish, ShopItemType.BUY_ONLY, 16, 0, 16, 50),
			new Shop.ShopItem(Item.cookedChicken, ShopItemType.BUY_ONLY, 16, 0, 8, 50));

	public Cook(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Cook", false, true, false);
		
		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU0ZDdmZGY5YTdlYTI5MzZkMjg2NzVhNTkzOTFkNTI2OGUxODYyOTA5ODgxZjk3YTI4MjBjYTg4Y2QyZiJ9fX0=");

		ItemStack body = Util.createItem(Material.LEATHER_CHESTPLATE, 1, (short) 0, "", "");
		Util.leatherSetColor(body, Color.WHITE);

		this.setEquipment(head, body, null, null, new ItemStack(Material.APPLE));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard())
			NPCSay(p, "Hi, Would you like to eat something?");
		else
			NPCSay(p, "Hey. Would you like to eat?");

		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.9F, 0.9F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				shop.openShop(p);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.7F);

			}
		}, 20);
	}

}
