//package tests;
//
//import enums.Hall;
//import managers.TileManagerForHall;
//import object.SuperObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class TileManagerForHallTest {
//    /**
//     * written by Zeynep Yorulmaz<br>
//     * {@link TileManagerForHall#convertToGrid(java.util.List)}<br>
//     * Maps a list of objects with world coordinates to their corresponding positions on the grid.<br>
//     * <br><br>
//     * Requires:<br>
//     * objects != null<br>
//     * All elements in objects are non-null and instances of {@link SuperObject}<br>
//     * Each object has valid worldX and worldY properties representing its coordinates<br>
//     * gridWorld is initialized and has dimensions that match the expected grid<br>
//     * <br><br>
//     * Modifies:<br>
//     * gridWorld by assigning objects to their respective grid positions<br>
//     * Overwrites gridWorld cells if multiple objects map to the same grid index<br>
//     * <br><br>
//     * Effects:<br>
//     * Populates gridWorld with objects from the provided list based on their worldX and worldY coordinates<br>
//     * Leaves positions in gridWorld as null if no object maps to them or if an object is out of bounds<br>
//     * Overwrites the grid cell with the last object in the list if multiple objects map to the same position<br>
//     */
//
//
//    private TileManagerForHall tileManager;
//
//    @BeforeEach
//    void setUp() {
//        System.out.println("Init");
//        tileManager = new TileManagerForHall(Hall.HallOfEarth, "/res/maps/hallOfAir.txt", 13, 14);
//    }
//
//    @Test
//    void testValidMapping() {
//        SuperObject obj1 = mock(SuperObject.class);
//        obj1.worldX = 384;
//        obj1.worldY = 144;
//
//        SuperObject obj2 = mock(SuperObject.class);
//        obj2.worldX = 432;
//        obj2.worldY = 192;
//
//        SuperObject obj3 = mock(SuperObject.class);
//        obj3.worldX = 480;
//        obj3.worldY = 240;
//
//        tileManager.objects.add(obj1);
//        tileManager.objects.add(obj2);
//        tileManager.objects.add(obj3);
//
//        tileManager.convertToGrid(tileManager.objects);
//
//        assertEquals(obj1, tileManager.gridWorld[1][1]);
//        assertEquals(obj2, tileManager.gridWorld[2][2]);
//        assertEquals(obj3, tileManager.gridWorld[3][3]);
//    }
//
//    @Test
//    void testOutOfBoundsObject() {
//        SuperObject obj1 = mock(SuperObject.class);
//        obj1.worldX = 500;
//        obj1.worldY = 323;
//
//        SuperObject obj2 = mock(SuperObject.class);
//        obj2.worldX = 384;
//        obj2.worldY = 144;
//
//        tileManager.objects.add(obj1);
//        tileManager.objects.add(obj2);
//
//        tileManager.convertToGrid(tileManager.objects);
//
//        assertNull(tileManager.gridWorld[0][0]); // No object placed here
//        assertEquals(obj2, tileManager.gridWorld[1][1]); // Valid object
//    }
//
//    @Test
//    void testOverlappingObjects() {
//        SuperObject obj1 = mock(SuperObject.class);
//        obj1.worldX = 384;
//        obj1.worldY = 144;
//
//        SuperObject obj2 = mock(SuperObject.class);
//        obj2.worldX = 384;
//        obj2.worldY = 144;
//
//        tileManager.objects.add(obj1);
//        tileManager.objects.add(obj2);
//
//        tileManager.convertToGrid(tileManager.objects);
//
//        assertEquals(obj2, tileManager.gridWorld[1][1]); // Last object should overwrite
//        assertTrue(tileManager.objects.contains(obj1));
//        assertTrue(tileManager.objects.contains(obj2));
//    }
//}
//
//
