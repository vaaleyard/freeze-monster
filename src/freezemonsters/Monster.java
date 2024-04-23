package FreezeMonsters;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import spriteframework.Commons;
import spriteframework.sprite.BadSprite;

import spriteframework.sprite.BadnessBoxSprite;

import java.util.Random;

import java.awt.Image;


public class Monster extends BadnessBoxSprite {

    private Gosma gosma;
    String Monster_CongImg;
    private boolean congelado;
    private int Timer = 10;

    public Monster(int x, int y) {
        initMonster(x, y);
        this.setCongelado(false);
    }

    private void initMonster(int x, int y) {

        this.x = x;
        this.y = y;
        gosma = new Gosma(x,y);

        Random random = new Random();
        int randomNum = random.nextInt(9) + 1;
        String monsterImg = "ImagesFreezeMonster/monster" + randomNum + ".png";
        Monster_CongImg = "ImagesFreezeMonster/monster" + randomNum + "bg.png";
        ImageIcon ii = new ImageIcon(monsterImg);
        Image originalImage = ii.getImage();
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }


    public void act() {

        x += dx;
        y += dy;

        if (x <= 2) {

            x = 2;
        }

        if (x >= spriteframework.Commons.BOARD_WIDTH - 2 * width) {

            x = spriteframework.Commons.BOARD_WIDTH - 2 * width;
        }

        if (y <= 2) {

            y = 2;
        }

        if (y >= Commons.BOARD_HEIGHT - 2 * width) {

            y = Commons.BOARD_HEIGHT - 2 * width;
        }
    }

    @Override
    public void die() {
        this.setCongelado(true);
        ImageIcon ii = new ImageIcon(this.Monster_CongImg);
        Image originalImage = ii.getImage();
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    public Gosma getGosma() {
        return gosma;
    }


    @Override
    public LinkedList<BadSprite> getBadnesses() {
        LinkedList<BadSprite> aBomb = new LinkedList<BadSprite>();
        aBomb.add(gosma);
        return aBomb;
    }

    public boolean isCongelado() {
        return congelado;
    }

    public void setCongelado(boolean congelado) {
        this.congelado = congelado;
    }

    public int getTimer() {
        return Timer;
    }

    public void setTimer(int timer) {
        Timer = timer;
    }
}
