package bukkit.exodusprison.spurkle;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import cells.Cell;
import cells.CellSaver;
import cells.handler.CellHandler;
import cells.listener.CellListener;
import economy.Vault;
import events.stash.Stash;
import events.stash.Stash.StashType;
import events.stash.handlers.StashHandler;
import events.stash.listeners.StashListener;
import events.stash.listeners.StashSpawnListener;
import exodusplayer.ExodusPlayer;
import exodusplayer.PlayerLoader;
import exodusplayer.Scoreboard;
import exodusplayer.listeners.PlayerListener;
import handlers.VoteReminder;
import items.Item;
import items.MineBomb;
import items.listeners.ItemListener;
import items.listeners.MineBombListener;
import jail.handlers.JailHandler;
import kit.handlers.KitHandler;
import kit.handlers.RedeemHandler;
import listeners.EnchantingListener;
import listeners.PVPTimerListener;
import listeners.SexyChatListener;
import listeners.TreeFarmListener;
import logger.Log;
import logger.Log.LogType;
import menu.list.AdminItemList;
import menu.list.CellSettingsMenu;
import menu.list.GuardChiefMenu;
import menu.list.KitMenu;
import menu.list.PlayerMenu;
import menu.list.TitleMenu;
import menu.list.TokenShop;
import menu.list.WarpMenu;
import menu.listeners.InventoryListener;
import mobs.SmartMob;
import mobs.listeners.MobSoundListener;
import mobs.listeners.MobSpawnListener;
import mobs.listeners.SmartMobListener;
import permission.PermissionShop;
import permission.Permissions;
import permission.Permissions.Permission;
import permission.listener.PermissionListener;
import records.listener.DailyPlayerListener;
import shop.listeners.ShopListener;
import statistics.handlers.ServerStatistics;
import statistics.listener.StatisticsListener;
import tutorial.handlers.TutorialHandler;
import tutorial.listeners.TutorialListener;
import util.GUI;
import util.Util;

public class Main extends JavaPlugin implements Listener, PluginMessageListener {

	private static Plugin plugin;
	private static World world;

	private static WorldGuardPlugin wg;
	private static RegionManager wgrm;

	String motd = "                  §6§lExodusPrison Reborn\n" + "    §e§lThe new generation of prison servers.";

	@Override
	public void onEnable() {

		plugin = this;
		world = Bukkit.getWorld("world");

		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new ItemListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new StatisticsListener(), this);
		getServer().getPluginManager().registerEvents(new TreeFarmListener(), this);
		getServer().getPluginManager().registerEvents(new SmartMobListener(), this);
		getServer().getPluginManager().registerEvents(new PermissionListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new ShopListener(), this);
		getServer().getPluginManager().registerEvents(new CellListener(), this);
		getServer().getPluginManager().registerEvents(new TutorialListener(), this);
		getServer().getPluginManager().registerEvents(new PVPTimerListener(), this);
		getServer().getPluginManager().registerEvents(new SexyChatListener(), this);
		getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
		getServer().getPluginManager().registerEvents(new EnchantingListener(), this);
		getServer().getPluginManager().registerEvents(new StashListener(), this);
		getServer().getPluginManager().registerEvents(new StashSpawnListener(), this);
		getServer().getPluginManager().registerEvents(new DailyPlayerListener(), this);
		getServer().getPluginManager().registerEvents(new MineBombListener(), this);
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		Permissions.Permission.initalize();
		Item.intialize();
		MobSoundListener.startListening();
		PermissionListener.startLoop();
		JailHandler.startHandler();
		CellHandler.loadCells();
		CellHandler.checkExpiredLoop();
		Vault.setup();
		SmartMob.setup();
		TutorialHandler.setupTutorial();
		ServerStatistics.loadServerStatistics();
		Scoreboard.initalizeBoards();
		StashHandler.setupVars();
		KitHandler.initalize();
		TokenShop.initalize();

		wg = getWorldGuard();
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		
		wgrm = container.get(BukkitAdapter.adapt(Main.getWorld()));

		if (wg != null)
			Log.log(LogType.NOTIFY, "[INITALIZING] [WORLDGUARD] WorldGuard attached.");
		else {
			Log.log(LogType.CRITICAL, "WORLD GUARD NOT FOUND!!!");
			this.setEnabled(false);
		}

