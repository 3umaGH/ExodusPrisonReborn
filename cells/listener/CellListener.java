package cells.listener;

import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import cells.Cell;
import cells.handler.CellHandler;
import economy.Vault;
import exodusplayer.ExodusPlayer;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class CellListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getClickedBlock() != null && e.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) e.getClickedBlock().getState();

			if (sign.getLine(0).equals("§e[Buy Cell]")) {
				int id = Integer.valueOf(sign.getLine(1));
				Cell cell = CellHandler.getCellList().get(id);
				ExodusPlayer eP = ExodusPlayer.get(p);
				
				if (!cell.isOccupied()) {

					if (eP.isGuard()) {
						GUI.sendTitle(p, 5, 60, 10, "&4You are a guard!", "&cYou can't get a cell.");
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 1F);
						return;
					}

					if (eP.getCellID() != -1 && !CellHandler.getCellList().get(eP.getCellID()).isPermanent()) {
						GUI.sendTitle(p, 5, 60, 10, "&4You already have a cell!", "&cType /cell remove to abandon it.");
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 1F);
						return;
					}

					if (Vault.takeMoney(p, cell.getPrice(), false)) {
						CellHandler.rentCell(p, cell, 1);
						p.sendMessage("§aYou have rented a cell.");
						
						ServerStatistics.incrementKey("cells_rented", 1);
					} else {

						GUI.sendTitle(p, 5, 60, 10, "", "&4You don't have enough money.");
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.98F, 1F);

					}

				} else {

					p.sendMessage("§6Owner: §e" + cell.getOwnerName());
					p.sendMessage("§6Expires: §e" + Util.formatTimeMillis(cell.getExpiry(), "yyyy/MM/dd HH:mm:ss"));
					p.sendMessage("§6Region: §e" + cell.getRegion());

				}

			}

		}
	}

}
