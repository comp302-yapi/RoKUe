package controllers;

import enums.BuildDirection;
import enums.Hall;
import managers.TileManagerForHall;
import object.SuperObject;
import validators.HallValidator;
import views.BuildPanel;


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

    public void toNextHall(TileManagerForHall currentHall, BuildDirection direction) {
        switch (currentHall.hall) {
            case HallOfEarth -> {
                if (direction == BuildDirection.Backward) { return; }
                else if(hallValidator.validateEarthHall(currentHall.objects)) {
                    buildPanel.setCurrentHall(Hall.HallOfAir);
                }
                else {
                    System.out.println("Not enough objects");
                }
            }
            case HallOfAir -> {
                if (direction == BuildDirection.Backward) { buildPanel.setCurrentHall(Hall.HallOfEarth); }
                else if(hallValidator.validateAirHall(currentHall.objects)) {
                    buildPanel.setCurrentHall(Hall.HallOfWater);
                }
                else {
                    System.out.println("Not enough objects");
                }
            }
            case HallOfWater -> {
                if (direction == BuildDirection.Backward) { buildPanel.setCurrentHall(Hall.HallOfAir); }
                else if(hallValidator.validateWaterHall(currentHall.objects)) {
                    buildPanel.setCurrentHall(Hall.HallOfFire);
                }
                else {
                    System.out.println("Not enough objects");
                }
            }
            case HallOfFire -> {
                if (direction == BuildDirection.Backward) { buildPanel.setCurrentHall(Hall.HallOfWater); }
                else if(hallValidator.validateFireHall(currentHall.objects)) {
                    buildPanel.getViewManager().switchTo("GamePanel", true);
                }
                else {
                    System.out.println("Not enough objects");
                }
            }
        }
    }

}