		for (Player p : Bukkit.getServer().getOnlinePlayers())
			PlayerLoader.loadPlayer(p);

		Util.globalMessage("ExodusCore has loaded successfully.");

	}

	@Override
	public void onDisable() {

		CellSaver.saveCells();
		SmartMob.removeMobs();
		ServerStatistics.saveServerStatistics();
		StashHandler.removeStashes();
		MineBomb.removeBombs();

		for (Player p : Bukkit.getServer().getOnlinePlayers())
			PlayerLoader.savePlayer(ExodusPlayer.get(p.getPlayer()), true, false, true);
	}

	public static Plugin getPlugin() { // Returns plugin instance
		return plugin;
	}

	public static World getWorld() {
		return world;
	}

	private WorldGuardPlugin getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}

	public static WorldGuardPlugin getWG() {
		return wg;
	}

	public static RegionManager getWGRegionManager() {
		return wgrm;
	}

	@EventHandler
	public void onPlayerMotdRefresh(final ServerListPingEvent e) {
		e.setMotd(motd);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// console commands

		if (label.equalsIgnoreCase("donateAdmin") && sender instanceof ConsoleCommandSender) {

			if (args[0].equals("giveitem")) {
				int id = Integer.parseInt(args[1]); // donateAdmin giveitem
													// (name) (amount) (player)
				int amount = Integer.parseInt(args[2]);
				Player p = Bukkit.getPlayer(args[3]);

				Util.giveItem(p, Item.getItemById(id).getItemStack(), amount);
				
				return false;

			}
			
			if (args[0].equals("vote")) {
				ExodusPlayer eP = ExodusPlayer.get(Bukkit.getPlayer(args[1])); //vote player

				KitHandler.votingKit.addKitsAmount(eP, 1);
				eP.updateLastVote();
				
				eP.getPlayer().sendMessage("§6§l+1 Voting Kit §7(Visit delivery cow at spawn)");
				Util.globalMessage("§6§lVOTE : > §ePlayer " + eP.getName() + " has voted and received a §6§lVoting Kit!");
					
				return false;
			}
				

			if (args[0].equals("makepermanentcell")) { // /donateAdmin
														// makepermanentcell
														// (cellid) (player)
				Cell c = CellHandler.getCellList().get(Integer.parseInt(args[1]));

				Util.globalMessage("args 1 " + args[1] + " args 2 " + args[2]);

				if (c.isOccupied()) {
					c.makePermanent();
					Log.log(LogType.NOTIFY, "Made cell ID " + c.getId() + " permanent. Donation by: " + args[2]);
				} else
					Log.log(LogType.WARNING, "Permanent cell via donation command failed. Cell ID: " + args[2]
							+ " cell wasn't owned, can't make it permanent..");
			}

			return false;
		}

		final Player p = (Player) sender;

		if (!ExodusPlayer.isLoaded(p))
			return false;

		final ExodusPlayer eP = ExodusPlayer.get(p);
		
		if(eP.getTutorial() != -1)
			return false;
		
			if(label.equalsIgnoreCase("redeem")) {
			if(args.length == 1) {

				if(p.hasPermission("exodus.fb")){
					p.sendMessage("§4You have already used a code.");
					return false;
				}
				
				RedeemHandler.attemptActivate(p, args[0]);
				
			} else p.sendMessage("§4Invalid usage! Use /redeem <code>");
		}
		
		if (label.equalsIgnoreCase("hub") || label.equalsIgnoreCase("lobby")) {
			Util.sendBungeeMessage(p, "Connect", "hub");
		}
			
			
		if (label.equalsIgnoreCase("warp")) {
			new WarpMenu().openMenu(p);
		}
		
		if(label.equalsIgnoreCase("stats"))
			new PlayerMenu(p, p, false);
		
		if (label.equalsIgnoreCase("kit")) {
			new KitMenu().openMenu(p);
		}
		
		if (label.equalsIgnoreCase("rankup")) {
			
			if(args.length == 1 && args[0].equals("2")){
				new PermissionShop("Block B Permissions", Permission.CHICKEN_FARM, Permission.GRAVEL_MINE,
						Permission.CELL_BLOCK_B, Permission.COAL_MINE, Permission.SHEEP_FARM,
						Permission.SPIDER_FARM, Permission.GOLD_MINE, Permission.BLOCK_C)
								.openMenu(p);
			} else {
				new PermissionShop("Block A Permissions", Permission.BEGINNERS_MINE, Permission.TREE_FARM, Permission.CACTUS_FARM,
						Permission.CELLBLOCK_A, Permission.PUMPKIN_FARM, Permission.SUGARCANE_FARM,  Permission.MELON_FARM, Permission.DANGEROUS_MINE, Permission.BLOCK_B).openMenu(p);
			}
			
			
		}

		if (label.equalsIgnoreCase("store") || label.equalsIgnoreCase("donate") || label.equalsIgnoreCase("buy"))
			p.sendMessage("§7Donation store can be found here: §ehttp://store.exodusreborn.org/");

		if (label.equalsIgnoreCase("pl") || label.equalsIgnoreCase("?") || label.equalsIgnoreCase("plugins"))
			p.sendMessage("§fPlugins (1): §aExodusRebornCore - coded by You.");

		if (label.equalsIgnoreCase("fb") || label.equalsIgnoreCase("facebook"))
			p.sendMessage("§7Our facebook page: §ehttp://bit.ly/2ibxRI4 §7(Search for the Like Reward button).");

		
		if (label.equalsIgnoreCase("rules") || label.equalsIgnoreCase("help"))
			p.sendMessage("§7Rules can be found here: §ehttp://bit.ly/2hDGjLQ");

		if (label.equalsIgnoreCase("website")) {
			p.sendMessage("§7Our website is: §ehttp://exodusreborn.org");
		}
		
		if (label.equalsIgnoreCase("vote")) {
			p.sendMessage("§7Vote Here: §ehttp://exodusreborn.org/vote");
		}

		if (label.equalsIgnoreCase("skip")) {
			eP.setSkipTutorial(true);
		}

		if (label.equalsIgnoreCase("timer")) {

			if (args.length == 0) {
				p.sendMessage("§7Please use /timer [remove].");
				return false;
			}

			if (args[0].equalsIgnoreCase("remove")) {

				if (!eP.canBeDamaged()) {
					eP.removePVPTimer();
					GUI.sendTitle(p, 5, 20, 5, "", "&cPVP timer removed!");
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.98F, 1.5F);
				} else {
					GUI.sendTitle(p, 5, 20, 5, "", "&cYou don't have a PVP Timer.");
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 1F);
				}

			}

		}

		if (label.equalsIgnoreCase("title")) {
			new TitleMenu().openMenu(p);
		}


		if (label.equalsIgnoreCase("cell")) {

			if (eP.getCellID() == -1) {
				p.sendMessage("§4You don't have a cell.");
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 1F);
				return false;
			} else if (args.length == 0) {
				p.sendMessage("§7Please use /cell [remove/renew/settings].");
				return false;
			}

			if (args[0].equalsIgnoreCase("remove")) {

				CellHandler.getCellList().get(eP.getCellID()).unownCell();
				p.sendMessage("§aYou have abandoned your cell.");

				ServerStatistics.incrementKey("cells_abandoned", 1);

			}
			
			if (args[0].equalsIgnoreCase("settings")) {
				Cell c = null;
				
				if(args.length == 2) {
					c = CellHandler.getCellList().get(Integer.parseInt(args[1]));
					
				}
				else {
					c = CellHandler.getCellList().get(eP.getCellID());
				}
				
				if(c == null || !c.getOwnerName().equals(p.getName())) {
					GUI.sendTitle(p, 10, 60, 10, "&4You don't own this cell!", "&fYou don't own this cell or it doesn't exist.");
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 0.7F);
					
					return false;
				}
				
				new CellSettingsMenu(c).openMenu(p);
				
			}

				

			if (args[0].equalsIgnoreCase("renew")) {
				Cell cell = CellHandler.getCellList().get(eP.getCellID());

				if(eP.isGuard()){
					GUI.sendTitle(p, 5, 60, 10, "&4You are a guard!", "&cYou can't renew your cell.");
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 1F);
					return false;
				}
				
				if (Vault.takeMoney(p, cell.getPrice(), false)) {
					cell.addTime(TimeUnit.DAYS.toMillis(1));
					CellHandler.updateSign(cell);

					GUI.sendTitle(p, 5, 60, 10, "&aAdded 1 day.", "&aYour cell will expire on "
							+ Util.formatTimeMillis(cell.getExpiry(), "yyyy/MM/dd HH:mm:ss"));
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 0.98F, 1F);

					ServerStatistics.incrementKey("cells_days_renewed", 1);

				} else {
					GUI.sendTitle(p, 5, 60, 10, "", "&4You don't have enough money.");
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 1F);
				}

			}

		}

		if (label.equalsIgnoreCase("admin") && p.hasPermission("exodus.admin")) {

			if(args[0].equalsIgnoreCase("chest1"))
				new Stash(p.getLocation(),StashType.PRISONERS);
			if(args[0].equalsIgnoreCase("chest2"))
				new Stash(p.getLocation(),StashType.GUARDS);
			if(args[0].equalsIgnoreCase("chest3"))
				new Stash(p.getLocation(),StashType.LEGENDARY);
			if(args[0].equalsIgnoreCase("stashevent"))
				StashHandler.startEvent();
			if(args[0].equalsIgnoreCase("votetest"))
				VoteReminder.showReminder(p);

			// not debug commands

			if (args[0].equalsIgnoreCase("cell")) {

				if (args.length < 3 || args.length > 3) {
					p.sendMessage("§7Please use /admin cell (id) [unown/setinfinite].");
					return false;
				}

				if (!Util.isNumeric(args[1])) {
					p.sendMessage("§cCell ID cannot be a string. (" + args[1] + ")");
					return false;
				}

				int id = Integer.parseInt(args[1]);
				String command = args[2];

				Cell c = CellHandler.getCellList().get(id);

				if (c == null) {
					p.sendMessage("§cCell is not found. (" + id + ")");
					return false;
				}

				if (command.equals("unown"))
					if (c.isOccupied())
						c.unownCell();
					else {
						p.sendMessage("§cCell is not occupied by anyone.");
						return false;
					}

				if (command.equals("setinfinite"))
					if (c.isOccupied())
						c.makePermanent();
					else {
						p.sendMessage("§cCannot make permanent cell with no owners.");
						return false;
					}

			}

			if (args[0].equalsIgnoreCase("itemdb")) {
				Item item = Item.getItem(p.getItemInHand());

				if (item == null) {
					p.sendMessage("§cItem not found.");
					return false;
				} else {
					p.sendMessage(item.getItemMeta().getDisplayName() + " : " + item.getId());
				}

			}

			if (args[0].equalsIgnoreCase("view")) {

				if (args.length == 2) {
					Player player = Bukkit.getPlayer(args[1]);

					if (player == null) {
						p.sendMessage("§cPlayer not found.");
						return false;
					} else {
						new PlayerMenu(p, player, true);
					}

				} else if (args.length == 1) {
					new PlayerMenu(p, p, true);
				} else {
					p.sendMessage("§7Please use /admin view (player)");
					return false;
				}

			}
			
			if(args[0].equalsIgnoreCase("saveall")) {
				for (Player allP : Bukkit.getServer().getOnlinePlayers())
					PlayerLoader.savePlayer(ExodusPlayer.get(allP.getPlayer()), false, true, false);
				
				p.sendMessage("Saving all players.");
			}

			if (args[0].equalsIgnoreCase("chief")) {
				new GuardChiefMenu().openMenu(p);
			}

			if (args[0].equalsIgnoreCase("itemList"))
				AdminItemList.openMenu(p, Integer.valueOf(args[1]));

			if (args[0].equalsIgnoreCase("bypass")) {
				if (args.length == 2) {
					Player player = Bukkit.getPlayer(args[1]);

					if (player == null) {
						p.sendMessage("§cPlayer not found.");
						return false;
					} else {
						ExodusPlayer.get(player).setFarmBypass(true);
					}

				} else if (args.length == 1) {
					ExodusPlayer.get(p).setFarmBypass(true);
				} else {
					p.sendMessage("§7Please use /admin bypass (player)");
					return false;
				}
			}

			// not debug commands

			return true;

		}
		return false;

	}

	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		
	}
}
