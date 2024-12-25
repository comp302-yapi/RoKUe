package listeners.mouselisteners;

import controllers.HallController;
import listeners.BaseMouseListener;
import managers.TileManagerForHall;
import object.SuperObject;
import views.HallPanel;

import java.awt.event.MouseEvent;

public class HallPanelMouseListener extends BaseMouseListener {

    private HallPanel hallPanel;

    public HallPanelMouseListener(HallPanel hallPanel, TileManagerForHall tileM) {
        this.hallPanel = hallPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Use the hallController instance to call getObjectSelectedInHall
        SuperObject clickedObject = HallController.getObjectSelectedInHall(hallPanel.getTileM(), mouseX, mouseY);
        if (clickedObject != null) {
            boolean foundRune = clickedObject.interact(hallPanel); // Call interact method to handle rune detection
            if (foundRune) {
                hallPanel.getTileM().openDoor();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}