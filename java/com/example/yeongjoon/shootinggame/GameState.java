package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.GameView;
import com.example.yeongjoon.gameframework.IState;
import com.example.yeongjoon.gameframework.IntroState;
import com.example.yeongjoon.gameframework.R;

import java.util.ArrayList;
import java.util.Random;

public class GameState implements IState {
    // 멤버 변수 추가할 곳
    private Player m_player;
    private BackGround m_background;

    // 적 리스트
    ArrayList<Enemy> m_enemlist = new ArrayList<Enemy>();
    // 유저 미사일
    ArrayList<Missile> m_pmslist =  new ArrayList<Missile>();
    // 적 미사일
    ArrayList<Missile> m_enemmslist = new ArrayList<Missile>();

    long LastRegenEnemy = System.currentTimeMillis();
    long LastRegenMissile = System.currentTimeMillis();

    Random randomEnemy = new Random();
    public int m_score = 0;
    Bitmap m_CharBit;

    boolean boss_state = false;

    public int screen_width;
    public int screen_height;

    public GameState(int width, int height) {
        screen_width = width;
        screen_height = height;

        AppManager.getInstance().m_gameState = this;
    }

    @Override
    public void Init() {
        m_CharBit = AppManager.getInstance().getBitmap(R.drawable.suckjang2);
        m_CharBit = Bitmap.createScaledBitmap(m_CharBit, 1200, 300, true);
        m_player = new Player(m_CharBit);
        m_background = new BackGround();
    }

    public void MakeEnemy() {
        int enemRegen = (randomEnemy.nextInt(5)+1)*100+500;

        if(System.currentTimeMillis() - LastRegenEnemy >= enemRegen) {
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
            m_enemlist.add(enemy);
        }
    }

    public void CheckCollision() {
        for (int i = 0; i < m_pmslist.size(); i++) {
            for (int j = 0; j < m_enemlist.size(); j++) {
                Missile pms = m_pmslist.get(i);
                Enemy enemy = m_enemlist.get(j);

                // 플레이어와 적의 충돌
                if (CollisionManager.CheckBoxToBox(m_player.m_BoundBox, m_enemlist.get(j).m_BoundBox)) {
                    m_enemlist.remove(j);
                    m_player.destroyPlayer();
                    if (m_player.getLife() < 2.0) {
                        AppManager.getInstance().getGameView().ChangeGameState(new IntroState());
                        return;
                    }
                }
                // 적과 유저 미사일과 충돌
                if (CollisionManager.CheckBoxToBox(pms.m_BoundBox, enemy.m_BoundBox)) {
                    m_pmslist.remove(i);
                    m_enemlist.remove(j);
                    m_score += 10;
                    return;
                }
            }
        }

        // 적 미사일과 유저 충돌
        for (int i = 0; i < m_enemmslist.size(); i++) {
            if (CollisionManager.CheckBoxToBox(m_player.m_BoundBox, m_enemmslist.get(i).m_BoundBox)) {
                m_enemmslist.remove(i);
                m_player.destroyPlayer();
                if (m_player.getLife() < 2.0) {
                    AppManager.getInstance().getGameView().ChangeGameState(new IntroState());
                    return;
                }
            }
        }
    }

    @Override
    public void Destroy() {
        // 보스전으로 전환 시
        if(boss_state) {
            AppManager.getInstance().getGameView().m_AppearBossState.Init(m_player, m_background);
        }
    }

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();

        m_player.Update(GameTime);
        m_background.Update(GameTime);

        for(int i=0; i<m_pmslist.size(); i++) {
            Missile pms = m_pmslist.get(i);
            pms.Update();
            if(pms.state == Missile.STATE_OUT)
                m_pmslist.remove(i);
        }
        for(int i=0; i<m_enemlist.size(); i++) {
            Enemy enemy = m_enemlist.get(i);
            enemy.Update(GameTime);
            if(enemy.state == Enemy.STATE_OUT)
                m_enemlist.remove(i);
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

        // 조건을 만족하면 보스 등장 상태로 변경
        if(m_player.getLife() <= 2.5) {
            AppManager.getInstance().getGameView().m_AppearBossState.Init(m_player, m_background);
            AppManager.getInstance().getGameView().ChangeGameState(AppManager.getInstance().getGameView().m_AppearBossState);
            Log.e("GameState", "플레이어, 배경 넘겨줌");
        }
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
        paint.setTextSize(70);
        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(true);
        canvas.drawText("현재 학점 : " + String.valueOf(m_player.getLife()), 400, 80,paint);
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
        int x = m_player.getX();
        int y = m_player.getY();
        int tx = (int) event.getX() - 81;

        if (x > tx)
            x = x - 15;
        else if (x < tx)
            x = x + 15;

        m_player.setPosition(x, y);
        if (System.currentTimeMillis() - LastRegenMissile >= 500) {
            LastRegenMissile = System.currentTimeMillis();
            m_pmslist.add(new Missile_Player(m_player.getX() - 66, m_player.getY() - 66));
        }
        return true;
    }
}
