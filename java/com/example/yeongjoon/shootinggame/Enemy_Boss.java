package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.GraphicObject;
import com.example.yeongjoon.gameframework.R;

public class Enemy_Boss extends GraphicObject {
    int hp = 50;

    public Enemy_Boss() {

    }

    public Enemy_Boss(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public void SetBitmap(Bitmap bitmap) {
        super.SetBitmap(bitmap);
    }

    @Override
    public void Draw(Canvas canvas) {
        super.Draw(canvas);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getY() {
        return super.getY();
    }
}
