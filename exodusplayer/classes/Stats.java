package exodusplayer.classes;

import java.util.HashMap;

public class Stats {

	HashMap<String, Long> statistics = new HashMap<String, Long>();

	public Stats() {

	}

	public void incrementKey(String name, Integer amount) {
		if (statistics.containsKey(name))
			statistics.put(name, statistics.get(name) + amount);
		else
			statistics.put(name, amount.longValue());
	}

	public void decrementKey(String name, Integer amount) {
		if (statistics.containsKey(name))
			statistics.put(name, statistics.get(name) - amount);
		else
			statistics.put(name, amount.longValue());
	}

	public Long getKey(String name) {
		return statistics.get(name);
	}

	public HashMap<String, Long> getStatistics() {
		return statistics;
	}

}
