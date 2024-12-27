package controllers;

import containers.HallContainer;
import entity.Entity;
import entity.Player;
import enums.BuildDirection;
import enums.Hall;
import managers.TileManagerForHall;
import managers.ViewManager;
import object.SuperObject;
import validators.HallValidator;
import views.BasePanel;
import views.BuildPanel;
import views.HallPanel;

import java.util.List;
import java.util.Random;


public class HallController {

    private final HallValidator hallValidator;
    private final BuildPanel buildPanel;
    public boolean isLureModeActive = false;


    public HallController(BuildPanel buildPanel) {
        this.buildPanel = buildPanel;
        hallValidator = new HallValidator();

    }

    public void addObject(TileManagerForHall currentHall, SuperObject obj, int x, int y) {

        x -= x % 48;
        y -= y % 48;

//        System.out.println(x + " | " + y);

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

    public static SuperObject getEnchantmentSelectedInHall(TileManagerForHall currentHall, int mouseX, int mouseY) {
        for (SuperObject obj : currentHall.enchantments) {
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

    public static void shouldSwitchHallsInGame(TileManagerForHall currentHall, Player player, HallPanel hallPanel) {
        if (player.screenY > currentHall.getBottomWorldBorder()) {

            hallPanel.nullTimer();

            switch (currentHall.hall) {
                case HallOfEarth -> {
                    hallPanel.currentHall = Hall.HallOfAir;
                    initNewHall(hallPanel.currentHall, player, hallPanel);
                }
                case HallOfAir -> {
                    hallPanel.currentHall = Hall.HallOfWater;
                    initNewHall(hallPanel.currentHall, player, hallPanel);
                }
                case HallOfWater -> {
                    hallPanel.currentHall = Hall.HallOfFire;
                    initNewHall(hallPanel.currentHall, player, hallPanel);
                }
                case HallOfFire -> {
                    System.out.println("HallOfFire");
                    hallPanel.getViewManager().switchTo("TitlePanel", true);

                }
            }
        }
    }

    private static void initNewHall(Hall nextHall, Player player, HallPanel hallPanel) {
        player.screenX = 700;
        player.screenY = 96 + BasePanel.tileSize * 15 - 100;
        hallPanel.tileM = HallContainer.getCurrentHallManager(nextHall);
        assignRunesToObjects(hallPanel.tileM);
        hallPanel.getHallMonsters().clear();
        hallPanel.getTileM().enchantments.clear();
        hallPanel.zeroMonsters();
        player.life = 6;
    }

    public static void movePlayerIfCollision(TileManagerForHall currentHall, Player player) {
        SuperObject objectCollided = getObjectSelectedInHall(currentHall, player.screenX + BasePanel.tileSize / 2, player.screenY + BasePanel.tileSize / 2);
        if (objectCollided != null) {
            player.screenX += objectCollided.worldX - player.screenX + objectCollided.image.getWidth() + 15;
            player.screenY += objectCollided.worldY - player.screenY + objectCollided.image.getHeight() + 15;
        }
    }
}
