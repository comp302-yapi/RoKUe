package listeners.keylisteners;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Arrays;

import listeners.BaseKeyListener;
import object.SuperObject;
import saveStates.GameState;
import views.BuildPanel;
import enums.Hall;
import views.HallPanel;

public class BuildPanelKeyListener extends BaseKeyListener implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L; // Add this for versioning
	
	private final BuildPanel buildPanel;
	
	public BuildPanelKeyListener(BuildPanel buildPanel) {
		super();
		this.buildPanel = buildPanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_O) {
			// Create a new GameState instance
			GameState newGameState = new GameState();

			newGameState.setBuildPanelState(buildPanel);

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

				if (savedState.getBuildPanelState() != null) {
					System.out.println("BuildPanel State: ");
					System.out.println("  Objects: " + savedState.getBuildPanelState().getCurrentHallManager().objects);
				}

			} catch (Exception c) {
				c.printStackTrace();
			}
		}

        if (code == KeyEvent.VK_ENTER) {
        	
        	switch(buildPanel.getCurrentHall()) {
        	

        	case HallOfWater:
        		buildPanel.setCurrentHall(Hall.HallOfEarth);
        		break;
        	case HallOfEarth:
        		buildPanel.setCurrentHall(Hall.HallOfAir);
        		break;
        	case HallOfAir:
        		buildPanel.setCurrentHall(Hall.HallOfFire);
        		break;
        	case HallOfFire:
        		buildPanel.getViewManager().switchTo("GamePanel", true);
        		break;
        	
        	
        	}
        	
                

        }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
