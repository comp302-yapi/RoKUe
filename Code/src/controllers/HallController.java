package controllers;

import enums.BuildDirection;
import enums.Hall;
import managers.TileManagerForHall;
import object.SuperObject;
import validators.HallValidator;
import views.BasePanel;
import views.BuildPanel;
import java.util.List;


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
                else if(hallValidator.validateHall(currentHall.hall, getNonNullElementCount(currentHall))) {
                    buildPanel.setCurrentHall(Hall.HallOfAir);
                }
                else {
                    System.out.println("Not enough objects");
                }
            }
            case HallOfAir -> {
                if (direction == BuildDirection.Backward) { buildPanel.setCurrentHall(Hall.HallOfEarth); }
                else if(hallValidator.validateHall(currentHall.hall, getNonNullElementCount(currentHall))) {
                    buildPanel.setCurrentHall(Hall.HallOfWater);
                }
                else {
                    System.out.println("Not enough objects");
                }
            }
            case HallOfWater -> {
                if (direction == BuildDirection.Backward) { buildPanel.setCurrentHall(Hall.HallOfAir); }
                else if(hallValidator.validateHall(currentHall.hall, getNonNullElementCount(currentHall))) {
                    buildPanel.setCurrentHall(Hall.HallOfFire);
                }
                else {
                    System.out.println("Not enough objects");
                }
            }
            case HallOfFire -> {
                if (direction == BuildDirection.Backward) { buildPanel.setCurrentHall(Hall.HallOfWater); }
                else if(hallValidator.validateHall(currentHall.hall, getNonNullElementCount(currentHall))) {
                    buildPanel.getViewManager().switchTo("GamePanel", true);
                }
                else {
                    System.out.println("Not enough objects");
                }
            }
        }
    }

    public SuperObject getObjectSelectedInHall(TileManagerForHall currentHall, int mouseX, int mouseY) {
        for (SuperObject obj: getObjectsInHall(currentHall)) {
            if (
                    mouseX > obj.worldX && mouseX < obj.worldX + obj.image.getWidth() * BasePanel.scale
                    && mouseY > obj.worldY && mouseY < obj.worldY + obj.image.getHeight() * BuildPanel.scale
            ) {
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
}
