package items.list.admin.tools;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.sk89q.worldedit.BlockVector;

import bukkit.exodusprison.spurkle.Main;
import cells.handler.CellHandler;
import items.Item;

public class CellMaker extends Item {

	public CellMaker() {
		super("Cell Creator", Material.STICK);

		this.setPriority(false);
		this.setLegal(LegalityType.AdminOnly);
		this.setDescription("");
		this.compile();

	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {

		Location loc = p.getTargetBlock((Set) null, 100).getLocation();
		Location sign = new Location(Main.getWorld(), Math.round(loc.getX()), Math.round(loc.getY() + 1),
				Math.round(loc.getZ() + 1));
		
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			BlockVector pointOne = new BlockVector(loc.getX() - 7, loc.getY(), loc.getZ() + 1);
			BlockVector pointTwo = new BlockVector(loc.getX() - 2, loc.getY() + 2, loc.getZ() - 2);
			CellHandler.registerNewCell(pointOne, pointTwo, sign, 2500);
			
		} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			BlockVector pointOne = new BlockVector(loc.getX() + 7, loc.getY(), loc.getZ() - 2);
			BlockVector pointTwo = new BlockVector(loc.getX() + 2, loc.getY() + 2, loc.getZ() + 1);
			
			CellHandler.registerNewCell(pointOne, pointTwo, sign, 2500);
		}


		e.setCancelled(true);


	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUseOnEntity(Player p, PlayerInteractEntityEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub
		
	}

}