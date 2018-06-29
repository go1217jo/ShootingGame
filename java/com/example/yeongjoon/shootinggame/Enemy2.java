package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.R;

import java.util.Random;

public class Enemy2 extends Enemy {
    public Enemy2() {
        super(AppManager.getInstance().getBitmap(R.drawable.enemy2));
        //this.initSpriteData(217, 325, 30, 6);
        //this.initSpriteData(186, 295, 30, 6);
        this.initSpriteData(273,273,5,1);
        hp = 20;
        speed = 4.0f;
        movetype = new Random().nextInt(3);
    }

    public Enemy2(Bitmap bitmap) {
        super(bitmap);
    }

    public void Update(long GameTime) {
        super.Update(GameTime);
        m_BoundBox.set(m_x, m_y, m_x + 186/2, m_y + 295);
    }

    @Override
    public void Move() {
        super.Move();
    }

    @Override
    public void Attack() {
        super.Attack();
    }
}
