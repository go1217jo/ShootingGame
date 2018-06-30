package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.R;

import java.util.Random;

public class Missile_Boss extends Missile {
    public int[] planets = {R.drawable.planet1, R.drawable.planet2, R.drawable.planet3, R.drawable.planet4, R.drawable.planet5, R.drawable.planet6};

    public Missile_Boss(int x, int y) {
        int idx = new Random().nextInt(planets.length);
        super.m_bitmap = Bitmap.createScaledBitmap(AppManager.getInstance().getBitmap(planets[idx]), 200, 200, true);
        setPosition(x, y);
    }

    public void Update() {
        // 미사일이 아래로 발사되는 효과
        m_y += 25;
        if(m_y > 2000)
            state = STATE_OUT;

        m_BoundBox.set(m_x, m_y, m_x + m_bitmap.getWidth(), m_y + m_bitmap.getHeight());
    }
}
