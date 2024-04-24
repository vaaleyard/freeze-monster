package FreezeMonsters;

import spriteframework.sprite.BadSprite;
import java.awt.Image;
import javax.swing.*;

public class Gosma extends BadSprite {
    private boolean destroyed;

    public Gosma(int x, int y) {
        initGosma(x, y);
    }

    private void initGosma(int x, int y) {

        setDestroyed(true);

        this.x = x;
        this.y = y;

        String GosmaImg = "ImagesFreezeMonster/gosma.png";
        ImageIcon ii = new ImageIcon(GosmaImg);
        Image originalImage = ii.getImage();
        Image scaledImage = originalImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
