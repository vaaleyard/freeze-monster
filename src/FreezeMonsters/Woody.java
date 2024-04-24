package FreezeMonsters;
import spriteframework.Commons;
import spriteframework.sprite.Player;
import java.awt.Image;



import javax.swing.*;
import java.awt.event.KeyEvent;

public class Woody extends Player {

    int last_mov;

    public Woody(){
        this.last_mov = KeyEvent.VK_UP;
        loadImage();
        getImageDimensions();
        resetState();
    }
    protected void loadImage() {
        ImageIcon ii = new ImageIcon("ImagesFreezeMonster/woody.png");
        Image originalImage = ii.getImage();
        Image scaledImage = originalImage.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }


    public void act() {

        x += dx;
        y += dy;

        if (x <= 2) {

            x = 2;
        }

        if (x >= Commons.BOARD_WIDTH - 2 * width) {

            x = Commons.BOARD_WIDTH - 2 * width;
        }

        if (y <= 2) {

            y = 2;
        }

        if (y >= Commons.BOARD_WIDTH - 2 * width) {

            y = Commons.BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {

            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {

            dy = 2;
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        if(key != KeyEvent.VK_SPACE){
            last_mov = key;
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;

        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {

            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {

            dy = 0;
        }
    }
    private void resetState() {

        setX(FreezeMonsters.Commons.WOODY_INIT_X);
        setY(FreezeMonsters.Commons.WOODY_INIT_Y);
    }

    public int getLast_mov() {
        return last_mov;
    }

    public void setLast_mov(int last_mov) {
        this.last_mov = last_mov;
    }
}
