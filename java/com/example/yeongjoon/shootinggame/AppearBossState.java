package com.example.yeongjoon.shootinggame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.yeongjoon.gameframework.AppManager;
import com.example.yeongjoon.gameframework.IState;

public class AppearBossState implements IState {
    private Player m_player;
    private BackGround m_backGround;

    // 보스전 전환 상태
    boolean transform_State;
    // 보스 등장 라이팅 이펙트 상태
    boolean lighting_State;
    // 보스 등장하고 있는 상태
    boolean appear_State;

    // 화면 전환 속도
    long TransformRegenScreen = System.currentTimeMillis();

    public AppearBossState() {
        AppManager.getInstance().m_appearBossState = this;
    }

    @Override
    public void Init() {
        transform_State = true;
        lighting_State = false;
        appear_State = false;
    }

    public void Init(Player player, BackGround backGround) {
        Init();
        m_player = player;
        m_backGround = backGround;
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
                }
            }
        }
    }

    @Override
    public void Render(Canvas canvas) {
        m_backGround.Draw(canvas);
        m_player.Draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(70);
        paint.setColor(Color.BLACK);
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
