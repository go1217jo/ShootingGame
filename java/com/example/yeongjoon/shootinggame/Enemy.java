package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.SpriteAnimation;

import org.w3c.dom.ls.LSException;

public class Enemy extends SpriteAnimation {
    protected int hp;
    protected float speed;

    public static final int STATE_NORMAL = 0;
    public static final int STATE_OUT = 1;
    public int state = STATE_NORMAL;

    public static final int MOVE_PATTERN_1 = 0;
    public static final int MOVE_PATTERN_2 = 1;
    public static final int MOVE_PATTERN_3 = 2;

    protected int movetype;
    Rect m_BoundBox = new Rect();
    long LastShoot = System.currentTimeMillis();

    public Enemy(Bitmap bitmap) {
        super(bitmap);
    }

    public void Update(long GameTime) {
        super.Update(GameTime);
        Attack();
        Move();
    }

    public void Move() {
        if(movetype == MOVE_PATTERN_1) {
            if(m_y <= 200)
                m_y += speed;
            else
                m_y += speed * 2;
        }
        else if(movetype == MOVE_PATTERN_2) {
            if(m_y <= 200)
                m_y += speed;
            else {
                m_x += speed;
                m_y += speed;
            }
        }
        else if(movetype == MOVE_PATTERN_3) {
            if(m_y <= 200)
                m_y += speed;
            else {
                m_x -= speed;
                m_y += speed;
            }
        }
        if(m_y > 2000)
            state = STATE_OUT;
    }

    public void Attack() {
        if(System.currentTimeMillis() - LastShoot >= 1200) {
            LastShoot = System.currentTimeMillis();
            // 미사일 발사 로직
            AppManager.getInstance().m_gameState.m_enemmslist.add(new Missile_Enemy(m_x + 10, m_y + m_bitmap.getHeight()));
        }
    }
}
