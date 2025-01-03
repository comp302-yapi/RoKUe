package listeners;
import views.BasePanel;
import java.awt.event.KeyListener;

public abstract class BaseKeyListener implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;

    protected BaseKeyListener() {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        spacePressed = false;
    }
}
