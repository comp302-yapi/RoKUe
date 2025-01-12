package views;

import managers.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HelpPanel extends NonPlayablePanel {

    public HelpPanel(ViewManager viewManager) {
        super(viewManager);

        // Main panel setup
        setLayout(new BorderLayout());

        // Create content panel with BoxLayout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(30, 30, 30));

        // Add help content
        addHelpContent(contentPanel);

        // Wrap content panel in a scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add scroll pane to main panel
        add(scrollPane, BorderLayout.CENTER);

        // Add KeyListener for ESC key
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    getViewManager().switchTo("TitlePanel", true);
                }
            }
        });

        // Make panel focusable
        setFocusable(true);
        requestFocusInWindow();
    }

    private void addHelpContent(JPanel contentPanel) {
        String[][] helpContent = {
                {"Goal of the Game", "There are 4 halls. When you start a new game, place a minimum of 3 objects in each hall. Each object gives you 10 more seconds to exit the hall."},
                {"Monsters", "Archer: Shoots when you are 4 blocks within its range.\nFighter: Follows when you are 4 blocks within its range. Only close combat.\nWizard: Teleports the key from its current object to a new random one."},
                {"Enchantments", "Revel Map: Press R to use it. Creates a 4x4 yellow rectangle near the object with the key.\nCloak of Protection: Press C to use it. Hides you from the Archer monster for 2 seconds.\nHeart: Increases your life by 1.\nLuring Gem: Press B and a direction (W/A/S/D) to throw a gem to attract Fighter monsters."},
                {"SuperPowers", "Reach Level 2 to use Ground Slam. \n Reach Level 5 to use Fire Ball. \n Reach Level 10 to use Lightning. \n Press G to unlock everything "},
                {"Key Bindings", """
                - Arrows: Move Up/Left/Down/Right
                - Space: Attack
                - R: Use Revel Map
                - P: Use Cloak of Protection
                - B: Use Luring Gem
                - Mouse Left Click: Pick Up Enchantment
                - 1/2/3: Activate powers (Ground Slam (Level 2) /Fire Ball (Level 5) /Lightning (Level 10))
                - G: Max Level Cheat
                - O: Save Game
                - Enter: Switch to Build Mode from Home Screen
                - Enter: Choose in Title Screen
                - ESC: Return to main menu
                """}
        };

        for (String[] section : helpContent) {
            // Section title
            JLabel titleLabel = new JLabel(section[0]);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(new Color(255, 215, 0)); // Gold color
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(titleLabel);

            // Section description
            String[] lines = section[1].split("\n");
            for (String line : lines) {
                JLabel lineLabel = new JLabel(line);
                lineLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                lineLabel.setForeground(Color.WHITE);
                lineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                contentPanel.add(lineLabel);
            }

            // Add spacing between sections
            contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        // Footer
        JLabel footerLabel = new JLabel("Press ESC to return to the main menu.");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        footerLabel.setForeground(Color.LIGHT_GRAY);
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(footerLabel);
    }
}