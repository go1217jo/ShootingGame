package com.example.yeongjoon.gameframework;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GraphicObject {
    protected Bitmap m_bitmap;
    protected int m_x;
    protected int m_y;

    public GraphicObject() {
        m_x = 0;
        m_y = 0;
    }

    public GraphicObject(Bitmap bitmap) {
        m_bitmap = bitmap;
        m_x = 0;
        m_y = 0;
    }

    public void SetBitmap(Bitmap bitmap) {
        m_bitmap = bitmap;
    }

    // 이미지 그림
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap, m_x, m_y, null);
    }

    // 좌표 설정
    public void setPosition(int x, int y) {
        m_x = x;
        m_y = y;
    }

    // x, y 각 좌표 반환
    public int getX() { return m_x; }
    public int getY() { return m_y; }
}
