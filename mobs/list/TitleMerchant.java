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

public class TitleMerchant extends SmartMob {

	// item,type,buyprice,sellprice,def amnt,refill time

	Shop shop = new Shop("Title Merchant", 5, 
			new Shop.ShopItem(Item.randomTitle, ShopItemType.BUY_ONLY, 25000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_1, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_2, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_3, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_3, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_4, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_5, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_6, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_7, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_8, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_9, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_10, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_11, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_12, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_13, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_14, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_15, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_16, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_17, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_18, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_19, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_20, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_21, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_22, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_23, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_24, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_25, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_26, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_27, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_28, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_29, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000),
			new Shop.ShopItem(Item.title_30, ShopItemType.BUY_ONLY, 50000, 0, 1, 216000)
		);


	public TitleMerchant(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Title Merchant", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDZhOTM2YTZmY2ZkY2M0ZmJlYmZiNjg4OTQ1ZGUxNzRjYWFkMTkxMmEzY2VmOGRkMjM2NDVjZDI1MGVhOWVmZSJ9fX0=");


		this.setEquipment(head, null,null,null, new ItemStack(Material.PAPER));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard()) {
			NPCSay(p, "Hi! Would you like to change your title?");
		} else {
			NPCSay(p, "Yo. I can make other prisoners call as you wish for some dollarz...");
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
