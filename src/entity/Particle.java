package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import javax.imageio.ImageIO;

import monster.MON_Archer;
import monster.MON_Fighter;
import utils.ImageUtils;
import views.BasePanel;
import views.HallPanel;
import views.HomePanel;

public class Particle extends Entity {

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(HallPanel hallPanel, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(hallPanel);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        worldX = generator.worldX;
        worldY = generator.worldY;
    }
}