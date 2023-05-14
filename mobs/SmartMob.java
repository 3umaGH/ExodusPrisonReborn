package mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import logger.Log;
import logger.Log.LogType;
import mobs.list.ArmoryManager;
import mobs.list.Auctioneer;
import mobs.list.BlackMarketBrewingWitch;
import mobs.list.BlackMarketShop;
import mobs.list.Butcher;
import mobs.list.Cook;
import mobs.list.CorruptGuard;
import mobs.list.DeliveryCow;
import mobs.list.FarmingShop;
import mobs.list.GuardChief;
import mobs.list.MiningShop;
import mobs.list.PermShop;
import mobs.list.PermShopBlockB;
import mobs.list.Prisoner_64;
import mobs.list.Storekeeper;
import mobs.list.TitleMerchant;
import mobs.list.Tutor;
import util.Util;

public abstract class SmartMob {

	public static List<SmartMob> mob_list = new ArrayList<SmartMob>();
	public static List<Player> cooldown_list = new ArrayList<Player>();

	public static int delay = 20;

	// private static EscortPrisoner escortGuard;

	public static void setup() {

		new Auctioneer(new Location(Main.getWorld(), -25.5, 81, -12.5));
		new Prisoner_64(new Location(Main.getWorld(), -111.5, 81, -2.5));
		new PermShop(new Location(Main.getWorld(), -42.5, 81, -10.5));
		new FarmingShop(new Location(Main.getWorld(), -37.5, 81, 18.5));
		new MiningShop(new Location(Main.getWorld(), -33.5, 81, 18.5));
		new GuardChief(new Location(Main.getWorld(), -22, 89, -8));
		new ArmoryManager(new Location(Main.getWorld(), -25.5, 89, -17.5));
		new Storekeeper(new Location(Main.getWorld(), -25.5, 89, 18.5));
		new Cook(new Location(Main.getWorld(), 23.5, 81, 0.5));

		// new KitGuard(new Location(Main.getWorld(), -47.5,81,-1.5));
		// new KitGuard(new Location(Main.getWorld(), -163.5,72,0.5));

		new BlackMarketShop(new Location(Main.getWorld(), -37, 31, -44.5));
		new CorruptGuard(new Location(Main.getWorld(), -21.5, 33, -46.6));
		new Tutor(new Location(Main.getWorld(), -18.5, 81, -3.5));
		new TitleMerchant(new Location(Main.getWorld(), -31.5, 31, -38.5));
		new Butcher(new Location(Main.getWorld(), -139.5, 81, -27.5));
		new BlackMarketBrewingWitch(new Location(Main.getWorld(), -4.5, 31, -46.5));
		new DeliveryCow(new Location(Main.getWorld(), -18.5, 81, 3.5));
		
		// escortGuard = new EscortPrisoner(new Location(Main.getWorld(),
		// -149,81,-1.5));

		// block b
		new PermShopBlockB(new Location(Main.getWorld(), -130.5, 96, -237.5));

		for (SmartMob mob : mob_list) {
			mob.spawn();
		}

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			public void run() {
				for (SmartMob mob : mob_list) {
					if (!mob.IsSpawned())
						mob.spawn();

					if (!mob.canMove)
						mob.mob.teleport(mob.spawnLocation);
					
					mob.removeDupes();
				}
			}
		}, 0L, 10L);

		

		Log.log(LogType.NOTIFY,
				"[INITALIZING] [SMART MOBS] Smart Mobs module initalized successfully. (" + mob_list.size() + " mobs)");

	}

	Location spawnLocation;
	EntityType type;
	Entity mob;

	boolean canMove, invincible, canDamage;
	ItemStack helmet, chestplate, leggings, boots, weapon;
	String mobName;

	boolean forceBaby;
	Profession prof;

	public SmartMob(Location spawn_loc, EntityType type, String name, boolean can_move, boolean god_mode,
			boolean can_dmg) {

		this.mob = null;
		this.spawnLocation = spawn_loc;
		this.type = type;
		this.mobName = name;
		this.prof = null;

		this.canMove = can_move;
		this.invincible = god_mode;
		this.canDamage = can_dmg;

		this.forceBaby = false;

		mob_list.add(this);
		removeDupes();
	}

	public void setEquipment(ItemStack helm, ItemStack chestplate, ItemStack leggings, ItemStack boots,
			ItemStack weapon) {
		this.helmet = helm;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;

		this.weapon = weapon;

		equip();
	}

	public void forceSmall() {
		forceBaby = true;
	}

	private void equip() {
		if (this.IsSpawned() && mob instanceof LivingEntity) {
			if (helmet != null)
				((LivingEntity) mob).getEquipment().setHelmet(this.helmet);
			if (chestplate != null)
				((LivingEntity) mob).getEquipment().setChestplate(this.chestplate);
			if (leggings != null)
				((LivingEntity) mob).getEquipment().setLeggings(this.leggings);
			if (boots != null)
				((LivingEntity) mob).getEquipment().setBoots(this.boots);

			if (weapon != null)
				((LivingEntity) mob).getEquipment().setItemInHand(this.weapon);

		}
	}

	private void applyProfession() {
		if (this.IsSpawned() && this.mob.getType().equals(EntityType.VILLAGER) && prof != null)
			((Villager) mob).setProfession(prof);
	}

	public abstract void onClick(Player p, ExodusPlayer eP);

	public void setProfession(Profession prof) {
		this.prof = prof;

		applyProfession();
	}

	private void spawn() {
		mob = spawnLocation.getWorld().spawnEntity(spawnLocation, type);

		if (!(mob instanceof LivingEntity))
			return;

		((LivingEntity) mob).getEquipment().setBootsDropChance(0F);
		((LivingEntity) mob).getEquipment().setLeggingsDropChance(0F);
		((LivingEntity) mob).getEquipment().setChestplateDropChance(0F);
		((LivingEntity) mob).getEquipment().setHelmetDropChance(0F);

		((LivingEntity) mob).setRemoveWhenFarAway(false);

		if (!mobName.isEmpty()) {
			mob.setCustomName(mobName);
			mob.setCustomNameVisible(true);
		}

		if (!canMove) {
			((LivingEntity) mob).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 128));
			((LivingEntity) mob).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -2));
		}

		if (mob instanceof Zombie) {
			Zombie zombie = (Zombie) mob;

			if (forceBaby)
				zombie.setBaby(true);
			else if (zombie.isBaby())
				zombie.setBaby(false);

			if (zombie.isVillager())
				zombie.setVillager(false);

		}

		if (mob instanceof Villager) {
			Villager villager = (Villager) mob;

			if (forceBaby)
				villager.setBaby();
			else
				villager.setAdult();
		}

		equip();
		applyProfession();
	}

	public boolean equals(Entity ent) {
		return ent.equals(mob);
	}

	public String getName() {
		return this.mobName;
	}

	public Location getLocation() {
		return this.spawnLocation;
	}

	public boolean IsSpawned() {
		return mob != null && mob.isValid();
	}

	public boolean invincible() {
		return invincible;
	}

	public boolean canDamage() {
		return canDamage;
	}

	public void setCanMove(boolean a) {
		this.canMove = a;
	}

	public Location getSpawnLocation() {
		return this.spawnLocation;
	}

	public LivingEntity getEntity() {
		return (LivingEntity) this.mob;
	}

	public void NPCSay(Player p, String text) {
		p.sendMessage("§e" + this.getName() + " §7:>> " + text);
	}

	public void scheduleDelayedMessage(Player p, String message, int delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				p.sendMessage(message);

			}
		}, delay);

	}

	private void removeDupes() {
		

		for (Entity ent : Util.getNearbyEntities(spawnLocation, 3)) {
			
			/*if(this.getName() == "[B] Permissions Shop"){
				Util.globalMessage((mobList_get(ent) == null) + " 1");
				Util.globalMessage(ent.getType().equals(this.type) + " 2");
				
			}*/
			
			if (mobList_get(ent) == null && ent.getType().equals(this.type))
				ent.remove();
		}


	}

	public static void despawn() {

		for (SmartMob mob : mob_list)
			if (mob.IsSpawned())
				mob.mob.remove();
	}

	public static SmartMob mobList_get(Entity ent) {
		SmartMob found_mob = null;

		for (SmartMob mob : mob_list)
			if (mob.equals(ent)) {
				found_mob = mob;
			}

		return found_mob;
	}

	public static void playerInteract(PlayerInteractEntityEvent e) {
		Entity rightClicked = e.getRightClicked();
		final Player p = e.getPlayer();

		if (mobList_get(rightClicked) == null)
			return;

		if (cooldown_list.contains(p)) {
			e.setCancelled(true);
			return;
		}

		e.setCancelled(true);

		mobList_get(rightClicked).onClick(p, ExodusPlayer.get(p));

		cooldown_list.add(p);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			public void run() {
				cooldown_list.remove(p);
			}
		}, delay);
	}

	public static void entityDamageEntity(EntityDamageByEntityEvent e) {

		if (SmartMob.mobList_get(e.getDamager()) != null && !SmartMob.mobList_get(e.getDamager()).canDamage())
			e.setCancelled(true);

		if (SmartMob.mobList_get(e.getEntity()) != null && SmartMob.mobList_get(e.getEntity()).invincible())
			e.setCancelled(true);

	}

	public static void entityCombust(EntityCombustEvent e) {
		if (SmartMob.mobList_get(e.getEntity()) != null)
			e.setCancelled(true);
	}

	public static void removeMobs() {
		for(SmartMob mob : mob_list)
			mob.getEntity().remove();
		
	}

}
