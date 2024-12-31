package data;

import object.SuperObject;

import java.io.Serializable;
import java.util.ArrayList;

public class HomePanelData implements Serializable {
    private static final long serialVersionUID = 1L;

    public ArrayList<SuperObject> objectsInHome; // List of objects in HomePanel
    public int playerGold;
    public int playerArmor;
    public int playerSpeed;
    public int playerLife;
    public int playerMaxLife;
    public int playerScreenX;
    public int playerScreenY;

    public HomePanelData(
            ArrayList<SuperObject> objectsInHome,
            int playerGold,
            int playerArmor,
            int playerSpeed,
            int playerLife,
            int playerMaxLife,
            int playerScreenX,
            int playerScreenY
    ) {
        this.objectsInHome = objectsInHome;
        this.playerGold = playerGold;
        this.playerArmor = playerArmor;
        this.playerSpeed = playerSpeed;
        this.playerLife = playerLife;
        this.playerMaxLife = playerMaxLife;
        this.playerScreenX = playerScreenX;
        this.playerScreenY = playerScreenY;
    }

    @Override
    public String toString() {
        return "HomePanelData {" +
                "\n  objectsInHome: " + objectsInHome +
                "\n  playerGold: " + playerGold +
                "\n  playerArmor: " + playerArmor +
                "\n  playerSpeed: " + playerSpeed +
                "\n  playerLife: " + playerLife +
                "\n  playerMaxLife: " + playerMaxLife +
                "\n  playerScreenX: " + playerScreenX +
                "\n  playerScreenY: " + playerScreenY +
                "\n}";
    }
}