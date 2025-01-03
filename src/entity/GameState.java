package entity;

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
    public String currentMode;

    // Constructor
    public GameState(HallPanelData hallPanelData, BuildPanelData buildPanelData, HomePanelData homePanelData) {
        this.hallPanelData = hallPanelData;
        this.buildPanelData = buildPanelData;
        this.homePanelData = homePanelData;
    }

    // Getters
    public HallPanelData getHallPanelData() {
        return hallPanelData;
    }

    public BuildPanelData getBuildPanelData() {
        return buildPanelData;
    }

    public HomePanelData getHomePanelData() {return homePanelData;}


    // Setters (if needed)
    public void setHallPanelData(HallPanelData hallPanelData) {
        this.hallPanelData = hallPanelData;
    }

    public void setBuildPanelData(BuildPanelData buildPanelData) {
        this.buildPanelData = buildPanelData;
    }

    @Override
    public String toString() {
        return "GameState {" +
                "\n  HallPanelData: " + (hallPanelData != null ? hallPanelData.toString() : "null") +
                "\n  BuildPanelData: " + (buildPanelData != null ? buildPanelData.toString() : "null") +
                "\n  HomePanelData: " + (homePanelData != null ? homePanelData.toString() : "null") +
                "\n  Current Mode: " + currentMode +
                "\n}";
    }
}