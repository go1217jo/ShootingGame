package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.IState;
import com.example.yeongjoon.gameframework.R;

import java.util.ArrayList;
import java.util.Random;

public class GameState implements IState {
    // 멤버 변수 추가할 곳
    private Player m_player;
    private BackGround m_background;
    ArrayList<Enemy> m_enemlist = new ArrayList<Enemy>();
    ArrayList<Missile> m_pmslist =  new ArrayList<Missile>();
    ArrayList<Missile> m_enemmslist = new ArrayList<Missile>();
    long LastRegenEnemy = System.currentTimeMillis();
    long LastRegenMissile = System.currentTimeMillis();

    Random randomEnemy = new Random();

    public int screen_width;
    public int screen_height;

    public GameState(int width, int height) {
        screen_width = width;
        screen_height = height;

        AppManager.getInstance().m_gameState = this;
    }

    @Override
    public void Init() {
        m_player = new Player(AppManager.getInstance().getBitmap(R.drawable.player));
        m_background = new BackGround(screen_width, screen_height, 1);
    }

    public void MakeEnemy() {
        if(System.currentTimeMillis() - LastRegenEnemy >= 1000) {
            LastRegenEnemy = System.currentTimeMillis();

            int enemytype = randomEnemy.nextInt(3);
            Enemy enemy = null;
            switch (enemytype) {
                case 0 : enemy = new Enemy1();
                    break;
                case 1 : enemy = new Enemy2();
                    break;
                case 2 : enemy = new Enemy3();
            }

            enemy.setPosition(randomEnemy.nextInt(screen_width - 1), -60);
            enemy.movetype = randomEnemy.nextInt(3);

            m_enemlist.add(enemy);
        }
    }

    public void CheckCollision() {
        for(int i=0; i<m_pmslist.size(); i++) {
            for(int j=0; j< m_enemlist.size(); j++) {
                Missile pms = m_pmslist.get(i);
                Enemy enemy = m_enemlist.get(j);

                // 플레이어와 적의 충돌
                if(CollisionManager.CheckBoxToBox2(m_player.m_BoundBox, m_enemlist.get(j).m_BoundBox)) {
                    m_enemlist.remove(j);
                    m_player.destroyPlayer();
                    if(m_player.getLife() <= 0) {
                        System.exit(0);
                        return;
                    }
                }
                // 적과 유저 미사일과 충돌
                if(CollisionManager.CheckBoxToBox(enemy.m_BoundBox, pms.m_BoundBox)) {
                    m_pmslist.remove(i);
                    m_enemlist.remove(j);
                    return;
                }
            }
        }
        // 적 미사일과 유저 충돌
        for(int i=0;i<m_enemmslist.size(); i++) {
            if(CollisionManager.CheckBoxToBox3(m_player.m_BoundBox, m_enemmslist.get(i).m_BoundBox)) {
                m_enemmslist.remove(i);
                m_player.destroyPlayer();
                if(m_player.getLife() <= 0) {
                    System.exit(0);
                    return;
                }
            }
        }
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();
        m_player.Update(GameTime);
        m_background.Update(GameTime);
        for(int i=0; i<m_enemlist.size(); i++) {
            Enemy enemy = m_enemlist.get(i);
            enemy.Update(GameTime);
            if(enemy.state == Enemy.STATE_OUT)
                m_enemlist.remove(i);
        }
        for(Enemy enemy : m_enemlist)
            enemy.Update(GameTime);
        for(int i=0; i<m_pmslist.size(); i++) {
            Missile pms = m_pmslist.get(i);
            pms.Update();
            if(pms.state == Missile.STATE_OUT)
                m_pmslist.remove(i);
        }
        for(int i=0; i < m_enemmslist.size(); i++){
            Missile enemms = m_enemmslist.get(i);
            enemms.Update();
            if(enemms.state == Missile.STATE_OUT) {
                m_enemmslist.remove(i);
            }
        }
        MakeEnemy();
        ShootMissile();
        CheckCollision();
    }

    @Override
    public void Render(Canvas canvas) {
        m_background.Draw(canvas);
        for(Enemy enemy : m_enemlist)
            enemy.Draw(canvas);
        for(Missile pms : m_pmslist)
            pms.Draw(canvas);
        for(Missile enemms : m_enemmslist) {
            enemms.Draw(canvas);
        }
        m_player.Draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.BLACK);
        canvas.drawText("남은 목숨 : " + String.valueOf(m_player.getLife()), 30, 60,paint);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int x = m_player.getX();
        int y = m_player.getY();

        if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
            m_player.setPosition(x-1, y);
        if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
            m_player.setPosition(x+1, y);
        if(keyCode == KeyEvent.KEYCODE_DPAD_UP)
            m_player.setPosition(x, y-1);
        if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
            m_player.setPosition(x, y+1);

        return true;
    }

    public void ShootMissile() {
        if(System.currentTimeMillis() - LastRegenMissile >= 300) {
            LastRegenMissile = System.currentTimeMillis();
            m_pmslist.add(new Missile_Player(m_player.getX() + 10, m_player.getY() - 10));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 접촉면이 두 개면 터치 중간에 플레이어를 위치
        if(event.getPointerCount() > 1)
            m_player.setPosition(((int)event.getX(0) - 100 + (int)event.getX(1) - 100)/2,
                    ((int)event.getY(0) - 100 + (int)event.getY(1) - 100)/2);
        else
            m_player.setPosition((int)event.getX()-100, (int)event.getY()-100);
        return false;
    }
}
