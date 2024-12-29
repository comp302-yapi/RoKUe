package listeners.keylisteners;

import java.awt.event.KeyEvent;
import java.io.Serial;
import java.io.Serializable;

import listeners.BaseKeyListener;
import views.BuildPanel;
import enums.Hall;

public class BuildPanelKeyListener extends BaseKeyListener implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L; // Add this for versioning
	
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
