package exodusplayer;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import bukkit.exodusprison.spurkle.Main;
import economy.Vault;
import events.stash.handlers.StashHandler;
import logger.Log;
import logger.Log.LogType;
import util.Util;

public class Scoreboard {

	public static HashMap<Integer, String> defaultBoard = new HashMap<Integer, String>();

	private static final int MAX_STEP = 11;
	private static int curStep = 0;

	private static int guardCount = 0;

	public static void initalizeBoards() {

		defaultBoard.put(1, ChatColor.RESET + " ");
		//defaultBoard.put(2, "  Protection: {PROTECTION}");
		defaultBoard.put(2, "  Balance: &a${BAL}");
		defaultBoard.put(3, "  Tokens: &6{TOKENS}");
		defaultBoard.put(4, ChatColor.RESET + " " + ChatColor.RESET);
		defaultBoard.put(5, "Players Online");
		
		defaultBoard.put(6, "   &b{GUARD_ONLINE} {GUARD_SUFFIX}");
		defaultBoard.put(7, "   &e{PRISONER_ONLINE} {PRISONER_SUFFIX}");
		
		defaultBoard.put(8, ChatColor.RESET + " " + ChatColor.RESET + " " + ChatColor.RESET);
		defaultBoard.put(9, "{STASH_EVENT_LINE_1}");
		defaultBoard.put(10, "{STASH_EVENT_LINE_2}");
		defaultBoard.put(11, ChatColor.RESET + " " + ChatColor.RESET + " " + ChatColor.RESET + " " + ChatColor.RESET);
		defaultBoard.put(12, "{VOTED}");

		
		new BukkitRunnable() {
			public void run() {

				addStep();

			}
		}.runTaskTimer(Main.getPlugin(), 0L, 20L);

		Log.log(LogType.NOTIFY, "[INITALIZING] [SCOREBOARD] Scoreboard module inistalized successfully.");
	}

	private static void addStep() {
		curStep++;

		if (curStep >= MAX_STEP)
			curStep = 0;

		updateAnimation();
		updateGuardsCount();

	}

	private static void updateGuardsCount() {
		int guards = 0;

		for (ExodusPlayer p : ExodusPlayer.getOnlinePlayers())
			if (p.isGuard())
				guards++;

		guardCount = guards;
	}

	private static void updateAnimation() {
		switch (curStep) {

		case 1:
			defaultBoard.put(0, "   &6&lExodus Reborn");
			break;
		case 2:
			defaultBoard.put(0, "   &4&lExodus Reborn");
			break;
		case 3:
			defaultBoard.put(0, "   &2&lExodus Reborn");
			break;
		case 4:
			defaultBoard.put(0, "   &3&lExodus Reborn");
			break;
		case 5:
			defaultBoard.put(0, "   &9&lExodus Reborn");
			break;
		case 6:
			defaultBoard.put(0, "   &5&lExodus Reborn");
			break;
		case 7:
			defaultBoard.put(0, "   &a&lExodus Reborn");
			break;
		case 8:
			defaultBoard.put(0, "   &e&lExodus Reborn");
			break;
		case 9:
			defaultBoard.put(0, "   &c&lExodus Reborn");
			break;
		case 10:
			defaultBoard.put(0, "   &b&lExodus Reborn");
			break;
		case 11:
			defaultBoard.put(0, "   &d&lExodus Reborn");
			break;

		}

		defaultBoard.put(0, Util.center(defaultBoard.get(0), 30));

	}

	public static org.bukkit.scoreboard.Scoreboard getScoreboard(final Player p, HashMap<Integer, String> lines) {
		
		ExodusPlayer eP = ExodusPlayer.get(p);
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();

		Objective objective = board.registerNewObjective("Score", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', lines.get(0)));

		for (Entry<Integer, String> entry : lines.entrySet()) {

			if (entry.getKey() == 0)
				continue;

			String key = entry.getValue();

			key = key.replace("{BAL}", String.valueOf(Vault.get().getBalance(p)));

			if (eP == null || !eP.canBeDamaged()) {
				key = key.replace("{PROTECTION}", "&aON");
			} else {
				key = key.replace("{PROTECTION}", "&4OFF");
			}
			
			if(guardCount == 1)
				key = key.replace("{GUARD_SUFFIX}", "Guard");
			else
				key = key.replace("{GUARD_SUFFIX}", "Guards");
			
			if(ExodusPlayer.getOnlinePlayers().size() - guardCount == 1)
				key = key.replace("{PRISONER_SUFFIX}", "Prisoner");
			else
				key = key.replace("{PRISONER_SUFFIX}", "Prisoners");
			
			
			key = key.replace("{GUARD_ONLINE}", String.valueOf(guardCount));
			key = key.replace("{PRISONER_ONLINE}", String.valueOf(ExodusPlayer.getOnlinePlayers().size() - guardCount));
			key = key.replace("{TOKENS}", String.valueOf(eP.getTokens()));
			
			if (TimeUnit.MILLISECONDS.toHours((System.currentTimeMillis() - eP.getLastVote())) > 24)
				key = key.replace("{VOTED}", "&cYou haven't voted today.");
			else
				key = key.replace("{VOTED}", "&eThank you for voting :3");
			
			if (ExodusPlayer.getOnlinePlayers().size() < StashHandler.getMinPlayers()) {
				key = key.replace("{STASH_EVENT_LINE_1}", "&cNeed " + (StashHandler.getMinPlayers() - ExodusPlayer.getOnlinePlayers().size()) + " more" );
				key = key.replace("{STASH_EVENT_LINE_2}", "&cplayers for the event." );
			} else {
				key = key.replace("{STASH_EVENT_LINE_1}", "&fStash Event" );
				key = key.replace("{STASH_EVENT_LINE_2}", "&a " + StashHandler.getCountdown() );
			}

			Score line = objective.getScore(Util.center(ChatColor.translateAlternateColorCodes('&', "    " + key), 30));
			line.setScore(lines.size() - entry.getKey());

		}

		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		return board;
	}

}
