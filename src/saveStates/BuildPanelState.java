package saveStates;

import enums.Hall;
import object.SuperObject;
import views.BuildPanel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuildPanelState implements Serializable {
    private static final long serialVersionUID = 1L;
    public List<SuperObject> objects;
    public Hall currentHall;

    public BuildPanelState(BuildPanel buildPanel) {
        this.objects = new ArrayList<>(buildPanel.getCurrentHallManager().objects);
        this.currentHall = buildPanel.getCurrentHall();
    }
}