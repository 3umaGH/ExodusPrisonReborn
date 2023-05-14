package handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InvisiblePlayer {

	public static void hidePlayer(Player p) {

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!p.equals(player)) {
				player.hidePlayer(p);
			}
		}

	}

	public static void showPlayer(Player p) {

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!p.equals(player)) {
				player.showPlayer(p);
			}
		}

	}

}
