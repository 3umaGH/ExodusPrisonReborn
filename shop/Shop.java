package shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;
import economy.Vault;
import items.Item;
import statistics.handlers.ServerStatistics;
import util.Util;

public class Shop {

	private static HashMap<String, Shop> shops = new HashMap<String, Shop>();
	private static int updateTime = 20;

	public enum ShopItemType {
		BUY_ONLY, SELL_ONLY, BUY_SELL
	};

	private HashMap<Integer, ShopItem> stock = new HashMap<Integer, ShopItem>();
	private int size;
	private Inventory shopInventory;

	public static class ShopItem {

		private Item item;
		private ShopItemType type;
		private int defaultAmount, currentAmount;
		private double buyPrice, sellPrice;
		private ItemStack displayItem;
		
		public ShopItem(Item item, ShopItemType type, double buyPrice, double sellPrice, int defaultAmount, int refillTime) {
			this.item = item;
			this.type = type;

			this.buyPrice = buyPrice;
			this.sellPrice = sellPrice;
			this.defaultAmount = defaultAmount;
			this.currentAmount = defaultAmount;
			this.displayItem = item.getNewItemStack(defaultAmount);

			updateDisplay();

			new BukkitRunnable() {
				public void run() {

					refill();

				}

			}.runTaskTimer(Main.getPlugin(), 0L, refillTime);

		}

		private void updateDisplay() {
			ItemMeta meta = displayItem.getItemMeta();
			ArrayList<String> loreList = new ArrayList<String>();

			for (String s : meta.getLore()) {
				loreList.add(s);
			}

			loreList.add(" ");
			loreList.add("§e§m------------------------------------------- ");

			switch (this.type) {

			case BUY_ONLY:
				loreList.add(Util.center("§aYou can buy 1x of this item for §e$" + buyPrice, 60));
				loreList.add(Util.center("§cYou cannot sell this item to this shop.", 60));

				loreList.add(" ");

				loreList.add(Util.center("§7Left Click To Buy.", 60));

				break;

			case SELL_ONLY:
				loreList.add(Util.center("§cYou cannot buy this item in this shop.", 60));
				loreList.add(Util.center("§aYou can sell 1x of this item for §e$" + sellPrice, 60));

				loreList.add(" ");

				loreList.add(Util.center("§7Left Click in your inventory to sell.", 60));

				break;

			case BUY_SELL:
				loreList.add(Util.center("§aYou can buy 1x of this item for §e$" + buyPrice, 60));
				loreList.add(Util.center("§aYou can sell 1x of this item for §e$" + sellPrice, 60));

				loreList.add(" ");

				loreList.add(Util.center("§7Left Click To Buy.", 60));
				loreList.add(Util.center("§7Left Click in your inventory to sell.", 60));

				break;
			}

			loreList.add(" ");

			loreList.add(Util.center("§e[1x - Left Click, 10x - Right Click, 64x - Shift Click]", 60));

			loreList.add(" ");

			meta.setLore(loreList);
			displayItem.setItemMeta(meta);

		}

		private void refill() {

			if (currentAmount < defaultAmount)
				currentAmount++;

			if (defaultAmount == 0 && currentAmount > 0)
				currentAmount--;

			updateAmount();

		}

		private void updateAmount() {
			if (currentAmount >= 64)
				displayItem.setAmount(64);
			else
				displayItem.setAmount(currentAmount);
		}

	}

	public Shop(String name, int rowsSize, ShopItem... items) {
		this.size = rowsSize * 9;

		this.shopInventory = Bukkit.createInventory(null, size, name);

		for (ShopItem itm : items) {
			this.stock.put(stock.size(), itm);
			this.shopInventory.setItem(stock.size() - 1, itm.displayItem);
		}

		new BukkitRunnable() {
			public void run() {

				updateStock();

			}

		}.runTaskTimer(Main.getPlugin(), 0L, updateTime);

		shops.put(name, this);

	}

	private void updateStock() {
		for (Entry<Integer, ShopItem> entry : stock.entrySet()) {
			entry.getValue().updateAmount();
			shopInventory.setItem(entry.getKey(), entry.getValue().displayItem);
		}
	}

	public void openShop(Player p) {
		p.openInventory(shopInventory);
	}

	public static HashMap<String, Shop> getShops() {
		return shops;
	}

	public static void shopClick(Shop shop, int slot, InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		int amount = 1;

		if (e.isRightClick()) 
			amount = 10;
		
		if(e.isShiftClick()) {
			amount = e.getCurrentItem().getAmount();
			
			if(amount <= 0)
				amount = 1;
		}

		e.setCancelled(true);

		if (slot >= 0 && slot < shop.size) { // Click inside of a shop
			ShopItem clickedItem = shop.stock.get(slot);
			
			if (clickedItem == null) {
				return; // Clicked empty spot.
			}

			if (clickedItem.type.equals(ShopItemType.SELL_ONLY)) {

				p.sendMessage("§cThis shop only buys this item.");
				p.playSound(p.getLocation(), Sound.valueOf("VILLAGER_NO"), 0.98F, 1F);

				return;
			}

			if (clickedItem.currentAmount - amount < 0) {

				p.sendMessage("§cNot enough of this item.");
				p.playSound(p.getLocation(), Sound.valueOf("VILLAGER_NO"), 0.98F, 1F);

				return; // Out of stock
			}

			if (Vault.takeMoney(p, Util.round(amount * clickedItem.buyPrice,2), false)) {

				Util.giveItem(p, clickedItem.item.getItemStack(), amount);
				clickedItem.currentAmount = clickedItem.currentAmount - amount;
				shop.updateStock();

				ServerStatistics.incrementKey("items_bought", amount);

			} else {

				p.sendMessage("§cYou don't have enough money. You need at least: $" + amount * clickedItem.buyPrice);
				p.playSound(p.getLocation(), Sound.valueOf("VILLAGER_NO"), 0.98F, 1F);

				return;
			}

		} else { // click inside player inventory or outside
			ItemStack clickedStack = e.getCurrentItem();
			ShopItem shopItem = null;

			if (clickedStack == null || clickedStack.getType().equals(Material.AIR)) {
				return;
			}

			for (ShopItem item : shop.stock.values()) {
				if (item.item.getItemStack().getItemMeta().getDisplayName().equals(clickedStack.getItemMeta().getDisplayName())) 
					shopItem = item;
				
			}

			if (shopItem == null || shopItem.type.equals(ShopItemType.BUY_ONLY)) {

				p.sendMessage("§cYou can't sell this item here.");
				p.playSound(p.getLocation(), Sound.valueOf("VILLAGER_NO"), 0.98F, 1F);

				return;

			}

			if (clickedStack.getAmount() >= amount) {

				if (clickedStack.getAmount() - amount == 0) {
					e.setCurrentItem(new ItemStack(Material.AIR));
				} else {
					e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - amount);
				}

				Vault.addMoney(p, Util.round(amount * shopItem.sellPrice,2), false);
				shopItem.currentAmount = shopItem.currentAmount + amount;
				shop.updateStock();

				ServerStatistics.incrementKey("items_sold", amount);

			} else {

				p.sendMessage("§cYou can't sell that much.");
				p.playSound(p.getLocation(), Sound.valueOf("VILLAGER_NO"), 0.98F, 1F);

				return;

			}

		}
	}

}
