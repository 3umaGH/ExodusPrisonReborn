package economy;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import bukkit.exodusprison.spurkle.Main;
import logger.Log;
import logger.Log.LogType;
import net.milkbowl.vault.economy.Economy;
import util.GUI;

public class Vault extends JavaPlugin {
	public static Economy econ = null;

	public static void setup() {

		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null)
			setupFailed();

		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

		if (rsp == null)
			setupFailed();

		econ = rsp.getProvider();

		Log.log(LogType.NOTIFY, "[INITALIZING] [VAULT] Vault module initalized successfully.");
	}

	private static void setupFailed() {
		Log.log(LogType.CRITICAL, "Vault plugin not found! Disabling...");
		Bukkit.getServer().getPluginManager().disablePlugin(Main.getPlugin());
	}

	public static Economy get() {
		return econ;
	}

	public static boolean takeMoney(Player p, double amount, boolean silent) {
		if (econ.getBalance(p) >= amount) {
			econ.withdrawPlayer(p, amount);

			if (!silent) {
				p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 10);
				GUI.sendActionBarText(p, "§c-$" + String.valueOf(amount));
			}

			return true;
		} else
			return false;
	}

	public static void addMoney(Player p, double amount, boolean silent) {
		econ.depositPlayer(p, amount);

		if (!silent) {
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 10);
			GUI.sendActionBarText(p, "§a+$" + String.valueOf(amount));
		}

	}
}