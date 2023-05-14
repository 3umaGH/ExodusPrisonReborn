package listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import util.Util;

public class SexyChatListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void AsyncChatEvent(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		ItemStack inHand = p.getItemInHand();
		
		if(e.isCancelled())
			return;
		
		String msg = e.getMessage().toLowerCase();
		
		if(msg.contains("[item]")){
			if(p.hasPermission("exodus.item")){
				if(!(inHand.getType() == Material.AIR)) {

					String replaceFormat = e.getFormat().replace("%1$s", p.getDisplayName()).replace("%2$s", msg);

					Util.globalItemMessage(replaceFormat, inHand);
					
					for(Player allP : Bukkit.getOnlinePlayers()){
						e.getRecipients().remove(allP);
						//new FancyMessage(replaceFormat).command("/view_item " + itemID).tooltip(tooltip).send(allP);
					}
				}
			} 
		}
			
	}

}
