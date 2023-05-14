package permission;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import bukkit.exodusprison.spurkle.Main;
import classes.Cuboid;
import logger.Log;
import logger.Log.LogType;
import util.Util;

public class Permissions {

	// private static HashMap<String, Permissions> permissions = new
	// HashMap<String, Permissions>();

	public enum Permission {

		BEGINNERS_MINE("Beginners Mine", 0, Material.GLOWSTONE_DUST), TREE_FARM("Tree Farm", 7500,
				Material.SAPLING), CELLBLOCK_A("Cell Block A", 10000, Material.CHEST), CACTUS_FARM("Cactus Farm", 15000,
						Material.CACTUS), PUMPKIN_FARM("Pumpkin Farm", 25000, Material.PUMPKIN), SUGARCANE_FARM(
								"Sugarcane Farm", 30000, Material.SUGAR_CANE), MELON_FARM("Melon Farm", 70000,
										Material.MELON), DANGEROUS_MINE("Dangerous Mine", 75000,
												Material.IRON_INGOT), BLOCK_B("Block B", 200000, Material.IRON_DOOR),

		CHICKEN_FARM("Chicken Farm", 35000, Material.EGG), GRAVEL_MINE("Gravel Mine", 45000,
				Material.GRAVEL), CELL_BLOCK_B("Cell Block B", 50000, Material.BED), COAL_MINE("Coal Mine", 60000,
						Material.COAL), SHEEP_FARM("Sheep Farm", 80000, Material.WOOL), SPIDER_FARM("Spider Farm",
								200000, Material.STRING), GOLD_MINE("Gold Mine", 250000,
										Material.GOLD_INGOT), BLOCK_C("Block C", 1000000, Material.MAP),

		SPAWN("Spawn", 0, Material.EMPTY_MAP), JAIL("Jail", 0, Material.IRON_FENCE), SHOPS("Shops", 0,
				Material.GOLD_INGOT),

		;

		private String name;
		private int price, warpSlot;

		private Location entrance, location;
		private List<Cuboid> cuboids = new ArrayList<Cuboid>();

		private Material icon;
		private String description;

		private boolean tpOnQuit, teleportableTo, visibleInPassport, defaultPerm, guardsEnterDefault;

		Permission(String name, int price, Material icon) {
			this.name = name;
			this.price = price;
			this.icon = icon;
			this.description = "&7";

			this.tpOnQuit = false;
			this.teleportableTo = true;
			this.visibleInPassport = true;
			this.defaultPerm = false;
			this.guardsEnterDefault = true;

			this.warpSlot = 0;
		}

		public List<Cuboid> getCuboids() {
			return cuboids;
		}

		public void addCuboid(Cuboid cuboid) {
			this.cuboids.add(cuboid);
		}

		public int getPrice() {
			return price;
		}

		public int getWarpSlot() {
			return warpSlot;
		}

		
		
		public boolean isGuardsEnterDefault() {
			return guardsEnterDefault;
		}

		public void setGuardsEnterDefault(boolean guardsEnterDefault) {
			this.guardsEnterDefault = guardsEnterDefault;
		}

		public void setWarpSlot(int warpSlot) {
			this.warpSlot = warpSlot;
		}

		public boolean isDefaultPerm() {
			return defaultPerm;
		}

