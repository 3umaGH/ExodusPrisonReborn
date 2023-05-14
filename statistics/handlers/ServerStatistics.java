package statistics.handlers;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import logger.Log;
import logger.Log.LogType;
import mysql.MySQL;

public class ServerStatistics {

	private static HashMap<String, Long> statistics = new HashMap<String, Long>();

	public static void incrementKey(String name, Integer amount) {
		if (statistics.containsKey(name))
			statistics.put(name, statistics.get(name) + amount);
		else
			statistics.put(name, amount.longValue());
	}
	
	public static void setKey(String name, Integer amount) {
			statistics.put(name, amount.longValue());
	}

	public static void decrementKey(String name, Integer amount) {
		if (statistics.containsKey(name))
			statistics.put(name, statistics.get(name) - amount);
		else
			statistics.put(name, amount.longValue());
	}
	
	public static Long getKey(String name){
		if (statistics.containsKey(name))
			return statistics.get(name);
		else
			return (long) 0;
	}

	public Long getStatistics(String name) {
		return statistics.get(name);
	}

	public static HashMap<String, Long> getStatistics() {
		return statistics;
	}
	
	public static void saveServerStatistics(){
		DailyStatistics.saveData();
		MySQL.saveServerStats(getJsonString());
		Log.log(LogType.WARNING,"[STOPPING] Server statistics have been saved.");
	}
	
	public static void loadServerStatistics(){
		MySQL.loadServerStats();
	}

	@SuppressWarnings("unchecked")
	private static String getJsonString() {
		JSONObject obj = new JSONObject();

		for (Entry<String, Long> entry : statistics.entrySet())
			obj.put("statistics_" + entry.getKey(), entry.getValue());

		return obj.toJSONString();
	}

	public static void loadJsonString(String jsonString) {
		JSONParser parser = new JSONParser();

		Object obj;
		try {
			obj = parser.parse(jsonString);
			JSONObject jsonObject = (JSONObject) obj;

			for (Object entry : jsonObject.keySet()) { // Load data

				if (entry.toString().startsWith("statistics_")) {
					String name = entry.toString().replace("statistics_", "");

					statistics.put(name, (Long) jsonObject.get(entry));
				}
			}

		} catch (ParseException e) {
			
		}
		
		DailyStatistics.loadData();
		Log.log(LogType.NOTIFY,"[INITALIZING] [SERVER STATS] Server statistics have been loaded. " + statistics.size() + " entires.");
	}

	
	

}