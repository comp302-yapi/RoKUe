package saveStates;

import java.io.Serializable;
import java.util.List;

public class HallPanelState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<MonsterState> monsters; // Monsters in the HallPanel
    private List<ObjectState> objects; // Objects in the HallPanel

    public HallPanelState(List<MonsterState> monsters, List<ObjectState> objects) {
        this.monsters = monsters;
        this.objects = objects;
    }

    public List<MonsterState> getMonsters() { return monsters; }
    public void setMonsters(List<MonsterState> monsters) { this.monsters = monsters; }

    public List<ObjectState> getObjects() { return objects; }
    public void setObjects(List<ObjectState> objects) { this.objects = objects; }
}