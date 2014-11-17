package com.thomdabeast.paddlepong.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thomdabeast.paddlepong.MyGdxGame;

public abstract class State {

    protected GSM gsm;
    protected OrthographicCamera cam;

    protected State(GSM gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false);
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void handleInput();
}
