package data;

import entity.Entity;
import enums.Hall;
import object.SuperObject;

import java.io.Serializable;
import java.util.ArrayList;

public class HallPanelData implements Serializable {
    private static final long serialVersionUID = 1L;

    public Hall currentHall;
    public ArrayList<Entity> monsters;
    public SuperObject[][] gridWorld;
    public ArrayList<SuperObject> objectsEarth;
    public ArrayList<SuperObject> objectsAir;
    public ArrayList<SuperObject> objectsWater;
    public ArrayList<SuperObject> objectsFire;
    public int[][] gridWorldAll;
    public int timeLeft;
    public boolean isPaused;
    public boolean checkInventoryForReveal;
    public boolean checkInventoryForCloak;
    public boolean checkInventoryForLuringGem;

    // Constructor to initialize data
    public HallPanelData(Hall currentHall, ArrayList<Entity> monsters, SuperObject[][] gridWorld, int[][] gridWorldAll,
                         int timeLeft, boolean isPaused, boolean checkInventoryForReveal,
                         boolean checkInventoryForCloak, boolean checkInventoryForLuringGem,
                         ArrayList<SuperObject> objectsEarth, ArrayList<SuperObject> objectsAir,
                         ArrayList<SuperObject> objectsWater, ArrayList<SuperObject> objectsFire) {
        this.currentHall = currentHall;
        this.monsters = monsters;
        this.gridWorld = gridWorld;
        this.gridWorldAll = gridWorldAll;
        this.timeLeft = timeLeft;
        this.isPaused = isPaused;
        this.checkInventoryForReveal = checkInventoryForReveal;
        this.checkInventoryForCloak = checkInventoryForCloak;
        this.checkInventoryForLuringGem = checkInventoryForLuringGem;
        this.objectsEarth = objectsEarth;
        this.objectsAir = objectsAir;
        this.objectsWater = objectsWater;
        this.objectsFire = objectsFire;
    }

    @Override
    public String toString() {
        return "HallPanelData {" +
                "\n  currentHall: " + currentHall +
                "\n  monsters: " + monsters +
                "\n  gridWorld: " + gridWorld +
                "\n  gridWorldAll: " + java.util.Arrays.deepToString(gridWorldAll) +
                "\n  timeLeft: " + timeLeft +
                "\n  isPaused: " + isPaused +
                "\n  checkInventoryForReveal: " + checkInventoryForReveal +
                "\n  checkInventoryForCloak: " + checkInventoryForCloak +
                "\n  checkInventoryForLuringGem: " + checkInventoryForLuringGem +
                "\n  Objects Earth: " + objectsEarth +
                "\n  Objects Air: " + objectsAir +
                "\n  Objects Water: " + objectsWater+
                "\n  Objects Fire: " + objectsFire +
                "\n}";
    }
}