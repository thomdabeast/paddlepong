package com.thomdabeast.paddlepong.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.thomdabeast.paddlepong.Ball;
import com.thomdabeast.paddlepong.MyGdxGame;
import com.thomdabeast.paddlepong.Paddle;

import java.util.Hashtable;

public class PlayState extends State implements InputProcessor{
    Paddle left, right, top, bottom;
    Ball ball;
    Texture paddle, touchAreaLeft, touchAreaRight;
    public static float SCALE, GAME_RATIO;
    int PADDING, SPEED;
    Hashtable<String, Integer> keys;
    BitmapFont font;
    ShapeRenderer sr;
    boolean lost = false;

    public PlayState(GSM gsm) {
        super(gsm);
        sr = new ShapeRenderer();
        font = new BitmapFont();
        keys = new Hashtable<String, Integer>();
        SCALE = 0.3f;
        SPEED = 15;
        PADDING = 10;
        GAME_RATIO = ((float)MyGdxGame.GAME_WIDTH-(MyGdxGame.GAME_WIDTH*2/9))/(float)MyGdxGame.GAME_HEIGHT;

        Gdx.input.setInputProcessor(this);

        touchAreaLeft = new Texture("TouchArea.png");
        touchAreaRight = new Texture("TouchArea.png");
        paddle = new Texture("paddle.png");

        ball = new Ball(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2), new Vector2(0, 0), 7, 15);
        //ball.generateRandomDirection();

        System.out.println(ball.getVelocity().x + " " + ball.getVelocity().y);

