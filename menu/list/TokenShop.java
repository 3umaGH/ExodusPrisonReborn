package menu.list;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import exodusplayer.ExodusPlayer;
import items.Item;
import logger.Log;
import logger.Log.LogType;
import menu.Menu;
import util.Util;

public class TokenShop extends Menu {

	private static HashMap<Integer, TokenItem> slots = new HashMap<Integer, TokenItem>();

	public static class TokenItem {

		ItemStack item;
		int price, slot, amount;

		public TokenItem(int slot, ItemStack i, int amount, int price) {
			this.item = i;
			this.amount = amount;
			this.slot = slot;
			this.price = price;

			slots.put(slot, this);
		}

		public TokenItem(int slot, Item i, int amount, int price) {
			new TokenItem(slot, i.getNewItemStack(1), amount, price);
		}

	}
	
	public static void initalize(){
		new TokenItem(0,Item.diamondPickaxe,1,840);
		
		ItemStack item = Item.ironPickaxe.getNewItemStack(1);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
		new TokenItem(1,item,1,60);
		
		item = Item.stonePickaxe.getNewItemStack(1);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
		new TokenItem(2,item,1,30);
		
		new TokenItem(3,Item.ironIngot,32,200);
		new TokenItem(4,Item.birchWood,32,20);
		new TokenItem(5,Item.meth,1,50);
		new TokenItem(6,Item.lockpick,2,330);
		new TokenItem(7,Item.legendaryStash,1,200);
		new TokenItem(8,Item.bigMineBomb,3,90);
		
		item = Item.enchantedBook.getNewItemStack(1);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
		new TokenItem(9,item,1,120);
		
		item = Item.enchantedBook.getNewItemStack(1);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
		new TokenItem(10,item,1,120);
		
		item = Item.enchantedBook.getNewItemStack(1);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		new TokenItem(11,item,1,150);
		
		new TokenItem(12,Item.randomTitle,1,150);
		new TokenItem(13,Item.itemPermissionVoucher,1,630);
		new TokenItem(14,Item.enchantingVoucher_low,1,100);
		new TokenItem(15,Item.enchantingVoucher_mid,1,200);
		new TokenItem(16,Item.enchantingVoucher_high,1,300);
		
		new TokenItem(17,Item.anvil,1,50);
		new TokenItem(18,Item.enchantingTable,1,420);
		new TokenItem(19,Item.xpBottle,1,15);
		new TokenItem(20,Item.brewingStand,2,420);
		
		
		
		Log.log(LogType.NOTIFY,"[INITALIZING] [TOKEN SHOP] Token Shop module initalized successfully.");
		
	}

	public TokenShop() {
		super("Token Shop", 5);

		this.setUpdating(false);

	}

	@Override
	public void loadIcons(Player p) {

		for (TokenItem item : slots.values()) {
			ItemStack displayItem = new ItemStack(item.item);

			ItemMeta meta = displayItem.getItemMeta();
			ArrayList<String> loreList = new ArrayList<String>();

			for (String s : meta.getLore()) {
				loreList.add(s);
			}

			loreList.add(Util.center("§7Left Click To Buy for §6§l" + item.price + " Tokens.", 60));

			meta.setLore(loreList);
			displayItem.setItemMeta(meta);
			displayItem.setAmount(item.amount);

			getInventory().setItem(item.slot, displayItem);
		}

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		e.setCancelled(true);
		ExodusPlayer eP = ExodusPlayer.get(p);

		if (slots.containsKey(e.getRawSlot())) {
			TokenItem item = slots.get(e.getRawSlot());

			if (eP.getTokens() >= item.price) {

				Util.giveItem(p, item.item, item.amount);
				eP.addTokens(-item.price);

				p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 0.98F, 1.4F);
			} else {
				p.sendMessage("§cYou don't have enough tokens! You need at least " + item.price + ".");
				p.playSound(p.getLocation(), Sound.valueOf("VILLAGER_NO"), 0.98F, 1F);
			}
		}

	}

}
