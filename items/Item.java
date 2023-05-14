package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import bukkit.exodusprison.spurkle.Main;
import events.stash.Stash.StashType;
import exodusplayer.ExodusPlayer;
import items.ItemUpgrade.Upgrade;
import items.list.admin.tools.CellMaker;
import items.list.armor.IronBoots;
import items.list.armor.IronChestplate;
import items.list.armor.IronHelmet;
import items.list.armor.IronLeggings;
import items.list.armor.LeatherBoots;
import items.list.armor.LeatherChestplate;
import items.list.armor.LeatherHelmet;
import items.list.armor.LeatherLeggings;
import items.list.donators.SuperIronPickaxe;
import items.list.donators.SuperWoodPickaxe;
import items.list.drugs.CactusGreen;
import items.list.drugs.Meth;
import items.list.drugs.Sugar;
import items.list.guard.ArrestStick;
import items.list.guard.ChiefsPanel;
import items.list.guard.GuardBoots;
import items.list.guard.GuardChestplate;
import items.list.guard.GuardHelmet;
import items.list.guard.GuardLeggings;
import items.list.guard.GuardSword;
import items.list.guard.InventorySearcher;
import items.list.guard.PepperSpray;
import items.list.guard.Taser;
import items.list.misc.Anvil;
import items.list.misc.Apple;
import items.list.misc.AwkwardPotion;
import items.list.misc.BirchSapling;
import items.list.misc.BirchWood;
import items.list.misc.BlazePowder;
import items.list.misc.Book;
import items.list.misc.Bread;
import items.list.misc.BrewingStand;
import items.list.misc.Cactus;
import items.list.misc.Chest;
import items.list.misc.Coal;
import items.list.misc.Cobblestone;
import items.list.misc.CookedChicken;
import items.list.misc.CookedFish;
import items.list.misc.Egg;
import items.list.misc.EnchantedBook;
import items.list.misc.EnchantingTable;
import items.list.misc.FermentedSpiderEye;
import items.list.misc.Flint;
import items.list.misc.Furnace;
import items.list.misc.GhastTear;
import items.list.misc.GlisteringMelon;
import items.list.misc.GlowstoneDust;
import items.list.misc.GoldIngot;
import items.list.misc.GoldOre;
import items.list.misc.GoldenCarrot;
import items.list.misc.Gravel;
import items.list.misc.Gunpowder;
import items.list.misc.IronIngot;
import items.list.misc.IronOre;
import items.list.misc.JackOLatern;
import items.list.misc.Leather;
import items.list.misc.MelonSeeds;
import items.list.misc.MelonSlice;
import items.list.misc.OakSapling;
import items.list.misc.OakWood;
import items.list.misc.Paper;
import items.list.misc.Pumpkin;
import items.list.misc.PumpkinSeeds;
import items.list.misc.RawBeef;
import items.list.misc.SpiderEye;
import items.list.misc.Steak;
import items.list.misc.Stone;
import items.list.misc.StringItem;
import items.list.misc.Sugarcane;
import items.list.misc.Torch;
import items.list.misc.Wool;
import items.list.misc.XPBottle;
import items.list.other.EnchantingVoucher;
import items.list.other.GuardTracker;
import items.list.other.ItemPermVoucher;
import items.list.other.Lockpick;
import items.list.other.PoisonedShiv;
import items.list.other.RandomTitle;
import items.list.tools.DiamondAxe;
import items.list.tools.DiamondHoe;
import items.list.tools.DiamondPickaxe;
import items.list.tools.DiamondShovel;
import items.list.tools.DiamondSword;
import items.list.tools.IronAxe;
import items.list.tools.IronHoe;
import items.list.tools.IronPickaxe;
import items.list.tools.IronShovel;
import items.list.tools.IronSword;
import items.list.tools.StoneAxe;
import items.list.tools.StoneHoe;
import items.list.tools.StonePickaxe;
import items.list.tools.StoneShovel;
import items.list.tools.StoneSword;
import items.list.tools.WoodAxe;
import items.list.tools.WoodHoe;
import items.list.tools.WoodPickaxe;
import items.list.tools.WoodShovel;
import items.list.tools.WoodSword;
import logger.Log;
import logger.Log.LogType;
import util.EnchantGlow;
import util.Util;

