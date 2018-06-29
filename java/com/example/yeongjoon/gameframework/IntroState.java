package com.example.yeongjoon.gameframework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class IntroState implements IState {
    Bitmap icon;
    int x, y;

    @Override
    public void Init() {
        icon = AppManager.getInstance().getBitmap(R.drawable.background1);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        canvas.drawBitmap(icon, x, y, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 터치하면 상태가 변경됨
        AppManager.getInstance().getGameView().ChangeGameState(new CreditState());
        return true;
    }
}
