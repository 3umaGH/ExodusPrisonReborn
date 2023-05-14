package mobs.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import bukkit.exodusprison.spurkle.Main;
import logger.Log;
import logger.Log.LogType;
import statistics.handlers.ServerStatistics;

public class MobSoundListener {

	public static void startListening() {

		ProtocolLibrary.getProtocolManager()
				.addPacketListener(new PacketAdapter(Main.getPlugin(), PacketType.Play.Server.NAMED_SOUND_EFFECT) {
					@Override
					public void onPacketSending(final PacketEvent e) {
						String soundName = e.getPacket().getStrings().read(0);
						Float volume = e.getPacket().getFloat().read(0);
						
						if (soundName.startsWith("mob.") && volume >= 1F) {
							 ServerStatistics.incrementKey("sounds_muted",1);
							e.setCancelled(true);
						}

					}
				});
		
		Log.log(LogType.NOTIFY,"[INITALIZING] [MOB SOUND] Sound muter initalized successfully.");

}

}
