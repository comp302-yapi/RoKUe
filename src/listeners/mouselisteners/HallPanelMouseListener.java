package listeners.mouselisteners;

import containers.HallContainer;
import controllers.HallController;
import enums.Hall;
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

        System.out.println("Clicking mouse");

        // Use the hallController instance to call getObjectSelectedInHall
        SuperObject clickedObject;

        switch (hallPanel.currentHall) {
            case Hall.HallOfWater ->
                    clickedObject = HallController.getObjectSelectedInHall(HallContainer.getHallOfWater(), mouseX, mouseY);
            case Hall.HallOfAir ->
                    clickedObject = HallController.getObjectSelectedInHall(HallContainer.getHallOfAir(), mouseX, mouseY);
            case Hall.HallOfFire ->
                    clickedObject = HallController.getObjectSelectedInHall(HallContainer.getHallOfFire(), mouseX, mouseY);
            case Hall.HallOfEarth ->
                    clickedObject = HallController.getObjectSelectedInHall(HallContainer.getHallOfEarth(), mouseX, mouseY);
            default ->
                    throw new IllegalStateException("Unexpected value: " + hallPanel.currentHall);
        }

        if (clickedObject != null) {
            System.out.println("Clicked Obj: " + clickedObject.name);
            boolean foundRune = clickedObject.interact(hallPanel); // Call interact method to handle rune detection
            if (foundRune) {
                switch (hallPanel.currentHall) {
                    case Hall.HallOfWater ->
                            HallContainer.getHallOfWater().openDoor();
                    case Hall.HallOfAir ->
                            HallContainer.getHallOfAir().openDoor();
                    case Hall.HallOfFire ->
                            HallContainer.getHallOfFire().openDoor();
                    case Hall.HallOfEarth ->
                            HallContainer.getHallOfEarth().openDoor();
                    default ->
                            throw new IllegalStateException("Unexpected value: " + hallPanel.currentHall);
                }
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