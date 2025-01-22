package listeners.keylisteners;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Random;

import containers.HallContainer;
import entity.GameState;
import listeners.BaseKeyListener;
import managers.TileManagerForHall;
import object.*;
import views.BasePanel;
import views.BuildPanel;
import enums.Hall;
import views.HallPanel;

public class BuildPanelKeyListener extends BaseKeyListener implements Serializable {

	private static final long serialVersionUID = 1L;

	Random random = new Random();
	private final BuildPanel buildPanel;
	
	public BuildPanelKeyListener(BuildPanel buildPanel) {
		this.buildPanel = buildPanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_O) {
			GameState gs = buildPanel.getViewManager().collectGameState();
			gs.currentMode = "Build";
			buildPanel.getViewManager().saveGame("src/saves/newSave.ser", gs);
			System.out.println(gs);
		}

		if (code == KeyEvent.VK_D) {
			System.out.println("Pressed D");

			HallContainer.getHallOfAir().objects.clear();
			HallContainer.getHallOfEarth().objects.clear();
			HallContainer.getHallOfWater().objects.clear();
			HallContainer.getHallOfFire().objects.clear();

			placeObjectsInHall(HallContainer.getHallOfEarth(), 6, 432, 900, 200, 700);
			placeObjectsInHall(HallContainer.getHallOfAir(), 9, 432, 900, 200, 700);
			placeObjectsInHall(HallContainer.getHallOfWater(), 13, 432, 900, 200, 700);
			placeObjectsInHall(HallContainer.getHallOfFire(), 17, 432, 964, 146, 771);
		}
	}

	private SuperObject createNewObject() {
		Random random = new Random();
		int randomIndex = random.nextInt(5);
        return switch (randomIndex) {
            case 0 -> new OBJ_Cactus();
            case 1 -> new OBJ_Barrel();
            case 2 -> new OBJ_Pot();
            case 3 -> new OBJ_Chain();
            case 4 -> new OBJ_Lantern();
            default -> throw new IllegalStateException("Unexpected randomIndex: " + randomIndex);
        };
	}

	private void placeObjectsInHall(TileManagerForHall hall, int targetCount, int minX, int maxX, int minY, int maxY) {
		int currentCount = 0;

		while (currentCount <= targetCount) {
			SuperObject object = createNewObject();
			int x = getRandomInRange(minX, maxX) - (int) (BasePanel.tileSize / 2);
			int y = getRandomInRange(minY, maxY) - (int) (BasePanel.tileSize / 2);

			// Check if the position is valid (not overlapping)
			hall.addObject(object, x, y);
			currentCount++;

		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getRandomInRange(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}
	

}
