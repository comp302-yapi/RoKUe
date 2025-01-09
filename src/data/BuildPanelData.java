package data;

import enums.Hall;
import object.SuperObject;

import java.io.Serializable;
import java.util.ArrayList;

public class BuildPanelData implements Serializable {
    private static final long serialVersionUID = 1L;

    public ArrayList<SuperObject> objectsToDraw;
    public Hall currentHall;
    public boolean isHallValidated;
    public ArrayList<SuperObject> objectsEarth;
    public ArrayList<SuperObject> objectsAir;
    public ArrayList<SuperObject> objectsWater;
    public ArrayList<SuperObject> objectsFire;

    public BuildPanelData(ArrayList<SuperObject> objectsToDraw, Hall currentHall, boolean isHallValidated,
                          ArrayList<SuperObject> objectsEarth, ArrayList<SuperObject> objectsAir,
                          ArrayList<SuperObject> objectsWater, ArrayList<SuperObject> objectsFire) {

        this.objectsToDraw = objectsToDraw;
        this.currentHall = currentHall;
        this.isHallValidated = isHallValidated;
        this.objectsEarth = objectsEarth;
        this.objectsAir = objectsAir;
        this.objectsWater = objectsWater;
        this.objectsFire = objectsFire;
    }

    @Override
    public String toString() {
        return "BuildPanelData {" +
                "\n  objectsToDraw: " + objectsToDraw +
                "\n  currentHall: " + currentHall +
                "\n  isHallValidated: " + isHallValidated +
                "\n  Objects Earth: " + objectsEarth +
                "\n  Objects Air: " + objectsAir +
                "\n  Objects Water: " + objectsWater+
                "\n  Objects Fire: " + objectsFire +
                "\n}";
    }
}