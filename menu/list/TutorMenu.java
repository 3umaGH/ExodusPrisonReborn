package menu.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import exodusplayer.ExodusPlayer;
import menu.Menu;
import tutorial.handlers.TutorialHandler;
import util.Util;

public class TutorMenu extends Menu {

	public TutorMenu() {
		super("Tutor Menu", 1);

	}

	@Override
	public void loadIcons(Player p) {
		ExodusPlayer eP = ExodusPlayer.get(p);
		
		getInventory().setItem(4, Util.createItem(Material.BOOK, 1, (short) 0, "&eGeneral Tutorial", "&7General tutorial explaining on how this server works.", "&aRecommended for new players"));

	}

	@Override
	public void onInventoryClick(Player p, InventoryClickEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getRawSlot() == 4) {
			TutorialHandler.startTutorial(ExodusPlayer.get(p), TutorialHandler.generalTutorial);
			p.closeInventory();
		}

	}

}
