package mobs.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobSpawnListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public static void onMobSpawn(CreatureSpawnEvent e) {

		if (e.getSpawnReason().equals(SpawnReason.CUSTOM) || e.getSpawnReason().equals(SpawnReason.SPAWNER_EGG) || e.getSpawnReason().equals(SpawnReason.SPAWNER))
			return;
		
			e.getEntity().remove();

	}

}
