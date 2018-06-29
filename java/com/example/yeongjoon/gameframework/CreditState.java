package com.example.yeongjoon.gameframework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class CreditState implements IState {
    Bitmap android;
    int x, y;

    @Override
    public void Init() {
        android = AppManager.getInstance().getBitmap(R.drawable.background2);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        canvas.drawBitmap(android, x, y, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
