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

    public BuildPanelData(ArrayList<SuperObject> objectsToDraw, Hall currentHall, boolean isHallValidated) {
        this.objectsToDraw = objectsToDraw;
        this.currentHall = currentHall;
        this.isHallValidated = isHallValidated;
    }

    @Override
    public String toString() {
        return "BuildPanelData {" +
                "\n  objectsToDraw: " + objectsToDraw +
                "\n  currentHall: " + currentHall +
                "\n  isHallValidated: " + isHallValidated +
                "\n}";
    }
}