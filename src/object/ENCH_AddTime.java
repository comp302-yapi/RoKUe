package object;

public class ENCH_AddTime extends SuperObject {

    public ENCH_AddTime() {
        super("/res/objects/ExtraTime.png", null, null); // Pass the image path for image1, others are null if unused
        name = "AddTime";
        this.collision = false;
    }
}