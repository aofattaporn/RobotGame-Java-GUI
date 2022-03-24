package mainGame;

import object.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler  {

    public LinkedList<GameObject> object = new LinkedList<>();
    private boolean up = false, down = false, right = false, left = false, spaceBar = false;

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isSpaceBar() {
        return spaceBar;
    }

    public void setSpaceBar(boolean spaceBar) {
        this.spaceBar = spaceBar;
    }

    public void tick(){
        for (int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
             tempObject.tick();
        }
    }

    public void render(Graphics g){
        for (int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);

            tempObject.render(g);

        }
    }

    public void addObject(GameObject tempObject){
        object.add(tempObject);
    }

    public void removeObject(GameObject tempObject){
        object.remove(tempObject);
    }

}
