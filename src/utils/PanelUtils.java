package utils;

import views.BasePanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PanelUtils {

    public Font maruMonica;

    public static int getXForCenteredText(String text, BasePanel panel, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return BasePanel.screenWidth/2 - length/2; 
    }

    public Font getNewFont() {
        try {
            File fontFile = new File("src/res/fonts/x12y16pxMaruMonica.ttf");
            if (fontFile == null) {
                System.out.println("NULL!");
            }
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maruMonica;
    }

}
