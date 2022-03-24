package object;

import mainGame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlockTile extends GameObject{

    BufferedImage image = null;

    public BlockTile(int x, int y, BufferedImage image, ID id) {
        super(x, y, id);
        this.image = image;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x * Game.BOX_SIZE, y * Game.BOX_SIZE, Game.BOX_SIZE * 100, Game.BOX_SIZE * 81 , null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
