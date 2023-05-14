package cells;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import bukkit.exodusprison.spurkle.Main;
import cells.handler.CellHandler;
import logger.Log;
import logger.Log.LogType;
public class CellSaver {

	private static String dataFolder = "plugins/ExodusReborn/";
	private static File cellsFile = new File(dataFolder + "cells.yml");
	private static FileConfiguration cells;

	public static void saveCells() {
		try {

			for (Cell cell : CellHandler.getCellList().values()) {
				cells.set(cell.getId() + ".id", cell.getId());
				cells.set(cell.getId() + ".owner", cell.getOwnerUUID());
				cells.set(cell.getId() + ".ownerName", cell.getOwnerName());
				cells.set(cell.getId() + ".price", cell.getPrice());
				cells.set(cell.getId() + ".region", cell.getRegion());
				cells.set(cell.getId() + ".expiry", cell.getExpiry());

				cells.set(cell.getId() + ".sign.X", cell.getSign().getX());
				cells.set(cell.getId() + ".sign.Y", cell.getSign().getY());
				cells.set(cell.getId() + ".sign.Z", cell.getSign().getZ());

			}

			cells.save(cellsFile);
			
			Log.log(LogType.WARNING,"[STOPPING] [CELLS] Saved " + CellHandler.getCellList().size() + " cells.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadCells() {

		cells = YamlConfiguration.loadConfiguration(cellsFile);

		for (String key : cells.getKeys(false)) {
			int id = Integer.valueOf(key);

			Cell cell = new Cell();

			cell.id = id;
			cell.uuid = cells.getString(id + ".owner");
			cell.playerName = cells.getString(id + ".ownerName");
			cell.price = cells.getInt(id + ".price");
			cell.region = cells.getString(id + ".region");
			cell.expiry = cells.getLong(id + ".expiry");
			cell.sign = new Location(Main.getWorld(), cells.getInt(id + ".sign.X"), cells.getInt(id + ".sign.Y"),
					cells.getInt(id + ".sign.Z"));
			
			CellHandler.getCellList().put(cell.id, cell);

		}

	}
}
