package tutorial.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import exodusplayer.PlayerLoader;
import handlers.InvisiblePlayer;
import logger.Log;
import logger.Log.LogType;
import net.md_5.bungee.api.ChatColor;
import util.Util;

public class TutorialHandler {

	private static String[] convictReasons = { // add chances for fun ones
			"murder", "theft", "drug possession", "assault", "breaking and entry", "kidnapping", "griefing",
			"stealing Diamonds", "twerking nude in public", "taking dump in the walmart",
			"farting in a colleague’s face" };

	private static HashMap<ExodusPlayer, Integer> playersWatching = new HashMap<ExodusPlayer, Integer>();

	private static class TutorialStep {

		private Location loc;
		private String text;
		private int delay;

		public TutorialStep(Location loc, String text, int delay) {
			this.loc = loc;
			this.text = ChatColor.translateAlternateColorCodes('&', text);
			this.delay = delay;
		}

	}

	public static class Tutorial {
		private static List<Tutorial> tutorials = new ArrayList<Tutorial>();

		private List<TutorialStep> step = new ArrayList<TutorialStep>();
		private Location loc;
		private int id;
		private int length;
		
		public Tutorial(Location loc) {
			this.id = tutorials.size();
			this.loc = loc;
			this.length = 0;
			tutorials.add(this);
		}
		
		public int getLength() {
			return length;
		}

		public void addStep(TutorialStep step) {
			this.step.add(step);
			this.length += step.delay;
		}

		public static Tutorial getTutorial(int id) {
			return tutorials.get(id);
		}

	}

	public static Tutorial generalTutorial = new Tutorial(PlayerLoader.spawn);

