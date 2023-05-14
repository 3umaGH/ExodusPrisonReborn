package menu.list;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import bukkit.exodusprison.spurkle.Main;
import economy.Vault;
import exodusplayer.ExodusPlayer;
import menu.Menu;
import mobs.list.EscortPrisoner;
import statistics.handlers.ServerStatistics;
import util.Util;

public class EscortMenu extends Menu {

	EscortPrisoner prisoner;
	int speed = 5;

	public EscortMenu(EscortPrisoner prisoner, ExodusPlayer clickedPlayer) {
		super("Escort Menu", 1);

		this.prisoner = prisoner;
		this.setUpdating(true);

	}

	@Override
	public void loadIcons(Player p) {

		if (prisoner.isBusy()) {
			p.closeInventory();
			return;
		}

		getInventory().setItem(3, Util.createItem(Material.IRON_SWORD, 1, (short) 0, "&7Walk : &6$700",
				"&7This prisoner will escort you to the safe zone for a fee."));
		getInventory().setItem(5, Util.createItem(Material.SUGAR, 1, (short) 0, "&7Run : &6$1000",
				"&7This prisoner will escort you to the safe zone for a fee."));

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		ExodusPlayer eP = ExodusPlayer.get(p);
		e.setCancelled(true);

		if (e.getRawSlot() == 3) {
			if (Vault.takeMoney(p, 700, false)) {
				prisoner.NPCSay(p, "Hold tight, boy!");
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_YES, 0.98F, 0.88F);
				startRun(eP, 6);
			} else {
				prisoner.NPCSay(p, "You don't have enough money, boy!");
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 0.88F);
			}
		}


		if (e.getRawSlot() == 5) {
			if (Vault.takeMoney(p, 1000, false)) {
				prisoner.NPCSay(p, "Hold tight, boy!");
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_YES, 0.98F, 0.88F);
				startRun(eP, 2);
			} else {
				prisoner.NPCSay(p, "You don't have enough money, boy!");
				p.getWorld().playSound(p.getLocation(), Sound.VILLAGER_NO, 0.98F, 0.88F);
			}
		}

	}

	private void startRun(ExodusPlayer eP, int speed) {
		Player p = eP.getPlayer();

		ServerStatistics.incrementKey("guard_escorts", 1);
		
		prisoner.setBusy(true);
		p.getWorld().playSound(prisoner.getLocation(), Sound.VILLAGER_YES, 0.98F, 0.8F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				prisoner.setCanMove(true);
				eP.setNoPVP(true);

				for (int i = 0; i < prisoner.getTpLocations().size(); i++) {
					final int step = i;

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						@Override
						public void run() {

							if (!p.isOnline()) {

								prisoner.setCanMove(false);
								prisoner.setBusy(false);

								return;
							}

							Location pLocation = new Location(Main.getWorld(),
									prisoner.getTpLocations().get(step).getX(),
									prisoner.getTpLocations().get(step).getY(),
									prisoner.getTpLocations().get(step).getZ());
							pLocation.setYaw(-90F);
							pLocation.add(-2.0, 0.0, 1.0);

							p.teleport(pLocation);
							prisoner.getEntity().teleport(prisoner.getTpLocations().get(step));

							if (step + 1 >= prisoner.getTpLocations().size()) {
								prisoner.getEntity().teleport(prisoner.getSpawnLocation());
								prisoner.setCanMove(false);
								eP.setNoPVP(false);

								prisoner.setBusy(false);
							}
							
							prisoner.getEntity().setHealth(20.0);

						}

					}, i * speed);

					p.getWorld().playSound(prisoner.getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.84F);

				}
			}
		}, 20);
	}

}
