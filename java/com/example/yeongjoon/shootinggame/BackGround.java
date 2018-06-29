package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Display;
import android.view.WindowManager;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.GraphicObject;
import com.example.yeongjoon.gameframework.R;

public class BackGround extends GraphicObject {
    static final float SCROLL_SPEED = 1.0f;
    private float m_scroll = -2000 + 480;

    Bitmap m_layer2;
    static final float SCROLL_SPEED_2 = 0.7f;
    private float m_scroll_2 = -2000 + 480;

    public BackGround() {
        super(AppManager.getInstance().getBitmap(R.drawable.background));
        m_bitmap = Bitmap.createScaledBitmap(m_bitmap, 1100,6000, true );
        setPosition(0,(int)m_scroll);
    }

    public BackGround(int width, int height, int backType) {
        super(null);
        Bitmap background = null;
        if(backType == 1)
            background = AppManager.getInstance().getBitmap(R.drawable.background1);
        else if(backType == 2)
            background = AppManager.getInstance().getBitmap(R.drawable.background2);

        SetBitmap(Bitmap.createScaledBitmap(background, width, background.getHeight(), true));

        m_layer2 = Bitmap.createScaledBitmap(AppManager.getInstance().getBitmap(R.drawable.cloud), width, AppManager.getInstance().getBitmap(R.drawable.cloud).getHeight(), true);
        setPosition(0, (int)m_scroll);
    }

    public void Update(long GameTime) {
        m_scroll = m_scroll + SCROLL_SPEED;
        if(m_scroll>=0) m_scroll = -6000+2000;//배경연결을 위한 상수
        setPosition(0,(int)m_scroll);
    }

    public void Draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap, m_x, m_y, null);
    }

}
