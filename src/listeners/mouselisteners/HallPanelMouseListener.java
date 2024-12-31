package listeners.mouselisteners;

import controllers.HallController;
import listeners.BaseMouseListener;
import managers.TileManagerForHall;
import object.*;
import views.HallPanel;

import java.awt.event.MouseEvent;
import java.io.Serializable;

public class HallPanelMouseListener extends BaseMouseListener implements Serializable {

    private static final long serialVersionUID = 1L;

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

        SuperObject clickedEnchantment = HallController.getEnchantmentSelectedInHall(hallPanel.getTileM(), mouseX, mouseY);
        if (clickedEnchantment != null) {
            if (clickedEnchantment instanceof ENCH_Cloak || clickedEnchantment instanceof ENCH_Reveal || clickedEnchantment instanceof ENCH_LuringGem) {

                hallPanel.getPlayer().inventory.add(clickedEnchantment);

                int num = hallPanel.getPlayer().inventoryLength() - 1;

                int numForX = num % 5;
                int numForY = num / 5;

                clickedEnchantment.worldX = (48 * (23 + numForX)) + 16;
                clickedEnchantment.worldY = (48 * (7 + (numForY)));

                hallPanel.getTileM().enchantments.remove(clickedEnchantment);

            } else if (clickedEnchantment instanceof ENCH_ExtraLife && hallPanel.getPlayer().life < 6){

                hallPanel.getTileM().enchantments.remove(clickedEnchantment);
                hallPanel.getPlayer().life += 1;

            } else if (clickedEnchantment instanceof MISC_Gold) {
                hallPanel.getPlayer().gold += 1;
                hallPanel.playSE(1);
                System.out.println("Player Gold: " + hallPanel.getPlayer().gold);
                hallPanel.getTileM().enchantments.remove(clickedEnchantment);
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