package tutorial.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import exodusplayer.ExodusPlayer;

public class TutorialListener implements Listener {
	
	@EventHandler
	public void onChatReceive(AsyncPlayerChatEvent e){
		ExodusPlayer eP = ExodusPlayer.get(e.getPlayer());
		
		if(eP != null && eP.getTutorial() != -1){ // disable chat for tutorial watchers
			e.setCancelled(true);
		}
		
		for(ExodusPlayer eP_1 : ExodusPlayer.getOnlinePlayers()){
			if(eP_1.getTutorial() != -1)
				e.getRecipients().remove(eP_1.getPlayer());
		}
		
	}

}
