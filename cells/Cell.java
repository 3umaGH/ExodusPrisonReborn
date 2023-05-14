package cells;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import bukkit.exodusprison.spurkle.Main;
import cells.handler.CellHandler;
import exodusplayer.ExodusPlayer;

public class Cell {
	int id, price;
	String uuid, playerName, region;
	Long expiry;

	Location sign;

	public Cell() {
		playerName = "";
		uuid = "";
	}

	public int getId() {
		return id;
	}

	public String getOwnerUUID() {
		return uuid;
	}

	public String getOwnerName() {
		return playerName;
	}

	public String getRegion() {
		return region;
	}

	public Long getExpiry() {
		return expiry;
	}

	public Location getSign() {
		return sign;
	}

	public int getPrice() {
		return price;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setExpiry(Long expiry) {
		this.expiry = expiry;
	}

	public void setSign(Location sign) {
		this.sign = sign;
	}

	public boolean isPermanent() {
		return (TimeUnit.MILLISECONDS.toDays(this.getExpiry() - System.currentTimeMillis()) > 10000);
	}

	public boolean isOccupied() {
		return !(this.getOwnerName() == null || this.getOwnerName().isEmpty());
	}

	public void unownCell() {

		ProtectedRegion reg = Main.getWGRegionManager().getRegion(this.getRegion());
		reg.getMembers().clear();

		Player p = Bukkit.getPlayer(this.getOwnerName());

		if (p != null)
			ExodusPlayer.get(p).setCellID(-1);

		try {
			Main.getWGRegionManager().save();
		} catch (StorageException e) {
			e.printStackTrace();
		}

		this.uuid = "";
		this.playerName = "";
		this.expiry = (long) 0;
		CellHandler.updateSign(this);

	}

	public void addTime(Long time) {
		this.expiry += time;
	}

	public void makePermanent() {
		this.expiry = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(36500);

		Player p = Bukkit.getPlayer(this.playerName);
	}

}