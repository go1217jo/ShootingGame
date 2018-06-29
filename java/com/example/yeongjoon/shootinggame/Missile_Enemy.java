package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.R;

public class Missile_Enemy extends Missile {
    public Missile_Enemy(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.monster_missile));
        this.setPosition(x, y);
    }
    public void Update() {
        // 미사일이 아래로 발사되는 효과
        m_y += 10;
        if(m_y > 2000)
            state = STATE_OUT;

        m_BoundBox.set(m_x, m_y, m_x + m_bitmap.getWidth(), m_y + m_bitmap.getHeight());
    }
}
