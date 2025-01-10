package views;

import managers.ViewManager;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HelpPanel extends NonPlayablePanel {

    public HelpPanel(ViewManager viewManager) {
        super(viewManager);

        // KeyListener ekle
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // ESC tuşuna basıldığında TitlePanel'e dön
                    getViewManager().switchTo("TitlePanel", true);
                }
            }
        });

        // Paneli odaklanabilir hale getir
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Ekran boyutlarına göre dinamik ayar
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Arka plan rengi
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, panelWidth, panelHeight);

        // Başlık için yazı ayarları
        g2.setFont(new Font("Arial", Font.BOLD, 36));
        g2.setColor(Color.WHITE);
        String title = "Help & Instructions";
        int titleX = (panelWidth - g2.getFontMetrics().stringWidth(title)) / 2;
        int titleY = 50;
        g2.drawString(title, titleX, titleY);

        // Yardım içerikleri
        String[][] helpContent = {
                {"Goal of the Game", "There are 4 halls. When you start a new game, place a minimum of 3 objects in each hall. Each object gives you 10 more seconds to exit the hall."},
                {"Monsters", "Archer: Shoots when you are 4 blocks within its range.\nFighter: Follows when you are 4 blocks within its range. Only close combat.\nWizard: Teleports the key from its current object to a new random one."},
                {"Enchantments", "Revel Map: Press R to use it. Creates a 4x4 yellow rectangle near the object with the key.\nCloak of Protection: Press C to use it. Hides you from the Archer monster for 2 seconds.\nHeart: Increases your life by 1.\nLuring Gem: Press B and a direction (W/A/S/D) to throw a gem to attract Fighter monsters."}
        };

        // İçerik için yazı ayarları
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        int startY = 100;
        int lineSpacing = 25; // Satırlar arası boşluk
        int sectionSpacing = 50; // Bölümler arası boşluk
        int margin = 20;

        for (String[] section : helpContent) {
            // Başlık
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.setColor(new Color(255, 215, 0)); // Altın sarısı başlık
            g2.drawString(section[0], margin, startY);

            // Açıklama
            g2.setFont(new Font("Arial", Font.PLAIN, 18));
            g2.setColor(Color.WHITE);

            int currentY = startY + lineSpacing;
            for (String line : section[1].split("\n")) {
                g2.drawString(line, margin, currentY);
                currentY += lineSpacing;
            }

            startY = currentY + sectionSpacing;
        }

        // Alt bilgi
        g2.setFont(new Font("Arial", Font.ITALIC, 16));
        g2.setColor(Color.LIGHT_GRAY);
        String exitInfo = "Press ESC to return to the main menu.";
        int exitInfoX = (panelWidth - g2.getFontMetrics().stringWidth(exitInfo)) / 2;
        g2.drawString(exitInfo, exitInfoX, panelHeight - 30);
    }
}
