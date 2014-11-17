package com.thomdabeast.paddlepong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.thomdabeast.paddlepong.states.PlayState;

import java.lang.String;

public class Paddle{
    private Texture texture;
    public TextureRegion region;
    private int startY;
    private int drag;
    private int positionY;
    private int positionX;
    private float rotation;
    public int truePosition;

    public Paddle (int positionX, int positionY, int start,int drag, float rotation, Texture text) {
        setRotation(rotation);
        this.truePosition = 0;
        setDrag(drag);
        setPositionX(positionX);
        setPositionY(positionY);
        setStartY(start);
        this.texture = text;
        this.region = new TextureRegion(this.texture);
    }

    public void updatePosition(String axis) {
        if (axis == "x") {
            truePosition = positionX + drag;
        }
        else if (axis == "y") {
            truePosition = positionY - drag;
        }
        else {
           //Inputted wrong axis, so do something...
        }
    }

    public void draw(SpriteBatch sb) {
        sb.begin();
        sb.draw(this.region, getPositionX(), truePosition, 0, 0, getWidth(), getHeight(), PlayState.SCALE, PlayState.SCALE, getRotation(), true);
        sb.end();
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getWidth () { return this.texture.getWidth(); }

    public int getHeight () { return this.texture.getHeight(); }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getDrag() {
        return drag;
    }

    public void setDrag(int dragY) {
        this.drag = dragY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }


}
