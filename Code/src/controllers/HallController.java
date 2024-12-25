package controllers;

import containers.HallContainer;
import enums.BuildDirection;
import enums.Hall;
import managers.TileManagerForHall;
import object.SuperObject;
import validators.HallValidator;
import views.BasePanel;
import views.BuildPanel;
import java.util.List;
import java.util.Random;


public class HallController {

    private final HallValidator hallValidator;
    private final BuildPanel buildPanel;

    public HallController(BuildPanel buildPanel) {
        this.buildPanel = buildPanel;
        hallValidator = new HallValidator();
    }

    public void addObject(TileManagerForHall currentHall, SuperObject obj, int x, int y) {
        currentHall.addObject(obj, x, y);
    }

    public boolean toNextHall(TileManagerForHall currentHall, BuildDirection direction) {
        switch (currentHall.hall) {
            case HallOfEarth -> {
                if (direction == BuildDirection.Backward) {
                    return true;
                }
                else if(hallValidator.validateHall(currentHall.hall, getNonNullElementCount(currentHall))) {
                    buildPanel.setCurrentHall(Hall.HallOfAir);
                    return true;
                }
                else {
                    return false;
                }
            }
            case HallOfAir -> {
                if (direction == BuildDirection.Backward) {
                    buildPanel.setCurrentHall(Hall.HallOfEarth);
                    return true;
                }
                else if(hallValidator.validateHall(currentHall.hall, getNonNullElementCount(currentHall))) {
                    buildPanel.setCurrentHall(Hall.HallOfWater);
                    return true;
                }
                else {
                    //System.out.println("Not enough objects");
                    return false;
                }
            }
            case HallOfWater -> {
                if (direction == BuildDirection.Backward) {
                    buildPanel.setCurrentHall(Hall.HallOfAir);
                    return true;
                }
                else if(hallValidator.validateHall(currentHall.hall, getNonNullElementCount(currentHall))) {
                    buildPanel.setCurrentHall(Hall.HallOfFire);
                    return true;
                }
                else {
                    //System.out.println("Not enough objects");
                    return false;
                }
            }
            case HallOfFire -> {
                if (direction == BuildDirection.Backward) {
                    buildPanel.setCurrentHall(Hall.HallOfWater);
                    return true;
                }
                else if(hallValidator.validateHall(currentHall.hall, getNonNullElementCount(currentHall))) {
                    buildPanel.getViewManager().switchTo("HallPanel", true);
                    HallController.assignRunesToObjects(HallContainer.getHallOfEarth());
                    return true;
                }
                else {
                    //System.out.println("Not enough objects");
                    return false;
                }
            }
        }
        return true;
    }

    public static SuperObject getObjectSelectedInHall(TileManagerForHall currentHall, int mouseX, int mouseY) {
        for (SuperObject obj : currentHall.objects) {
            if (mouseX > obj.worldX && mouseX < obj.worldX + obj.image.getWidth() * BasePanel.scale
                    && mouseY > obj.worldY && mouseY < obj.worldY + obj.image.getHeight() * BuildPanel.scale) {
                return obj;
            }
        }
        return null;
    }

    private int getNonNullElementCount(TileManagerForHall hall) {
        return getObjectsInHall(hall).size();
    }

    private List<SuperObject> getObjectsInHall(TileManagerForHall currentHall) {
        return currentHall.objects;
    }

    public static void assignRunesToObjects(TileManagerForHall currentHall) {
        Random random = new Random();

        // Get all objects in the current hall (for example purposes, using HallOfEarth)
        SuperObject[] objects = currentHall.objects.toArray(new SuperObject[20]);

        // Number of objects that can have runes (you can adjust this number as needed)
        int runeObjectCount = 1;

        // Randomly assign 'rune' to the specified number of objects
        for (int i = 0; i < runeObjectCount; i++) {
            int randomIndex;

            // Find a random object that does not already have a rune
            do {
                randomIndex = random.nextInt(objects.length);
            } while (objects[randomIndex] == null || objects[randomIndex].hasRune);

            // Assign a rune to the object
            objects[randomIndex].hasRune = true;
        }
    }
}