	public static void setupTutorial() {

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -19.5, 89, 0.5, -90, 10),
				"&e%PLAYER%, Welcome to the &6ExodusPrison!", 3));
		
		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -18.5, 81, -1.5, 57, 1),
				"&eThis is not just a regular prison server. It has a slightly different concept.", 5));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -38, 85, -13.37, 58, 35),
				"&eThere are no &6ranks&e, instead there are &6permissions&e. Different permissions give you different farm accesses.",
				15));
		
		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -160.5, 72, -1.7, 56, 2),
				"&eYour first permission will be &6Beginners Mine. &eCollect &6Cobblestone &eand &6Glowstone&e, and then sell it at the shop.",
				7));
		
		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -39, 81, 1.65, -45, 3),
				"&eYou can find the shops next to the &6canteen&e. When you get enough money, you can advance and get better farms, which will have better profits. (&7Use &6/warp&7 to teleport)",
				7));
		
		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -138.5, 86, -17, 143, 19),
				"&eUnlocking different kinds of &6permissions&e will give you access to the different &6resources&e. &7(Use &6/rankup&7 to open permissions shop)",
				7));
		
		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -131.4, 97, -105, 50, 11),
				"&ePermissions are not just farms, &6Cell Block A&e will let you to store items in your cell, for example.",
				7));
		
		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), 40, 83, -6.5, 90, 0),
				"&eThere are also different kind of &cdrugs &eand &cillegal items&e.", 4));
		
		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -60.5, 75, -3.5, 45, 25),
				"&eRemember to behave well! You can &cPVP&e of course, but if you get caught, you will serve your time in the detention cell, and your illegal items will be confiscated.",
				7));
		
		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -42.5, 81, 0.5, 90, 0),
				"&eKeep in mind that hallways are a &cPVP ZONE. &eGetting some friends will greatly increase your chance to survive.",
				7));
		
		generalTutorial.addStep(new TutorialStep(PlayerLoader.spawn, "&eGood luck, my friend.", 3));

		
		/*generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -19.5, 89, 0.5, -90, 10),
				"&e%PLAYER%, Welcome to the &6ExodusPrison!", 3));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -18.5, 81, -1.5, 57, 1),
				"&eThis is not just a regular prison server. It has an entirely different concept.", 5));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -38, 85, -13.37, 58, 35),
				"&eThere are no &6ranks&e, instead there are &6permissions&e. Different permissions give you different farm accesses.",
				15));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -160.5, 72, -1.7, 56, 2),
				"&eYour first permission will be &6Beginners Mine. &eCollect &6Cobblestone &eand &6Glowstone&e, and then sell it at the shop.",
				7));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -42.5, 81, 0.5, 110, 0),
				"&eYou can get &6PVP Timer &eand &6tools&e from this guard.", 4));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -39, 81, 1.65, -45, 3),
				"&eYou can find the shops next to the &6canteen&e. When you get enough money, you can advance and get better farms, which will have better profits.",
				7));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -138.5, 86, -17, 143, 19),
				"&eUnlocking different kinds of &6permissions&e will give you access to the different &6resources&e.",
				7));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -131.4, 97, -105, 50, 11),
				"&ePermissions are not just farms, &6Cell Block A&e will let you to store items in your cell, for example.",
				7));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), 40, 83, -6.5, 90, 0),
				"&eThere are also different kind of &cdrugs &eand &cillegal items&e.", 4));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -60.5, 75, -3.5, 45, 25),
				"&eRemember to behave well! You can &cPVP&e of course, but if you get caught, you will serve your time in the detention cell, and your illegal items will be confiscated.",
				7));

		generalTutorial.addStep(new TutorialStep(new Location(Main.getWorld(), -42.5, 81, 0.5, 90, 0),
				"&eKeep in mind that majority of this prison is a &cPVP ZONE. &eGetting some friends will greatly increase your chance to survive.",
				7));*/

		new BukkitRunnable() {
			public void run() {

				for (ExodusPlayer eP : playersWatching.keySet()) {
					playersWatching.put(eP, playersWatching.get(eP) + 1); 
					Tutorial tutorial = Tutorial.getTutorial(eP.getTutorial());
															
					if(tutorial == null)
						continue;

					if (playersWatching.get(eP) > tutorial.step.get(eP.getTutorialStep()).delay) {
						playersWatching.put(eP, 0); // reset player watching timer
						eP.setTutorialStep(eP.getTutorialStep() + 1); // add player step

						if ((eP.getTutorialStep() > tutorial.step.size() - 1) || eP.isSkipTutorial()) { //if playersteps has more steps that tutorial has, then end tutorial
							eP.unfreeze();
							InvisiblePlayer.showPlayer(eP.getPlayer());
							eP.getPlayer().teleport(tutorial.loc);

							playersWatching.remove(eP);
							eP.setTutorialStep(-1);
							eP.setTutorial(-1);
							eP.setFarmBypass(false);
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + eP.getName() + " remove nocheatplus.checks");
							
							if(tutorial.equals(generalTutorial) && !eP.isSkipTutorial()){
								if(eP.getData().getDataInt("claimed_tutorial_reward") == 0){
									eP.getData().setDataInt("claimed_tutorial_reward", 1);
									
									Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "eco give " + eP.getName() + " 5000");
									Util.globalMessage("§aPlayer " + eP.getName() + " has watched a tutorial without skipping and received §6$5000§a bonus!");
								}
							}
							
							if(eP.isSkipTutorial()){
								eP.getPlayer().sendMessage("§cTutorial has been skipped.");
								eP.setSkipTutorial(false);
							}

							return;
						}

						showTutorialStep(tutorial.step.get(eP.getTutorialStep()), eP);
						
					}

				}

			}

		}.runTaskTimer(Main.getPlugin(), 0L, 20L);
		
		Log.log(LogType.NOTIFY,"[INITALIZING] [TUTORIAL] Tutorial mode initalized successfully. Loaded " + Tutorial.tutorials.size() + " tutorials.");

	}

	public static void startTutorial(ExodusPlayer eP, Tutorial tutorial) {
		eP.freeze();
		InvisiblePlayer.hidePlayer(eP.getPlayer());
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + eP.getName() + " add nocheatplus.checks");

		playersWatching.put(eP, 0);
		eP.setTutorial(tutorial.id);
		eP.setTutorialStep(1);
		eP.setFarmBypass(true);
		showTutorialStep(tutorial.step.get(0), eP);
	}

	private static void showTutorialStep(TutorialStep step, ExodusPlayer eP) {
		Util.clearChat(eP.getPlayer());

		eP.getPlayer().sendMessage("§8[Type /skip to skip the tutorial - You can watch it again later]");
		
		eP.getPlayer().teleport(step.loc);
		eP.getPlayer().sendMessage(step.text.replace("%PLAYER%", eP.getPlayer().getName()));
	}

	public static String generateWelcomeText(Player p, int prisonerNumber) {
		String textFormat = "§eSpeaker§7] : > §6Prisoner {USERNAME} §f#{NUMBER}§7 was convicted of §f{REASON}§7 and has been arrested for §f{YEARS} years.";

		textFormat = textFormat.replace("{USERNAME}", p.getName());
		textFormat = textFormat.replace("{NUMBER}", String.valueOf(prisonerNumber));
		textFormat = textFormat.replace("{REASON}", String.valueOf(Util.getListRandom(Arrays.asList(convictReasons))));
		textFormat = textFormat.replace("{YEARS}", String.valueOf(Util.generateRandomBetween(10, 80)));

		return textFormat;
	}

}
