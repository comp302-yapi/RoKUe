//package tests;
//
//import managers.ViewManager;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import javax.swing.*;
//
//public class ViewManagerClassTest {
//    /**
//     * Test Class for  {@link ViewManager} class
//     *
//     * Overview
//     * AbstractFunction
//     * RepresentationInvariant
//     *
//     * setUp
//     * repOk
//     *
//     * testRepOk
//     * testAddPanel
//     * testSwitchPanel
//     * testIllegalSwitch
//     */
//
//    // Overview
//    // The ViewManager class is used to manage a GUI interface with single JFrame and associated JPanels.
//    // It organizes multiple JPanel views, defined in views package, to allowing dynamic switching
//    // It also manages game thread updates for continuous rendering and game state updates
//    // This class is used as a singleton instance initialized in main and used by its switchTo() method
//
//    // Abstract Function:
//    // AF(_vm) {...}
//    // _vm represents a test suite for ViewManager class tests
//    // _vm.frame is the main JFrame containing panels
//    // _vm.panels -> Map<String, JPanel> in the form of (key, panel instance)
//    // _vm.currentPanel -> JPanel to be displayed
//    // _vm.gameThread -> Thread responsible for rendering and game update
//
//    // Representation Invariant:
//    // _vm != null
//    // _vm.frame != null.
//    // _vm.panels != && _vm.panels.keys != null && _vm.panels.values != null
//    // _vm.currentPanel == null || isInstance(JPanel)
//    // _vm.gameThread != null
//
//    // Testing components
//    private ViewManager _viewManager;
//
//    @BeforeEach
//    void setUp() {
//        _viewManager = new ViewManager(new JFrame());
//    }
//
//    public boolean repOk() {
//        if (
//            _viewManager.getPanels() == null
//            || _viewManager.getPanels().containsKey(null)
//            || _viewManager.getPanels().containsValue(null)
//        ) return false;
//        if (
//            !(_viewManager.getCurrentPanel() == null || _viewManager.getCurrentPanel().getClass() == JPanel.class)
//            && !_viewManager.getPanels().containsValue(_viewManager.getCurrentPanel())
//        ) return false;
//        return _viewManager.getGameThread() != null;
//    }
//
//    @Test
//    void testRepOk() {
//        Assertions.assertTrue(
//            repOk(),
//            "ViewManager must satisfy its representation invariant after initialization."
//        );
//    }
//
//    @Test
//    void testAddPanel() {
//        JPanel testPanel = new JPanel();
//        _viewManager.addPanel("TestPanel", testPanel);
//        Assertions.assertTrue(
//            _viewManager.getPanels().containsKey("TestPanel"),
//            "TestPanel is not found in _viewManager's panels"
//        );
//
//        Assertions.assertSame(
//            _viewManager.getPanels().get("TestPanel").getClass(),
//            JPanel.class,
//            "TestPanel should be a JPanel");
//    }
//
//    @Test
//    void testSwitchPanel() {
//        JPanel testPanel = new JPanel();
//        _viewManager.addPanel("TestPanel", testPanel);
//        _viewManager.switchTo("TestPanel", true);
//
//        Assertions.assertNotNull(
//            _viewManager.getCurrentPanel(),
//            "Current Panel must not be null"
//        );
//
//        Assertions.assertEquals(
//            _viewManager.getCurrentPanel(),
//            testPanel,
//            "Switch to method does not switch to desired panel");
//    }
//
//    @Test
//    void testIllegalSwitch() {
//        Assertions.assertThrows(
//            IllegalArgumentException.class,
//            () -> _viewManager.switchTo("IllegalPanel", true),
//            "Switching to IllegalPanel is successful or does not throw desired exception");
//    }
//}
