package validators;

import enums.Hall;

public class HallValidator {

    private final int EARTH_HALL_MIN_OBJECTS = 1;
    private final int AIR_HALL_MIN_OBJECTS = 2;
    private final int WATER_HALL_MIN_OBJECTS = 3;
    private final int FIRE_HALL_MIN_OBJECTS = 4;

    public boolean validateHall(Hall hall, int hallObjectsCount) {
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

    public boolean validateEarthHall(int hallObjectsCount) {
        return hallObjectsCount >= EARTH_HALL_MIN_OBJECTS;
    }

    public boolean validateAirHall(int hallObjectsCount) {
        return hallObjectsCount >= AIR_HALL_MIN_OBJECTS;
    }

    public boolean validateWaterHall(int hallObjectsCount) {
        return hallObjectsCount >= WATER_HALL_MIN_OBJECTS;
    }

    public boolean validateFireHall(int hallObjectsCount) {
        return hallObjectsCount >= FIRE_HALL_MIN_OBJECTS;
    }
}
