package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.R;

public class Enemy3 extends Enemy {
    public Enemy3() {
        super(AppManager.getInstance().getBitmap(R.drawable.enemy3));
        //this.initSpriteData(217, 325, 30, 6);
        this.initSpriteData(186, 295, 30, 6);
        hp = 20;
        speed = 6.5f;
        movetype = Enemy.MOVE_PATTERN_2;
    }

    public Enemy3(Bitmap bitmap) {
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
