package monster;

import containers.HallContainer;
import entity.*;
import enums.Hall;
import object.OBJ_LuringGem;
import object.SuperObject;
import views.BasePanel;
import views.BossPanel;
import views.HallPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class BOSS_Sorcerer extends Entity {

    BasePanel gp;
    public boolean spawned = false;
    public int countdown;
    public boolean attacking;
    private int fighterCounter;
    public int tempScreenY, tempScreenX;
    public int loadCountWalk, loadCountAttacking;

    public boolean firstAttackCall;
    private int attackTimer = 0;
    private int randomAttackInterval = 60 + new Random().nextInt(181);
    private int attackDuration = 48; // Frames for attack animation to complete
    private int attackAnimationCounter = 0;

    public SorcererProjectile sorcererProjectile;
    public Laser laser;

    private transient BufferedImage up_walking1, up_walking2, down_walking1, down_walking2;
    private transient BufferedImage left_walking1, left_walking2, right_walking1, right_walking2;
    private transient BufferedImage up_attacking1, up_attacking2, down_attacking1, down_attacking2;
    private transient BufferedImage left_attacking1, left_attacking2, right_attacking1, right_attacking2;

    private final String upWalking1Path = "/res/monster/orc_up_1";
    private final String upWalking2Path = "/res/monster/orc_up_2";
    private final String downWalking1Path = "/res/monster/orc_down_1";
    private final String downWalking2Path = "/res/monster/orc_down_2";
    private final String leftWalking1Path = "/res/monster/orc_left_1";
    private final String leftWalking2Path = "/res/monster/orc_left_2";
    private final String rightWalking1Path = "/res/monster/orc_right_1";
    private final String rightWalking2Path = "/res/monster/orc_right_2";

    private final String rightIdle1PathF = "/res/monster/SorcererRightIdle/tile000";
    private final String rightIdle2PathF = "/res/monster/SorcererRightIdle/tile001";
    private final String rightIdle3PathF = "/res/monster/SorcererRightIdle/tile002";
    private final String rightIdle4PathF = "/res/monster/SorcererRightIdle/tile003";
    private final String rightIdle5PathF = "/res/monster/SorcererRightIdle/tile004";

    private final String rightAttacking1PathF = "/res/monster/SorcererRightAttack/tile000";
    private final String rightAttacking2PathF = "/res/monster/SorcererRightAttack/tile001";
    private final String rightAttacking3PathF = "/res/monster/SorcererRightAttack/tile002";
    private final String rightAttacking4PathF = "/res/monster/SorcererRightAttack/tile003";
    private final String rightAttacking5PathF = "/res/monster/SorcererRightAttack/tile004";
    private final String rightAttacking6PathF = "/res/monster/SorcererRightAttack/tile005";
    private final String rightAttacking7PathF = "/res/monster/SorcererRightAttack/tile006";
    private final String rightAttacking8PathF = "/res/monster/SorcererRightAttack/tile007";


    public BOSS_Sorcerer(BasePanel gp) {
        super(gp);

        this.gp = gp;

        type = 2; // monster type
        name = "Sorcerer Monster";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 80;
        damage = 5;
        life = maxLife;

        // Solid area for collision detection
        solidArea.x = -30;
        solidArea.y = -10;
        solidArea.width = 50*2;
        solidArea.height = 48*2;

        solidAreaDefaultWidth = solidArea.width;
        solidAreaDefaultHeight = solidArea.height;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Attack Area
        attackArea.width = 36;
        attackArea.height = 36;

        getImage();
        getImageAttacking();
        chooseImage();
    }

    public void chooseImage() {

        if (attacking) {
            chooseImageAttacking();
        }
        else {
            chooseImageWalking();
        }
    }

    public void getImage() {
        up_walking1 = setup(rightIdle1PathF, 118 * 4, 118 * 4);
        up_walking2 = setup(rightIdle2PathF, 118 * 4, 118 * 4);
        up_walking3 = setup(rightIdle3PathF, 118 * 4, 118 * 4);
        up_walking4 = setup(rightIdle4PathF, 118 * 4, 118 * 4);
        up_walking5 = setup(rightIdle5PathF, 118 * 4, 118 * 4);

        down_walking1 = setup(rightIdle1PathF, 118 * 4, 118 * 4);
        down_walking2 = setup(rightIdle2PathF, 118 * 4, 118 * 4);
        down_walking3 = setup(rightIdle3PathF, 118 * 4, 118 * 4);
        down_walking4 = setup(rightIdle4PathF, 118 * 4, 118 * 4);
        down_walking5 = setup(rightIdle5PathF, 118 * 4, 118 * 4);

        left_walking1 = setup(rightIdle1PathF, 118 * 4, 118 * 4);
        left_walking2 = setup(rightIdle2PathF, 118 * 4, 118 * 4);
        left_walking3 = setup(rightIdle3PathF, 118 * 4, 118 * 4);
        left_walking4 = setup(rightIdle4PathF, 118 * 4, 118 * 4);
        left_walking5 = setup(rightIdle5PathF, 118 * 4, 118 * 4);

        right_walking1 = setup(rightIdle1PathF, 118 * 4, 118 * 4);
        right_walking2 = setup(rightIdle2PathF, 118 * 4, 118 * 4);
        right_walking3 = setup(rightIdle3PathF, 118 * 4, 118 * 4);
        right_walking4 = setup(rightIdle4PathF, 118 * 4, 118 * 4);
        right_walking5 = setup(rightIdle5PathF, 118 * 4, 118 * 4);
    }

    public void getImageAttacking() {
        right_attacking1 = setup(rightAttacking1PathF, 118*4, 118*4);
        right_attacking2 = setup(rightAttacking2PathF, 118*4, 118*4);
        right_attacking3 = setup(rightAttacking3PathF, 118*4, 118*4);
        right_attacking4 = setup(rightAttacking4PathF, 118*4, 118*4);
        right_attacking5 = setup(rightAttacking5PathF, 118*4, 118*4);
        right_attacking6 = setup(rightAttacking6PathF, 118*4, 118*4);
        right_attacking7 = setup(rightAttacking7PathF, 118*4, 118*4);
        right_attacking8 = setup(rightAttacking8PathF, 118*4, 118*4);

        left_attacking1 = setup(rightAttacking1PathF, 118*4, 118*4);
        left_attacking2 = setup(rightAttacking2PathF, 118*4, 118*4);
        left_attacking3 = setup(rightAttacking3PathF, 118*4, 118*4);
        left_attacking4 = setup(rightAttacking4PathF, 118*4, 118*4);
        left_attacking5 = setup(rightAttacking5PathF, 118*4, 118*4);
        left_attacking6 = setup(rightAttacking6PathF, 118*4, 118*4);
        left_attacking7 = setup(rightAttacking7PathF, 118*4, 118*4);
        left_attacking8 = setup(rightAttacking8PathF, 118*4, 118*4);

        up_attacking1 = setup(rightAttacking1PathF, 118*4, 118*4);
        up_attacking2 = setup(rightAttacking2PathF, 118*4, 118*4);
        up_attacking3 = setup(rightAttacking3PathF, 118*4, 118*4);
        up_attacking4 = setup(rightAttacking4PathF, 118*4, 118*4);
        up_attacking5 = setup(rightAttacking5PathF, 118*4, 118*4);
        up_attacking6 = setup(rightAttacking6PathF, 118*4, 118*4);
        up_attacking7 = setup(rightAttacking7PathF, 118*4, 118*4);
        up_attacking8 = setup(rightAttacking8PathF, 118*4, 118*4);

        down_attacking1 = setup(rightAttacking1PathF, 118*4, 118*4);
        down_attacking2 = setup(rightAttacking2PathF, 118*4, 118*4);
        down_attacking3 = setup(rightAttacking3PathF, 118*4, 118*4);
        down_attacking4 = setup(rightAttacking4PathF, 118*4, 118*4);
        down_attacking5 = setup(rightAttacking5PathF, 118*4, 118*4);
        down_attacking6 = setup(rightAttacking6PathF, 118*4, 118*4);
        down_attacking7 = setup(rightAttacking7PathF, 118*4, 118*4);
        down_attacking8 = setup(rightAttacking8PathF, 118*4, 118*4);
    }

    public void chooseImageAttacking() {

        up1 = up_attacking1;
        up2 = up_attacking2;
        up3 = up_attacking3;
        up4 = up_attacking4;
        up5 = up_attacking5;
        up6 = up_attacking6;
        up7 = up_attacking7;
        up8 = up_attacking8;

        down1 = down_attacking1;
        down2 = down_attacking2;
        down3 = down_attacking3;
        down4 = down_attacking4;
        down5 = down_attacking5;
        down6 = down_attacking6;
        down7 = down_attacking7;
        down8 = down_attacking8;

        left1 = left_attacking1;
        left2 = left_attacking2;
        left3 = left_attacking3;
        left4 = left_attacking4;
        left5 = left_attacking5;
        left6 = left_attacking6;
        left7 = left_attacking7;
        left8 = left_attacking8;

        right1 = right_attacking1;
        right2 = right_attacking2;
        right3 = right_attacking3;
        right4 = right_attacking4;
        right5 = right_attacking5;
        right6 = right_attacking6;
        right7 = right_attacking7;
        right8 = right_attacking8;
    }

    public void chooseImageWalking() {

        up1 = up_walking1;
        up2 = up_walking2;
        up3 = up_walking3;
        up4 = up_walking4;
        up5 = up_walking5;

        down1 = down_walking1;
        down2 = down_walking2;
        down3 = down_walking3;
        down4 = down_walking4;
        down5 = down_walking5;

        left1 = left_walking1;
        left2 = left_walking2;
        left3 = left_walking3;
        left4 = left_walking4;
        left5 = left_walking5;

        right1 = right_walking1;
        right2 = right_walking2;
        right3 = right_walking3;
        right4 = right_walking4;
        right5 = right_walking5;

    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        getImage();
        getImageAttacking();
    }

    public boolean isAttackingFighter() {
        return attacking;
    }

    public void setAction() {
        if (!spawned) {
            Random random = new Random();
            worldX = random.nextInt(BasePanel.worldWidth);
            worldY = random.nextInt(BasePanel.worldHeight);
            spawned = true;
        }

        boolean hitPlayer = panel.getCollisionChecker().checkPlayer(this);

        if (hitPlayer && !panel.getPlayer().invincible) {
            panel.getPlayer().damagePlayer(damage);
        }

        if (attacking) {
            attackAnimationCounter++;
            if (attackAnimationCounter >= attackDuration) {
                attacking = false;
                attackAnimationCounter = 0;
            }
        } else {
            attackTimer++;
            if (attackTimer >= randomAttackInterval) {
                firstAttackCall = true;
                attacking = true;
                attackTimer = 0;
                randomAttackInterval = 60 + new Random().nextInt(181); // New random interval
            } else {
                firstAttackCall = false;
                actionLockCounter++;

                if (actionLockCounter == 120) {
                    Random random = new Random();
                    int i = random.nextInt(100) + 1;

                    direction = "right";

                    if (checkLuringGem()) direction = determineDirectionLuringGem();
                    actionLockCounter = 0;
                }
            }
        }
    }

    public BufferedImage getAnimateImage() {
        return image;
    }

    public int getBufferX() {
        return tempScreenX;
    }

    public int getBufferY() {
        return tempScreenY;
    }

    private String determineDirection() {
        int playerX = panel.getPlayer().screenX;
        int playerY = panel.getPlayer().screenY;

        if (Math.abs(playerX - worldX) > Math.abs(playerY - worldY)) {
            return playerX > worldX ? "right" : "right";
        } else {
            return playerY > worldY ? "right" : "right";
        }
    }

    public boolean checkLuringGem() {
        boolean checker = false;

        if (panel instanceof HallPanel) {
            switch (((HallPanel) panel).currentHall) {
                case HallOfEarth -> {
                    for (SuperObject obj : HallContainer.getHallOfEarth().objects) {
                        if (obj instanceof OBJ_LuringGem) {
                            checker = true;
                            break;
                        }
                    }
                }
                case HallOfFire -> {
                    for (SuperObject obj : HallContainer.getHallOfFire().objects) {
                        if (obj instanceof OBJ_LuringGem) {
                            checker = true;
                            break;
                        }
                    }
                }
                case HallOfWater -> {
                    for (SuperObject obj : HallContainer.getHallOfWater().objects) {
                        if (obj instanceof OBJ_LuringGem) {
                            checker = true;
                            break;
                        }
                    }
                }
                case HallOfAir -> {
                    for (SuperObject obj : HallContainer.getHallOfAir().objects) {
                        if (obj instanceof OBJ_LuringGem) {
                            checker = true;
                            break;
                        }
                    }
                }
                default -> checker = false;
            }
        }
        return checker;
    }

    public String determineDirectionLuringGem() {
        if (panel instanceof HallPanel) {
            for (SuperObject obj : HallContainer.getHallOfEarth().objects) {
                if (obj instanceof OBJ_LuringGem luringGem) {
                    if (Math.abs(luringGem.worldX - worldX) > Math.abs(luringGem.worldY - worldY)) {
                        return luringGem.worldX > worldX ? "right" : "left";
                    } else {
                        return luringGem.worldY > worldY ? "down" : "up";
                    }
                }
            }
        }
        return direction;
    }

    public void animate() {
        fighterCounter++;
        if (fighterCounter > 6) {
            if (isAttackingFighter()) {
                if (spriteNum == 4) {
                    attack1();
                }
                if (firstAttackCall) {
                    spriteNum = 1;
                    firstAttackCall = false;
                }
                if (spriteNum <= 8) {
                    spriteNum++;
                } else {
                    spriteNum = 1;
                }
                // Save current values
                int currentWorldX = worldX;
                int currentWorldY = worldY;
                int solidAreaWidth = solidArea.width;
                int solidAreaHeight = solidArea.height;
                this.stopMovingAttacking = true;

                // Adjust for attack animation
                switch (direction) {
                    case "up" -> worldY -= attackArea.height;
                    case "down" -> worldY += attackArea.height;
                    case "left" -> worldX -= attackArea.width;
                    case "right" -> worldX += attackArea.width;
                }

                solidArea.width = attackArea.width;
                solidArea.height = attackArea.height;

                boolean playerHitAttackCheck = panel.getCollisionChecker().checkPlayer(this);
                if (playerHitAttackCheck && !panel.getPlayer().invincible) {
                    panel.getPlayer().damagePlayer(damage);
                }

                // Restore previous values
                worldX = currentWorldX;
                worldY = currentWorldY;
                solidArea.width = solidAreaWidth;
                solidArea.height = solidAreaHeight;
                this.stopMovingAttacking = false;
            } else {
                if (spriteNum < 5) {
                    spriteNum++;
                } else {
                    spriteNum = 1;
                }
            }
            fighterCounter = 0;
        }
    }

    public void animateDirection() {
        tempScreenX = worldX;
        tempScreenY = worldY;

        if (!isAttackingFighter()) {
            switch (direction) {
                case "up" -> {
                    image = switch (spriteNum) {
                        case 1 -> up1;
                        case 2 -> up2;
                        case 3 -> up3;
                        case 4 -> up4;
                        case 5 -> up5;
                        default -> up1;
                    };
                }
                case "down" -> {
                    image = switch (spriteNum) {
                        case 1 -> down1;
                        case 2 -> down2;
                        case 3 -> down3;
                        case 4 -> down4;
                        case 5 -> down5;
                        default -> down1;
                    };
                }
                case "left" -> {
                    image = switch (spriteNum) {
                        case 1 -> left1;
                        case 2 -> left2;
                        case 3 -> left3;
                        case 4 -> left4;
                        case 5 -> left5;
                        default -> left1;
                    };
                }
                case "right" -> {
                    image = switch (spriteNum) {
                        case 1 -> right1;
                        case 2 -> right2;
                        case 3 -> right3;
                        case 4 -> right4;
                        case 5 -> right5;
                        default -> right1;
                    };
                }
            }
        }

        if (isAttackingFighter()) {
            image = switch (spriteNum) {
                case 1 -> right1;
                case 2 -> right2;
                case 3 -> right3;
                case 4 -> right4;
                case 5 -> right5;
                case 6 -> right6;
                case 7 -> right7;
                case 8 -> right8;
                default -> right1;
            };
        }
    }

    private int calculateDistanceToPlayer() {
        int playerX = panel.getPlayer().screenX;
        int playerY = panel.getPlayer().screenY;

        return (int) Math.sqrt(
                Math.pow(worldX - playerX, 2) +
                        Math.pow(worldY - playerY, 2)
        );
    }

    private void attack1() {
        Random random = new Random();
        if (random.nextBoolean()) {
            System.out.println("Attack 1: Sorcerer Projectile");
            shootSorcererProjectile();
        } else {
            System.out.println("Attack 1: Laser");
            shootLaser();
        }
    }

    public void shootSorcererProjectile() {
        attacking = true;
        this.collisionOn = true;

        String[] directions = {"farLeft", "closeLeft", "down", "closeRight", "farRight"};

        for (String sorcererProjectileDirection : directions) {
            SorcererProjectile sorcererProjectile = new SorcererProjectile(panel, worldX, worldY, sorcererProjectileDirection);
            if (panel instanceof BossPanel bossPanel) {
                bossPanel.addSorcererProjectile(sorcererProjectile);
            }
        }
    }

    public void shootLaser() {
        Random random = new Random();
        int randomY = random.nextInt(600 - 288 + 1) + 288; // Random Y between 288 and 816

        Laser laser = new Laser(panel, worldX, randomY, "right");
        if (panel instanceof BossPanel bossPanel) {
            bossPanel.addLaser(laser);
        }
    }

    public void drawBossHealthBar(Graphics2D g2, BOSS_Sorcerer boss) {
        int barWidth = 400;
        int barHeight = 20;
        int x = 0;

        if (panel instanceof BossPanel bossPanel) {
            x = (bossPanel.getWidth() - barWidth) / 2;
            x += 10;
        }
        int y = 72;

        double healthPercentage = (double) boss.life / boss.maxLife;
        int currentHealthWidth = (int) (barWidth * healthPercentage);

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, y, barWidth, barHeight);

        g2.setColor(Color.RED);
        g2.fillRect(x, y, currentHealthWidth, barHeight);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(x, y, barWidth, barHeight);
    }

}