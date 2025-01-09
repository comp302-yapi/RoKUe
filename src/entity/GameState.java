package entity;

import data.BossPanelData;
import data.BuildPanelData;
import data.HallPanelData;
import data.HomePanelData;
import views.HomePanel;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private HallPanelData hallPanelData;
    private BuildPanelData buildPanelData;
    private HomePanelData homePanelData;
    private BossPanelData bossPanelData;
    public String currentMode;

    // Constructor
    public GameState(HallPanelData hallPanelData, BuildPanelData buildPanelData, HomePanelData homePanelData, BossPanelData bossPanelData) {
        this.hallPanelData = hallPanelData;
        this.buildPanelData = buildPanelData;
        this.homePanelData = homePanelData;
        this.bossPanelData = bossPanelData;
    }

    // Getters
    public HallPanelData getHallPanelData() {
        return hallPanelData;
    }

    public BossPanelData getBossPanelData() {
        return bossPanelData;
    }

    public BuildPanelData getBuildPanelData() {
        return buildPanelData;
    }

    public HomePanelData getHomePanelData() {return homePanelData;}

    public String getCurrentMode() {return currentMode;}

    @Override
    public String toString() {
        return "GameState {" +
                "\n  HallPanelData: " + (hallPanelData != null ? hallPanelData.toString() : "null") +
                "\n  BuildPanelData: " + (buildPanelData != null ? buildPanelData.toString() : "null") +
                "\n  HomePanelData: " + (homePanelData != null ? homePanelData.toString() : "null") +
                "\n  BossPanelData: " + (bossPanelData != null ? bossPanelData.toString() : "null") +
                "\n  Current Mode: " + currentMode +
                "\n}";
    }
}