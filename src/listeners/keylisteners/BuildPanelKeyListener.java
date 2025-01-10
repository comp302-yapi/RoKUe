package listeners.keylisteners;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import entity.GameState;
import listeners.BaseKeyListener;
import views.BuildPanel;
import enums.Hall;
import views.HallPanel;

public class BuildPanelKeyListener extends BaseKeyListener implements Serializable {

	private static final long serialVersionUID = 1L;

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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
