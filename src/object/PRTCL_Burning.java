package object;

import entity.Entity;
import views.BasePanel;
import views.HallPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class PRTCL_Burning extends SuperParticle {
    public PRTCL_Burning(ArrayList<BufferedImage> burningAnimation, Entity entityToFollow) {
        super(burningAnimation);

        this.animationSpeed = 15;
        setFollowEntity(entityToFollow); // Set the entity to follow
    }

    public void draw(Graphics2D g2, BasePanel panel) {
//        System.out.println("In draw");
        if (animationFrames != null && currentFrame < animationFrames.size() && followEntity.alive) {
            g2.drawImage(animationFrames.get(currentFrame), this.followEntity.worldX + 5, this.followEntity.worldY, BasePanel.tileSize, BasePanel.tileSize, null);
        }
    }

    public void loadFrames() {
        if (animationFrames != null) {
            animationFrames.clear(); // Clear existing frames
        }
        try {
            for (int i = 0; i < 7; i++) {
                String filePath = "src/res/particles/Burning/tile00" + i + ".png";
                BufferedImage frame = ImageIO.read(new File(filePath));
                if (animationFrames != null) {
                    animationFrames.add(frame);
                }
            }
            for (int i = 0; i < 7; i++) {
                String filePath = "src/res/particles/Burning/tile00" + i + ".png";
                BufferedImage frame = ImageIO.read(new File(filePath));
                if (animationFrames != null) {
                    animationFrames.add(frame);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

