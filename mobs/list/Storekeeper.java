package mobs.list;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import mobs.SmartMob;

public class Storekeeper extends SmartMob {

	public Storekeeper(Location spawn_loc) {

		super(spawn_loc, EntityType.VILLAGER, "Storekeeper", false, true, false);

		this.setEquipment(null, null, null, null, null);
		this.setProfession(Profession.LIBRARIAN);

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		if (eP.isGuard()) {
			NPCSay(p, "Hello, Want to access your storage?");
			p.getWorld().playSound(this.getLocation(), Sound.VILLAGER_HAGGLE, 0.98F, 0.9F);

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				@Override
				public void run() {

					NPCSay(p, "Hold on, let me find your chest.");
					p.getWorld().playSound(getLocation(), Sound.VILLAGER_NO, 0.98F, 0.6F);

					setCanMove(true);
					getEntity().teleport(new Location(Main.getWorld(), -25.5, 89, 22.5));

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						@Override
						public void run() {

							NPCSay(p, "It's there.");
							p.getWorld().playSound(getLocation(), Sound.VILLAGER_YES, 0.98F, 1F);

							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								@Override
								public void run() {

									if (p.getLocation().distance(getLocation()) > 10) {
										NPCSay(p, "Hey! Where did you go?");
										p.getWorld().playSound(getLocation(), Sound.VILLAGER_YES, 0.2F, 1F);
									} else {
										p.performCommand("pv 1");
										p.getWorld().playSound(getLocation(), Sound.CHEST_OPEN, 0.98F, 1F);
									}
									
									setCanMove(false);

								}
							}, 20);

						}
					}, 100);

				}
			}, 40);

		} else {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " Free Cam trap.");
			return;
		}

	}

}
