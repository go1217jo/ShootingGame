package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.R;

import java.util.Random;

public class Enemy1 extends Enemy {
    public Enemy1() {
        super(AppManager.getInstance().getBitmap(R.drawable.enemy1));
        //this.initSpriteData(217, 325, 30, 6);
        //this.initSpriteData(186, 295, 30, 6);
        this.initSpriteData(273,273,5,1);
        hp = 10;
        speed = 2.5f;
        movetype = new Random().nextInt(3);
    }

    public Enemy1(Bitmap bitmap) {
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
