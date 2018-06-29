package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.R;

// 플레이어가 발사할 미사일 클래스
public class Missile_Player extends Missile {

    public Missile_Player(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.player_missile));
        this.setPosition(x, y);
    }
    public void Update() {
        m_y -= 20;

        if(m_y < 0)
            state = STATE_OUT;

        m_BoundBox.left = m_x + 20;
        m_BoundBox.top = m_y + 40;
        m_BoundBox.right = m_x + m_bitmap.getWidth();
        m_BoundBox.bottom = m_y + m_bitmap.getHeight();
    }
}