public abstract class Item {

	static HashMap<String, Item> items = new HashMap<String, Item>();
	public static HashMap<Integer, String> itemIds = new HashMap<Integer, String>();

	static HashMap<String, Item> convertibles = new HashMap<String, Item>();

	public static Item anvil = new Anvil();
	public static Item xpBottle = new XPBottle();
	
	public static Item itemPermissionVoucher = new ItemPermVoucher();
	
	public static Item enchantingTable = new EnchantingTable();
	public static Item brewingStand = new BrewingStand();
	
	public static Item randomTitle = new RandomTitle();
	public static Item guardTracker = new GuardTracker();
	public static Item poisonedShiv = new PoisonedShiv();
	public static Item lockpick = new Lockpick();
	
	public static Item enchantingVoucher_low = new EnchantingVoucher("Low Level", 1);
	public static Item enchantingVoucher_mid = new EnchantingVoucher("Mid. Level", 2);
	public static Item enchantingVoucher_high = new EnchantingVoucher("Hi Level", 3);
	
	public static Item prisonersStash = new StashItem("Prisoners Stash", StashType.PRISONERS);
	public static Item guardsStash = new StashItem("Guards Stash", StashType.GUARDS);
	public static Item legendaryStash = new StashItem("&6&lLegendary Stash", StashType.LEGENDARY);
	
