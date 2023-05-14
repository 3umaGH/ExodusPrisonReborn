package menu.list;

import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import exodusplayer.classes.Offenses.Offense;
import exodusplayer.classes.OnlineDay;
import items.ItemUpgrade;
import menu.Menu;
import permission.Permissions.Permission;
import statistics.handlers.DailyStatistics;
import statistics.handlers.ServerStatistics;
import util.Util;

public class DateStats extends Menu {

	public DateStats() {
		super("Date Stats", 1);


		this.setUpdating(true);
	}

	@Override
	public void loadIcons(Player p) {

		String desc = "";
		
		for(Entry<String, Long> entry : DailyStatistics.getStatistics().entrySet()){
			if(entry.getKey().startsWith("max_players_")) {
				desc = desc + "&7" + entry.getKey() + " : &f" + entry.getValue() + "/n";
			}
		}

		getInventory().setItem(0, Util.createItem(Material.PAPER, 1, (short) 0, "&eMax Players", desc));
		desc = "";
		
		for(Entry<String, Long> entry : DailyStatistics.getStatistics().entrySet()){
			if(entry.getKey().startsWith("players_time_spent")) {
				desc = desc + "&7" + entry.getKey() + " : &f" + entry.getValue() + "/n";
			}
		}

		getInventory().setItem(1, Util.createItem(Material.PAPER, 1, (short) 0, "&eTime Spent", desc));
		desc = "";
		
		for(Entry<String, Long> entry : DailyStatistics.getStatistics().entrySet()){
			if(entry.getKey().startsWith("logins_")) {
				desc = desc + "&7" + entry.getKey() + " : &f" + entry.getValue() + "/n";
			}
		}

		getInventory().setItem(2, Util.createItem(Material.PAPER, 1, (short) 0, "&eLogins", desc));
		desc = "";
		
		for(Entry<String, Long> entry : DailyStatistics.getStatistics().entrySet()){
			if(entry.getKey().startsWith("uniques_")) {
				desc = desc + "&7" + entry.getKey() + " : &f" + entry.getValue() + "/n";
			}
		}

		getInventory().setItem(3, Util.createItem(Material.PAPER, 1, (short) 0, "&eUniques", desc));
		desc = "";
		
	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		e.setCancelled(true);
	}

}
