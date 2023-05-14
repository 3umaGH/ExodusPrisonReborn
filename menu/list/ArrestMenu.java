package menu.list;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import exodusplayer.ExodusPlayer;
import menu.Menu;
import util.GUI;
import util.Util;

public class ArrestMenu extends Menu {

	private static final int MAX_JAIL_TIME = 1200;

	ExodusPlayer clickedPlayer;

	public ArrestMenu(ExodusPlayer clickedPlayer) {
		super("Arrest Settings: " + clickedPlayer.getName(), 1);

		this.clickedPlayer = clickedPlayer;
		this.setUpdating(true);

	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(p);

		if (!clickedPlayer.isArrested()) {
			GUI.sendTitle(p, 0, 50, 5, "", "&a" + p.getName() + " has served his time and is now released.");
			p.closeInventory();
		}

		getInventory().setItem(0, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 14, "&c+100 seconds",
				"&7Click this to add 100 seconds to the prisoners sentence."));
		getInventory().setItem(1, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 14, "&c+10 seconds",
				"&7Click this to add 10 seconds to the prisoners sentence."));

		getInventory().setItem(4,
				Util.createItem(Material.WATCH, 1, (short) 0, "&7Time left in jail: &6" + clickedPlayer.getArrestTime(),
						"&7Right click this to &aRELEASE&7 the prisoner."));

		getInventory().setItem(7, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 5, "&c-10 seconds",
				"&7Click this to reduce prisoners sentence by 10 seconds."));
		getInventory().setItem(8, Util.createItem(Material.STAINED_GLASS_PANE, 1, (short) 5, "&a-100 seconds",
				"&7Click this to reduce prisoners sentence by 100 seconds."));

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		e.setCancelled(true);

		if (e.getRawSlot() == 4 && e.isRightClick()) {
			clickedPlayer.unarrestPlayer();
			p.getWorld().playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
		}

		if (e.getRawSlot() == 0) {
			clickedPlayer.addArrestTime(100);
			p.getWorld().playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
			GUI.sendTitle(clickedPlayer.getPlayer(), 0, 50, 5, "",
					"&c" + p.getName() + " has increased your sentence by 100 seconds.");
		}

		if (e.getRawSlot() == 1) {
			clickedPlayer.addArrestTime(10);
			p.getWorld().playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
			GUI.sendTitle(clickedPlayer.getPlayer(), 0, 50, 5, "",
					"&c" + p.getName() + " has increased your sentence by 10 seconds.");
		}

		if (e.getRawSlot() == 7 && clickedPlayer.getArrestTime() < MAX_JAIL_TIME) {
			clickedPlayer.addArrestTime(-10);
			p.getWorld().playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
			GUI.sendTitle(clickedPlayer.getPlayer(), 0, 50, 5, "",
					"&a" + p.getName() + " has reduced your sentence by 10 seconds.");
		}

		if (e.getRawSlot() == 8 && clickedPlayer.getArrestTime() < MAX_JAIL_TIME) {
			if (clickedPlayer.getArrestTime() <= 100)
				clickedPlayer.unarrestPlayer();
			else
				clickedPlayer.addArrestTime(-100);
			p.getWorld().playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
			GUI.sendTitle(clickedPlayer.getPlayer(), 0, 50, 5, "",
					"&a" + p.getName() + " has reduced your sentence by 100 seconds.");
		}

	}

}
