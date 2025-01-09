/*import managers.TileManagerForHall;
import object.SuperObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import views.HallPanel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

class TileManagerForHallTest {

    private TileManagerForHall tileManager;

    @BeforeEach
    void setUp() {
        tileManager = new TileManagerForHall(HallPanel.currentHall, "/res/maps/hallOfAir.txt", 13, 14);
    }

    @Test
    void testValidMapping() {
        SuperObject obj1 = mock(SuperObject.class);
        obj1.worldX = 384;
        obj1.worldY = 144;

        SuperObject obj2 = mock(SuperObject.class);
        obj2.worldX = 432;
        obj2.worldY = 192;

        SuperObject obj3 = mock(SuperObject.class);
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
        SuperObject obj1 = mock(SuperObject.class);
        obj1.worldX = 0;
        obj1.worldY = 0;

        SuperObject obj2 = mock(SuperObject.class);
        obj2.worldX = 384;
        obj2.worldY = 144;

        tileManager.objects.add(obj1);
        tileManager.objects.add(obj2);

        tileManager.convertToGrid(tileManager.objects);

        assertNull(tileManager.gridWorld[0][0]); // No object placed here
        assertEquals(obj2, tileManager.gridWorld[1][1]); // Valid object
    }

    @Test
    void testOverlappingObjects() {
        SuperObject obj1 = mock(SuperObject.class);
        obj1.worldX = 384;
        obj1.worldY = 144;

        SuperObject obj2 = mock(SuperObject.class);
        obj2.worldX = 384;
        obj2.worldY = 144;

        tileManager.objects.add(obj1);
        tileManager.objects.add(obj2);

        tileManager.convertToGrid(tileManager.objects);

        assertEquals(obj2, tileManager.gridWorld[1][1]); // Last object should overwrite
        assertTrue(tileManager.objects.contains(obj1));
        assertTrue(tileManager.objects.contains(obj2));
    }
}

 */
