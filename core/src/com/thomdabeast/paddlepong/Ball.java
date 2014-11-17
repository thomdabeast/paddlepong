package com.thomdabeast.paddlepong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public class Ball {
    private Vector2 position, velocity;
    public int speed, radius;


    public Ball(Vector2 position, Vector2 velocity, int speed, int radius) {
        this.radius = radius;
        setSpeed(speed);
        setPosition(position);
        setVelocity(velocity);
    }

    public void generateRandomDirection() {
        double direction = (Math.random()*2.0*Math.PI) + 1;
        this.setVelocity(new Vector2((int) (this.getSpeed()*Math.cos(direction)), (int) (this.getSpeed()*Math.sin(direction))));
    }

    public void draw(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0, 0, 0, 1);
        sr.circle(this.getPosition().x, this.getPosition().y, this.radius);
        sr.end();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
}
