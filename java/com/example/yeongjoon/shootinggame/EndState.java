package com.example.yeongjoon.shootinggame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.GameView;
import com.example.yeongjoon.gameframework.IState;

public class EndState implements IState {
    public BackGround m_background;
    boolean Win_State = false;

    public EndState() {
        AppManager.getInstance().m_endState = this;
    }
    @Override
    public void Init() {

    }
    public void Init(BackGround backGround, boolean win_State) {
        m_background = backGround;
        Win_State = win_State;
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();
        m_background.Update(GameTime);
    }

    @Override
    public void Render(Canvas canvas) {
        m_background.Draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(120);
        paint.setFakeBoldText(true);

        if(Win_State) {
            paint.setColor(Color.YELLOW);
            canvas.drawText("졸업을 축하드립니다!", 140, 800, paint);
        }
        else {
            paint.setColor(Color.RED);
            canvas.drawText("절망적인 상황..", 160, 800, paint);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GameView gameView = AppManager.getInstance().getGameView();
        gameView.ChangeGameState(new GameState(gameView.m_screen_width, gameView.m_screen_height));
        return true;
    }
}
