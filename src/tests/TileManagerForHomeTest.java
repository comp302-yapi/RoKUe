package tests;

import managers.TileManagerForHome;
import object.OBJ_Cactus;
import object.SuperObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import views.BasePanel;
import views.HomePanel;

import static org.junit.jupiter.api.Assertions.*;

class TileManagerForHomeTest {

    /**
     * Written by Burak Özkaya <br>
     * {@link tests.TileManagerForHomeTest}<br>
     * This test class validates the functionality of the {@link managers.TileManagerForHome} class,
     * focusing on mapping objects to a grid, handling out-of-bounds objects, and resolving overlapping object scenarios.<br>
     * <br><br>
     * Requires:<br>
     * {@link managers.TileManagerForHome} to correctly map {@link object.SuperObject} instances to a 2D grid
     * based on their world coordinates.<br>
     * {@link views.HomePanel} or any {@link views.BasePanel} instance to initialize the tile manager.<br>
     * A valid map resource file for the tile manager (e.g., "/res/maps/Home.txt").<br>
     * <br><br>
     * Modifies:<br>
     * {@link managers.TileManagerForHome#gridWorld} by placing objects into appropriate grid cells.<br>
     * {@link managers.TileManagerForHome#objects} during test execution to simulate different scenarios.<br>
     * <br><br>
     * Effects:<br>
     * {@link #testValidMapping()} confirms that objects with valid world coordinates are correctly placed in the grid.<br>
     * {@link #testOutOfBoundsObject()} ensures that objects with coordinates outside the grid bounds are not placed in the grid.<br>
     * {@link #testOverlappingObjects()} verifies that when objects overlap, the grid reflects the most recent object added.<br>
     */


    private TileManagerForHome tileManager;
    private BasePanel panel;

    @BeforeEach
    void setUp() {
        panel = new HomePanel(null); // Replace null with required parameters if necessary
        tileManager = new TileManagerForHome(panel, "/res/maps/Home.txt", 13, 14);
    }

    @Test
    void testValidMapping() {
        SuperObject obj1 = new OBJ_Cactus();
        obj1.worldX = 384;
        obj1.worldY = 144;

        SuperObject obj2 = new OBJ_Cactus();
        obj2.worldX = 432;
        obj2.worldY = 192;

        SuperObject obj3 = new OBJ_Cactus();
        obj3.worldX = 480;
        obj3.worldY = 240;

        tileManager.objects.add(obj1);
        tileManager.objects.add(obj2);
        tileManager.objects.add(obj3);

        tileManager.convertToGrid(tileManager.objects);

        assertEquals(obj1, tileManager.gridWorld[1][1]);
        assertEquals(obj2, tileManager.gridWorld[2][2]);
        assertEquals(obj3, tileManager.gridWorld[3][3]);
    }

    @Test
    void testOutOfBoundsObject() {
        SuperObject obj1 = new OBJ_Cactus();
        obj1.worldX = 600; // Out of bounds
        obj1.worldY = 600; // Out of bounds

        SuperObject obj2 = new OBJ_Cactus();
        obj2.worldX = 384;
        obj2.worldY = 144;

        tileManager.objects.add(obj1);
        tileManager.objects.add(obj2);

        tileManager.convertToGrid(tileManager.objects);

        assertNull(tileManager.gridWorld[0][0]); // No object here
        assertEquals(obj2, tileManager.gridWorld[1][1]); // Valid object
    }

    @Test
    void testOverlappingObjects() {
        SuperObject obj1 = new OBJ_Cactus();
        obj1.worldX = 384;
        obj1.worldY = 144;

        SuperObject obj2 = new OBJ_Cactus();
        obj2.worldX = 384;
        obj2.worldY = 144; // Same position as obj1

        tileManager.objects.add(obj1);
        tileManager.objects.add(obj2);

        tileManager.convertToGrid(tileManager.objects);

        assertEquals(obj2, tileManager.gridWorld[1][1]); // Last object should overwrite
        assertTrue(tileManager.objects.contains(obj1));
        assertTrue(tileManager.objects.contains(obj2));
    }
}