package handlers;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import util.GUI;
import util.Util;

public class VoteReminder {

	private static final String LINE_1 = "&6Remember to &l/vote&6!";
	private static final String LINE_2 = "&6Remember to &4&l/vote&6!";

	private static final String SUBLINE_1 = "To get your voting kit.";
	private static final String SUBLINE_2 = "To get your voting kit.";


	public static void showReminder(Player p) {

		GUI.sendTitle(p, 10, 100, 10, LINE_1, SUBLINE_1);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			public void run() {

				for (int i = 0; i < 4; i++) {

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						public void run() {

							GUI.sendTitle(p, 0, 100, 10, LINE_1, SUBLINE_1);

							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								public void run() {

									GUI.sendTitle(p, 0, 100, 10, LINE_2, SUBLINE_2);
									p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.98F, 2);

								}
							}, 3);

						}
					}, 5 * i);

				}

			}
		}, 15);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			public void run() {
				GUI.sendTitle(p, 10, 60, 10, "", "&6P.S: You can hide these messages by voting.");
			}
		}, 120);

	}

	public static void startReminderLoop(ExodusPlayer eP) {
		final Player p = eP.getPlayer();
		
		// if (p.hasPermission("exodus.novotereminder"))
		// return;
	
		new BukkitRunnable() {
			public void run() {

				if (!p.isOnline()) {
					cancel();
					return;
				}

				if (TimeUnit.MILLISECONDS.toHours((System.currentTimeMillis() - eP.getLastVote())) > 24) {
					showReminder(p);
				} else
					cancel();

			}

		}.runTaskTimer(Main.getPlugin(), 0L, 72000L);

	}

}
