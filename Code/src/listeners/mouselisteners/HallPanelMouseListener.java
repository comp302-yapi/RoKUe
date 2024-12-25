package listeners.mouselisteners;

import controllers.HallController;
import listeners.BaseMouseListener;
import managers.TileManagerForHall;
import object.SuperObject;
import views.HallPanel;

import java.awt.event.MouseEvent;

public class HallPanelMouseListener extends BaseMouseListener {

    private HallPanel hallPanel;
    private TileManagerForHall tileM;

    public HallPanelMouseListener(HallPanel hallPanel, TileManagerForHall tileM) {
        this.tileM = tileM;
        this.hallPanel = hallPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Use the hallController instance to call getObjectSelectedInHall
        SuperObject clickedObject = HallController.getObjectSelectedInHall(tileM, mouseX, mouseY);
        if (clickedObject != null) {
            clickedObject.interact(hallPanel); // Call interact method to handle rune detection
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