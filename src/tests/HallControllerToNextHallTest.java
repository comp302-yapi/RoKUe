package tests;

import controllers.HallController;
import enums.BuildDirection;
import enums.Hall;
import managers.TileManagerForHall;
import managers.ViewManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import views.BuildPanel;

import javax.swing.*;

public class HallControllerToNextHallTest {
    /**
     * written by Bora Kaymakçıoğlu<br>
     * {@link controllers.HallController#toNextHall(TileManagerForHall, BuildDirection)}<br>
     * Moves to the next or previous hall based on the current hall and movement direction for the build mode.<br>
     * <br><br>
     * Requires:<br>
     * currentHall != null<br>
     * direction != null && is an element of BuildDirection enum<br>
     * currentHall.Hall is an element of Hall enum<br>
     * <br><br>
     * Modifies:<br>
     * currentHall.Hall if successful<br>
     * Hall validation error messages on build panel display if not successful<br>
     * <br><br>
     * Effects:<br>
     * return true if successful<br>
     * return false is not successful (i.e. hall is not valid before transition request)<br>
     * updates buildPanel.currentHall depending on the build direction<br>
     * calls the validateHall method of HallValidator for deciding the operation success<br>
     * Starts the game if BuildDirection is FORWARD in the final hall and operation is successful<br>
     */

    private BuildPanel _bp;
    private HallController _hc;

    @BeforeEach
    void setUp() {
        ViewManager _vm = new ViewManager(new JFrame());
        _bp = new BuildPanel(_vm);
        _hc = new HallController(_bp);
    }

    @Test
    void testInvalidForwardToNextHall() {
        Assertions.assertFalse(
            _hc.toNextHall(_bp.getCurrentHallManager(), BuildDirection.Forward),
            "toNextHall must fail due to not enough super objects"
        );

        Assertions.assertEquals(
            _bp.getCurrentHall(),
            Hall.HallOfEarth,
            "Current hall should remain as HallOfEarth"
        );
    }

    @Test
    void testInvalidBackwardsToNextHall() {
        Assertions.assertFalse(
            _hc.toNextHall(_bp.getCurrentHallManager(), BuildDirection.Backward),
            "toNextHall must fail due to being at the initial hall"
        );

        Assertions.assertEquals(
            _bp.getCurrentHall(),
            Hall.HallOfEarth,
            "Current hall should remain as HallOfEarth"
        );
    }

    @Test
    void testSuccessfulBackwardsToNextHall() {
        _bp.setCurrentHall(Hall.HallOfAir);

        Assertions.assertTrue(
            _hc.toNextHall(_bp.getCurrentHallManager(), BuildDirection.Backward),
            "toNextHall must fbe successful"
        );

        Assertions.assertEquals(
            _bp.getCurrentHall(),
            Hall.HallOfEarth,
            "Current hall should be HallOfEarth as we moved backwards"
        );
    }
}
