package FreezeMonsters;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;


import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Player;

public class FreezeMonstersBoard extends AbstractBoard{

    //define sprites
    //private List<BadSprite> aliens;
    private Freeze_ray ray;
    Color customColor = new Color(51,212,106);
    private int ultima_tecla;

    // define global control vars
    private int directionX = -1;

    private int directionY = -1;
    private int frozen = 0;
    private String explImg = "imagesSpaceInvaders/explosion.png";

    public void doDrawing(Graphics g1) {

        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(customColor);
        g.fillRect(0, 0, d.width, d.height);


        if (inGame) {
            drawBadSprites(g);
            drawPlayers(g);
            drawOtherSprites(g);

        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    protected void createBadSprites() {  // create sprites
        for (int i = 0; i < 9; i++) {
            Monster monster = new Monster(Commons.MONSTER_INIT_X,Commons.MONSTER_INIT_Y);
                badSprites.add(monster);
        }
    }

    protected Woody createPlayer(){
        return new Woody();
    }

    protected void createOtherSprites() {
        ray = new Freeze_ray();
        ultima_tecla = KeyEvent.VK_UP;
    }

    private void drawRay(Graphics g) {

        if (ray.isVisible()) {
            g.drawImage(ray.getImage(), ray.getX(), ray.getY(), this);
        }
    }

    // Override
    protected void drawOtherSprites(Graphics g) {
        drawRay(g);
        g.setColor(Color.green);
    }

    protected void processOtherSprites(Player player, KeyEvent e) {
        int x = player.getX();
        int y = player.getY();

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {

            if (inGame) {
                if (!ray.isVisible()) {
                    ultima_tecla = ((Woody)player).getLast_mov();
                    ray = new Freeze_ray(x, y);
                }
            }
        }
    }

    protected void update() {

        if (frozen == Commons.NUMBER_OF_MONSTERS_TO_FREEZE) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        for (Player player: players)
            player.act();

        // shot
        if (ray.isVisible()) {

            int rayX = ray.getX();
            int rayY = ray.getY();

            for (BadSprite monster : badSprites) {

                int monsterX = monster.getX();
                int monsterY = monster.getY();

                Gosma gosma = ((Monster) monster).getGosma();
                int gosmaX = monster.getX();
                int gosmaY = monster.getY();

                if (monster.isVisible() && ray.isVisible() && !((Monster)monster).isCongelado()) {
                    if (rayX >= (monsterX)
                            && rayX <= (monsterX + Commons.MONSTER_WIDTH)
                            && rayY >= (monsterY)
                            && rayY <= (monsterY + Commons.MONSTER_HEIGHT)) {

                        monster.die();
                        frozen++;
                        ray.die();
                    }
                    if (rayX >= (gosmaX)
                            && rayX <= (gosmaX + Commons.GOSMA_WIDTH)
                            && rayY >= (gosmaY)
                            && rayY <= (gosmaY + Commons.GOSMA_HEIGHT)){

                        gosma.die();
                        gosma.setDestroyed(true);
                        ray.die();
                    }
                }

            }
            if(ultima_tecla == KeyEvent.VK_LEFT){
                rayX -= 4;
            }
            if(ultima_tecla == KeyEvent.VK_RIGHT){
                rayX += 4;
            }
            if(ultima_tecla == KeyEvent.VK_UP){
                rayY -= 4;
            }
            if(ultima_tecla == KeyEvent.VK_DOWN){
                rayY += 4;
            }
            if(rayY < 0 || rayX < 0 || rayY > Commons.BOARD_HEIGHT || rayX > Commons.BOARD_WIDTH - Commons.BORDER_RIGHT){
                ray.die();
            }
            else {
                ray.setX(rayX);
                ray.setY(rayY);
            }
        }

        // aliens
        for (BadSprite monster : badSprites) {
            if (!((Monster) monster).isCongelado()) {
                    Random rand = new Random();
                    if(((Monster) monster).getTimer() == 40) {
                        monster.setDx(rand.nextInt(-1, 2));
                        monster.setDy(rand.nextInt(-1, 2));
                        ((Monster) monster).setTimer(0);
                    }
                    ((Monster) monster).act();
                    ((Monster) monster).setTimer(((Monster) monster).getTimer()+1);
            }
        }
        updateOtherSprites();
    }


    protected void updateOtherSprites() {

        Random generator = new Random();

        for (BadSprite monster : badSprites) {

            Gosma gosma = ((Monster) monster).getGosma();

            if (!((Monster) monster).isCongelado() && gosma.isDestroyed()) {

                int directionX = generator.nextInt(-4,5);
                int directionY = generator.nextInt(-4,5);

                while(directionY == 0 && directionX == 0){
                    directionX = generator.nextInt(-4,5);
                    directionY = generator.nextInt(-4,5);
                }

                gosma.setDx(directionX);
                gosma.setDy(directionY);

                gosma.setDestroyed(false);
                gosma.setX(monster.getX());
                gosma.setY(monster.getY());
            }

            if (players.get(0).isVisible() && !gosma.isDestroyed()) {

                if (gosma.getX() >= (players.getFirst().getX())
                        && gosma.getX() <= (players.getFirst().getX() + Commons.WOODY_WIDTH)
                        && gosma.getY() >= (players.getFirst().getY())
                        && gosma.getY() <= (players.getFirst().getY() + Commons.WOODY_HEIGHT)) {

                    ImageIcon ii = new ImageIcon(explImg);
                    players.get(0).setImage(ii.getImage());
                    players.get(0).setDying(true);
                    gosma.setDestroyed(true);
                }
            }

            if (!gosma.isDestroyed()) {
                gosma.setX(gosma.getX() + gosma.getDx());
                gosma.setY(gosma.getY() + gosma.getDy());

                if (gosma.getX() >= Commons.BOARD_WIDTH || gosma.getX() <= 0 || gosma.getY() <= 0) {
                    gosma.setDestroyed(true);
                }
                if(gosma.getX() == ray.getX() && gosma.getY() == ray.getY()){
                    gosma.setDestroyed(true);
                    ray.die();
                }
            }
        }
    }
}
