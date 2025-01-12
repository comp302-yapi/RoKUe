package tests;

import managers.TileManagerForBoss;
import object.OBJ_Cactus;
import object.SuperObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import views.BasePanel;
import views.BossPanel;

import static org.junit.jupiter.api.Assertions.*;

class TileManagerForBossTest {

    /**
     * Written by Ömer Taha Şişmanoğlu<br>
     * {@link tests.TileManagerForBossTest}<br>
     * This test class verifies the functionality of the {@link managers.TileManagerForBoss} class,
     * including mapping objects to a grid, handling out-of-bounds objects, and managing overlapping objects.<br>
     * <br><br>
     * Requires:<br>
     * {@link managers.TileManagerForBoss} to correctly map objects to a 2D grid based on their world coordinates.<br>
     * {@link views.BossPanel} or any {@link views.BasePanel} instance to initialize the tile manager.<br>
     * Objects used in testing must be instances of {@link object.SuperObject}.<br>
     * <br><br>
     * Modifies:<br>
     * {@link managers.TileManagerForBoss#gridWorld} by placing objects into the grid.<br>
     * {@link managers.TileManagerForBoss#objects} during testing to simulate different scenarios.<br>
     * <br><br>
     * Effects:<br>
     * {@link #testValidMapping()} ensures objects with valid coordinates are correctly placed in the grid.<br>
     * {@link #testOutOfBoundsObject()} verifies that objects outside the grid's bounds are not mapped to the grid.<br>
     * {@link #testOverlappingObjects()} ensures the grid handles overlapping objects and the most recent object is placed.<br>
     */


    private TileManagerForBoss tileManager;
    private BasePanel panel;

    @BeforeEach
    void setUp() {
        panel = new BossPanel(null);
        tileManager = new TileManagerForBoss(panel, "/res/maps/Boss.txt", 15, 15);
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
        obj1.worldX = 500;
        obj1.worldY = 500;

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
        obj2.worldY = 144;

        tileManager.objects.add(obj1);
        tileManager.objects.add(obj2);

        tileManager.convertToGrid(tileManager.objects);

        assertEquals(obj2, tileManager.gridWorld[1][1]);
        assertTrue(tileManager.objects.contains(obj1));
        assertTrue(tileManager.objects.contains(obj2));
    }
}