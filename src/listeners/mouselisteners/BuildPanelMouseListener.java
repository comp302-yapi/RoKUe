package listeners.mouselisteners;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.Random;

import containers.HallContainer;
import controllers.HallController;
import enums.BuildDirection;
import listeners.BaseMouseListener;
import managers.TileManagerForHall;
import object.*;
import views.BasePanel;
import views.BuildPanel;

public class BuildPanelMouseListener extends BaseMouseListener implements MouseMotionListener, Serializable {

	private static final long serialVersionUID = 1L;

	private final BuildPanel buildPanel;
	private final HallController hallController;
	Random random = new Random();

	public BuildPanelMouseListener(BuildPanel buildPanel) {
		this.buildPanel = buildPanel;
		hallController = new HallController(buildPanel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		buildPanel.mouseClickedX = x;
		buildPanel.mouseClickedY = y;
		buildPanel.mouseDraggedX = x;
		buildPanel.mouseDraggedY = y;

		if (buildPanel.isInPreviousButton(x, y)) {
			buildPanel.isHallValidated = hallController.toNextHall(buildPanel.getCurrentHallManager(), BuildDirection.Backward);
		}

		else if (buildPanel.isInNextButton(x, y)) {
			buildPanel.isHallValidated = hallController.toNextHall(buildPanel.getCurrentHallManager(), BuildDirection.Forward);
		}

		else if (isInTopLeftRegion(x, y)) {

			System.out.println("Clicked Top Left");

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

	public int getRandomInRange(int min, int max) {
		return random.nextInt(max - min + 1) + min;
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



	// TODO:buradaki sayıları map in size ile kullanmak daha iyi olur diye düşünüyorum
    public boolean checkBorder(int x, int y) {
		return x - (int) (BasePanel.tileSize / 2) > 384 && x + (int) (BasePanel.tileSize / 2) < 960
				&& y - (int) (BasePanel.tileSize / 2) > 144 && y + (int) (BasePanel.tileSize / 2) < 772;
	}

	public boolean isInTopLeftRegion(int x, int y) {
		return x >= 0 && x <= 300 && y >= 0 && y <= 300;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		int x = e.getX();
		int y = e.getY();
		
		buildPanel.mouseDraggedX = x;
		buildPanel.mouseDraggedY = y;
		
		
			// If selected an object in map
			SuperObject objInMap = HallController.getObjectSelectedInHall(buildPanel.getCurrentHallManager(), x, y);
			
			if (objInMap != null) {
				// If selected an object on the map
				buildPanel.getCurrentHallManager().removeObject(objInMap);
				buildPanel.getCurrentHallManager().gridWorld[(x/48) - 7 ][(y/48) - 2] = null;

				for(int i = 0; i < buildPanel.objectsToDraw.size(); i++) {
					if(buildPanel.objectsToDraw.get(i).getClass() == objInMap.getClass()) {
						buildPanel.selectedIdx = i;
						buildPanel.selected = true;
					}
				}
			}
			else {
				// If selected an object in the menu
				for(int i = 0; i < buildPanel.objectsToDraw.size(); i++) {
					if(buildPanel.objectsToDraw.get(i) != null) {
						SuperObject obj = buildPanel.objectsToDraw.get(i);
						if (x > obj.worldX && x < obj.worldX + BasePanel.tileSize
								&& y > obj.worldY && y < obj.worldY + BasePanel.tileSize) {
							buildPanel.selectedIdx = i;
							buildPanel.selected = true;
						}
					}
				}
			}
		
		
		

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		int x = e.getX();
		int y = e.getY();

		if(buildPanel.selected) {
			if(checkBorder(x,y)) {
				
				SuperObject objInMap = HallController.getObjectSelectedInHall(buildPanel.getCurrentHallManager(), x, y);
				if (objInMap == null) {
					
					SuperObject newObj = null; 

					if(buildPanel.objectsToDraw.get(buildPanel.selectedIdx) instanceof OBJ_Barrel) {
						newObj = new OBJ_Barrel();
					}
					
					else if(buildPanel.objectsToDraw.get(buildPanel.selectedIdx) instanceof OBJ_Chest) {
						newObj = new OBJ_Chest();
					}

					else if(buildPanel.objectsToDraw.get(buildPanel.selectedIdx) instanceof OBJ_Cactus) {
						newObj = new OBJ_Cactus();
					}
					
					else if(buildPanel.objectsToDraw.get(buildPanel.selectedIdx) instanceof OBJ_Lantern) {
						newObj = new OBJ_Lantern();
					}
					
					else if(buildPanel.objectsToDraw.get(buildPanel.selectedIdx) instanceof OBJ_Chain) {
						newObj = new OBJ_Chain();
					}

					else if(buildPanel.objectsToDraw.get(buildPanel.selectedIdx) instanceof OBJ_Pot) {
						newObj = new OBJ_Pot();
					}

					if (newObj != null) {
						hallController.addObject(buildPanel.getCurrentHallManager(), newObj, x - (int)(BasePanel.tileSize / 2), y - (int)(BasePanel.tileSize / 2));
					}
					
				}

			}

			buildPanel.selected = false;
			buildPanel.selectedIdx = -1; // gerek yok ama dursun
		}
		
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		//System.out.println("Mouse dragged at X: " + e.getX() + " Y: " + e.getY());
		// TODO Auto-generated method stub
		
		
		if (buildPanel.selected) {
			buildPanel.mouseDraggedX = e.getX();
			buildPanel.mouseDraggedY = e.getY();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
		//System.out.println("Mouse moved at X: " + e.getX() + " Y: " + e.getY());
		
		/*
		if (buildPanel.selected) {
			buildPanel.mouseDraggedX = e.getX();
			buildPanel.mouseDraggedY = e.getY();
		}
		*/
	}

}
