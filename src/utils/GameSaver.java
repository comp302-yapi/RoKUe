package utils;

import entity.GameState;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GameSaver {
    public static void saveGame(GameState gameState, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(gameState); // Serialize GameState to file
            System.out.println("Game saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}