package mobs.list;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import bukkit.exodusprison.spurkle.Main;
import exodusplayer.ExodusPlayer;
import menu.list.KitMenu;
import mobs.SmartMob;

public class DeliveryCow extends SmartMob {

	public DeliveryCow(Location spawn_loc) {

		super(spawn_loc, EntityType.COW, "Delivery Cow", false, true, false);

		this.setEquipment(null, null, null, null, null);

	}

	@Override
	public void onClick(Player p, ExodusPlayer eP) {

		NPCSay(p, "Moooo!");

		p.getWorld().playSound(this.getLocation(), Sound.COW_IDLE, 0.98F, 1.3F);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				new KitMenu().openMenu(p);
			}
		}, 20);

	}

}
