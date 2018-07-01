package com.example.yeongjoon.shootinggame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.GraphicObject;
import com.example.yeongjoon.gameframework.IState;
import com.example.yeongjoon.gameframework.R;

import java.util.ArrayList;

public class AppearBossState implements IState {
    private Player m_player;
    private BackGround m_backGround;
    private Enemy_Boss m_boss;
    private GraphicObject m_hp_bar;

    private GraphicObject m_hit;
    private boolean Hit_State = false;
    ArrayList<Missile> m_pmslist =  new ArrayList<Missile>();
    ArrayList<Missile> m_bossmslist = new ArrayList<Missile>();

    // 보스전 전환 상태
    boolean transform_State;
    // 보스 등장하고 있는 상태
    boolean appear_State;

    // 화면 전환 속도
    long TransformRegenScreen = System.currentTimeMillis();
    // 미사일 발사 속도
    long LastRegenMissile = System.currentTimeMillis();


    public AppearBossState() {
        AppManager.getInstance().m_appearBossState = this;
    }

    @Override
    public void Init() {
        transform_State = true;
        appear_State = false;
    }

    public void Init(Player player, BackGround backGround) {
        Init();
        m_player = player;
        m_backGround = backGround;
        Bitmap bitmap = Bitmap.createScaledBitmap(AppManager.getInstance().getBitmap(R.drawable.boss), 1000,800, true );
        m_boss = new Enemy_Boss(bitmap);
        m_boss.setPosition(50, -800);
        bitmap = Bitmap.createScaledBitmap(AppManager.getInstance().getBitmap(R.drawable.hp_bar), 500,50, true );
        m_hp_bar = new GraphicObject(bitmap);
        m_hp_bar.setPosition(300, 830);
        bitmap = Bitmap.createScaledBitmap(AppManager.getInstance().getBitmap(R.drawable.hit), 200, 200, true);
        m_hit = new GraphicObject(bitmap);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();

        m_player.Update(GameTime);
        m_backGround.Update(GameTime);

        // 보스용 화면으로 전환하고 있는 상태면
        if(transform_State) {
            if(System.currentTimeMillis() - TransformRegenScreen >= 500) {
                TransformRegenScreen = System.currentTimeMillis();
                m_backGround.SCROLL_SPEED *= 1.8;

                // 일정 속도가 되면 보스용 배경으로 변경, 전환 완료
                if (m_backGround.SCROLL_SPEED > 512) {
                    m_backGround = new BackGround(2);
                    m_backGround.SCROLL_SPEED = 1.0f;
                    transform_State = false;
                    appear_State = true;
                }
            }
        }
        // 보스가 등장하고 있는 상태
        else if(appear_State) {
            if(System.currentTimeMillis() - TransformRegenScreen >= 20) {
                TransformRegenScreen = System.currentTimeMillis();
                m_boss.setPosition(m_boss.getX(), m_boss.getY() + 8);
            }
            if(m_boss.getY() >= 0)
                appear_State = false;
        }
        // 보스전 진행
        else {
            for(int i=0; i<m_pmslist.size(); i++) {
                Missile pms = m_pmslist.get(i);
                pms.Update();
                if(pms.state == Missile.STATE_OUT)
                    m_pmslist.remove(i);
            }

            for(int i=0; i < m_bossmslist.size(); i++){
                Missile bossms = m_bossmslist.get(i);
                bossms.Update();
                if(bossms.state == Missile.STATE_OUT) {
                    m_bossmslist.remove(i);
                }
            }
            m_boss.Attack();
            CheckCollision();
            ShootMissile();
        }
    }

    public void CheckCollision() {
        Hit_State = false;
        for (int i = 0; i < m_pmslist.size(); i++) {
            Missile pms = m_pmslist.get(i);
            // 플레이어 미사일과 보스 충돌
            if (CollisionManager.CheckBoxToBox(pms.m_BoundBox, m_boss.m_BoundBox)) {
                Hit_State = true;
                m_hit.setPosition(m_pmslist.get(i).getX(), m_pmslist.get(i).getY());
                m_pmslist.remove(i);
                m_boss.hp--;
                if(m_boss.hp == 0) {
                    EndState endState = new EndState();
                    endState.Init(m_backGround, true);
                    AppManager.getInstance().getGameView().ChangeGameState(endState);
                    return;
                }
                Bitmap bitmap = Bitmap.createScaledBitmap(AppManager.getInstance().getBitmap(R.drawable.hp_bar), m_boss.hp*10,50, true );
                m_hp_bar.SetBitmap(bitmap);
                return;
            }
        }
        for (int i = 0; i < m_bossmslist.size(); i++) {
            Missile bossms = m_bossmslist.get(i);
            // 보스미사일과 플레이어 충돌
            if (CollisionManager.CheckBoxToBox(bossms.m_BoundBox, m_player.m_BoundBox)) {
                m_bossmslist.remove(i);
                m_player.destroyPlayer();
                if(m_player.m_Life == 0) {
                    EndState endState = new EndState();
                    endState.Init(m_backGround, false);
                    AppManager.getInstance().getGameView().ChangeGameState(endState);
                    return;
                }
                return;
            }
        }
    }

    public void ShootMissile() {
        if(System.currentTimeMillis() - LastRegenMissile >= 300) {
            LastRegenMissile = System.currentTimeMillis();
            m_pmslist.add(new Missile_Player(m_player.getX() + 10, m_player.getY() - 10));
        }
    }

    @Override
    public void Render(Canvas canvas) {
        m_backGround.Draw(canvas);
        m_player.Draw(canvas);

        if(!transform_State) {
            m_boss.Draw(canvas);
            if(!appear_State) {
                for(Missile pms : m_pmslist)
                    pms.Draw(canvas);
                for(Missile bossms : m_bossmslist)
                    bossms.Draw(canvas);
                m_hp_bar.Draw(canvas);
                if(Hit_State)
                    m_hit.Draw(canvas);
            }
        }

        Paint paint = new Paint();
        paint.setTextSize(70);
        paint.setColor(Color.WHITE);
        paint.setFakeBoldText(true);
        canvas.drawText("현재 학점 : " + String.valueOf(m_player.getLife()), 400, 80,paint);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
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

        return true;
    }
}
