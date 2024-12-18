package listeners.mouselisteners;

import java.awt.event.MouseEvent;

import listeners.BaseMouseListener;
import views.BuildPanel;

public class BuildPanelMouseListener extends BaseMouseListener{
	
	
	private final BuildPanel buildPanel;
	
	public BuildPanelMouseListener(BuildPanel buildPanel) {
		this.buildPanel = buildPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("Mouse clicked at X: " + e.getX() + " Y: " + e.getY());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("Mouse pressed at X: " + e.getX() + " Y: " + e.getY());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("Mouse released at X: " + e.getX() + " Y: " + e.getY());
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
