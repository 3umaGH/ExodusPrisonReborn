package items.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import exodusplayer.ExodusPlayer;
import guardwatcher.handlers.GuardWatcher;
import items.Item;
import items.Item.LegalityType;
import items.handlers.ItemConverter;
import statistics.handlers.ServerStatistics;

public class ItemListener implements Listener {

	@EventHandler
	public static void convertCrafts(PrepareItemCraftEvent e) {
		ItemConverter.convertItem(e.getInventory().getItem(0),(Player) e.getInventory().getHolder());
	}

	@EventHandler
	public static void convertDrops(PlayerPickupItemEvent e) { // Checks if item
																// needs to be
																// converted on
																// item pickup.
		GuardWatcher.compareItem(e.getItem(), e.getPlayer());
		ItemConverter.convertItem(e.getItem().getItemStack(), e.getPlayer());

		Item item = Item.getItem(e.getItem().getItemStack());

		if (item != null && !item.hasPermission(e.getPlayer()))
			e.setCancelled(true);

		ServerStatistics.incrementKey("items_pickedup", e.getItem().getItemStack().getAmount());
	}

	@EventHandler
	public static void convertFurnaceItems(FurnaceSmeltEvent e) {
		ItemConverter.convertItem(e.getResult(), null);
	}

	@EventHandler
	public static void convertDrops(ItemSpawnEvent e) {
		ItemStack newItem = ItemConverter.convertItem(e.getEntity().getItemStack(),null);

		if (newItem != null)
			e.getEntity().setItemStack(newItem);

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public static void convertInventoryClicks(InventoryClickEvent e) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		Player p = (Player) e.getWhoClicked();

		if(e.isCancelled())
			return;
		
		items.add(e.getCurrentItem());
		items.add(e.getCursor());

		for (ItemStack itemStack : items) {
			try {

				ItemConverter.convertItem(itemStack,p);
				Item item = Item.getItem(itemStack);

				if (item == null)
					return;

				if (!item.hasPermission((Player) e.getWhoClicked())) {
					e.setCancelled(true);
					return;
				}

				if (e.getInventory().getType().equals(InventoryType.ANVIL)
						&& item.getLegal().equals(LegalityType.GuardsOnly)) {

					p.sendMessage("§cYou can't put guards items here!");
					p.getLocation().getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 1F, 0.02F);

					e.setCancelled(true);
					return;
				}

				if (e.isCancelled())
					return;

			} catch (Exception ex) {

			}
		}

	}

	@EventHandler
	public static void logGuardIllegalDrops(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		ExodusPlayer eP = ExodusPlayer.get(p);

		if (eP.isGuard()) { // applies only to guards
			Item item = Item.getItem(e.getItemDrop().getItemStack());
			Location loc = e.getItemDrop().getLocation();

			if (item == null)
				return;

			if (item.getLegal() == LegalityType.Illegal && !eP.isFarmBypass()) {
				GuardWatcher.logItemDrop(e.getItemDrop().getUniqueId(), p.getName(), e.getItemDrop());
				eP.getStats().incrementKey("illegal_drops", 1);
			}

			if (item.getLegal() == LegalityType.GuardsOnly && !eP.isFarmBypass()) {
				GuardWatcher.logItemDrop(e.getItemDrop().getUniqueId(), p.getName(), e.getItemDrop());
				eP.getStats().incrementKey("guard_item_drops", 1);
			}

		}

	}

	@EventHandler
	public static void removeNonDroppableItemsOnDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();

		List<ItemStack> toBeRemoved = new ArrayList<ItemStack>();

		for (ItemStack drop : e.getDrops()) {
			Item item = Item.getItem(drop);

			if (item == null)
				continue;

			if (!item.isDropOnDeath() || item.getLegal().equals(LegalityType.AdminOnly))
				toBeRemoved.add(item.getItemStack());
		}

		for (ItemStack item : toBeRemoved)
			e.getDrops().remove(item);

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onItemPlace(BlockPlaceEvent e) {

		Item item = Item.getItem(e.getItemInHand());

		if (item == null)
			return;

		if (!item.hasPermission(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}

		item.onBlockPlace(e.getPlayer(), e);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onItemInteract(PlayerInteractEvent e) {

		if (e.getItem() == null)
			return;

		Item item = Item.getItem(e.getItem());

		if (item == null)
			return;

		if (!item.hasPermission(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}

		item.onInteract(e.getPlayer(), e);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onItemEntityInteract(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();

		if (p.getItemInHand() == null)
			return;

		Item item = Item.getItem(p.getItemInHand());

		if (item == null)
			return;

		if (!item.hasPermission(p)) {
			e.setCancelled(true);
			return;
		}

		item.onUseOnEntity(e.getPlayer(), e);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerDamageWithItem(EntityDamageByEntityEvent e) {

		if (e.getDamager() instanceof Player) {
			Player damager = (Player) e.getDamager();

			if (damager.getItemInHand() == null)
				return;

			Item item = Item.getItem(damager.getItemInHand());

			if (item == null)
				return;

			if (!item.hasPermission(damager)) {
				e.setCancelled(true);
				return;
			}

			item.onDamageEntity(damager, e);

		}

	}

}
