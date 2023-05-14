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

public class BlackMarketShop extends SmartMob {

	// item,type,buyprice,sellprice,def amnt,refill time

	Shop shop = new Shop("Blackmarket", 5, 
			new Shop.ShopItem(Item.meth, ShopItemType.BUY_ONLY, 3000, 0, 1, 864000),
			new Shop.ShopItem(Item.LSD, ShopItemType.BUY_ONLY, 500, 0, 8, 72000),
			new Shop.ShopItem(Item.ironHelmet, ShopItemType.BUY_ONLY, 1750, 0, 3, 24000),
			new Shop.ShopItem(Item.ironChestplate, ShopItemType.BUY_ONLY, 2500, 0, 3, 24000),
			new Shop.ShopItem(Item.ironLeggings, ShopItemType.BUY_ONLY, 2000, 0, 3, 24000),
			new Shop.ShopItem(Item.ironBoots, ShopItemType.BUY_ONLY, 1500, 0, 3, 24000),
			new Shop.ShopItem(Item.stoneSword, ShopItemType.BUY_ONLY, 500, 0, 5, 12000),
			new Shop.ShopItem(Item.ironSword, ShopItemType.BUY_ONLY, 1000, 0, 5, 12000)
		);


	public BlackMarketShop(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Blackmarket Shop", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDlhNDg4NjMwMTdlNGZmZjJiYmNjZGYyYzA1M2FjYjdiZGM3Y2FiYzM4MmVjOGI2ZGI1ODI2NDgwZWM5YSJ9fX0=");


		this.setEquipment(head, new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS),new  ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.DIAMOND_SWORD));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard()) {
			NPCSay(p, "Get away from me, pig!");
			p.damage(16.0);
			
			p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_DEATH, 0.98F, 0.5F);
			return; 
		}
		else {
			NPCSay(p, "Hi. This is our hideout. Would you like to buy something?");
		}

		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.9F, 0.6F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				shop.openShop(p);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.58F);

			}
		}, 20);
	}

}
