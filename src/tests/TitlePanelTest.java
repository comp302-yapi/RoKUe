//package tests;
//
//import listeners.keylisteners.TitlePanelKeyListener;
//import managers.TileManagerForHall;
//import managers.ViewManager;
//import object.SuperObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import views.TitlePanel;
//
//import javax.swing.*;
//import java.awt.event.KeyEvent;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
///**
// * written by Bekir Alegoz<br>
// * {@link TileManagerForHall#convertToGrid(java.util.List)}<br>
// * Maps a list of objects with world coordinates to their corresponding positions on the grid.<br>
// * <br><br>
// * Requires:<br>
// * objects != null<br>
// * All elements in objects are non-null and instances of {@link SuperObject}<br>
// * Each object has valid worldX and worldY properties representing its coordinates<br>
// * gridWorld is initialized and has dimensions that match the expected grid<br>
// * <br><br>
// * Modifies:<br>
// * gridWorld by assigning objects to their respective grid positions<br>
// * Overwrites gridWorld cells if multiple objects map to the same grid index<br>
// * <br><br>
// * Effects:<br>
// * Populates gridWorld with objects from the provided list based on their worldX and worldY coordinates<br>
// * Leaves positions in gridWorld as null if no object maps to them or if an object is out of bounds<br>
// * Overwrites the grid cell with the last object in the list if multiple objects map to the same position<br>
// */
//
//class TitlePanelTest {
//
//    private TitlePanel titlePanel;
//    private ViewManager mockViewManager;
//    private TitlePanelKeyListener keyListener;
//
//    @BeforeEach
//    void setUp() {
//        mockViewManager = mock(ViewManager.class);
//        titlePanel = new TitlePanel(mockViewManager);
//        keyListener = new TitlePanelKeyListener(titlePanel);
//
//    }
//
//    @Test
//    void testSwitchToHomePanel() {
//        // Arrange
//        TitlePanelKeyListener keyListener = new TitlePanelKeyListener(titlePanel);
//        KeyEvent enterKeyEvent = new KeyEvent(new JTextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
//
//        titlePanel.setCommandNum(0); // Set menu selection to "NEW GAME"
//
//        // Act
//        keyListener.keyPressed(enterKeyEvent);
//
//        // Assert
//        verify(mockViewManager).switchTo("HomePanel", true); // Ensure ViewManager switches to HomePanel
//    }
//
//    @Test
//    void testSwitchToLoadPanel() {
//        // Arrange
//        TitlePanelKeyListener keyListener = new TitlePanelKeyListener(titlePanel);
//        KeyEvent enterKeyEvent = new KeyEvent(new JTextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
//
//        titlePanel.setCommandNum(1); // Set menu selection to "LOAD GAME"
//
//        // Act
//        keyListener.keyPressed(enterKeyEvent);
//
//        // Assert
//        verify(mockViewManager).switchTo("LoadPanel", true); // Ensure ViewManager switches to LoadPanel
//    }
//
//    @Test
//    void testInvalidMenuNavigationDown() {
//        // Set commandNum to the last option
//        titlePanel.setCommandNum(2);
//
//        // Simulate pressing the "S" key to move down
//        KeyEvent keyEvent = new KeyEvent(titlePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
//        keyListener.keyPressed(keyEvent);
//
//        // Verify commandNum does not go beyond the last option
//        assertEquals(2, titlePanel.getCommandNum());
//    }
//
//
//}
