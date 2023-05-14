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

public class MiningShop extends SmartMob {

	// item,type,buyprice,sellprice,def amnt,refill time

	Shop shop = new Shop("Mining Shop", 5, 
			new Shop.ShopItem(Item.cobblestone, ShopItemType.SELL_ONLY, 0, 5.8, 0, 50),
			new Shop.ShopItem(Item.stone, ShopItemType.SELL_ONLY, 0, 8, 0, 50),
			new Shop.ShopItem(Item.glowstoneDust, ShopItemType.SELL_ONLY, 0, 6, 0, 50),
			new Shop.ShopItem(Item.ironIngot, ShopItemType.SELL_ONLY, 0, 125, 0, 180),
			new Shop.ShopItem(Item.goldIngot, ShopItemType.SELL_ONLY, 0, 350, 0, 180),
			new Shop.ShopItem(Item.coal, ShopItemType.SELL_ONLY, 0, 30, 0, 50),
			new Shop.ShopItem(Item.gravel, ShopItemType.SELL_ONLY, 0, 4, 0, 50),
			new Shop.ShopItem(Item.flint, ShopItemType.SELL_ONLY, 0, 6, 0, 50)
			
			);


	public MiningShop(Location spawn_loc) {

		super(spawn_loc, EntityType.ZOMBIE, "Mining Shop", false, true, false);

		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "", "");
		Util.setHeadTexture(head,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWI1ZTIzNTE5NGRkNmNkYWQ4ZDA3MTE4YTM3NWEzMzczZjY1YjVkMjMzMTExM2EzNTMxYjA4ODdiMDYwOTQifX19");

		ItemStack body = Util.createItem(Material.LEATHER_CHESTPLATE, 1, (short) 0, "", "");
		Util.leatherSetColor(body, Color.YELLOW);

		this.setEquipment(head, body, null, null, new ItemStack(Material.IRON_PICKAXE));

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard())
			NPCSay(p, "Hi, Guard!");
		else
			NPCSay(p, "Diamonds... Precious diamonds... Uh hi!");

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
