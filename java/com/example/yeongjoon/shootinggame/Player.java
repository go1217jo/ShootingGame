package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.yeongjoon.gameframework.SpriteAnimation;

public class Player extends SpriteAnimation {
    Rect m_BoundBox = new Rect();
    public double m_Life = 4.5;

    public Player(Bitmap bitmap) {
        super(bitmap);
        // 애니메이션 정보 설정
        //this.initSpriteData(217, 325, 30, 6);
        this.initSpriteData(300, 300, 3, 4);
        // 초기 위치 값을 설정
        this.setPosition(500, 1800);
    }
    public void Update(long gameTime) {
        super.Update(gameTime);
        m_BoundBox.set(m_x, m_y, m_x + 186/2, m_y  + m_bitmap.getHeight()/2);
    }

    public double getLife() {
        return m_Life;
    }
    public void addLife() {
        m_Life += 0.5;
    }
    public void destroyPlayer() { m_Life -= 0.5; }
}
