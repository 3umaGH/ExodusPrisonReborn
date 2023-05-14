package statistics.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import util.Util;

public class DailyStatistics {

	private static HashMap<String, Long> data = new HashMap<String, Long>();
	private static List<String> ids = new ArrayList<String>();

	public static String getCurrentDay() {
		return Util.formatTimeMillis(System.currentTimeMillis(), "yyyy/MM/dd");
	}
	
	public static void incrementKey(String key, Integer amount){
		String keyFormat = key + "_" + getCurrentDay();
		
		if(data.containsKey(keyFormat))  {
			data.put(keyFormat, data.get(keyFormat) + amount);
		}
		else {
			data.put(keyFormat, amount.longValue());
		}
		
	}
	
	public static void setKey(String key, Integer amount){
		String keyFormat = key + "_" + getCurrentDay();
		
		data.put(keyFormat, amount.longValue());
		
		
	}
	
	public static Long getKey(String key){
		if(data.containsKey(key + "_" + getCurrentDay()))
			return data.get(key + "_" + getCurrentDay());
		else
			return (long) 0;
	}

	public static void loadData() {
		for (Entry<String, Long> entry : ServerStatistics.getStatistics().entrySet()) {
			
			if (entry.getKey().startsWith("date-data_")) {
				String key = entry.getKey().replace("date-data_", "");

				data.put(key, entry.getValue());
				ids.add(key);

			}
		}
	}

	public static void saveData() {
		for (Entry<String, Long> entry : data.entrySet()) {
			
			if(data.size() >= 200) {
				data.remove(ids.get(0));
				ids.remove(0);
			}

			ServerStatistics.getStatistics().put("date-data_" + entry.getKey(), entry.getValue());
		}
	}

	public static HashMap<String, Long> getStatistics() {
		return data;
	}
	
	

}
