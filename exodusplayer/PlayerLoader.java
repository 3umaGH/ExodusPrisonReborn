package exodusplayer;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import bukkit.exodusprison.spurkle.Main;
import cells.Cell;
import cells.handler.CellHandler;
import exodusplayer.classes.Offenses.Offense;
import exodusplayer.classes.Offenses.OffenseType;
import exodusplayer.classes.OnlineDay;
import handlers.InvisiblePlayer;
import items.ItemUpgrade;
import logger.Log;
import logger.Log.LogType;
import mysql.MySQL;
import permission.Permissions.Permission;
import statistics.handlers.DailyStatistics;
import statistics.handlers.ServerStatistics;
import tutorial.handlers.TutorialHandler;
import tutorial.handlers.TutorialHandler.Tutorial;
import util.GUI;
import util.Util;

public class PlayerLoader {

	public static Location spawn = new Location(Main.getWorld(), 0.5, 81.0, 0.5, 90, 0);

	public static String backupPlayer(Player p, String jsonString) {

		String path = Bukkit.getServer().getWorldContainer().getAbsolutePath() + "\\local_player_backup\\"
				+ p.getUniqueId();

		/*try {
			FileUtils.writeStringToFile(new File(path), jsonString);
		} catch (IOException e) {

		}*/
		
		return path;

	}

	@SuppressWarnings("unchecked")
	public static void savePlayer(ExodusPlayer p, boolean silent, boolean async, boolean unloadPlayer) {

		if (!p.loaded)
			return;

		if (!silent) {
			p.getPlayer().sendMessage("§a§m-----------------------------------------------------");
			p.getPlayer().sendMessage(Util.center("§aSaving your profile...", 76));
			p.getPlayer().sendMessage("§a§m-----------------------------------------------------");
		}

		JSONObject obj = new JSONObject();

		for (Entry<String, String> entry : p.getData().getData().entrySet())
			obj.put("data_" + entry.getKey(), entry.getValue());

		for (Entry<String, Long> entry : p.getStats().getStatistics().entrySet())
			obj.put("statistics_" + entry.getKey(), entry.getValue());

		for (Entry<Integer, Offense> entry : p.getOffenses().getOffenses().entrySet()) {

			if (p.getOffenses().getOffenses().size() >= 31)
				continue;

			String value = entry.getValue().getType().toString() + "SPLT" + entry.getValue().getTime() + "SPLT"
					+ entry.getValue().getDetails();
			obj.put("offense_" + entry.getKey(), value.replace("§", "&"));
		}

		for (Permission perm : p.getPermissions()) {
			obj.put("permission_" + perm.toString(), "1");
		}

		for (String title : p.getTitles()) {
			obj.put("title_" + title, "1");
		}

		for (ItemUpgrade entry : p.getUpgrades().getItemUpgrades().keySet()) {
			obj.put("itemUpgrade_" + entry.getName(), p.getUpgrades().getLevel(entry));
		}

		for (OnlineDay timePlayed : p.getOnlineTime()) {

			if (p.getOnlineTime().size() >= 31)
				p.getOnlineTime().remove(0);

			obj.put("online_time_" + timePlayed.getDate(), timePlayed.getSeconds());
		}

		String jsonString = obj.toJSONString();

		if (!async)
			MySQL.savePlayerConfiguration(p.player, jsonString);
		else {

			new BukkitRunnable() {
				@Override

				public void run() {
					MySQL.savePlayerConfiguration(p.player, jsonString);
				}

			}.runTaskAsynchronously(Main.getPlugin());
		}

		if (unloadPlayer) {
			p.onPlayerQuit();
			ExodusPlayer.getOnlinePlayers().remove(p);
		}
	}

	public static void loadPlayer(Player p) {

		// Util.clearChat(p);

		/*p.sendMessage("§c§m-----------------------------------------------------");
		p.sendMessage(Util.center("§cLoading your profile...", 76));
		p.sendMessage("§c§m-----------------------------------------------------");*/
		
		GUI.sendTitle(p, 0, 10000, 0, "&cLoading your profile...", "Please wait...");

		InvisiblePlayer.hidePlayer(p);
		MySQL.loadPlayerConfiguration(p);

	}

