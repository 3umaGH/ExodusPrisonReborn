package util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import bukkit.exodusprison.spurkle.Main;
import logger.Log;
import logger.Log.LogType;
import mkremins.fanciful.FancyMessage;

public class Util {

	static Random randomGenerator = new Random();

	public static void sendBungeeMessage(Player p, String... msg) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		for (String message : msg)
			out.writeUTF(message);

		p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
	}
	
	public static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	
	public static List<Block> getNearbyBlocks(Location location, int Radius) {
		List<Block> Blocks = new ArrayList<Block>();

		for (int X = location.getBlockX() - Radius; X <= location.getBlockX() + Radius; X++) {
			for (int Y = location.getBlockY() - Radius; Y <= location.getBlockY() + Radius; Y++) {
				for (int Z = location.getBlockZ() - Radius; Z <= location.getBlockZ() + Radius; Z++) {
					Block block = location.getWorld().getBlockAt(X, Y, Z);
					if (!block.isEmpty()) {
						Blocks.add(block);
					}
				}
			}
		}

		return Blocks;
	}

	public static boolean inRegion(ProtectedRegion reg, Location loc) {
		WorldGuardPlugin guard = Main.getWG();
		Vector v = BukkitUtil.toVector(loc);
		RegionManager manager = guard.getRegionManager(loc.getWorld());
		ApplicableRegionSet set = manager.getApplicableRegions(v);
		return set.getRegions().contains(reg);
	}

	public static String convertEnchantmentName(Enchantment e) {

		switch (e.getName()) {

		case "PROTECTION_ENVIRONMENTAL":
			return "Protection";
		case "PROTECTION_FIRE":
			return "";
		case "PROTECTION_FALL":
			return "Fire Protection";
		case "PROTECTION_EXPOLOSIONS":
			return "Blast Protection";
		case "PROTECTION_PROJECTILE":
			return "Projectile Protection";
		case "OXYGENL":
			return "Respiration";
		case "WATER_WORKER":
			return "Aqua Affinity";
		case "THORNS":
			return "Thorns";
		case "DEPTH_STRIDER":
			return "Depth Strider";
		case "FROST_WALKER":
			return "Frost Walker";

		case "DAMAGE_ALL":
			return "Sharpness";
		case "DAMAGE_UNDEAD":
			return "Smite";
		case "DAMAGE_ARTHROPODS":
			return "Bane of Arthropods";
		case "KNOCKBACK":
			return "Knockback";
		case "FIRE_ASPECT":
			return "Fire Aspect";
		case "LOOT_BONUS_MOBS":
			return "Looting";
		case "DIG_SPEED":
			return "Efficiency";
		case "SILK_TOUCH":
			return "Silk Touch";
		case "DURABILITY":
			return "Unbreaking";
		case "LOOT_BONUS_BLOCKS":
			return "Fortune";

		case "ARROW_DAMAGE":
			return "Power";
		case "ARROW_KNOCKBACK":
			return "Punch";
		case "ARROW_FIRE":
			return "Flame";
		case "ARROW_INFINITE":
			return "Infinity";

		case "LUCK":
			return "Luck of the sea";
		case "LURE":
			return "Lure";
		case "MENDING":
			return "Mending";
		default:
			return "error";

		}
	}

	public static float getRangedFloat(float highest, float lowest) {
		float rn = randomGenerator.nextFloat();
		if (!(rn > highest || rn < lowest))
			return rn;
		else
			return getRangedFloat(highest, lowest);
	}

	public static String formatTimeMillis(Long time, String format) { // ex
																		// yyyy/MM/dd
																		// HH:mm:ss
		Date date = new Date(time);
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String readableLocation(Location loc) {
		String location = String.format("%.1f", loc.getX()) + ", " + String.format("%.1f", loc.getY()) + ", "
				+ String.format("%.1f", loc.getZ());
		return location;
	}

	public static String splitText(String text, int MAX_CHARS) {
		String input = text;
		String output = "";

		if (text.length() > MAX_CHARS) {

			int i = 0;
			String[] words = text.split(" ");
			while (i < words.length - 1) {
				String newLine = "";

				while (newLine.length() < MAX_CHARS) {

					if (i > words.length - 1)
						break;

					if (newLine.isEmpty())
						newLine = newLine + words[i];
					else
						newLine = newLine + " " + words[i];

					if (newLine.length() > MAX_CHARS) {

						newLine = newLine.replace(words[i], "");
						continue;

					}

					input.replace(words[i], "");

					i++;
				}

				if (!output.isEmpty())
					output = output + "/n" + newLine;
				else
					output = output + newLine;

			}
		}

		return output;
	}

	public static Object getListRandom(List<?> list) {
		return list.get(Util.generateRandom(list.size()));
	}

	public static int removeItems(org.bukkit.inventory.Inventory inventory, ItemStack is) {

		int amount = is.getAmount();

		if (is.getType() == null || inventory == null)
			return -1;
		if (amount <= 0)
			return -1;

		if (amount == Integer.MAX_VALUE) {
			inventory.remove(is);
			return 0;
		}

		HashMap<Integer, ItemStack> retVal = inventory.removeItem(is);

		int notRemoved = 0;
		for (ItemStack item : retVal.values()) {
			notRemoved += item.getAmount();
		}
		return notRemoved;
	}

	public static int LongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

	public static void killAllMobs(World world) {
		for (Entity e : world.getEntities()) {
			if (e instanceof LivingEntity && !(e instanceof Player))
				e.remove();

		}
	}

	public static void giveItem(Player p, ItemStack item, int amount) {

		ItemStack newItem = new ItemStack(item);
		newItem.setAmount(amount);

		if (inventoryFull(p))
			p.getLocation().getWorld().dropItem(p.getLocation(), newItem);
		else
			p.getInventory().addItem(newItem);

		p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 1F);
		p.updateInventory();

	}

	public static String getDisplayName(ItemStack item) {
		String item_name = "";

		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName())
			item_name = item.getItemMeta().getDisplayName();
		else {

			String lowItem = item.getType().name().replace("_", " ").toLowerCase();
			String itemName = lowItem.substring(0, 1).toUpperCase() + lowItem.substring(1);

			item_name = itemName;
		}

		return item_name;
	}

	public static boolean isNumeric(String s) {
		return s.matches("\\d+");
	}

	public static ItemStack createItem(Material material, int amount, short data, String name, String... lore) {
		ItemStack stack = new ItemStack(material, amount, data);
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(colorize(name));
		ArrayList<String> loreList = new ArrayList<String>();

		for (String s : lore) {

			s = "§7" + s;

			if (s.contains("/n")) {
				String[] text = s.split("/n");

				for (String ss : text) {

					ss = "§7" + ss;

					loreList.add(colorize(ss));

				}
			} else
				loreList.add(colorize(s));

		}

		meta.setLore(loreList);
		stack.setItemMeta(meta);

		return stack;
	}

	public static String colorize(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static void removeFromHand(Player p) {
		ItemStack inHand = p.getItemInHand();

		if (inHand.getAmount() == 1)
			p.setItemInHand(null);
		else
			inHand.setAmount(inHand.getAmount() - 1);

		p.updateInventory();
	}

	public static void removeFromCursor(Player p) {
		ItemStack inHand = p.getItemOnCursor();

		if (inHand.getAmount() == 1)
			p.setItemOnCursor(null);
		else
			inHand.setAmount(inHand.getAmount() - 1);

		p.updateInventory();
	}

	public static int generateRandom(int nextInt) {
		return randomGenerator.nextInt(nextInt);
	}

	public static int generateRandomBetween(int min, int max) {
		return randomGenerator.nextInt(max - min + 1) + min;
	}

	public static void globalMessage(String msg) {
		for (Player allP : Bukkit.getOnlinePlayers())
			allP.sendMessage(msg);

		Log.log(LogType.LOG, msg);
	}

	public static void globalItemMessage(String message, ItemStack i) {
		String tooltip = " ";

		StringBuilder result = new StringBuilder();
		result.append(i.getItemMeta().getDisplayName());

		for (Entry<Enchantment, Integer> s : i.getEnchantments().entrySet())
			result.append("\n")
					.append("§7" + convertEnchantmentName(s.getKey()) + " " + Util.arabicToRoman(s.getValue()));

		if (i.hasItemMeta() && i.getItemMeta().hasLore()) {

			for (String s : i.getItemMeta().getLore())
				result.append("\n").append(s);

		}

		tooltip = result.toString();

		if (tooltip == null)
			tooltip = " ";

		String lowItem = i.getType().name().replace("_", " ").toLowerCase();
		String itemName = lowItem.substring(0, 1).toUpperCase() + lowItem.substring(1);

		if (i.hasItemMeta() && i.getItemMeta().getDisplayName() != null)
			itemName = i.getItemMeta().getDisplayName();

		for (Player allP : Bukkit.getOnlinePlayers())
			new FancyMessage(message.replace("[item]", "§7[" + i.getAmount() + "x §f" + itemName + "§7]§r"))
					.tooltip(tooltip).send(allP);

		Log.log(message.replace("[item]", "§7[" + i.getAmount() + "x §f" + itemName + "§7]§r"));
	}
	
	public static void privateItemMessage(Player p, String message, ItemStack i) {
		String tooltip = " ";

		StringBuilder result = new StringBuilder();
		result.append(i.getItemMeta().getDisplayName());

		for (Entry<Enchantment, Integer> s : i.getEnchantments().entrySet())
			result.append("\n")
					.append("§7" + convertEnchantmentName(s.getKey()) + " " + Util.arabicToRoman(s.getValue()));

		if (i.hasItemMeta() && i.getItemMeta().hasLore()) {

			for (String s : i.getItemMeta().getLore())
				result.append("\n").append(s);

		}

		tooltip = result.toString();

		if (tooltip == null)
			tooltip = " ";

		String lowItem = i.getType().name().replace("_", " ").toLowerCase();
		String itemName = lowItem.substring(0, 1).toUpperCase() + lowItem.substring(1);

		if (i.hasItemMeta() && i.getItemMeta().getDisplayName() != null)
			itemName = i.getItemMeta().getDisplayName();

		new FancyMessage(message.replace("[item]", "§7[" + i.getAmount() + "x §f" + itemName + "§7]§r"))
					.tooltip(tooltip).send(p);

		Log.log(message.replace("[item]", "§7[" + i.getAmount() + "x §f" + itemName + "§7]§r"));
	}

	public static void globalMessageWorld(String msg, World world) {
		for (Player allP : Bukkit.getOnlinePlayers())
			if (allP.getWorld().equals(world))
				allP.sendMessage(msg);

		Log.log(LogType.LOG, msg);
	}

	public static void globalMessageExcept(Player p, String msg) {
		for (Player allP : Bukkit.getOnlinePlayers())
			if (!allP.equals(p))
				allP.sendMessage(msg);

		Log.log(LogType.LOG, msg);
	}

	public static String makeFirstCapital(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}

	public static String arabicToRoman(int number) {
		switch (number) {
		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return "V";
		case 6:
			return "VI";
		case 7:
			return "VII";
		case 8:
			return "VIII";
		case 9:
			return "IX";
		case 10:
			return "X";
		default:
			return String.valueOf(number);
		}
	}

	public static int romanToArabic(String number) {
		switch (number) {
		case "I":
			return 1;
		case "II":
			return 2;
		case "III":
			return 3;
		case "IV":
			return 4;
		case "V":
			return 5;
		case "VI":
			return 6;
		case "VII":
			return 7;
		case "VIII":
			return 8;
		case "IX":
			return 9;
		case "X":
			return 10;
		default:
			return Integer.valueOf(number);
		}
	}

	public static String tierToText(int tier) {
		switch (tier) {
		case 1:
			return tierToColor(tier) + "Common";
		case 2:
			return tierToColor(tier) + "Uncommon";
		case 3:
			return tierToColor(tier) + "Rare";
		case 4:
			return tierToColor(tier) + "Ultimate";
		case 5:
			return tierToColor(tier) + "Legendary";
		case 6:
			return tierToColor(tier) + "Unreal";
		default:
			return tierToColor(tier) + "Common";
		}
	}

	public static String tierToColor(int tier) {
		switch (tier) {
		case 1:
			return "§7";
		case 2:
			return "§f";
		case 3:
			return "§c";
		case 4:
			return "§e";
		case 5:
			return "§6";
		case 6:
			return "§d";
		default:
			return "§7";
		}
	}

	public static boolean inventoryFull(Player p) {
		return p.getInventory().firstEmpty() == -1;
	}

	public static void leatherSetColor(ItemStack item, Color color) {
		try {
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(color);
			item.setItemMeta(meta);
		} catch (Exception localException) {
		}
	}

	public static boolean isInBorder(Location center, Location notCenter, int range) {
		int x = center.getBlockX(), z = center.getBlockZ();
		int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();

		if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)
				|| range <= (center.getY() - notCenter.getY()) || range <= (notCenter.getY() - center.getY()))
			return false;

		if (!center.getWorld().getName().equals(notCenter.getWorld().getName()))
			return false;

		return true;
	}

	public static List<Entity> getNearbyEntities(Location where, int range) {
		List<Entity> found = new ArrayList<Entity>();

		for (Entity entity : where.getWorld().getEntities())
			if (isInBorder(where, entity.getLocation(), range))
				found.add(entity);

		return found;
	}

	public static ItemStack getTexturedHead(String texture) {
		ItemStack head = Util.createItem(Material.SKULL_ITEM, 1, (short) 3, "head");

		setHeadTexture(head, texture);

		return head;
	}

	public static void setHeadTexture(ItemStack head, String texture) {
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		String uuid = UUID.randomUUID().toString();

		GameProfile profile = new GameProfile(UUID.fromString(uuid), null);
		profile.getProperties().put("textures", new Property("textures", new String(texture)));

		Field profileField = null;

		try {
			profileField = meta.getClass().getDeclaredField("profile");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		profileField.setAccessible(true);
		try {
			profileField.set(meta, profile);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		head.setItemMeta(meta);

	}

	public static boolean countChance(int chance) {
		return (generateRandom(100) < chance);
	}

	public static boolean closerThan(Location loc1, Location loc2, int radius) {
		return loc1.distance(loc2) < radius;
	}

	public static String center(String text, int size) {
		return StringUtils.center(text, size, ' ');
	}

	public static void clearChat(Player p) {
		for (int i = 0; i < 20; i++)
			p.sendMessage("");
	}

}