        final int height = ((int)(MyGdxGame.GAME_HEIGHT/2)) - ((int)((float)paddle.getWidth()*SCALE)/2),
                width = MyGdxGame.GAME_WIDTH/2 - (int)((float)paddle.getWidth()*SCALE)/2;
        //Create each paddle
        //left = new Paddle((int)(paddle.getHeight()*SCALE) + PADDING + MyGdxGame.GAME_WIDTH/9, height, 0, 0, paddle);
        left = new Paddle((int)(paddle.getHeight() + PADDING + MyGdxGame.GAME_WIDTH/9), 0, 0, 0, 90f, paddle);
        right = new Paddle(MyGdxGame.GAME_WIDTH, height, 0, 0, 90f, paddle);
        top = new Paddle(MyGdxGame.GAME_WIDTH/2, MyGdxGame.GAME_HEIGHT- paddle.getHeight() - PADDING, 0, 0, 0f, paddle);
        bottom = new Paddle(MyGdxGame.GAME_WIDTH/2, PADDING, 0, 0, 0f, paddle);
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {

//Update each paddles positions
        //System.out.println(keys.toString());
        paddleMovement();
        collisionDetection();
        left.updatePosition("y");
        right.updatePosition("y");
        top.updatePosition("x");
        bottom.updatePosition("x");
        ball.setPosition(new Vector2(ball.getPosition().x + ball.getVelocity().x, ball.getPosition().y + ball.getVelocity().y));


//        edgeCollisionDetect();
        Gdx.gl.glClearColor(255, 255, 250, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        cam.update();

        sb.begin();

//        if (lost) {
//            font.setColor(0.0f, 0.0f, 1.0f, 1.0f); // tint font blue
//            font.setScale(5f);
//            font.draw(sb, "YOU LOST!!!", MyGdxGame.GAME_WIDTH/2 - 200, MyGdxGame.GAME_HEIGHT/2);
//        }

        sb.draw(touchAreaLeft, 0, 0, MyGdxGame.GAME_WIDTH/9, MyGdxGame.GAME_HEIGHT);
        sb.draw(touchAreaRight, MyGdxGame.GAME_WIDTH - (MyGdxGame.GAME_WIDTH/9), 0, MyGdxGame.GAME_WIDTH/9, MyGdxGame.GAME_HEIGHT);

        //sb.draw(left.region, left.getPositionX(), left.truePosition, 0, 0, left.getWidth(), left.getHeight(), SCALE, SCALE, 90f, true);

        sb.draw(right.region, right.getPositionX(), right.truePosition, 0, 0, right.getWidth(), right.getHeight(), SCALE, SCALE, 90f, true);

        sb.draw(top.region, top.truePosition, top.getPositionY(), 0, 0, top.getWidth(), top.getHeight(), SCALE, SCALE, 0f, true);

        //sb.draw(bottom.region, bottom.truePosition, bottom.getPositionY(), 0, 0, bottom.getWidth(), bottom.getHeight(), SCALE, SCALE, 0f, true);

        sb.end();
        left.draw(sb);
        bottom.draw(sb);

        //Draw ball
        ball.draw(sr);

        //DEBUGGING STUFF
//        debugString("Left: " + left.getPositionX() + " " + left.getPositionY());
//        debugString("Right: " + right.getPositionX() + " " + right.getPositionY());
//        debugString("Top: " + top.getPositionX() + " " + top.getPositionY());
//        debugString("Bottom: " + bottom.getPositionX() + " " + bottom.getPositionY());
        debugLine(ball.getPosition().x, ball.getPosition().y, left.getPositionX(), left.getPositionY());
        debugLine(ball.getPosition().x, ball.getPosition().y, right.getPositionX(), right.getPositionY());
        debugLine(ball.getPosition().x, ball.getPosition().y, top.getPositionX(), top.getPositionY());
        debugLine(ball.getPosition().x, ball.getPosition().y, bottom.getPositionX(), bottom.getPositionY());
//        debugLine(MyGdxGame.GAME_WIDTH/2, 0, MyGdxGame.GAME_WIDTH/2, MyGdxGame.GAME_HEIGHT);
//        debugLine(0, MyGdxGame.GAME_HEIGHT/2, MyGdxGame.GAME_WIDTH, MyGdxGame.GAME_HEIGHT/2);
    }

    public void debugString(String str) {
        System.out.println(str);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public boolean keyDown(int keycode) {
        keys.put(Integer.toString(keycode), keycode);
        System.out.println(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keys.remove(Integer.toString(keycode));
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Set left paddle touch
        if (screenX < Gdx.graphics.getWidth()/2) {
            left.setStartY(screenY);
            top.setStartY(screenY);
        }
        else {
            right.setStartY(screenY);
            bottom.setStartY(screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenX < Gdx.graphics.getWidth()/2) {
            left.setDrag(0);
            top.setDrag(0);
            left.setPositionY(left.truePosition);
            top.setPositionX(top.truePosition);
        }
        else {
            right.setDrag(0);
            bottom.setDrag(0);
            right.setPositionY(right.truePosition);
            bottom.setPositionX(bottom.truePosition);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (screenX < Gdx.graphics.getWidth()/2) {
            left.setDrag(screenY - left.getStartY());
            top.setDrag(screenY - top.getStartY());
            top.setDrag((int)Math.ceil(top.getDrag()*(GAME_RATIO+0.15f)));
        }
        else {
            right.setDrag(screenY - right.getStartY());
            bottom.setDrag(screenY - bottom.getStartY());
            bottom.setDrag((int) Math.ceil(bottom.getDrag() * (GAME_RATIO + 0.15f)));
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void collisionDetection() {
        //Ball is in top-left quadrant
        if (ball.getPosition().x <= MyGdxGame.GAME_WIDTH/2 && ball.getPosition().y >= MyGdxGame.GAME_HEIGHT/2) {
            //Is ball.position.y within the left paddle's edge?
            if (left.getPositionY() + left.getDrag() <= ball.getPosition().y && ball.getPosition().y <= left.getPositionY()  + left.getDrag() + left.getWidth()*SCALE) {
                //Has the ball touched the paddles edge?
                if (ball.getPosition().x - ball.radius + ball.getVelocity().x <= left.getPositionX()) {
                    //TODO: Set appropriate direction after hit
                    ball.setVelocity(new Vector2(2, 2));
                }
            }

            //Is ball.position.x within the top paddle's edge
            if (top.getPositionX() + top.getDrag() <= ball.getPosition().x && ball.getPosition().x <= top.getPositionX() + top.getDrag() + top.getWidth()*SCALE) {
                //Has the ball touched the top paddle's edge?
                if (ball.getPosition().y + ball.radius + ball.getVelocity().y >= top.getPositionY()) {
                    //TODO: Set appropriate direction after hit
                    ball.setVelocity(new Vector2(0, -2));
                }
            }
        }

        //Ball is in top-right quadrant
        else if (ball.getPosition().x > MyGdxGame.GAME_WIDTH/2 && ball.getPosition().y >= MyGdxGame.GAME_HEIGHT/2) {
            //Is ball.position.x within the top paddle's edge
            if (top.getPositionX() + top.getDrag() <= ball.getPosition().x && ball.getPosition().x <= top.getPositionX() + top.getDrag() + top.getWidth()*SCALE) {
                //Has the ball touched the top paddle's edge?
                if (ball.getPosition().y + ball.radius + ball.getVelocity().y >= top.getPositionY()) {
                    //TODO: Set appropriate direction after hit
                    ball.setVelocity(new Vector2(-2, -2));
                }
            }
            if (right.getPositionY() + right.getDrag() <= ball.getPosition().y - ball.radius && ball.getPosition().y + ball.radius <= right.getPositionY() + right.getDrag() + right.getWidth()) {
                //Has the ball touched the paddles edge?
                if (ball.getPosition().x + ball.radius + ball.getVelocity().x >= right.getPositionX() - right.getHeight()*SCALE) {
                    //TODO: Set appropriate direction after hit
                    ball.setVelocity(new Vector2(-2, 2));
                }
            }
        }

        //Ball is in bottom-left quadrant
        else if (ball.getPosition().x < MyGdxGame.GAME_WIDTH/2 && ball.getPosition().y < MyGdxGame.GAME_HEIGHT/2) {
            //Is ball.position.y within the left paddle's edge?
            if (left.getPositionY() + left.getDrag() <= ball.getPosition().y - ball.radius && ball.getPosition().y + ball.radius <= left.getPositionY() + left.getDrag() + left.getWidth()*SCALE) {
                //Has the ball touched the paddles edge?
                if (ball.getPosition().x - ball.radius + ball.getVelocity().x <= left.getPositionX()) {
                    //TODO: Set appropriate direction after hit
                    ball.setVelocity(new Vector2(2, 0));
                }
            }
            //Is ball.position.x within the bottom paddle's edge
            if (bottom.getPositionX() + bottom.getDrag() <= ball.getPosition().x && ball.getPosition().x <= bottom.getPositionX() + bottom.getDrag() + bottom.getWidth()*SCALE) {
                //Has the ball touched the bottom paddle's edge?
                if (ball.getPosition().y - ball.radius - ball.getVelocity().y <= bottom.getPositionY() + bottom.getHeight()*SCALE) {
                    //TODO: Set appropriate direction after hit
                    ball.setVelocity(new Vector2(2, 2));
                }
            }
        }

        //Ball is in bottom-right quadrant
        else if (ball.getPosition().x > MyGdxGame.GAME_WIDTH/2 && ball.getPosition().y < MyGdxGame.GAME_HEIGHT/2) {
            //TODO: check if ball is within the right paddles
            if (right.getPositionY() <= ball.getPosition().y - ball.radius && ball.getPosition().y + ball.radius <= right.getPositionY() + right.getWidth()) {
                //Has the ball touched the paddles edge?
                if (ball.getPosition().x + ball.radius + ball.getVelocity().x >= right.getPositionX() - right.getHeight()*SCALE) {
                    ball.setVelocity(new Vector2(-2, 1));
                    ball.setPosition(new Vector2(ball.getPosition().x + ball.getVelocity().x, ball.getPosition().y));
                    System.out.println("hit right");
                }
            }
            //Is ball.position.x within the bottom paddle's edge
            if (bottom.getPositionX() <= ball.getPosition().x && ball.getPosition().x <= bottom.getPositionX() + bottom.getWidth()*SCALE) {
                //Has the ball touched the bottom paddle's edge?
                if (ball.getPosition().y - ball.radius - ball.getVelocity().y <= bottom.getPositionY() + bottom.getHeight()*SCALE) {
                    //TODO: Set appropriate direction after hit
                    ball.setVelocity(new Vector2(2, 2));
                }
            }
        }
    }

    void paddleMovement() {
        if (keys.size() != 0) {
            //Up
            try {
                if (keys.get("51") == 51) {
                    left.setPositionY(left.getPositionY() + SPEED);
                    top.setPositionX(top.getPositionX() - SPEED);
                }
            } catch (NullPointerException e) {}
            try {
                if (keys.get("19") == 19) {
                    right.setPositionY(right.getPositionY() + SPEED);
                    bottom.setPositionX(bottom.getPositionX() - SPEED);
                }
            } catch (NullPointerException ex) {}

            //Down
            try {
                if (keys.get("47") == 47) {
                    left.setPositionY(left.getPositionY() - SPEED);
                    top.setPositionX(top.getPositionX() + SPEED);
                }
            } catch (NullPointerException e) {}

            try {
                if (keys.get("20") == 20) {
                    right.setPositionY(right.getPositionY() - SPEED);
                    bottom.setPositionX(bottom.getPositionX() + SPEED);
                }
            } catch (NullPointerException e){}
        }
    }

    void debugLine(float firstX, float firstY, float secondX, float secondY) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(0, 1, 0, 1);
        sr.line(firstX, firstY, secondX, secondY);
        sr.end();
    }

    public void edgeCollisionDetect() {
        //Ball edge Detection
        if (ball.getPosition().x + ball.radius < 0 || ball.getPosition().x - ball.radius > MyGdxGame.GAME_WIDTH
                || ball.getPosition().y + ball.radius < 0 || ball.getPosition().y - ball.radius > MyGdxGame.GAME_HEIGHT) {
            lost = true;
        }

        if (left.truePosition >= MyGdxGame.GAME_HEIGHT - (int)(left.getTexture().getWidth()*SCALE)){
            left.truePosition = MyGdxGame.GAME_HEIGHT - (int)(left.getTexture().getWidth()*SCALE);
        }
        else if (left.truePosition <= 0) {
            left.truePosition = 0;
        }
        if (right.truePosition >= MyGdxGame.GAME_HEIGHT - (int)(right.getTexture().getWidth()*SCALE)) {
            right.truePosition =MyGdxGame.GAME_HEIGHT - (int)(left.getTexture().getWidth()*SCALE);
        }
        else if (right.truePosition <= 0) {
            right.truePosition = 0;
        }
        if (top.truePosition <= touchAreaLeft.getWidth()) {
            top.truePosition = touchAreaLeft.getWidth();
        }
        else if (top.truePosition >= MyGdxGame.GAME_WIDTH - touchAreaRight.getWidth() - (int)(top.getTexture().getWidth()*SCALE)) {
            top.truePosition = MyGdxGame.GAME_WIDTH - touchAreaRight.getWidth() - (int)(top.getTexture().getWidth()*SCALE);
        }
        if (bottom.truePosition <= touchAreaLeft.getWidth()) {
            bottom.truePosition = touchAreaLeft.getWidth();
        }
        else if (bottom.truePosition >= MyGdxGame.GAME_WIDTH - touchAreaRight.getWidth() - (int)(bottom.getTexture().getWidth()*SCALE)) {
            bottom.truePosition = MyGdxGame.GAME_WIDTH - touchAreaRight.getWidth() - (int)(bottom.getTexture().getWidth()*SCALE);
        }
    }
}
