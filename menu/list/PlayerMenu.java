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

public class PlayerMenu extends Menu {

	private Player who;
	private boolean adminView = false;

	public PlayerMenu(Player p, Player who, boolean adminView) {
		super("Player " + who.getName(), 1);

		this.who = who;
		this.adminView = adminView;

		new BukkitRunnable() {
			public void run() {

				if (getInventory().getViewers().size() <= 0) {
					cancel();
					return;
				}

				loadIcons(who);

			}

		}.runTaskTimer(Main.getPlugin(), 0L, 10L);

		p.openInventory(this.getInventory());

		this.setUpdating(false);
	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(who);

		String permissions = "";
		String offenses = "";
		String statistics = "";
		String serverStatistics = "";
		String guardUpgrades = "";
		String data = "";
		String titles = "";
		String onlineDays = "";
		String dateStats = "";

		for (Permission perm : Permission.values()) {

			if (!perm.isVisibleInPassport())
				continue;

			if (eP.getPermissions().contains(perm))
				permissions = permissions + "&a" + perm.getName() + "/n";
			else
				permissions = permissions + "&c" + perm.getName() + "/n";

		}

		for (Offense offense : eP.getOffenses().getOffenses().values()) {
			offenses = offenses + "&7" + offense.getFormattedTime() + " : " + offense.getDetails() + "/n";
		}

		for (Entry<String, Long> entry : eP.getStats().getStatistics().entrySet()) {
			statistics = statistics + "&7" + entry.getKey() + " : &f" + entry.getValue() + "/n";
		}

		boolean newLine = false;
		for (Entry<String, Long> entry : ServerStatistics.getStatistics().entrySet()) {
			if (entry.getKey().startsWith("date-data") || entry.getKey().startsWith("blocks"))
				continue;

			String value = String.valueOf(entry.getValue());

			if (entry.getKey().startsWith("time_spent_by_players")) {
				value = Long.valueOf(Integer.valueOf(value)/60) + " minutes.";
			}

			serverStatistics = serverStatistics + Util.center("&7" + entry.getKey() + " : &f" + value, 50)
					+ (newLine ? "/n" : " | ");

			if (newLine)
				newLine = false;
			else
				newLine = true;
		}

		for (Entry<ItemUpgrade, Integer> entry : eP.getUpgrades().getItemUpgrades().entrySet()) {
			if (entry.getValue() >= 0)
				guardUpgrades = guardUpgrades + entry.getKey().getUpgradeByLevel(entry.getValue()).getItem().getName()
						+ "/n";
		}

		for (Entry<String, String> entry : eP.getData().getData().entrySet()) {
			String value = entry.getValue();

			try {
				if (Long.parseLong(value) > Integer.MAX_VALUE)
					value = Util.formatTimeMillis(Long.parseLong(value), "yyyy/MM/dd HH:mm:ss");
			} catch (Exception ex) {

			}

			data = data + "&7" + entry.getKey() + " : &f" + value + "/n";
		}

		for (OnlineDay day : eP.getOnlineTime()) {
			onlineDays = onlineDays + "&7" + day.getDate() + " : &f" + day.getTime() + "/n";
		}

		for (Entry<String, Long> entry : DailyStatistics.getStatistics().entrySet()) {
			if (entry.getKey().startsWith("players_time_spent"))

				dateStats = dateStats + "&7" + entry.getKey() + " : &f"
						+ TimeUnit.SECONDS.toHours(entry.getValue()) + " hours/n";
			else
				dateStats = dateStats + "&7" + entry.getKey() + " : &f" + entry.getValue() + "/n";
		}

		data = data + "&7" + "guard : &f" + eP.isGuard() + "/n";
		data = data + "&7" + "farmBypass : &f" + eP.isFarmBypass() + "/n";
		data = data + "&7" + "frozen : &f" + eP.isFrozen() + "/n";

		for (String title : eP.getTitles())
			titles = titles + "&7" + title + "/n";

		getInventory().setItem(0, Util.createItem(Material.MAP, 1, (short) 0, "&ePlayer Data", data));
		getInventory().setItem(1, Util.createItem(Material.PAPER, 1, (short) 0, "&ePermissions", permissions));
		getInventory().setItem(2, Util.createItem(Material.FEATHER, 1, (short) 0, "&eStatistics", statistics));
		getInventory().setItem(3,
				Util.createItem(Material.DIAMOND_SWORD, 1, (short) 0, "&eGuard Upgrades", guardUpgrades));
		getInventory().setItem(4, Util.createItem(Material.BOOK_AND_QUILL, 1, (short) 0, "&eTitles", titles));
		getInventory().setItem(5, Util.createItem(Material.WATCH, 1, (short) 0, "&eOnline Time", onlineDays));

		if (adminView) {
			getInventory().setItem(6, Util.createItem(Material.BARRIER, 1, (short) 0, "&cOffenses", offenses));
			getInventory().setItem(7, Util.createItem(Material.COMPASS, 1, (short) 0, "&eDate Stats", dateStats));

		}

		getInventory().setItem(8, Util.createItem(Material.MAP, 1, (short) 0, "&eServer Statistics", serverStatistics));

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		e.setCancelled(true);
		
		if(e.getRawSlot() == 7 && adminView){
			new DateStats().openMenu(p);
		}
	}

}