	public static Item smallMineBomb = new MineBomb("Small Mine Bomb", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzVkMDk2ZjhjNjhlZWMyYTcwMWQxZDVkMmQzMDdjMjdmOGRjYmU4Mzc5ZDAwNTI4YmZiMjg2NGM2NjRjMSJ9fX0=", 1);
	public static Item mediumMineBomb = new MineBomb("Medium Mine Bomb", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhNmRjZDBjNmNiZjg4NjM5MDVlMTlkOWUwOTRiZjVjMTQ4N2FhMzZjZWIyMjkzZTdlNjMxZGEwY2NhNTdkIn19fQ==", 2);
	public static Item bigMineBomb = new MineBomb("Big Mine Bomb", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2U0ZWNjYjE3YThlOTI2MDFhNTE5MTdjYjVhNmZjMTdlYWYyYWRjMjdlODM3MzIzNmIyMzIzZjQ3NGVmNDhmMSJ9fX0=", 3);

	
	// Titles
	public static Item title_1 = new Title("&dVinegar Tits");
	public static Item title_2 = new Title("&cHacker");
	public static Item title_3 = new Title("&eGold Digger");
	public static Item title_4 = new Title("&6Trololo");
	public static Item title_5 = new Title("&4Unleashed");
	public static Item title_6 = new Title("&aSir");
	public static Item title_7 = new Title("&eMinion");
	public static Item title_8 = new Title("&7Nerd");
	public static Item title_9 = new Title("&eHero");
	public static Item title_10 = new Title("&6God");
	public static Item title_11 = new Title("&aCheerful");
	public static Item title_12 = new Title("&bAthlete");
	public static Item title_13 = new Title("&dStylish");
	public static Item title_14 = new Title("&dAdorable");
	public static Item title_15 = new Title("&eSweet Cheeks");
	public static Item title_16 = new Title("&k&9|Mysterious&k&9|");
	public static Item title_17 = new Title("&2Untouchable&8");
	public static Item title_18 = new Title("&3Wild");
	public static Item title_19 = new Title("&6Hot");
	public static Item title_20 = new Title("&4Hellriser&8");
	public static Item title_21 = new Title("&cRaider");
	public static Item title_22 = new Title("&eOverpowered");
	public static Item title_23 = new Title("&eBig Cheese");
	public static Item title_24 = new Title("&aA&ew&ce&bs&do&9m&6e");
	public static Item title_25 = new Title("&8Batman");
	public static Item title_26 = new Title("&4Mad Cow");
	public static Item title_27 = new Title("&6Wookie");
	public static Item title_28 = new Title("&cLobster");
	public static Item title_29 = new Title("&dBarbie");
	public static Item title_30 = new Title("&aDollar");
	
	public static Item title_custom_1 = new Title("&cMr. Mine Bomb");
	public static Item title_custom_2 = new Title("&eBug Finder");

	// drugs
	public static Item cactusGreen = new CactusGreen();

	public static Item taser_lvl1 = new Taser(1);
	public static Item taser_lvl2 = new Taser(2);

	public static Item pepperspray_lvl1 = new PepperSpray(1);
	public static Item pepperspray_lvl2 = new PepperSpray(2);
	public static Item pepperspray_lvl3 = new PepperSpray(3);

	public static Item guardSword_lv1 = new GuardSword(1);
	public static Item guardSword_lv2 = new GuardSword(2);
	public static Item guardSword_lv3 = new GuardSword(3);
	public static Item guardSword_lv4 = new GuardSword(4);
	public static Item guardSword_lv5 = new GuardSword(5);
	public static Item guardSword_lv6 = new GuardSword(6);
	public static Item guardSword_lv7 = new GuardSword(7);

	// Armor
	public static Item guardHelm_lv1 = new GuardHelmet(1);
	public static Item guardHelm_lv2 = new GuardHelmet(2);
	public static Item guardHelm_lv3 = new GuardHelmet(3);
	public static Item guardHelm_lv4 = new GuardHelmet(4);
	public static Item guardHelm_lv5 = new GuardHelmet(5);
	public static Item guardHelm_lv6 = new GuardHelmet(6);
	public static Item guardHelm_lv7 = new GuardHelmet(7);
	public static Item guardHelm_lv8 = new GuardHelmet(8);

	public static Item guardChest_lv1 = new GuardChestplate(1);
	public static Item guardChest_lv2 = new GuardChestplate(2);
	public static Item guardChest_lv3 = new GuardChestplate(3);
	public static Item guardChest_lv4 = new GuardChestplate(4);
	public static Item guardChest_lv5 = new GuardChestplate(5);
	public static Item guardChest_lv6 = new GuardChestplate(6);
	public static Item guardChest_lv7 = new GuardChestplate(7);
	public static Item guardChest_lv8 = new GuardChestplate(8);

	public static Item guardLegs_lv1 = new GuardLeggings(1);
	public static Item guardLegs_lv2 = new GuardLeggings(2);
	public static Item guardLegs_lv3 = new GuardLeggings(3);
	public static Item guardLegs_lv4 = new GuardLeggings(4);
	public static Item guardLegs_lv5 = new GuardLeggings(5);
	public static Item guardLegs_lv6 = new GuardLeggings(6);
	public static Item guardLegs_lv7 = new GuardLeggings(7);
	public static Item guardLegs_lv8 = new GuardLeggings(8);

	public static Item guardBoots_lv1 = new GuardBoots(1);
	public static Item guardBoots_lv2 = new GuardBoots(2);
	public static Item guardBoots_lv3 = new GuardBoots(3);
	public static Item guardBoots_lv4 = new GuardBoots(4);
	public static Item guardBoots_lv5 = new GuardBoots(5);
	public static Item guardBoots_lv6 = new GuardBoots(6);
	public static Item guardBoots_lv7 = new GuardBoots(7);
	public static Item guardBoots_lv8 = new GuardBoots(8);

	public static Item inv_searcher = new InventorySearcher();

	public static Item arreststick_lvl1 = new ArrestStick(1);
	public static Item arreststick_lvl2 = new ArrestStick(2);
	public static Item arreststick_lvl3 = new ArrestStick(3);
	public static Item arreststick_lvl4 = new ArrestStick(4);
	public static Item arreststick_lvl5 = new ArrestStick(5);

	// upgrades

	public static ItemUpgrade guardSword = new ItemUpgrade("Sword", 0, new Upgrade(guardSword_lv1, 0),
			new Upgrade(guardSword_lv2, 10000), new Upgrade(guardSword_lv3, 15000), new Upgrade(guardSword_lv4, 25000),
			new Upgrade(guardSword_lv5, 50000), new Upgrade(guardSword_lv6, 75000),
			new Upgrade(guardSword_lv7, 100000));

	public static ItemUpgrade arrest_baton = new ItemUpgrade("Arrest Baton", 1, new Upgrade(arreststick_lvl1, 0),
			new Upgrade(arreststick_lvl2, 10000), new Upgrade(arreststick_lvl3, 25000),
			new Upgrade(arreststick_lvl4, 35000), new Upgrade(arreststick_lvl5, 150000));

	public static ItemUpgrade guardHelm = new ItemUpgrade("Helmet", 2, new Upgrade(guardHelm_lv1, 0),
			new Upgrade(guardHelm_lv2, 5000), new Upgrade(guardHelm_lv3, 10000), new Upgrade(guardHelm_lv4, 20000),
			new Upgrade(guardHelm_lv5, 20000), new Upgrade(guardHelm_lv6, 20000), new Upgrade(guardHelm_lv7, 20000),
			new Upgrade(guardHelm_lv8, 75000));

	public static ItemUpgrade guardChest = new ItemUpgrade("Chestplate", 3, new Upgrade(guardChest_lv1, 0),
			new Upgrade(guardChest_lv2, 10000), new Upgrade(guardChest_lv3, 20000), new Upgrade(guardChest_lv4, 30000),
			new Upgrade(guardChest_lv5, 30000), new Upgrade(guardChest_lv6, 30000), new Upgrade(guardChest_lv7, 30000),
			new Upgrade(guardChest_lv8, 100000));

	public static ItemUpgrade guardLegs = new ItemUpgrade("Leggings", 4, new Upgrade(guardLegs_lv1, 0),
			new Upgrade(guardLegs_lv2, 10000), new Upgrade(guardLegs_lv3, 20000), new Upgrade(guardLegs_lv4, 30000),
			new Upgrade(guardLegs_lv5, 30000), new Upgrade(guardLegs_lv6, 30000), new Upgrade(guardLegs_lv7, 30000),
			new Upgrade(guardLegs_lv8, 100000));

	public static ItemUpgrade guardBoots = new ItemUpgrade("Boots", 5, new Upgrade(guardBoots_lv1, 0),
			new Upgrade(guardBoots_lv2, 5000), new Upgrade(guardBoots_lv3, 10000), new Upgrade(guardBoots_lv4, 20000),
			new Upgrade(guardBoots_lv5, 20000), new Upgrade(guardBoots_lv6, 20000), new Upgrade(guardBoots_lv7, 20000),
			new Upgrade(guardBoots_lv8, 75000));

	public static ItemUpgrade inventory_searcher = new ItemUpgrade("Inventory Searcher", 6,
			new Upgrade(inv_searcher, 0));

	public static ItemUpgrade pepperspray = new ItemUpgrade("Pepper Spray", 7, new Upgrade(pepperspray_lvl1, 15000),
			new Upgrade(pepperspray_lvl2, 15000), new Upgrade(pepperspray_lvl3, 20000));

	public static ItemUpgrade taser = new ItemUpgrade("Taser", 8, new Upgrade(taser_lvl1, 15000),
			new Upgrade(taser_lvl2, 20000));

	public static Item leatherHelmet = new LeatherHelmet();
	public static Item leatherChestplate = new LeatherChestplate();
	public static Item leatherLeggings = new LeatherLeggings();
	public static Item leatherBoots = new LeatherBoots();

	public static Item ironHelmet = new IronHelmet();
	public static Item ironChestplate = new IronChestplate();
	public static Item ironLeggings = new IronLeggings();
	public static Item ironBoots = new IronBoots();

	// Weapons
	public static Item woodSword = new WoodSword();
	public static Item stoneSword = new StoneSword();
	public static Item ironSword = new IronSword();
	public static Item diamondSword = new DiamondSword();
	// Tools
	public static Item diamondPickaxe = new DiamondPickaxe();
	public static Item diamondAxe = new DiamondAxe();
	public static Item diamondShovel = new DiamondShovel();
	public static Item diamondHoe = new DiamondHoe();
	
	public static Item ironAxe = new IronAxe();
	public static Item ironPickaxe = new IronPickaxe();
	public static Item ironShovel = new IronShovel();
	public static Item ironHoe = new IronHoe();

	public static Item stoneAxe = new StoneAxe();
	public static Item stonePickaxe = new StonePickaxe();
	public static Item stoneShovel = new StoneShovel();
	public static Item stoneHoe = new StoneHoe();

	public static Item woodAxe = new WoodAxe();
	public static Item woodPickaxe = new WoodPickaxe();
	public static Item woodShovel = new WoodShovel();
	public static Item woodHoe = new WoodHoe();

	// Misc
	public static Item birchSapling = new BirchSapling();
	public static Item birchWood = new BirchWood();
	public static Item cactus = new Cactus();
	public static Item cobblestone = new Cobblestone();
	public static Item glowstoneDust = new GlowstoneDust();
	public static Item leather = new Leather();
	public static Item oakSapling = new OakSapling();
	public static Item oakWood = new OakWood();
	public static Item pumpkin = new Pumpkin();
	public static Item rawBeef = new RawBeef();
	public static Item sugarcane = new Sugarcane();

	// New
	public static Item book = new Book();
	public static Item chest = new Chest();
	public static Item furnace = new Furnace();
	public static Item jackOLatern = new JackOLatern();
	public static Item melonSeeds = new MelonSeeds();
	public static Item paper = new Paper();
	public static Item pumpkinSeeds = new PumpkinSeeds();
	public static Item sugar = new Sugar();
	public static Item torch = new Torch();
	public static Item stone = new Stone();

	public static Item ironOre = new IronOre();
	public static Item ironIngot = new IronIngot();
	public static Item steak = new Steak();
	public static Item apple = new Apple();
	public static Item melonslice = new MelonSlice();
	public static Item bread = new Bread();
	public static Item cookedfish = new CookedFish();
	public static Item cookedChicken = new CookedChicken();

	public static Item superWoodPickaxe = new SuperWoodPickaxe();
	public static Item superIronPickaxe = new SuperIronPickaxe();

	public static Item cellMaker = new CellMaker();

	public static Item LSD = new items.list.drugs.LSD();
	public static Item meth = new Meth();
	
	public static Item chiefsPanel = new ChiefsPanel();
	public static Item enchantedBook = new EnchantedBook();
	
	public static Item awkwardPotion = new AwkwardPotion();
	public static Item fermentedSpiderEye = new FermentedSpiderEye();
	public static Item glistringMelon = new GlisteringMelon();
	public static Item goldenCarrot = new GoldenCarrot();
	public static Item gunpowder = new Gunpowder();
	public static Item ghastTear = new GhastTear();
	public static Item blazePowder = new BlazePowder();
	
	public static Item coal = new Coal();
	public static Item egg = new Egg();
	public static Item goldIngot = new GoldIngot();
	public static Item goldOre = new GoldOre();

	public static Item gravel = new Gravel();
	public static Item flint = new Flint();
	
	public static Item wool = new Wool();
	
	public static Item spiderEye = new SpiderEye();
	public static Item string = new StringItem();

	public static void intialize() {
		Log.log(LogType.NOTIFY, "[INITALIZING] [ITEMS] Loaded " + items.size() + " custom items.");
	}

	public static enum LegalityType {
		Legal, Illegal, GuardsOnly, AdminOnly
	}

	private List<Player> cooldown = new ArrayList<Player>();

	ItemStack item;

	String name;
	String lore;

	Material material;

	int id, loreSize, data, cooldownDelay;

	List<String> customLore = new ArrayList<String>();

	LegalityType legal;
	boolean priority, dropOnDeath, glow;

	ItemMeta meta;

	public Item(String name, Material material) {

		this.name = name;
		this.material = material;

		this.data = 0;
		this.loreSize = 0;

		this.legal = LegalityType.Legal;

		this.priority = false;
		this.glow = false;
		this.dropOnDeath = true;
		
		this.id = -1;

	}

	public void setDescription(String description) {

		description = description.replace("&", "§");

		if (description.length() < 50) {
			lore = description;
			loreSize = 1;

		} else {
			lore = Util.splitText(description, 50);
			loreSize = lore.split("/n").length;
		}
	}

	public void setData(int data) {
		this.data = data;
	}

	private String getLegalityColor() {
		switch (this.legal) {

		case Legal:
			return "§a";

		case Illegal:
			return "§c";

		case GuardsOnly:
			return "§b";

		case AdminOnly:
			return "§c";

		default:
			return "";

		}
	}

	public Item compile() {

		this.item = Util.createItem(material, 1, (short) data, getLegalityColor() + this.name, this.lore);

		if (this.getId() == -1)
			this.setId(items.size());

		if (this.glow)
			EnchantGlow.addGlow(item);

		items.put(this.item.getItemMeta().getDisplayName(), this);
		itemIds.put(this.getId(), this.getName());

		updateMeta();

		return this;
	}

	protected void updateMeta() {

		ItemMeta meta = item.getItemMeta();
		ArrayList<String> loreList = new ArrayList<String>();

		if (loreSize == 1) {
			loreList.add(this.getLore());
		} else {
			for (int i = 0; i < loreSize; i++)
				loreList.add(meta.getLore().get(i));
		}

		for (String lore : customLore) {
			loreList.add(ChatColor.translateAlternateColorCodes('&', lore));
		}

		loreList.add(" ");

		switch (this.legal) {

		case Illegal:
			loreList.add(Util.center("§8This item is §cILLEGAL", 60));
			break;

		case Legal:
			loreList.add(Util.center("§8This item is §aLEGAL", 60));
			break;

		case GuardsOnly:
			loreList.add(Util.center("   §8This item is §bGUARDS ONLY", 60));
			break;

		case AdminOnly:
			loreList.add(Util.center("   §cThis item is §c§lADMIN ONLY", 60));
			this.dropOnDeath = false;
			break;

		}

		if (this.dropOnDeath == false)
			loreList.add(Util.center("§c[*] Doesn't drop on death [*]", 60));

		meta.setLore(loreList);
		item.setItemMeta(meta);
		this.meta = meta;

	}

	public void setCooldown(int delay) {
		this.cooldownDelay = delay;
	}

	public void activateCooldown(Player p) {
		this.cooldown.add(p);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			public void run() {
				cooldown.remove(p);
			}
		}, this.cooldownDelay);

	}

	public boolean isCooleddown(Player p) {
		return cooldown.contains(p);
	}

	public ItemStack getItemStack() {
		this.item.setAmount(1);
		return this.item;
	}

	public int getId() {
		return id;
	}

	public ItemStack getNewItemStack(int amnt) {
		ItemStack item = new ItemStack(this.item);
		item.setAmount(amnt);

		return item;
	}

	public String getName() {
		return this.item.getItemMeta().getDisplayName();
	}

	public static HashMap<String, Item> getItemList() {
		return items;
	}

	public static Item getItemById(int id) {
		return items.get(itemIds.get(id));
	}

	public Material getMaterial() {
		return material;
	}

	public String getLore() {
		return lore;
	}

	public int getItemStackData() {
		return data;
	}

	public int getLoreSize() {
		return loreSize;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static Item getItem(ItemStack i) {
		if (i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName())
			return items.get(i.getItemMeta().getDisplayName());
		else
			return null;
	}

	public abstract void onInteract(Player p, PlayerInteractEvent e);

	public abstract void onDamageEntity(Player p, EntityDamageByEntityEvent e);

	public abstract void onUseOnEntity(Player p, PlayerInteractEntityEvent e);

	public abstract void onBlockPlace(Player p, BlockPlaceEvent e);

	
	public LegalityType getLegal() {
		return legal;
	}

	public void setLegal(LegalityType legal) {
		this.legal = legal;
	}

	public boolean isPriority() {
		return priority;
	}

	public void setPriority(boolean priority) {
		this.priority = priority;

		if (priority)
			convertibles.put(this.getMaterial().toString() + ":" + getItemStackData(), this);
	}

	public boolean isDropOnDeath() {
		return dropOnDeath;
	}

	public void setDropOnDeath(boolean droppableOnDeath) {
		this.dropOnDeath = droppableOnDeath;
	}

	public static HashMap<String, Item> getConvertibles() {
		return convertibles;
	}

	public ItemMeta getItemMeta() {
		return meta;
	}

	public void removeFromHand(Player p) {
		Util.removeFromHand(p);
	}

	public void addCustomLore(String line) {
		this.customLore.add(line);
	}

	public void setGlow(boolean glow) {
		this.glow = glow;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public boolean hasPermission(Player p){
		ExodusPlayer eP = ExodusPlayer.get(p);
		LegalityType type = this.getLegal();
		
		//if(type.equals(LegalityType.GuardsOnly) && !eP.isGuard())
		//	return false;
		
		if(type.equals(LegalityType.AdminOnly) && !eP.getPlayer().hasPermission("exodus.admin"))
			return false;
		
		return true;
		
	}

}
