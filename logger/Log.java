package logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Log {
	
	public enum LogType {
		CRITICAL, NOTIFY, LOG, WARNING
	}

	public static void log(LogType logType, String txt) {

		String prefix = "";

		switch (logType) {

		case CRITICAL:
			prefix = "§cCRITICAL";
			break;
		case WARNING:
			prefix = "§eWARNING";
			break;
		case NOTIFY:
			prefix = "§bNotify";
			break;
		case LOG:
			prefix = "§7Log";
			break;

		}

		System.out.println(prefix + " -> " + txt);
	}
	
	public static void log(String txt){
		log(LogType.LOG, txt);
	}
	
	public static void notifyStaff(String txt){
		log(LogType.LOG, txt);
		
		for(Player p : Bukkit.getOnlinePlayers())
			if(p.hasPermission("exodus.moderator") || p.hasPermission("exodus.chief")) {
				p.sendMessage(txt);
			}
		
		
	}
	
}
