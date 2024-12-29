package saveStates;

import java.io.Serializable;

public class MonsterState implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private int x;
    private int y;
    private int health;

    public MonsterState(String type, int x, int y, int health) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.health = health;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
}