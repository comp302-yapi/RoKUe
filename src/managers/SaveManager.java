package managers;

import saveStates.BuildPanelState;
import saveStates.GameState;
import views.BuildPanel;
import views.HallPanel;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveManager {

    // Method to update the GameState with the BuildPanel state
    public void updateBuildPanelState(GameState gameState, BuildPanel buildPanel) {
    }

    // Method to update the GameState with the HallPanel state
    public void updateHallPanelState(GameState gameState, HallPanel hallPanel) {

    }

    // Save the updated GameState to a file
    public boolean saveToFile(GameState gameState, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(gameState);
            System.out.println("Game saved successfully to: " + filePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save the game to: " + filePath);
            return false;
        }
    }
}