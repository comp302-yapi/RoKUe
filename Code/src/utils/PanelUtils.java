package utils;

import views.BasePanel;

import java.awt.*;

public class PanelUtils {

    public static int getXForCenteredText(String text, BasePanel panel, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return panel.screenWidth/2 - length/2;
    }
}
