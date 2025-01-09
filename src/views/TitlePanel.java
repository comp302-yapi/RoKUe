package views;

import listeners.keylisteners.TitlePanelKeyListener;
import managers.ViewManager;
import managers.soundManager;
import utils.PanelUtils;
import java.awt.*;

public class TitlePanel extends NonPlayablePanel {

    private int commandNum;
    private final TitlePanelKeyListener titlePanelKeyListener;
    private soundManager soundManager;

    public TitlePanel(ViewManager viewManager) {
        super(viewManager);
        this.soundManager = soundManager.getInstance();
        setCommandNum(0);
        this.titlePanelKeyListener = new TitlePanelKeyListener(this);
        this.addKeyListener(titlePanelKeyListener);

        playMusic(0);
        managers.soundManager.getInstance().activeClips.put(1, soundManager.clip);
        System.out.println(managers.soundManager.getInstance().activeClips);
    }

    public void playMusic(int i) {
        soundManager.setFile(i);
        soundManager.play();
        soundManager.loop();
    }

    public void stopMusic() {
        soundManager.stop();
    }

    public int getCommandNum() {
        return commandNum;
    }

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }

    @Override
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawTitleScreen(g2, this);
    }

    public void drawTitleScreen(Graphics2D g2, BasePanel panel) {
        g2.setColor(new Color(62, 41, 52)); // Arka plan rengi
        g2.fillRect(0, 0, BasePanel.screenWidth, BasePanel.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        String titleText = "RoKue Like";
        int titleX = PanelUtils.getXForCenteredText(titleText, panel, g2); // Yatayda ortalama
        int titleY = BasePanel.screenHeight / 5; // Başlık ekranın üst kısmına hizalandı

        // Gölge
        g2.setColor(new Color(40, 35, 38)); // Gölge rengi
        g2.drawString(titleText, titleX + 3, titleY + 3);

        // Ana metin
        g2.setColor(new Color(26, 17, 23)); // Ana metin rengi
        g2.drawString(titleText, titleX, titleY);

        // BLUE BOY IMAGE
        /*
        int imageX = panel.screenWidth / 2 - (panel.tileSize * 2) / 2;
        int imageY = titleY + BasePanel.tileSize * 3;
        g2.drawImage(panel.monster[2].up1, imageX, imageY, panel.tileSize * 2, panel.tileSize * 2, null);
        */

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        String[] menuOptions = {"NEW GAME", "LOAD GAME", "QUIT GAME"};
        int totalMenuHeight = menuOptions.length * BasePanel.tileSize * 3; // Menü toplam yüksekliği
        int menuStartY = titleY + BasePanel.tileSize * 5; // Menü, başlığın altına hizalandı
        int lineHeight = BasePanel.tileSize * 3; // Menü seçenekleri arası mesafe

        for (int i = 0; i < menuOptions.length; i++) {
            String optionText = menuOptions[i];
            int optionX = PanelUtils.getXForCenteredText(optionText, panel, g2); // Yatayda ortalama
            int optionY = menuStartY + (i * lineHeight); // Her seçeneği eşit aralıklarla hizala

            // Seçenek işaretleme
            if (commandNum == i) {
                g2.drawString(">", optionX - BasePanel.tileSize, optionY);
            }

            g2.drawString(optionText, optionX, optionY);
        }
    }
}