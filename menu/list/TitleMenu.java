package menu.list;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import exodusplayer.ExodusPlayer;
import menu.Menu;
import util.Util;

public class TitleMenu extends Menu {

	public TitleMenu() {
		super("Your Titles", 4);

	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(p);

		getInventory().setItem(0, Util.createItem(Material.BARRIER, 1, (short) 0, "&cRemove Title",
				"&7Right click to remove all your title."));

		int slot = 1;

		for (String title : eP.getTitles()) {
			getInventory().setItem(slot, Util.createItem(Material.PAPER, 1, (short) 0, title,
					"&7Right click to set your title to " + title + "&7."));

			slot++;

		}

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		int slot = e.getRawSlot();

		e.setCancelled(true);

		if (slot >= 0 && slot <= getInventory().getSize()) {

			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
				return;

			if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
				setTitle(p, "");
			} else {
				setTitle(p, e.getCurrentItem().getItemMeta().getDisplayName());
			}

		}

	}

	public static void setTitle(Player p, String suffix) {

		if (!suffix.equals(""))
			suffix = " &8[" + suffix + "&8]";

		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
				"pex user " + p.getName() + " suffix \"" + suffix + "\"");


		p.sendMessage("§6Title updated. Type /title to open Title Menu.");

	}

}
