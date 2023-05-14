package exodusplayer.classes;

import java.util.HashMap;

import exodusplayer.ExodusPlayer;
import util.Util;

public class Offenses {

	HashMap<Integer, Offense> offenseList = new HashMap<Integer, Offense>();
	ExodusPlayer p;

	public static enum OffenseType {
		GUARD_ILLEGAL_DROP
	}

	public static class Offense {

		OffenseType type;
		Long time;
		String details;

		int id;

		public Offense(OffenseType type, String details) {
			this.type = type;
			this.time = System.currentTimeMillis();
			this.details = details;
		}

		public void setTime(Long time) {
			this.time = time;
		}

		public String getFormattedTime() {
			return Util.formatTimeMillis(time,"yyyy/MM/dd HH:mm:ss");
		}

		public Long getTime() {
			return time;
		}

		public OffenseType getType() {
			return type;
		}

		public String getDetails() {
			return details;
		}

		public int getId() {
			return this.id;
		}

		public void setType(OffenseType type) {
			this.type = type;
		}

		public void setDetails(String details) {
			this.details = details;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

	public Offenses(ExodusPlayer p) {
		this.p = p;
	}

	public void addOffense(OffenseType type, String details) {
		Offense offense = new Offense(type, details);

		offense.setId(p.getData().getDataInt("offenses_id"));
		p.getData().setDataInt("offenses_id", offense.getId() + 1);

		offenseList.put(offense.getId(), offense);
		
		p.getStats().incrementKey("offenses_amount", 1);
	}

	public HashMap<Integer, Offense> getOffenses() {
		return this.offenseList;
	}

}
