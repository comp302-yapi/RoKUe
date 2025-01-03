package object;

import entity.Entity;
import entity.Player;
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

public class PRTCL_Lightning extends SuperParticle {
    public PRTCL_Lightning(ArrayList<BufferedImage> lightningAnimation, Entity entityToFollow) {
        super(lightningAnimation);

        this.animationSpeed = 5;
        setFollowEntity(entityToFollow);
    }

    public void draw(Graphics2D g2, HallPanel panel) {
        if (animationFrames != null && currentFrame < animationFrames.size()) {
            if (followEntity instanceof Player player) {
                g2.drawImage(animationFrames.get(currentFrame), player.screenX + 5, player.screenY, BasePanel.tileSize*4, BasePanel.tileSize*4, null);
            } else {
                g2.drawImage(animationFrames.get(currentFrame), this.followEntity.worldX - 76, this.followEntity.worldY - 144, BasePanel.tileSize*4, BasePanel.tileSize*4, null);
            }
        }
    }

    public void loadFrames() {
        if (animationFrames != null) {
            animationFrames.clear(); // Clear existing frames
        }
        try {
            for (int i = 1; i < 5; i++) {
                String filePath = "src/res/particles/Lightning/lightning_skill4_frame" + i + ".png";
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

