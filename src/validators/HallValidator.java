package validators;

import enums.Hall;
import java.io.Serializable;
import java.util.Map;

public class HallValidator implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int EARTH_HALL_MIN_OBJECTS = 1;
    private static final int AIR_HALL_MIN_OBJECTS = 2;
    private static final int WATER_HALL_MIN_OBJECTS = 3;
    private static final int FIRE_HALL_MIN_OBJECTS = 4;

    public static boolean validateHall(Hall hall, int hallObjectsCount) {
        switch (hall) {
            case HallOfEarth -> {
                return validateEarthHall(hallObjectsCount);
            }
            case HallOfAir -> {
                return validateAirHall(hallObjectsCount);
            }
            case HallOfWater -> {
                return validateWaterHall(hallObjectsCount);
            }
            case HallOfFire -> {
                return validateFireHall(hallObjectsCount);
            }
            default -> throw new IllegalStateException("Unexpected value: " + hall.name());
        }
    }

    public static boolean validateEarthHall(int hallObjectsCount) {
        return hallObjectsCount >= EARTH_HALL_MIN_OBJECTS;
    }

    public static boolean validateAirHall(int hallObjectsCount) {
        return hallObjectsCount >= AIR_HALL_MIN_OBJECTS;
    }

    public static boolean validateWaterHall(int hallObjectsCount) {
        return hallObjectsCount >= WATER_HALL_MIN_OBJECTS;
    }

    public static boolean validateFireHall(int hallObjectsCount) {
        return hallObjectsCount >= FIRE_HALL_MIN_OBJECTS;
    }

    public static Map<Hall, Integer> getHallObjectLimits() {
        return Map.of(
            Hall.HallOfEarth, EARTH_HALL_MIN_OBJECTS,
            Hall.HallOfAir, AIR_HALL_MIN_OBJECTS,
            Hall.HallOfWater, WATER_HALL_MIN_OBJECTS,
            Hall.HallOfFire, FIRE_HALL_MIN_OBJECTS
        );
    }
}
