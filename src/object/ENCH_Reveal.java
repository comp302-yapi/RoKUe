package object;

public class ENCH_Reveal extends SuperObject {

    public ENCH_Reveal() {
        super("/res/objects/reveal.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Reveal";
        this.collision = false;
    }
}