package exodusplayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import achievements.Achievement;
import bukkit.exodusprison.spurkle.Main;
import exodusplayer.classes.Data;
import exodusplayer.classes.Offenses;
import exodusplayer.classes.OnlineDay;
import exodusplayer.classes.Stats;
import exodusplayer.classes.Upgrades;
import handlers.VoteReminder;
import items.Item;
import items.ItemUpgrade;
import jail.handlers.JailHandler;
import permission.Permissions.Permission;
import statistics.handlers.DailyStatistics;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class ExodusPlayer {

	private static HashMap<Player, ExodusPlayer> onlinePlayers = new HashMap<Player, ExodusPlayer>();

	public static ExodusPlayer get(Player p) {
		return onlinePlayers.get(p);
	}

	public static Collection<ExodusPlayer> getOnlinePlayers() {
		return onlinePlayers.values();
	}

	public static HashMap<Player, ExodusPlayer> getOnlinePlayersMap() {
		return onlinePlayers;
	}

	public static void asyncPlayerKick(final Player p, final String reason) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			public void run() {
				p.kickPlayer(reason);
			}
		}, 1L);
	}

	Player player;
	String name;
	
	Data data = new Data();
	Stats stats = new Stats();
	Upgrades upgrades = new Upgrades();
	Offenses offenses = new Offenses(this);
	
	List<Permission> permissions = new ArrayList<Permission>();
	List<Achievement> achievements = new ArrayList<Achievement>();
	List<OnlineDay> onlineDays = new ArrayList<OnlineDay>();
	List<String> titles = new ArrayList<String>();
	
	boolean frozen, loaded, farmBypass, skipTutorial, noPVP;
	
	int prisonerNumber;

	public ExodusPlayer(Player p) {
		this.player = p;
		this.name = p.getName();

		this.loaded = false;
		this.farmBypass = false;

		getData().setData("first_login", System.currentTimeMillis());
		getData().setData("no_pvp_timer_expiry", System.currentTimeMillis());
		getData().setData("kit_receive_time", System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10));
		getData().setDataInt("offenses_id", 0);
		getData().setData("last_disconnect", System.currentTimeMillis());
		getData().setDataInt("claimed_tutorial_reward", 0);
		getData().setDataInt("tokens", 0);
		getData().setDataInt("kit_stored_9", 0);
		getData().setData("last_vote", System.currentTimeMillis() - TimeUnit.HOURS.toMillis(25));
		
		getData().setDataInt("prisoner_number", Util.generateRandom(1000));
		this.prisonerNumber = getData().getDataInt("prisoner_number");
		
		getData().setDataInt("jail_time", -1);
		getData().setDataInt("watching_tutorial", -1);
		getData().setDataInt("tutorial_step", 0);
		
		for(ItemUpgrade upgradeValue : ItemUpgrade.getItemUpgrades().values())
			getUpgrades().getItemUpgrades().put(upgradeValue, -1);
		
		getUpgrades().setLevel(Item.arrest_baton,0);
		getUpgrades().setLevel(Item.guardSword,0);
		getUpgrades().setLevel(Item.inventory_searcher,0);
		
		getUpgrades().setLevel(Item.guardHelm,0);
		getUpgrades().setLevel(Item.guardChest,0);
		getUpgrades().setLevel(Item.guardLegs,0);
		getUpgrades().setLevel(Item.guardBoots,0);
		
		new BukkitRunnable() {
			public void run() {

				if (!p.isOnline()) {
					cancel();
					return;
				}

				if (isFrozen()) {
					freezeTick();
				}
				
				if(!canBeDamaged()) {
					GUI.sendActionBarText(getPlayer(), "§bPVP Timer will expire in " + getPvPTimerMinutes() + " minutes.");
				}
				
				if(loaded)
					getCurrentOnlineDay().addSecond();
				
				ServerStatistics.incrementKey("time_spent_by_players", 1);
				DailyStatistics.incrementKey("players_time_spent", 1);

			}

		}.runTaskTimer(Main.getPlugin(), 0L, 20L);
		
		new BukkitRunnable() {
			public void run() {

				if (!p.isOnline()) {
					cancel();
					return;
				}

				if(ExodusPlayer.isLoaded(p))
					getPlayer().setScoreboard(Scoreboard.getScoreboard(p, Scoreboard.defaultBoard));

			}

		}.runTaskTimer(Main.getPlugin(), 0L, 20L);
	}
	
	public void onPlayerQuit() {
		JailHandler.disconnectCheck(this);
	}
	
	public void onPlayerLoad(){
		ExodusPlayer eP = this;
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			public void run() {

				VoteReminder.startReminderLoop(eP);

			}
		}, 60);
		
		
	}

	// Freeze
	private void freezeTick() {
		getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -5));
	}

	public void freeze() {
		freezeTick();
		this.frozen = true;
	}

	public boolean isFrozen() {
		return this.frozen;
	}

	public void unfreeze() {
		getPlayer().removePotionEffect(PotionEffectType.JUMP);
		this.frozen = false;
	}

	// Data
	public Data getData() {
		return this.data;
	}

	// Stats
	public Stats getStats() {
		return this.stats;
	}

	// Offenses
	public Offenses getOffenses() {
		return this.offenses;
	}

	// Permissions
	public List<Permission> getPermissions() {
		return this.permissions;
	}

	public void addPermission(Permission perm) {
		this.permissions.add(perm);
	}
	
	// Titles
	public List<String> getTitles() {
		return titles;
	}

	public void addTitle(String title) {
		this.titles.add(title);
	}
	
	// Achievements
	public List<Achievement> getAchievements(){
		return this.achievements;
	}
	
	public void addAchievement(Achievement a){
		this.achievements.add(a);
	}
	
	// upgrades
	public Upgrades getUpgrades(){
		return this.upgrades;
	}
	
	// online time
	public List<OnlineDay> getOnlineTime(){
		return this.onlineDays;
	}
	
	public OnlineDay getCurrentOnlineDay(){
		String curDay = Util.formatTimeMillis(System.currentTimeMillis(), "yyyy/MM/dd");
		OnlineDay day = null;
		
		for(OnlineDay onlineTime : getOnlineTime()){
			if(onlineTime.getDate().equals(curDay)) {
				day = onlineTime;
				break;
			}
		}
		
		if(day == null) {
			day = new OnlineDay(curDay, 0);
			getOnlineTime().add(day);
		}
		
		
		return day;
	}
	
	// tutorial
	public int getTutorial() {
		return getData().getDataInt("watching_tutorial");
	}

	public void setTutorial(int id) {
		getData().setDataInt("watching_tutorial", id);
	}
	
	public void setTutorialStep(int id) {
		getData().setDataInt("tutorial_step", id);
	}
	
	public int getTutorialStep() {
		return getData().getDataInt("tutorial_step");
	}
	
	public boolean isSkipTutorial() {
		return skipTutorial;
	}

	public void setSkipTutorial(boolean skipTutorial) {
		this.skipTutorial = skipTutorial;
	}

	// Player stuff
	public Player getPlayer() {
		return this.player;
	}

	public String getName() {
		return name;
	}

	public boolean isGuard() {
		return getPlayer().hasPermission("exodus.guard");
	}
	
	public boolean isGuardChief() {
		return getPlayer().hasPermission("exodus.chief");
	}

	public Long getAgeMillis() {
		Long difference = System.currentTimeMillis() - getData().getData("first_login");
		return difference;
	}
	
	public int getAgeHours() {
		return Util.LongToInt(TimeUnit.MILLISECONDS.toHours(getAgeMillis()));

	}

	public boolean isFarmBypass() {
		return farmBypass;
	}

	public void setFarmBypass(boolean farmBypass) {
		this.farmBypass = farmBypass;
	}

	public int getPrisonerNumber() {
		return prisonerNumber;
	}
	
	public int getLastDisconnectMinutes(){
		return Util.LongToInt(TimeUnit.MILLISECONDS.toMinutes(this.getData().getData("last_disconnect") - System.currentTimeMillis()));
	}

	public boolean isNoPVP() {
		return noPVP;
	}

	public void setNoPVP(boolean noPVP) {
		this.noPVP = noPVP;
	}

	public Long getLastVote() {
		return getData().getData("last_vote");
	}
	
	public void updateLastVote() {
		getData().setData("last_vote", System.currentTimeMillis());
	}
	
	// arest
	public int getArrestTime() {
		return getData().getDataInt("jail_time");
	}
	
	public void addArrestTime(int time) {
		getData().setDataInt("jail_time", getData().getDataInt("jail_time") + time);
	}
	
	public void setJailTime(int time) {
		getData().setDataInt("jail_time", time);
	}
	
	public void arrestPlayer(int time) {
			getData().setDataInt("jail_time", time);
			getPlayer().teleport(JailHandler.JAIL_LOCATION);
	}

	public void unarrestPlayer(){
		getPlayer().teleport(JailHandler.JAIL_EXIT_LOCATION);
		getData().setDataInt("jail_time", -1);
	}
	
	public boolean isArrested(){
		return getArrestTime() > 0;
	}
	
	public void setCellID(int id){
		getData().setDataInt("owned_cell", id);
	}
	
	public int getCellID(){
		return getData().getDataInt("owned_cell");
	}
	
	//pvp timer
	public boolean canBeDamaged(){
		return System.currentTimeMillis() >= this.getData().getData("no_pvp_timer_expiry");
	}
	
	public int getPvPTimerMinutes(){
		return Util.LongToInt(TimeUnit.MILLISECONDS.toMinutes(this.getData().getData("no_pvp_timer_expiry") - System.currentTimeMillis()));
	}
	
	public void setPvPTimerMinutes(int min){
		 getData().setData("no_pvp_timer_expiry", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(min));
	}
	
	public void removePVPTimer(){
		getData().setData("no_pvp_timer_expiry", System.currentTimeMillis());
	}
	
	//kit
	public void updateKitTime(){
		getData().setData("kit_receive_time", System.currentTimeMillis());
	}
	
	public int getLastKitMinutes(){
		return Util.LongToInt(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()-this.getData().getData("kit_receive_time")));
	}

	// tokens
	public int getTokens(){
		return getData().getDataInt("tokens");
	}
	
	public void addTokens(int tokens){
		getData().setDataInt("tokens", getTokens() + tokens);
	}
	
	
	
	
	
	
	public static boolean isLoaded(Player p) {
		return onlinePlayers.containsKey(p);
	}
	
	
}
