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
    public int timeLeftSave;
    public boolean isPaused;
    public boolean checkInventoryForReveal;
    public boolean checkInventoryForCloak;
    public boolean checkInventoryForLuringGem;
    public int playerX;
    public int playerY;
    public boolean wizardChecker;
    public ArrayList<SuperObject> enchantmentsEarth;
    public ArrayList<SuperObject> enchantmentsAir;
    public ArrayList<SuperObject> enchantmentsWater;
    public ArrayList<SuperObject> enchantmentsFire;
    public int gold;
    public int xp, level;
    public boolean armorOnIronHead;
    public boolean armorOnIronTorso;
    public boolean armorOnLeatherHead;
    public boolean armorOnLeatherTorso;

    public HallPanelData(Hall currentHall, ArrayList<Entity> monsters, SuperObject[][] gridWorld, int[][] gridWorldAll,
                         int timeLeft, boolean isPaused, boolean checkInventoryForReveal,
                         boolean checkInventoryForCloak, boolean checkInventoryForLuringGem,
                         ArrayList<SuperObject> objectsEarth, ArrayList<SuperObject> objectsAir,
                         ArrayList<SuperObject> objectsWater, ArrayList<SuperObject> objectsFire,
                         int playerX, int playerY, boolean wizardChecker,
                         ArrayList<SuperObject> enchantmentsEarth, ArrayList<SuperObject> enchantmentsAir,
                         ArrayList<SuperObject> enchantmentsWater, ArrayList<SuperObject> enchantmentsFire,
                         int gold, int level, int xp, boolean armorOnIronHead, boolean armorOnIronTorso,
                         boolean armorOnLeatherHead, boolean armorOnLeatherTorso) {

        this.currentHall = currentHall;
        this.monsters = monsters;
        this.gridWorld = gridWorld;
        this.gridWorldAll = gridWorldAll;
        this.timeLeftSave = timeLeft;
        this.isPaused = isPaused;
        this.checkInventoryForReveal = checkInventoryForReveal;
        this.checkInventoryForCloak = checkInventoryForCloak;
        this.checkInventoryForLuringGem = checkInventoryForLuringGem;
        this.objectsEarth = objectsEarth;
        this.objectsAir = objectsAir;
        this.objectsWater = objectsWater;
        this.objectsFire = objectsFire;
        this.playerX = playerX;
        this.playerY = playerY;
        this.wizardChecker = wizardChecker;
        this.enchantmentsEarth = enchantmentsEarth;
        this.enchantmentsAir = enchantmentsAir;
        this.enchantmentsWater = enchantmentsWater;
        this.enchantmentsFire = enchantmentsFire;
        this.gold = gold;
        this.xp = xp;
        this.level = level;
        this.armorOnIronHead = armorOnIronHead;
        this.armorOnIronTorso = armorOnIronTorso;
        this.armorOnLeatherHead = armorOnLeatherHead;
        this.armorOnLeatherTorso = armorOnLeatherTorso;
    }

    @Override
    public String toString() {
        return "HallPanelData {" +
                "\n  currentHall: " + currentHall +
                "\n  monsters: " + monsters +
                "\n  gridWorld: " + gridWorld +
                "\n  gridWorldAll: " + java.util.Arrays.deepToString(gridWorldAll) +
                "\n  timeLeft: " + timeLeftSave +
                "\n  isPaused: " + isPaused +
                "\n  checkInventoryForReveal: " + checkInventoryForReveal +
                "\n  checkInventoryForCloak: " + checkInventoryForCloak +
                "\n  checkInventoryForLuringGem: " + checkInventoryForLuringGem +
                "\n  Objects Earth: " + objectsEarth +
                "\n  Objects Air: " + objectsAir +
                "\n  Objects Water: " + objectsWater+
                "\n  Objects Fire: " + objectsFire +
                "\n  Player X: " + playerX +
                "\n  Player Y: " + playerY +
                "\n  Enchantments Earth: " + enchantmentsEarth +
                "\n  Enchantments Air: " + enchantmentsAir +
                "\n  Enchantments Water: " + enchantmentsWater +
                "\n  Enchantments Fire: " + enchantmentsFire +
                "\n  Gold: " + gold +
                "\n  XP: " + xp +
                "\n  Level: " + level +
                "\n  Iron Armor Head: " + armorOnIronHead +
                "\n  Iron Armor Torso: " + armorOnIronTorso +
                "\n  Leather Armor Head: " + armorOnLeatherHead +
                "\n  Leather Armor Torso: " + armorOnLeatherTorso +
                "\n}";
    }
}