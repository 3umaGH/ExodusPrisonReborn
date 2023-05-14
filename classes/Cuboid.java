package classes;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Cuboid {
	
	Vector min;
	Vector max;
	
	public Cuboid(Location loc1, Location loc2) {
		
		this.min = loc1.toVector();
		this.max = loc2.toVector();

	}

	public boolean isIn(Location loc) {
		return loc.toVector().isInAABB(min, max);
	}

}
