package com.company;

import java.awt.*;

public class Box extends GameObject {

    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, 32, 32);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