	public static void setupPlayer(Player p, String jsonString) {
		try {
			ExodusPlayer player = new ExodusPlayer(p);

			if (jsonString == null) {

				// This is a new player!
				for (Permission perm : Permission.values())
					if (perm.isDefaultPerm())
						player.getPermissions().add(perm);

				player.getPermissions().add(Permission.BEGINNERS_MINE);

				GUI.sendTitle(p, 10, 80, 10, "&6Tutorial",
						"&ePlease watch tthe tutorial because this server is a little bit different.");

				ServerStatistics.incrementKey("unique_logins", 1);
				DailyStatistics.incrementKey("uniques", 1);

				Util.globalMessage(TutorialHandler.generateWelcomeText(player.getPlayer(), player.getPrisonerNumber()));
				Log.log(LogType.NOTIFY, "New player connected, creating entry in database.");

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					public void run() {
						InvisiblePlayer.showPlayer(p);
					}
				}, 1L);

			} else {

				JSONParser parser = new JSONParser();

				Object obj = parser.parse(jsonString);
				JSONObject jsonObject = (JSONObject) obj;

				for (Object entry : jsonObject.keySet()) { // Load player data

					if (entry.toString().startsWith("data_")) {
						String name = entry.toString().replace("data_", "");

						player.getData().getData().put(name, (String) jsonObject.get(entry));
					}

					if (entry.toString().startsWith("statistics_")) {
						String name = entry.toString().replace("statistics_", "");

						player.getStats().getStatistics().put(name, (Long) jsonObject.get(entry.toString()));
					}

					if (entry.toString().startsWith("offense_")) {
						String name = entry.toString().replace("offense_", "");
						String value = (String) jsonObject.get(entry);
						String data[] = value.split("SPLT");

						Offense offense = new Offense(OffenseType.valueOf(data[0]), data[2].replace("&", "§"));
						offense.setTime(Long.valueOf(data[1]));
						offense.setId(Integer.valueOf(name));

						player.getOffenses().getOffenses().put(Integer.valueOf(name), offense);

					}

					if (entry.toString().startsWith("permission_")) {
						String name = entry.toString().replace("permission_", "");
						player.addPermission(Permission.valueOf(name));
					}

					if (entry.toString().startsWith("title_")) {
						String name = entry.toString().replace("title_", "");
						player.titles.add(name);
					}

					if (entry.toString().startsWith("itemUpgrade_")) {
						String name = entry.toString().replace("itemUpgrade_", "");

						ItemUpgrade upgrade = ItemUpgrade.getByName(name);

						if (upgrade == null) // some weird crap happened here
							continue;

						player.getUpgrades().getItemUpgrades().put(upgrade,
								Util.LongToInt((Long) jsonObject.get(entry.toString())));
					}

					if (entry.toString().startsWith("online_time_")) {
						String name = entry.toString().replace("online_time_", "");
						player.getOnlineTime()
								.add(new OnlineDay(name, Util.LongToInt((long) jsonObject.get(entry.toString()))));

					}

					if (player.getTutorial() != -1) { // continue tutorial
														// on disconnect
						TutorialHandler.startTutorial(player, Tutorial.getTutorial(player.getTutorial()));
					}

					// update cell
					if (player.getCellID() != -1) {
						Cell cell = CellHandler.getCellList().get(player.getCellID());

						if (!cell.getOwnerUUID().equals(p.getUniqueId().toString())) {
							GUI.sendTitle(p, 5, 120, 10, "&4Your cell has expired!",
									"Your cell ID " + cell.getId() + " has expired.");
							p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 1F);
							player.setCellID(-1);
						}

					}

				}

			}

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				public void run() {
					InvisiblePlayer.showPlayer(p);
				}
			}, 1L);

			for (Permission perm : Permission.values()) {
				if (perm.isDefaultPerm() && !player.getPermissions().contains(perm)) {
					player.getPermissions().add(perm);
				}
			}

			player.unfreeze();
			player.loaded = true;
			ExodusPlayer.getOnlinePlayersMap().put(p, player);

			// Util.clearChat(p);
			/*p.sendMessage("§a§m-----------------------------------------------------");
			p.sendMessage(Util.center("§aSuccessfully Loaded...", 76));
			p.sendMessage("§a§m-----------------------------------------------------");*/
			
			GUI.sendTitle(p, 0, 40, 0, "", "&aSuccessfully Loaded...");

			player.onPlayerLoad();
			
			ServerStatistics.incrementKey("player_logins", 1);
			DailyStatistics.incrementKey("logins", 1);

			Log.log(LogType.LOG, "Loaded player " + p.getName());

		} catch (Exception e) {
			ExodusPlayer.asyncPlayerKick(p, "§cSomething wen't wrong! Cannot load your profile.\n" + e.getMessage());
			e.printStackTrace();

			ServerStatistics.incrementKey("json_exceptions", 1);
		}

	}

}
