package jail.handlers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import items.Item;
import items.Item.LegalityType;
import logger.Log;
import logger.Log.LogType;
import net.minecraft.server.v1_8_R3.Material;
import statistics.handlers.ServerStatistics;
import util.GUI;
import util.Util;

public class JailHandler {

	public static Location JAIL_LOCATION = new Location(Main.getWorld(), -63.5, 73, -0.5,90,0);
	public static Location JAIL_EXIT_LOCATION = new Location(Main.getWorld(), -70.5, 73, -3.5,90,0);

	public static int ARREST_TIME = 300;

	public static void startHandler() {
		new BukkitRunnable() {
			public void run() {

				for (ExodusPlayer p : ExodusPlayer.getOnlinePlayers()) {

					if (p.isArrested()) {

						GUI.sendTitle(p.getPlayer(), 5, 60, 5, "",
								"&4You are arrested for " + p.getArrestTime() + " seconds.");
						
						ServerStatistics.incrementKey("time_spent_in_jail_seconds", 1);

						if (JAIL_LOCATION.distance(p.getPlayer().getLocation()) >= 10)
							p.getPlayer().teleport(JAIL_LOCATION);

						p.setJailTime(p.getArrestTime() - 1);

						if (p.getArrestTime() <= 0) {
							GUI.sendTitle(p.getPlayer(), 5, 60, 5, "", "&aYou have served your time in prsion.");
							p.unarrestPlayer();
						}
					

					}

				}

			}

		}.runTaskTimer(Main.getPlugin(), 0L, 20L);
		
		Log.log(LogType.NOTIFY,"[INITALIZING] [JAIL] Jail module initalized successfully.");
	}
	
	public static void disconnectCheck(ExodusPlayer eP){
		Player p = eP.getPlayer();
		
		if (!eP.isArrested())
			return;

		// rip u mofo
		
		int items = 0;

		for (ItemStack i : p.getInventory()) {
			
			if(i != null && i.getType().equals(Material.AIR))
				continue;
			
			Item item = Item.getItem(i);
			
			if(item == null)
				continue;
			
			
			if (item.getLegal().equals(LegalityType.Illegal) || item.getLegal().equals(LegalityType.GuardsOnly)) {

				p.getInventory().remove(i);
				p.getLocation().getWorld().dropItemNaturally(p.getLocation(), i);
				
				items++;
			}

		}
		
		Util.globalMessage("§eSpeaker§7] : > §4" + p.getName() + " has disconnected in jail and automatically dropped " + items + " illegal items.");
	}

}
