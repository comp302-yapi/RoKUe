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
        obj1.worldX = 1000;
        obj1.worldY = 1000;

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