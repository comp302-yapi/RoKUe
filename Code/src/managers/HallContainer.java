

package managers;

import enums.Hall;

public class HallContainer {

	
	
    public static TileManagerForHall hallOfWater = new TileManagerForHall(Hall.HallOfWater, "/res/maps/hallOfWater.txt", 14, 15);
    public static TileManagerForHall hallOfEarth = new TileManagerForHall( Hall.HallOfEarth, "/res/maps/hallOfEarth.txt", 14, 15);
    public static TileManagerForHall hallOfAir = new TileManagerForHall(Hall.HallOfAir, "/res/maps/hallOfAir.txt", 14, 15);
    public static TileManagerForHall hallOfFire = new TileManagerForHall( Hall.HallOfFire, "/res/maps/hallOfFire.txt", 14, 15);
    
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
