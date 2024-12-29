package saveStates;
import java.io.Serializable;

public class ObjectState implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private int x;
    private int y;
    private int count;

    public ObjectState(String type, int x, int y, int count) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.count = count;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}