package listeners.mouselisteners;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import listeners.BaseMouseListener;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.SuperObject;
import views.BasePanel;
import views.BuildPanel;

public class BuildPanelMouseListener extends BaseMouseListener implements  MouseMotionListener{
	
	
	private final BuildPanel buildPanel;
	
	public BuildPanelMouseListener(BuildPanel buildPanel) {
		this.buildPanel = buildPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		System.out.println("Mouse clicked at X: " + e.getX() + " Y: " + e.getY());
		int x = e.getX();
		int y = e.getY();
		if(buildPanel.selected) {
			buildPanel.mouseClickedX = x;
			buildPanel.mouseClickedY = y;
			if(checkBorder(x,y)) {
				
				SuperObject newObj = null; 
				
				if(buildPanel.objectsToDraw[buildPanel.selectedIdx] instanceof OBJ_Key) {
					newObj = new OBJ_Key();
				}
				
				else if(buildPanel.objectsToDraw[buildPanel.selectedIdx]instanceof OBJ_Chest) {
					newObj = new OBJ_Chest();
				}
				else if(buildPanel.objectsToDraw[buildPanel.selectedIdx] instanceof OBJ_Door) {
					newObj = new OBJ_Door();
				}
				
				
				if (newObj != null) {
					buildPanel.addObject(newObj, x - (int)(BasePanel.tileSize / 2), y - (int)(BasePanel.tileSize / 2));
					
					
				}
				buildPanel.selected = false;
				buildPanel.selectedIdx = -1; // gerek yok ama dursun
				
			}
			
			
			
			
		}
		else {
			
			for(int i = 0;i < buildPanel.objectsToDraw.length;i++) {
				if(buildPanel.objectsToDraw[i] != null) {
					SuperObject obj = buildPanel.objectsToDraw[i];
					
					if (x > obj.worldX && x < obj.worldX + BasePanel.tileSize
							&& y > obj.worldY && y < obj.worldY + BasePanel.tileSize) {
						buildPanel.selectedIdx = i;
						buildPanel.selected = true;
					}
					
				}
			}
			
		}
		
	
		
	}
	
    public boolean checkBorder(int x, int y) {
    	
    	
    	if(x - (int)(BasePanel.tileSize / 2) > 384 && x + (int)(BasePanel.tileSize / 2) < 960
    			&& y - (int)(BasePanel.tileSize / 2) > 144 && y + (int)(BasePanel.tileSize / 2) < 772) {
    		return true;
    	}
    	
    	return false;
    	
    }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//System.out.println("Mouse pressed at X: " + e.getX() + " Y: " + e.getY());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//System.out.println("Mouse released at X: " + e.getX() + " Y: " + e.getY());
		
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
		// TODO Auto-generated method stub
			
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	
	}

}
