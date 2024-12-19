package controllers;

import enums.Hall;
import managers.TileManagerForHall;
import managers.ViewManager;
import object.SuperObject;
import validators.HallValidator;
import views.BuildPanel;

import javax.swing.*;

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

    public void toNextHall(TileManagerForHall currentHall) {
        switch (currentHall.hall) {
            case HallOfEarth -> {
                if(hallValidator.validateEarthHall(currentHall.objects)) {
                    buildPanel.setCurrentHall(Hall.HallOfAir);
                }
                else {
                    System.out.println("Not enough objects: " + currentHall.objects.length);
                }
            }
            case HallOfAir -> {
                if(hallValidator.validateAirHall(currentHall.objects)) {
                    buildPanel.setCurrentHall(Hall.HallOfWater);
                }
                else {
                    System.out.println("Not enough objects: " + currentHall.objects.length);
                }
            }
            case HallOfWater -> {
                if(hallValidator.validateWaterHall(currentHall.objects)) {
                    buildPanel.setCurrentHall(Hall.HallOfFire);
                }
                else {
                    System.out.println("Not enough objects: " + currentHall.objects.length);
                }
            }
            case HallOfFire -> {
                if(hallValidator.validateFireHall(currentHall.objects)) {
                    buildPanel.getViewManager().switchTo("GamePanel", true);
                }
                else {
                    System.out.println("Not enough objects: " + currentHall.objects.length);
                }
            }
        }
    }

}
