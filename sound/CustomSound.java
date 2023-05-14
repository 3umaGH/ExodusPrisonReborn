package sound;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;



public class CustomSound {
	
	public static enum SoundType {
		PEPPER_SPRAY, BATON_HIT, INV_SEARCH, TASER
	}
	
	public static void playSound(SoundType sound, Location where){
		for(Player allP : Bukkit.getOnlinePlayers())
			allP.playSound(where, "exodus." + sound.toString(), 1.0F, 1.0F);
	}

}