		public void setDefaultPerm(boolean defaultPerm) {
			this.defaultPerm = defaultPerm;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public void setTeleportOnDisconnect(boolean t) {
			this.tpOnQuit = t;
		}

		public boolean getTeleportOnDisconnect() {
			return this.tpOnQuit;
		}

		public String getName() {
			return name;
		}

		public Location getEntrance() {
			return entrance;
		}

		public String getDescription() {
			return this.description;
		}

		public Location getLocation() {
			return location;
		}

		public boolean isVisibleInPassport() {
			return visibleInPassport;
		}

		public void setVisibleInPassport(boolean visibleInPassport) {
			this.visibleInPassport = visibleInPassport;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public boolean isTeleportableTo() {
			return teleportableTo;
		}

		public void setTeleportableTo(boolean teleportableTo) {
			this.teleportableTo = teleportableTo;
		}

		public void setEntrance(Location entrance) {
			this.entrance = entrance;
		}

		public void setDescription(String... description) {
			this.description = "";

			for (String line : description) {
				this.description = this.description + Util.center(line, 60) + "/n";
			}

		}

		public Material getIcon() {
			return this.icon;
		}

		public void setIcon(Material icon) {
			this.icon = icon;
		}

		public boolean isInside(Player p) {
			boolean isInside = false;

			for (Cuboid cuboid : this.getCuboids()) {
				if (cuboid.isIn(p.getPlayer().getLocation())) {
					isInside = true;
					break;
				}
			}

			return isInside;
		}

		public static void initalize() {

			BLOCK_C.setTeleportableTo(false);
			BLOCK_C.setDescription("Block C of the Prison.", "", " &e&lContents:", "Even more mines and farms!");
			
			CHICKEN_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -135, 71, -243), // chicken_farm_1
					new Location(Main.getWorld(), -132, 83, -236)));
			CHICKEN_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -137, 73, -268), // chicken_farm
					new Location(Main.getWorld(), -125, 80, -242)));
			CHICKEN_FARM.setLocation(new Location(Main.getWorld(), -133, 81, -237, -180, 0));
			CHICKEN_FARM.setEntrance(new Location(Main.getWorld(), -133, 81, -233.5, -180, 0));
			CHICKEN_FARM.setDescription("An eggcelent farm bauk!", "", " &e&lContents:", "Chickens");

			CELL_BLOCK_B.addCuboid(new Cuboid(new Location(Main.getWorld(), -139, 81, -361), // cell_block_b
					new Location(Main.getWorld(), -123, 103, -300)));
			CELL_BLOCK_B.setLocation(new Location(Main.getWorld(), -130, 81, -301, -180, 0));
			CELL_BLOCK_B.setEntrance(new Location(Main.getWorld(), -130, 81, -296, -180, 0));
			CELL_BLOCK_B.setDescription("Slightly bigger cells than in Block A", "", " &e&lContents:", "4x6x3 cells.");
			
			GOLD_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -31, 50, -277), // gold_mine
					new Location(Main.getWorld(), 10, 108, -238)));
			GOLD_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -27, 5, -272), // gold_mine_mining
					new Location(Main.getWorld(), 6, 49, -240)));
			GOLD_MINE.setLocation(new Location(Main.getWorld(), -10.5, 101, -274, 0, 0));
			GOLD_MINE.setEntrance(new Location(Main.getWorld(), -10.5, 101, -279, 0, 0));
			GOLD_MINE.setDescription("SO MUCH GOLD OMG JACKPOT!", "", " &e&lContents:", "98.6% Stone","0.7% Gold Ore", "0.7% Iron Ore", "0.4% Glowstone");
			GOLD_MINE.setTeleportOnDisconnect(true);

			GRAVEL_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -120, 18, -283), // gravel_mine
					new Location(Main.getWorld(), -91, 68, -267)));
			GRAVEL_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -107, 68, -295), // gravel_mine_1
					new Location(Main.getWorld(), -98, 91, -289)));
			GRAVEL_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -124, 68, -287), // gravel_mine_2
					new Location(Main.getWorld(), -87, 76, -263)));
			GRAVEL_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -108, 68, -289), // gravel_mine_3
					new Location(Main.getWorld(), -100, 79, -288)));
			GRAVEL_MINE.setLocation(new Location(Main.getWorld(), -100, 81, -290, -180, 0));
			GRAVEL_MINE.setEntrance(new Location(Main.getWorld(), -100, 81, -286, -180, 0));
			GRAVEL_MINE.setDescription("Whats that falling?! Ouch!!!", "", " &e&lContents:", "Gravel");
			GRAVEL_MINE.setTeleportOnDisconnect(true);

			SHEEP_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -90, 95, -294), // sheep_farm
					new Location(Main.getWorld(), -55, 111, -272)));
			SHEEP_FARM.setLocation(new Location(Main.getWorld(), -56, 101, -282, 90, 0));
			SHEEP_FARM.setEntrance(new Location(Main.getWorld(), -52, 101, -282, 90, 0));
			SHEEP_FARM.setDescription("Provides wool to keep you warm through the winter!", "", " &e&lContents:", "Roughly 22 sheep");
			

			SPIDER_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -21, 67, -341), // spider_farm
					new Location(Main.getWorld(), -1, 77, -314)));
			SPIDER_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -15, 67, -345), // spider_farm_2
					new Location(Main.getWorld(), -8, 105, -341)));
			SPIDER_FARM.setLocation(new Location(Main.getWorld(), -10.5, 101, -341, -180, 0));
			SPIDER_FARM.setEntrance(new Location(Main.getWorld(), -10.5, 101, -337, -180, 0));
			SPIDER_FARM.setDescription("These spiders may be dangerous...", "", " &e&lContents:", "3x Spider Spawner","Tons of cobweb");
			SPIDER_FARM.setGuardsEnterDefault(false);

			COAL_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -116, 14, -251), // coal_mine
					new Location(Main.getWorld(), -86, 70, -212)));
			COAL_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -120, 70, -255), // coal_mine_1
					new Location(Main.getWorld(), -81, 83, -208)));
			COAL_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -104, 70, -262), // coal_mine_2
					new Location(Main.getWorld(), -97, 83, -256)));
			COAL_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -105, 77, -276), // coal_mine_3
					new Location(Main.getWorld(), -96, 87, -262)));
			COAL_MINE.setLocation(new Location(Main.getWorld(), -100, 81, -274, 0, 0));
			COAL_MINE.setEntrance(new Location(Main.getWorld(), -100, 81, -278, 0, 0));
			COAL_MINE.setDescription("This mine will fuel your furnace through the night!", "", " &e&lContents:", "8% Coal","2% Glowstone", "90% Stone");
			COAL_MINE.setTeleportOnDisconnect(true);

			BLOCK_B.addCuboid(new Cuboid(new Location(Main.getWorld(), -300, 1, -400),
					new Location(Main.getWorld(), 140, 256, -157)));
			BLOCK_B.setEntrance(new Location(Main.getWorld(), -130.5, 81, -154, -180, 0));
			BLOCK_B.setDescription("Block B of the Prison.", "", " &e&lContents:", "More farms & mines");
			BLOCK_B.setLocation(new Location(Main.getWorld(), -130.5, 81, -153, 180, 0));

			CACTUS_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -197, 82, 60),
					new Location(Main.getWorld(), -133, 95, 88)));
			CACTUS_FARM.setEntrance(new Location(Main.getWorld(), -131.5, 82, 61.1, 90, 0));
			CACTUS_FARM.setDescription("Cactus Farm", "", " &e&lContents:", "Cacti");
			CACTUS_FARM.setLocation(new Location(Main.getWorld(), -136, 82, 61.5, 50, 0));

			PUMPKIN_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -129, 82, 60),
					new Location(Main.getWorld(), -95, 95, 115)));
			PUMPKIN_FARM.setEntrance(new Location(Main.getWorld(), -131.5, 82, 61.1, -90, 0));
			PUMPKIN_FARM.setDescription("Pumpkin farm with a bunch of pumpkins.", "", " &e&lContents:", "Pumpkins");
			PUMPKIN_FARM.setLocation(new Location(Main.getWorld(), -127, 82, 61.5, -50, 0));

			TREE_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -165, 80, -54), // tree_farm_2
					new Location(Main.getWorld(), -138, 124, -9)));
			TREE_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -178, 81, -53), // tree_farm_3
					new Location(Main.getWorld(), -139, 97, -39)));
			TREE_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -245, 97, -53), // tree_farm_1
					new Location(Main.getWorld(), -164, 124, -31)));
			TREE_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -245, 100, -31), // tree_farm
					new Location(Main.getWorld(), -166, 124, 32)));
			TREE_FARM.setEntrance(new Location(Main.getWorld(), -135, 81, -23, 90, 0));
			TREE_FARM.setDescription("Useful wood and leather source.", "", " &e&lContents:", "Trees, Cows");
			TREE_FARM.setLocation(new Location(Main.getWorld(), -139.5, 81, -23, 90, 0));

			DANGEROUS_MINE.addCuboid( // ladder
					new Cuboid(new Location(Main.getWorld(), -125, 35, -25),
							new Location(Main.getWorld(), -123, 82, -22)));
			DANGEROUS_MINE.addCuboid( // mine
					new Cuboid(new Location(Main.getWorld(), -163, 5, -64),
							new Location(Main.getWorld(), -125, 55, 17)));
			DANGEROUS_MINE.setEntrance(new Location(Main.getWorld(), -125, 81, -23, -90, 0));
			DANGEROUS_MINE.setDescription("Contains plenty of lava and iron.", "", " &e&lContents:", "90% Stone",
					"8% Lava", "2% Iron Ore");
			DANGEROUS_MINE.setLocation(new Location(Main.getWorld(), -124.5, 36, -23, 90, 0));
			DANGEROUS_MINE.setTeleportOnDisconnect(true);

			CELLBLOCK_A.addCuboid(new Cuboid(new Location(Main.getWorld(), -158, 80, -157),
					new Location(Main.getWorld(), -105, 113, -54)));
			CELLBLOCK_A.setEntrance(new Location(Main.getWorld(), -131.5, 81, -51.5, -180, 0));
			CELLBLOCK_A.setDescription("You can rent cells here to store stuff.", "", " &e&lContents:", "3x3x3 Cells");
			CELLBLOCK_A.setLocation(new Location(Main.getWorld(), -131, 81, -57, 180, 0));

			SUGARCANE_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -91, 84, -4), // ladder
					new Location(Main.getWorld(), -88, 87, 4)));
			SUGARCANE_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -135, 89, -24), // farm
					new Location(Main.getWorld(), -52, 120, 24)));
			SUGARCANE_FARM.setEntrance(new Location(Main.getWorld(), -83.5, 81, -0.5, 90, 0));
			SUGARCANE_FARM.setDescription("Contains approximately 1872 sugarcanes.", "", "  &e&lContents:",
					"Sugarcanes");
			SUGARCANE_FARM.setLocation(new Location(Main.getWorld(), -94.5, 90, 0.5, 90, 0));

			MELON_FARM.addCuboid(new Cuboid(new Location(Main.getWorld(), -162, 78, 9),
					new Location(Main.getWorld(), -139, 100, 54)));
			MELON_FARM.setEntrance(new Location(Main.getWorld(), -135, 81, 25, 90, 0));
			MELON_FARM.setDescription("Melon farm containing 294 Melon Blocks.", "", " &e&lContents:", "Melons");
			MELON_FARM.setLocation(new Location(Main.getWorld(), -139.5, 81, 25, 90, 0));

			BEGINNERS_MINE.addCuboid(new Cuboid(new Location(Main.getWorld(), -226, 50, -31),
					new Location(Main.getWorld(), -164, 99, 31)));
			BEGINNERS_MINE.setDescription("This is the first mine where you will be mining in order to earn money.");
			BEGINNERS_MINE.setEntrance(new Location(Main.getWorld(), -165.3, 72, 0.5, 90, 0));
			BEGINNERS_MINE.setLocation(new Location(Main.getWorld(), -165.3, 72, 0.5, 90, 0));
			BEGINNERS_MINE.setTeleportOnDisconnect(true);

			SPAWN.setLocation(new Location(Main.getWorld(), 0.5,81.0,0.5,90,0));
			SPAWN.setDescription("The main canteen area where you spawn.");
			SPAWN.setDefaultPerm(true);
			SPAWN.setVisibleInPassport(false);
			SPAWN.setWarpSlot(20);

			SHOPS.setLocation(new Location(Main.getWorld(), -35.5, 81, 14.5, 0, 0));
			SHOPS.setDescription("Sell your stuff here.");
			SHOPS.setDefaultPerm(true);
			SHOPS.setVisibleInPassport(false);
			SPAWN.setWarpSlot(22);

			JAIL.setLocation(new Location(Main.getWorld(), -78.5, 73, 0.5, -90, 0));
			JAIL.setDescription("Prisoners punished for illegal activities are held here.", " ",
					"&4&lWARNING: PVP ZONE!");
			JAIL.setDefaultPerm(true);
			JAIL.setVisibleInPassport(false);
			SPAWN.setWarpSlot(21);

			Log.log(LogType.NOTIFY,
					"[INITALIZING] [PERMISSIONS] Initalized " + Permission.values().length + " permissions.");

		}

	}

}
