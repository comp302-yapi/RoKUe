package validators;

import object.SuperObject;

import java.util.Arrays;
import java.util.Objects;

public class HallValidator {

    private final int EARTH_HALL_MIN_OBJECTS = 6;
    private final int AIR_HALL_MIN_OBJECTS = 9;
    private final int WATER_HALL_MIN_OBJECTS = 13;
    private final int FIRE_HALL_MIN_OBJECTS = 17;

    public boolean validateHall(String hallName, SuperObject[] hallObjects) {
        switch (hallName) {
            case ("earth") -> {
                return validateEarthHall(hallObjects);
            }
            case ("air") -> {
                return validateAirHall(hallObjects);
            }
            case ("water") -> {
                return validateWaterHall(hallObjects);
            }
            case ("fire") -> {
                return validateFireHall(hallObjects);
            }
            default -> throw new IllegalStateException("Unexpected value: " + hallName);
        }
    }

    private int getNonNullElementCount(SuperObject[] hallObjects) {
        return (int) Arrays.stream(hallObjects).filter(Objects::nonNull).count();
    }

    public boolean validateEarthHall(SuperObject[] hallObjects) {
        return getNonNullElementCount(hallObjects) >= EARTH_HALL_MIN_OBJECTS;
    }

    public boolean validateAirHall(SuperObject[] hallObjects) {
        return getNonNullElementCount(hallObjects) >= AIR_HALL_MIN_OBJECTS;
    }

    public boolean validateWaterHall(SuperObject[] hallObjects) {
        return getNonNullElementCount(hallObjects) >= WATER_HALL_MIN_OBJECTS;
    }

    public boolean validateFireHall(SuperObject[] hallObjects) {
        return getNonNullElementCount(hallObjects) >= FIRE_HALL_MIN_OBJECTS;
    }
}
