package events.stash.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import bukkit.exodusprison.spurkle.Main;
import classes.Reward;
import events.stash.Stash;
import events.stash.Stash.StashType;
import items.Item;
import logger.Log;
import logger.Log.LogType;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class StashHandler {

	private static final int MIN_PLAYERS = 5;
	private static final int CHESTS_AMOUNT = 2;

	private static final int GUARDS_PERCENT = 8;
	private static final int LEGENDARY_PERCENT = 1;

	private static List<Location> randomLocations = new ArrayList<Location>();

	private static int minutes = 59, seconds = 59;
	private static boolean timerRunning = false;

	public static void setupVars() {

		startTimer();

		StashType.MINING.addReward(new Reward().setMoneyReward(100, 200), 5);
		StashType.MINING.addReward(new Reward().setTokenReward(1, 3), 2);
		
		StashType.MINING.addReward(new Reward().setItemReward(Item.smallMineBomb, 1, 5), 5);
		StashType.MINING.addReward(new Reward().setItemReward(Item.mediumMineBomb, 1, 5), 3);
		StashType.MINING.addReward(new Reward().setItemReward(Item.bigMineBomb, 1, 3), 1);

		StashType.MINING.addReward(new Reward().setItemReward(Item.cobblestone, 1, 16), 150);
		StashType.MINING.addReward(new Reward().setItemReward(Item.glowstoneDust, 1, 16), 5);
		StashType.MINING.addReward(new Reward().setItemReward(Item.ironOre, 1, 4), 5);
		StashType.MINING.addReward(new Reward().setItemReward(Item.coal, 1, 16), 5);

		StashType.MINING.addReward(new Reward().setItemReward(Item.leatherHelmet, 1, 1), 3);
		StashType.MINING.addReward(new Reward().setItemReward(Item.leatherChestplate, 1, 1), 3);
		StashType.MINING.addReward(new Reward().setItemReward(Item.leatherLeggings, 1, 1), 3);
		StashType.MINING.addReward(new Reward().setItemReward(Item.leatherBoots, 1, 1), 3);

		StashType.MINING.addReward(new Reward().setItemReward(Item.woodPickaxe, 1, 1), 15);
		StashType.MINING.addReward(new Reward().setItemReward(Item.stonePickaxe, 1, 1), 2);
		StashType.MINING.addReward(new Reward().setItemReward(Item.ironPickaxe, 1, 1), 1);

		StashType.MINING.addReward(new Reward().setItemReward(Item.bread, 1, 8), 5);
		StashType.MINING.addReward(new Reward().setItemReward(Item.steak, 1, 8), 5);
		StashType.MINING.addReward(new Reward().setItemReward(Item.furnace, 1, 5), 5);
		StashType.MINING.addReward(new Reward().setItemReward(Item.torch, 32, 64), 5);

		randomLocations.add(new Location(Main.getWorld(), 21, 82, -4));
		randomLocations.add(new Location(Main.getWorld(), 18, 82, -20));
		randomLocations.add(new Location(Main.getWorld(), 14, 82, -16));
		randomLocations.add(new Location(Main.getWorld(), 4, 81, 9));
		randomLocations.add(new Location(Main.getWorld(), -3, 81, 18));
		randomLocations.add(new Location(Main.getWorld(), -21, 84, 10));
		randomLocations.add(new Location(Main.getWorld(), -21, 81, 15));
		randomLocations.add(new Location(Main.getWorld(), -21, 81, -8));
		randomLocations.add(new Location(Main.getWorld(), -25, 86, -3));
		randomLocations.add(new Location(Main.getWorld(), -31, 81, -7));
		randomLocations.add(new Location(Main.getWorld(), -26, 81, -14));
		randomLocations.add(new Location(Main.getWorld(), -34, 85, -14));
		randomLocations.add(new Location(Main.getWorld(), -37, 81, -15));
		randomLocations.add(new Location(Main.getWorld(), -36, 81, -7));
		randomLocations.add(new Location(Main.getWorld(), -42, 82, -13));
		randomLocations.add(new Location(Main.getWorld(), -44, 82, -15));
		randomLocations.add(new Location(Main.getWorld(), -33, 81, -16));
		randomLocations.add(new Location(Main.getWorld(), -26, 82, -17));
		randomLocations.add(new Location(Main.getWorld(), -29, 84, -16));
		randomLocations.add(new Location(Main.getWorld(), -26, 82, -33));
		randomLocations.add(new Location(Main.getWorld(), -34, 82, -36));
		randomLocations.add(new Location(Main.getWorld(), -41, 82, -50));
		randomLocations.add(new Location(Main.getWorld(), -36, 32, -50));
		randomLocations.add(new Location(Main.getWorld(), -25, 32, -45));
		randomLocations.add(new Location(Main.getWorld(), -14, 35, -39));
		randomLocations.add(new Location(Main.getWorld(), -56, 82, -42));
		randomLocations.add(new Location(Main.getWorld(), -70, 82, -50));
		randomLocations.add(new Location(Main.getWorld(), -75, 86, -43));
		randomLocations.add(new Location(Main.getWorld(), -115, 85, -50));
		randomLocations.add(new Location(Main.getWorld(), -116, 82, -52));
		randomLocations.add(new Location(Main.getWorld(), -127, 81, -46));
		randomLocations.add(new Location(Main.getWorld(), -136, 82, -31));
		randomLocations.add(new Location(Main.getWorld(), -126, 81, -25));
		randomLocations.add(new Location(Main.getWorld(), -128, 82, -7));
		randomLocations.add(new Location(Main.getWorld(), -149, 81, -3));
		randomLocations.add(new Location(Main.getWorld(), -164, 72, 2));
		randomLocations.add(new Location(Main.getWorld(), -174, 97, 30));
		randomLocations.add(new Location(Main.getWorld(), -211, 97, -27));
		randomLocations.add(new Location(Main.getWorld(), -139, 82, 4));
		randomLocations.add(new Location(Main.getWorld(), -136, 82, 20));
		randomLocations.add(new Location(Main.getWorld(), -141, 82, 25));
		randomLocations.add(new Location(Main.getWorld(), -128, 82, 33));
		randomLocations.add(new Location(Main.getWorld(), -130, 82, 59));
		randomLocations.add(new Location(Main.getWorld(), -113, 81, -3));
		randomLocations.add(new Location(Main.getWorld(), -98, 82, 4));
		randomLocations.add(new Location(Main.getWorld(), -80, 73, -2));
		randomLocations.add(new Location(Main.getWorld(), -77, 73, -6));
		randomLocations.add(new Location(Main.getWorld(), -76, 76, -12));
		randomLocations.add(new Location(Main.getWorld(), -69, 72, -12));
		randomLocations.add(new Location(Main.getWorld(), -87, 76, 0));
		randomLocations.add(new Location(Main.getWorld(), -87, 85, 2));
		randomLocations.add(new Location(Main.getWorld(), -73, 85, -4));
		randomLocations.add(new Location(Main.getWorld(), -50, 81, -3));
		randomLocations.add(new Location(Main.getWorld(), -45, 82, 4));
		randomLocations.add(new Location(Main.getWorld(), -35, 82, -4));
		randomLocations.add(new Location(Main.getWorld(), -32, 81, 12));
		randomLocations.add(new Location(Main.getWorld(), -33, 82, 17));
		randomLocations.add(new Location(Main.getWorld(), -40, 84, 12));
		randomLocations.add(new Location(Main.getWorld(), -39, 84, 7));
		randomLocations.add(new Location(Main.getWorld(), -20, 88, -8));
		randomLocations.add(new Location(Main.getWorld(), -23, 33, -47));

		StashType.PRISONERS.addReward(new Reward().setMoneyReward(1000, 5000), 5);
		StashType.PRISONERS.addReward(new Reward().setMoneyReward(100, 500), 10);
		StashType.PRISONERS.addReward(new Reward().setMoneyReward(100, 100), 15);

		StashType.PRISONERS.addReward(new Reward().setTokenReward(1, 4), 5);

		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.enchantingVoucher_low, 1, 2), 2);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.enchantingVoucher_mid, 1, 1), 2);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.enchantingVoucher_high, 1, 1), 1);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.poisonedShiv, 1, 1), 2);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.guardTracker, 1, 1), 5);
		
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.smallMineBomb, 1, 5), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.mediumMineBomb, 1, 5), 3);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.bigMineBomb, 1, 3), 2);
		
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.guardSword_lv1, 1, 1), 3);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.guardHelm_lv1, 1, 1), 3);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.guardChest_lv1, 1, 1), 3);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.guardLegs_lv1, 1, 1), 3);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.guardBoots_lv1, 1, 1), 3);

		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironHelmet, 1, 1), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironChestplate, 1, 1), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironLeggings, 1, 1), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironBoots, 1, 1), 5);

		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.leatherHelmet, 1, 1), 8);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.leatherChestplate, 1, 1), 8);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.leatherLeggings, 1, 1), 8);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.leatherBoots, 1, 1), 8);

		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.cactusGreen, 1, 16), 10);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.LSD, 1, 2), 3);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.meth, 1, 1), 1);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.sugar, 1, 64), 20);

		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.woodSword, 1, 1), 15);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.stoneSword, 1, 1), 10);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironSword, 1, 1), 3);

		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.woodAxe, 1, 1), 15);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.woodHoe, 1, 1), 15);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.woodPickaxe, 1, 1), 15);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.woodShovel, 1, 1), 15);

		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.stoneAxe, 1, 1), 10);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.stoneHoe, 1, 1), 10);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.stonePickaxe, 1, 1), 10);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.stoneShovel, 1, 1), 10);

		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironAxe, 1, 1), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironHoe, 1, 1), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironPickaxe, 1, 1), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironShovel, 1, 1), 5);

		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.apple, 1, 16), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.awkwardPotion, 1, 5), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.birchSapling, 1, 32), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.birchWood, 1, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.blazePowder, 1, 3), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.book, 1, 8), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.bread, 1, 16), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.cactus, 1, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.chest, 1, 5), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.cobblestone, 48, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.cookedChicken, 1, 16), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.cookedfish, 1, 16), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.fermentedSpiderEye, 1, 8), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.furnace, 1, 5), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ghastTear, 1, 3), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.glistringMelon, 1, 2), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.glowstoneDust, 16, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.goldenCarrot, 1, 5), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.ironIngot, 8, 32), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.jackOLatern, 1, 1), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.leather, 16, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.melonslice, 48, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.oakSapling, 1, 32), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.oakWood, 1, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.paper, 1, 16), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.pumpkin, 1, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.rawBeef, 32, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.steak, 1, 16), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.stone, 16, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.sugarcane, 64, 64), 5);
		StashType.PRISONERS.addReward(new Reward().setItemReward(Item.torch, 32, 64), 5);

		StashType.GUARDS.addReward(new Reward().setMoneyReward(1000, 5000), 7);
		StashType.GUARDS.addReward(new Reward().setMoneyReward(100, 500), 8);
		StashType.GUARDS.addReward(new Reward().setMoneyReward(100, 100), 5);

		StashType.GUARDS.addReward(new Reward().setTokenReward(1, 8), 5);

		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardSword_lv1, 1, 1), 10);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardHelm_lv1, 1, 1), 10);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardChest_lv1, 1, 1), 10);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardLegs_lv1, 1, 1), 10);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardBoots_lv1, 1, 1), 10);

		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardSword_lv2, 1, 1), 8);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardHelm_lv2, 1, 1), 8);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardChest_lv2, 1, 1), 8);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardLegs_lv2, 1, 1), 8);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardBoots_lv2, 1, 1), 8);

		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardSword_lv3, 1, 1), 4);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardHelm_lv3, 1, 1), 4);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardChest_lv3, 1, 1), 4);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardLegs_lv3, 1, 1), 4);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.guardBoots_lv3, 1, 1), 4);

		StashType.GUARDS.addReward(new Reward().setItemReward(Item.cactusGreen, 1, 16), 10);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.LSD, 1, 2), 3);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.meth, 1, 1), 1);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.sugar, 1, 64), 2);

		StashType.GUARDS.addReward(new Reward().setItemReward(Item.woodSword, 1, 1), 5);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.stoneSword, 1, 1), 10);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.ironSword, 1, 1), 3);

		StashType.GUARDS.addReward(new Reward().setItemReward(Item.stoneAxe, 1, 1), 2);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.stoneHoe, 1, 1), 2);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.stonePickaxe, 1, 1), 2);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.stoneShovel, 1, 1), 2);

		StashType.GUARDS.addReward(new Reward().setItemReward(Item.ironAxe, 1, 1), 5);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.ironHoe, 1, 1), 5);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.ironPickaxe, 1, 1), 5);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.ironShovel, 1, 1), 5);
		
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.enchantingVoucher_low, 1, 2), 2);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.enchantingVoucher_mid, 1, 1), 2);
		StashType.GUARDS.addReward(new Reward().setItemReward(Item.enchantingVoucher_high, 1, 1), 1);


		StashType.LEGENDARY.addReward(new Reward().setMoneyReward(1000, 5000), 1);
		StashType.LEGENDARY.addReward(new Reward().setMoneyReward(100, 500), 2);
		StashType.LEGENDARY.addReward(new Reward().setTokenReward(1, 16), 5);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardSword_lv1, 1, 1), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardHelm_lv1, 1, 1), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardChest_lv1, 1, 1), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardLegs_lv1, 1, 1), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardBoots_lv1, 1, 1), 5);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardSword_lv2, 1, 1), 4);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardHelm_lv2, 1, 1), 4);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardChest_lv2, 1, 1), 4);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardLegs_lv2, 1, 1), 4);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardBoots_lv2, 1, 1), 4);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardSword_lv3, 1, 1), 3);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardHelm_lv3, 1, 1), 3);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardChest_lv3, 1, 1), 3);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardLegs_lv3, 1, 1), 3);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardBoots_lv3, 1, 1), 3);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardSword_lv4, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardHelm_lv4, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardChest_lv4, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardLegs_lv4, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardBoots_lv4, 1, 1), 1);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironHelmet, 1, 1), 4);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironChestplate, 1, 1), 4);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironLeggings, 1, 1), 4);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironBoots, 1, 1), 4);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.leatherHelmet, 1, 1), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.leatherChestplate, 1, 1), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.leatherLeggings, 1, 1), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.leatherBoots, 1, 1), 2);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.cactusGreen, 8, 64), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.LSD, 1, 8), 3);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.meth, 1, 8), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.sugar, 64, 64), 5);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.woodSword, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.stoneSword, 1, 1), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironSword, 1, 1), 3);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.woodAxe, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.woodHoe, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.woodPickaxe, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.woodShovel, 1, 1), 1);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.stoneAxe, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.stoneHoe, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.stonePickaxe, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.stoneShovel, 1, 1), 1);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironAxe, 1, 1), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironHoe, 1, 1), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironPickaxe, 1, 1), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironShovel, 1, 1), 2);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.apple, 1, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.awkwardPotion, 1, 16), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.birchSapling, 32, 64), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.birchWood, 32, 64), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.blazePowder, 1, 16), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.book, 1, 8), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.bread, 1, 16), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.cactus, 64, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.chest, 1, 5), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.cobblestone, 48, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.cookedChicken, 1, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.cookedfish, 1, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.fermentedSpiderEye, 1, 16), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.furnace, 1, 3), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ghastTear, 4, 8), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.glistringMelon, 3, 8), 3);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.glowstoneDust, 54, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.goldenCarrot, 1, 8), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.ironIngot, 48, 64), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.jackOLatern, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.leather, 48, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.melonslice, 48, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.oakSapling, 32, 64), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.oakWood, 32, 64), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.paper, 1, 16), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.pumpkin, 1, 64), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.rawBeef, 32, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.steak, 1, 16), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.stone, 16, 64), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.sugarcane, 64, 64), 5);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.torch, 32, 64), 1);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.randomTitle, 1, 1), 1);

		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.smallMineBomb, 1, 16), 2);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.mediumMineBomb, 1, 16), 3);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.bigMineBomb, 1, 16), 2);
		
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.lockpick, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.enchantingVoucher_low, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.enchantingVoucher_mid, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.enchantingVoucher_high, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.poisonedShiv, 1, 1), 1);
		StashType.LEGENDARY.addReward(new Reward().setItemReward(Item.guardTracker, 1, 1), 1);
		
		
		Log.log(LogType.NOTIFY,"[INITALIZING] [STASH] Stash module initalized successfully.");
	}

	private static void startTimer() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			public void run() {
				updateTime();
			}
		}, 0, 20L);
	}

	private static void updateTime() {

		timerRunning = Bukkit.getOnlinePlayers().size() >= MIN_PLAYERS;

		if (timerRunning) {
			if (seconds <= 0) {
				seconds = 60;
				minutes -= 1;

				if (minutes < 0) {

					startEvent();

				}

			}

			if (minutes < 0)
				minutes = 59;

			seconds--;
		}
	}

	public static void startEvent() {
		removeStashes();

		for (int i = 0; CHESTS_AMOUNT >= i; i++) {
			Location random = (Location) Util.getListRandom(randomLocations);

			while (!random.getBlock().getType().equals(Material.AIR))
				random = (Location) Util.getListRandom(randomLocations);

			spawnStash(random);

		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5F, 0.8F);
			GUI.sendTitle(p, 10, 60, 10, "&6Prisoner Stash Event",
					"&6" + CHESTS_AMOUNT + "&e chests have been randomly spawned.");
		}

		ServerStatistics.incrementKey("prisoner_stashes_spawned", 1);
	}

	public static void removeStashes() {

		for (Location loc : Stash.getStashes().keySet()) {
			loc.getBlock().setType(Material.AIR);

		}
		
		Log.log(LogType.WARNING,"[STOPPING] [STASHES] Removed " + Stash.getStashes().size() + " stashes.");

	}

	public static void spawnStash(Location loc) {
		StashType type;
		
		if (Util.countChance(LEGENDARY_PERCENT))
			type = StashType.LEGENDARY;
		else if (Util.countChance(GUARDS_PERCENT))
			type = StashType.GUARDS;
		else
			type = StashType.PRISONERS;

		new Stash(loc, type);

	}

	public static int getMinPlayers() {
		return MIN_PLAYERS;
	}

	public static String getCountdown() {
		return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
	}

}
