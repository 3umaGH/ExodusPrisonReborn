package mobs.list;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import items.Item;
import mobs.SmartMob;
import shop.Shop;
import shop.Shop.ShopItemType;
import util.Util;

public class BlackMarketBrewingWitch extends SmartMob {

	// item,type,buyprice,sellprice,def amnt,refill time

	Shop shop = new Shop("Brewing Shop", 5, 
			new Shop.ShopItem(Item.awkwardPotion, ShopItemType.BUY_ONLY, 100, 0, 8, 3000),
			new Shop.ShopItem(Item.gunpowder, ShopItemType.BUY_ONLY, 5000, 0, 8, 3600),
			new Shop.ShopItem(Item.fermentedSpiderEye, ShopItemType.BUY_ONLY, 350, 0, 8, 50),
			new Shop.ShopItem(Item.glistringMelon, ShopItemType.BUY_ONLY, 1000, 0, 8, 50),
			new Shop.ShopItem(Item.goldenCarrot, ShopItemType.BUY_ONLY, 500, 0, 8, 50),
			new Shop.ShopItem(Item.ghastTear, ShopItemType.BUY_ONLY, 750, 0, 8, 50),
			new Shop.ShopItem(Item.blazePowder, ShopItemType.BUY_ONLY, 750, 0, 8, 50)
			);

	public BlackMarketBrewingWitch(Location spawn_loc) {

		super(spawn_loc, EntityType.VILLAGER, "Witch", false, true, false);
		
		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY2NDFjNWFmYjY2OGQ1YzllODM4NzE0OGU5MzMxZGU2MzNlNDE3MTRkYTVkYjY4MGQ3YjE0ODE3OGU5In19fQ==");

		this.setProfession(Profession.LIBRARIAN);
		this.setEquipment(head, null, null, null, null);

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard())
			NPCSay(p, "Hi, officer. We shouldn't be talking, but I'll make an exception.");
		else
			NPCSay(p, "Hello. Would you like to learn secrets of potion brewing?");

		p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.9F, 1.8F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				shop.openShop(p);
				p.getWorld().playSound(getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 1.6F);

			}
		}, 20);
	}

}
