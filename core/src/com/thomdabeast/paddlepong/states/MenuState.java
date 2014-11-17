package com.thomdabeast.paddlepong.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.thomdabeast.paddlepong.MyGdxGame;

public class MenuState extends State{
    ShapeRenderer sr;
    public MenuState(GSM gsm) {
        super(gsm);
        sr = new ShapeRenderer();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 255, 0, 1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(MyGdxGame.GAME_HEIGHT/4, MyGdxGame.GAME_HEIGHT/4, MyGdxGame.GAME_HEIGHT*3/4, MyGdxGame.GAME_HEIGHT*3/4);
        sr.end();
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (screenX < MyGdxGame.GAME_WIDTH/2) {
                    gsm.pop();
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });
    }
}
