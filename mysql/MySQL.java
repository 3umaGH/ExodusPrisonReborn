package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import exodusplayer.PlayerLoader;
import logger.Log;
import logger.Log.LogType;
import statistics.handlers.ServerStatistics;

public class MySQL {

	static String mySqlURL = "jdbc:mysql://" + "127.0.0.1" + ":3306/" + "mc_prison1" + "";
	static String mySqlUser = "mc_prison1";
	static String mySqlPass = "xQkx03bTwqv4GGXc7J0x";

	/*static String mySqlURL = "jdbc:mysql://" + "127.0.0.1" + ":3306/" + "mc_dev_server" + "";
	static String mySqlUser = "mc_dev_server";
	static String mySqlPass = "";*/

	
	
	/*
	 * static String mySqlURL = "jdbc:mysql://" + "db4free.net" + ":3306/" +
	 * "galactixrpg" + ""; static String mySqlUser = "galactixrpg"; static
	 * String mySqlPass = "galactixrpggalactixrpg";
	 */

	public static void saveServerStats(String jsonString) {
		final String JsonString = jsonString;

		Connection mySQL;
		try {
			mySQL = DriverManager.getConnection(mySqlURL, mySqlUser, mySqlPass);
			PreparedStatement stmt = mySQL
					.prepareStatement("INSERT users (username, uuid, jsonString) VALUES ('Server','Server','"
							+ JsonString + "')" + " ON DUPLICATE KEY UPDATE jsonString='" + JsonString + "';");

			stmt.execute();
			stmt.close();
			mySQL.close();

		} catch (Exception e) {

		}
	}
	
	public static void loadServerStats() {
		new BukkitRunnable() {
			@Override
			public void run() {

				ResultSet res = null;

				Connection mySQL;
				try {
					mySQL = DriverManager.getConnection(mySqlURL, mySqlUser, mySqlPass);
					PreparedStatement stmt = mySQL
							.prepareStatement("SELECT * FROM users WHERE uuid='Server'");
					res = stmt.executeQuery();

					if (res.next())
						ServerStatistics.loadJsonString(res.getString("jsonString"));

				} catch (SQLException e) {
					e.printStackTrace();
					ServerStatistics.incrementKey("mysql_exceptions", 1);
				}
			}

		}.runTaskAsynchronously(Main.getPlugin());
	}

	public static void savePlayerConfiguration(Player p, String jsonString) {

		final UUID playerID = p.getUniqueId();
		final String JsonString = jsonString;

		Connection mySQL;
		try {
			mySQL = DriverManager.getConnection(mySqlURL, mySqlUser, mySqlPass);
			PreparedStatement stmt = mySQL.prepareStatement(
					"INSERT users (username, uuid, jsonString) VALUES ('" + p.getName() + "','" + playerID + "','"
							+ JsonString + "')" + " ON DUPLICATE KEY UPDATE jsonString='" + JsonString + "';");

			stmt.execute();
			stmt.close();
			mySQL.close();
			
			

			Log.log(LogType.NOTIFY, p.getName() + " has been updated.");

		} catch (Exception e) {

			Log.log(LogType.CRITICAL, p.getName() + " had an error while saving. Data backed up at "
					+ PlayerLoader.backupPlayer(p, jsonString));
			Log.log(LogType.CRITICAL, e.getMessage());

		}
	}

	public static void loadPlayerConfiguration(final Player p) {

		final UUID PlayerID = p.getUniqueId();

		new BukkitRunnable() {
			@Override
			public void run() {

				ResultSet res = null;

				Connection mySQL;
				try {
					mySQL = DriverManager.getConnection(mySqlURL, mySqlUser, mySqlPass);
					PreparedStatement stmt = mySQL
							.prepareStatement("SELECT * FROM users WHERE uuid='" + PlayerID + "'");
					res = stmt.executeQuery();

					if (res.next()) {
						// existing player
						PlayerLoader.setupPlayer(p, res.getString("jsonString"));
					} else {
						// new player
						PlayerLoader.setupPlayer(p, null);
					}

					Log.log(LogType.NOTIFY, p.getName() + " has been loaded.");

				} catch (SQLException e) {
					e.printStackTrace();
					ExodusPlayer.asyncPlayerKick(p,
							"§cSomething wen't wrong! Cannot load your profile.\n" + e.getMessage());

					ServerStatistics.incrementKey("mysql_exceptions", 1);
				}
			}

		}.runTaskAsynchronously(Main.getPlugin());
	}

}
