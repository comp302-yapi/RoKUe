package listeners.keylisteners;

import listeners.BaseKeyListener;
import managers.SaveManager;
import saveStates.GameState;
import views.BuildPanel;
import views.HallPanel;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Arrays;

public class HallPanelKeyListener extends BaseKeyListener implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Add this for versioning

    private final HallPanel hallPanel;
    public boolean monsterSpawn = false;
    boolean isLureModeActive = false;
    private SaveManager saveManager;

    public HallPanelKeyListener(HallPanel hallPanel) {
        super();
        this.hallPanel = hallPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_O) {
            // Create a new GameState instance
            GameState newGameState = new GameState();

            // Example: Set initial player position, time, lives, etc.
            newGameState.setHeroX(hallPanel.getPlayer().screenX); // Initial X position
            newGameState.setHeroY(hallPanel.getPlayer().screenY); // Initial Y position
            newGameState.setTimeRemaining(300); // Initial time remaining (in seconds)
            newGameState.setLivesRemaining(hallPanel.getPlayer().life); // Initial number of lives

            newGameState.setHallPanelState(hallPanel);

            // Optionally, print out confirmation
            System.out.println("New game state created: " + newGameState);

            // Save the game state to a file
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game_save.ser"))) {
                oos.writeObject(newGameState);
                System.out.println("Game saved successfully.");
            } catch (Exception a) {
                a.printStackTrace();
                System.out.println("Failed to save the game.");
            }
        }

        if (code == KeyEvent.VK_L) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_save.ser"))) {
                GameState savedState = (GameState) ois.readObject();

                // Print out the contents of the saved game
                System.out.println("Hero X: " + savedState.getHeroX());
                System.out.println("Hero Y: " + savedState.getHeroY());
                System.out.println("Time Remaining: " + savedState.getTimeRemaining());
                System.out.println("Lives Remaining: " + savedState.getLivesRemaining());

                if (savedState.getHallPanelState() != null) {
                    System.out.println("HallPanel State: ");
                    System.out.println("  Monsters: " + Arrays.toString(savedState.getHallPanelState().getMonsters()));
                    System.out.println("  Enchantments: " + savedState.getHallPanelState().tileM.enchantments);
                    System.out.println("  Objects: " + savedState.getHallPanelState().tileM.objects);

                }

                if (savedState.getBuildPanelState() != null) {
                    System.out.println("BuildPanel State: ");
                }


            } catch (Exception c) {
                c.printStackTrace();
            }
        }

        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;

        }

        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        
        if (code == KeyEvent.VK_E) {
        	monsterSpawn = true;
        }

        if (code == KeyEvent.VK_R) {
            hallPanel.checkInventoryForReveal();
        }

        if (code == KeyEvent.VK_C) {
            hallPanel.checkInventoryForCloak();
        }

        if (code == KeyEvent.VK_B) {
            boolean checker = hallPanel.checkInventoryForLuringGem();
            if (checker) {
                isLureModeActive = true;
            }
        } else if (isLureModeActive) {
            switch (code) {
                case KeyEvent.VK_A -> {
                    isLureModeActive = false;
                    hallPanel.throwGem("Left");

                }
                case KeyEvent.VK_D -> {
                    hallPanel.throwGem("Right");
                    isLureModeActive = false;
                }
                case KeyEvent.VK_W -> {
                    hallPanel.throwGem("Up");
                    isLureModeActive = false;
                }
                case KeyEvent.VK_S -> {
                    hallPanel.throwGem("Down");
                    isLureModeActive = false;
                }
                default -> System.out.println("Invalid direction! Use A, D, W, or S.");
            }
        }

        if (code == KeyEvent.VK_P) {
            hallPanel.setPaused(!hallPanel.isPaused());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
