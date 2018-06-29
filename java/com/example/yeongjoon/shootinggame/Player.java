package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.yeongjoon.gameframework.SpriteAnimation;

public class Player extends SpriteAnimation {
    Rect m_BoundBox = new Rect();
    int m_Life = 3;

    public Player(Bitmap bitmap) {
        super(bitmap);
        // 애니메이션 정보 설정
        //this.initSpriteData(217, 325, 30, 6);
        this.initSpriteData(186, 295, 30, 6);
        // 초기 위치 값을 설정
        this.setPosition(600, 1300);
    }
    public void Update(long gameTime) {
        super.Update(gameTime);
        m_BoundBox.set(m_x, m_y, m_x + 186/2, m_y  + m_bitmap.getHeight()/2);
    }

    public int getLife() {
        return m_Life;
    }
    public void addLife() {
        m_Life ++;
    }
    public void destroyPlayer() {
        m_Life--;
    }
}
