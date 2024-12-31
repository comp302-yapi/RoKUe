package entity;

import data.BuildPanelData;
import data.HallPanelData;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private HallPanelData hallPanelData;
    private BuildPanelData buildPanelData;
    public String currentMode;

    // Constructor
    public GameState(HallPanelData hallPanelData, BuildPanelData buildPanelData) {
        this.hallPanelData = hallPanelData;
        this.buildPanelData = buildPanelData;
    }

    // Getters
    public HallPanelData getHallPanelData() {
        return hallPanelData;
    }

    public BuildPanelData getBuildPanelData() {
        return buildPanelData;
    }

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
                "\n  Current Mode: " + currentMode +
                "\n}";
    }
}