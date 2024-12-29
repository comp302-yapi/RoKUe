package saveStates;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BuildPanelState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ObjectState> objects; // Objects on the BuildPanel

    public BuildPanelState(List<ObjectState> objects) {
        this.objects = objects;
    }

    public List<ObjectState> getObjects() { return objects; }
    public void setObjects(List<ObjectState> objects) { this.objects = objects; }
}