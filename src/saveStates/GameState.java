package saveStates;

import enums.Hall;
import views.HallPanel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    // Hero State
    private int heroX;
    private int heroY;
    private int timeRemaining;
    private int livesRemaining;

    // Collected enchantments stored in the bag
    private Map<String, Integer> collectedEnchantments; // e.g., "FireGem": 3

    // Objects in the game
    private List<ObjectState> objects; // Type, count, position

    // Monsters in the game
    private List<MonsterState> monsters; // Type, count, position

    // BuildPanel state
    private BuildPanelState buildPanelState;

    // HallPanel state
    private HallPanelState hallPanelState;
    private HallPanel saveHallPanel;

    // Any other variables you want to persist
    private String currentLevel;

    // Getters and Setters
    public int getHeroX() { return heroX; }
    public void setHeroX(int heroX) { this.heroX = heroX; }

    public int getHeroY() { return heroY; }
    public void setHeroY(int heroY) { this.heroY = heroY; }

    public int getTimeRemaining() { return timeRemaining; }
    public void setTimeRemaining(int timeRemaining) { this.timeRemaining = timeRemaining; }

    public int getLivesRemaining() { return livesRemaining; }
    public void setLivesRemaining(int livesRemaining) { this.livesRemaining = livesRemaining; }

    public Map<String, Integer> getCollectedEnchantments() { return collectedEnchantments; }
    public void setCollectedEnchantments(Map<String, Integer> collectedEnchantments) {
        this.collectedEnchantments = collectedEnchantments;
    }

    public List<ObjectState> getObjects() { return objects; }
    public void setObjects(List<ObjectState> objects) { this.objects = objects; }

    public List<MonsterState> getMonsters() { return monsters; }
    public void setMonsters(List<MonsterState> monsters) { this.monsters = monsters; }

    public BuildPanelState getBuildPanelState() { return buildPanelState; }
    public void setBuildPanelState(BuildPanelState buildPanelState) { this.buildPanelState = buildPanelState; }

    public HallPanel getHallPanelState() { return saveHallPanel; }
    public void setHallPanelState(HallPanel hallPanel) {
        System.out.println("Saving Hall Panel...");

        if (hallPanel != null) {
            System.out.println("Saved Hall Panel");

        }

        this.saveHallPanel = hallPanel;
    }

    public String getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(String currentLevel) { this.currentLevel = currentLevel; }
}