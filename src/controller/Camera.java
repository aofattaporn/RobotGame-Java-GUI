package controller;

import minaGame.Game;
import object.GameObject;
import object.ID;

public class Camera {

    private float VIEWPORT_SIZE_X;
    private float VIEWPORT_SIZE_Y;

    // 1600 * 1200
    private final float WORLD_SIZE_X = Game.BOX_SIZE * (100+ 4);
    private final float WORLD_SIZE_Y = Game.BOX_SIZE * (80 + 4);

    private float offsetMaxX;
    private float offsetMaxY;
    private float offsetMinX;
    private float offsetMinY;

    private float camX;
    private float camY;

    private float x, y;

    public Camera(Game game) {

        VIEWPORT_SIZE_X = game.getWidth();
        VIEWPORT_SIZE_Y = game.getHeight();

        offsetMaxX = WORLD_SIZE_X - VIEWPORT_SIZE_X;
        offsetMaxY = WORLD_SIZE_Y - VIEWPORT_SIZE_Y;
        offsetMinX = 0;
        offsetMinY = 0;


    }

    public void tick(GameObject robot) {

        if (robot.getId() == ID.player) {

            camX = robot.getX() - VIEWPORT_SIZE_X / 2;
            camY = robot.getY() - VIEWPORT_SIZE_Y / 2;

            if (camX > offsetMaxX) camX = offsetMaxX + 32;
            else if (camX < offsetMinX) camX = offsetMinX;

            if (camY > offsetMaxY) camY = offsetMaxY - 12;
            else if (camY < offsetMinY) camY = offsetMinY;

        }

    }

    public float getCamX() {
        return camX;
    }

    public void setCamX(float camX) {
        this.camX = camX;
    }

    public float getCamY() {
        return camY;
    }

    public void setCamY(float camY) {
        this.camY = camY;
    }

}


