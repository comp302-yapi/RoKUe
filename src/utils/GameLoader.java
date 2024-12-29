package utils;

import entity.GameState;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class GameLoader {
    public static GameState loadGame(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (GameState) ois.readObject(); // Deserialize GameState
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}