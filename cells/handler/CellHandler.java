package cells.handler;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockVector;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import bukkit.exodusprison.spurkle.Main;
import cells.Cell;
import cells.CellSaver;
import exodusplayer.ExodusPlayer;
import logger.Log;
import logger.Log.LogType;
import util.GUI;
import util.Util;

public class CellHandler {

	private static HashMap<Integer, Cell> cellList = new HashMap<Integer, Cell>();

	public static void checkExpiredLoop() {
		new BukkitRunnable() {
			public void run() {

				checkExpired();
			}

		}.runTaskTimer(Main.getPlugin(), 0L, 1200L);

	}

	public static void loadCells() {
		CellSaver.loadCells();
		Log.log(LogType.NOTIFY, "[INITALIZING] Loaded " + cellList.size() + " cells.");

	}

	private static void checkExpired() {
		for (Cell cell : cellList.values()) {
			
			if(!cell.isOccupied() || cell.isPermanent())
				continue;

			if (cell.getExpiry() > 0 && System.currentTimeMillis() > cell.getExpiry()) {
				Log.log("Cell " + cell.getId() + " has just expired.");

				Player p = Bukkit.getPlayer(cell.getOwnerName());
				if (p != null) {
					GUI.sendTitle(p, 5, 120, 10, "&4Your cell has expired!",
							"Your cell ID " + cell.getId() + " has expired.");
					ExodusPlayer.get(p).setCellID(-1);
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 1F);
				}

				cell.unownCell();

			}

			updateSign(cell);

		}
	}

	public static void updateSign(Cell c) {
		Sign sign = (Sign) c.getSign().getBlock().getState();

		if (c.isOccupied()) {
			sign.setLine(0, "§cCell " + c.getId());
			sign.setLine(1, "§e" + c.getOwnerName());
			sign.setLine(2, "Expires: ");

			if (c.isPermanent())
				sign.setLine(3, "Never");
			else
				sign.setLine(3, Util.formatTimeMillis(c.getExpiry(), "MM/dd HH:mm"));
		} else {
			sign.setLine(0, "§e[Buy Cell]");
			sign.setLine(1, String.valueOf(c.getId()));
			sign.setLine(2, "Right Click to Rent");
			sign.setLine(3, "$" + c.getPrice() + "/day");
		}

		sign.update();

	}

	public static void rentCell(Player p, Cell cell, int rentLengthDays) {
		RegionManager mgr = Main.getWGRegionManager();
		ProtectedRegion region = mgr.getRegion(cell.getRegion());

		LocalPlayer player = Main.getWG().wrapPlayer(p);
		region.getMembers().addPlayer(player);

		try {
			mgr.save();
		} catch (StorageException e) {
			e.printStackTrace();
		}

		cell.setUuid(p.getUniqueId().toString());
		cell.setPlayerName(p.getName());
		cell.setExpiry(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(rentLengthDays));

		ExodusPlayer.get(p).setCellID(cell.getId());

		updateSign(cell);

	}

	public static void registerNewCell(BlockVector3 min, BlockVector3 max, Location signLocation, int price) {
		Cell cell = new Cell();

		cell.setId(cellList.size() + 1);
		cell.setPrice(price);
		cell.setRegion("cell_" + cell.getId());
		cell.setSign(signLocation);
		cell.setExpiry((long) 0);
		cell.setPlayerName("");
		cell.setUuid("");

		ProtectedCuboidRegion region = new ProtectedCuboidRegion(cell.getRegion(), min, max);
	
		
		/*region.setFlag(DefaultFlag.ENTRY, State.DENY);
		region.setFlag(DefaultFlag.ENTRY_DENY_MESSAGE, "&4This isn't your cell.");
		region.setFlag(DefaultFlag.PVP, State.DENY);*/

		Main.getWGRegionManager().addRegion(region);

		try {
			CellHandler.updateSign(cell);
		} catch (Exception ex) {
			Main.getWGRegionManager().removeRegion(region.getId());
			Util.globalMessage("ERROR SIGN");
			return;
		}

		cellList.put(cellList.size() + 1, cell);

		Util.globalMessage("New cell registered. ID: " + cell.getId());
	}

	public static HashMap<Integer, Cell> getCellList() {
		return cellList;
	}

}
