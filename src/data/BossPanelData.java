package data;

import entity.Entity;
import entity.Laser;
import entity.SorcererProjectile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BossPanelData implements Serializable {
    private static final long serialVersionUID = 1L;

    public ArrayList<Entity> monsters;
    public boolean isPaused;
    public int playerX;
    public int playerY;
    public int gold;
    public int xp, level;
    public boolean armorOnIronHead;
    public boolean armorOnIronTorso;
    public boolean armorOnIronLeg;
    public boolean armorOnLeatherHead;
    public boolean armorOnLeatherTorso;
    public boolean armorOnLeatherLeg;
    public boolean spawned;
    public List<SorcererProjectile> sorcererProjectiles = new ArrayList<>();
    public List<Laser> lasers = new ArrayList<>();



    public BossPanelData(ArrayList<Entity> monsters,
                         boolean isPaused,
                         int playerX, int playerY, int gold, int level, int xp,
                         boolean armorOnIronHead, boolean armorOnIronTorso, boolean armorOnIronLeg,
                         boolean armorOnLeatherHead, boolean armorOnLeatherTorso, boolean armorOnLeatherLeg,
                         boolean spawned, List<SorcererProjectile> sorcererProjectiles,
                         List<Laser> lasers) {

        this.monsters = monsters;
        this.isPaused = isPaused;
        this.playerX = playerX;
        this.playerY = playerY;
        this.gold = gold;
        this.xp = xp;
        this.level = level;
        this.armorOnIronHead = armorOnIronHead;
        this.armorOnIronTorso = armorOnIronTorso;
        this.armorOnIronLeg = armorOnIronLeg;
        this.armorOnLeatherHead = armorOnLeatherHead;
        this.armorOnLeatherTorso = armorOnLeatherTorso;
        this.armorOnLeatherLeg = armorOnLeatherLeg;
        this.spawned = spawned;
        this.sorcererProjectiles = sorcererProjectiles;
        this.lasers = lasers;
    }

    @Override
    public String toString() {
        return "HallPanelData {" +
                "\n  monsters: " + monsters +
                "\n  Boss Spawned: " + spawned +
                "\n  isPaused: " + isPaused +
                "\n  Player X: " + playerX +
                "\n  Player Y: " + playerY +
                "\n  Gold: " + gold +
                "\n  XP: " + xp +
                "\n  Level: " + level +
                "\n  Iron Armor Head: " + armorOnIronHead +
                "\n  Iron Armor Torso: " + armorOnIronTorso +
                "\n  Iron Armor Leg: " + armorOnLeatherLeg +
                "\n  Leather Armor Head: " + armorOnLeatherHead +
                "\n  Leather Armor Torso: " + armorOnLeatherTorso +
                "\n  Leather Armor Leg: " + armorOnLeatherLeg +
                "\n  Sorcerer Projectiles: " + sorcererProjectiles +
                "\n  Lasers: " + lasers +
                "\n}";
    }
}