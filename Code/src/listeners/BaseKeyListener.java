package listeners;
import views.BasePanel;
import java.awt.event.KeyListener;

public abstract class BaseKeyListener implements KeyListener {

    final BasePanel panel;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    protected BaseKeyListener(BasePanel panel) {
        this.panel = panel;

        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
    }
}
