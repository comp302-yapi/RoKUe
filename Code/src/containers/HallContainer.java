package containers;

import enums.Hall;
import managers.TileManagerForHall;

public class HallContainer {
    private static final TileManagerForHall hallOfWater = new TileManagerForHall(Hall.HallOfWater, "/res/maps/hallOfWater.txt", 14, 15);
	private static final TileManagerForHall hallOfEarth = new TileManagerForHall( Hall.HallOfEarth, "/res/maps/hallOfEarth.txt", 14, 15);
	private static final TileManagerForHall hallOfAir = new TileManagerForHall(Hall.HallOfAir, "/res/maps/hallOfAir.txt", 14, 15);
	private static final TileManagerForHall hallOfFire = new TileManagerForHall( Hall.HallOfFire, "/res/maps/hallOfFire.txt", 14, 15);
    
	public static TileManagerForHall getHallOfWater() {
		return hallOfWater;
	}
	public static TileManagerForHall getHallOfEarth() {
		return hallOfEarth;
	}
	public static TileManagerForHall getHallOfAir() {
		return hallOfAir;
	}
	public static TileManagerForHall getHallOfFire() {
		return hallOfFire;
	}
}
