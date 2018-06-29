package com.example.yeongjoon.gameframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.example.yeongjoon.shootinggame.AppearBossState;
import com.example.yeongjoon.shootinggame.GameState;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private IState m_state;
    private GameViewThread m_thread;
    public AppearBossState m_AppearBossState;

    public GameView(Context context) {
        super(context);
        Display display = ((WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE)).getDefaultDisplay();

        // 키 입력 처리를 받기 위해서
        setFocusable(true);

        AppManager.getInstance().setGameView(this);
        AppManager.getInstance().setResources(getResources());

        // 추후 SurfaceHolder에서 Surface에 처리 완료된 것을 알려주기 위해서 콜백 사용
        getHolder().addCallback(this);
        m_thread = new GameViewThread(getHolder(), this);

        m_AppearBossState = new AppearBossState();
        ChangeGameState(new GameState(display.getWidth(), display.getHeight()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        m_state.Render(canvas);
    }

    public void Update() {
        m_state.Update();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 스레드를 실행 상태로 만든다.
        m_thread.setRunning(true);
        // 스레드 실행
        m_thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        m_thread.setRunning(false);
        // 스레드가 한 번에 종료되지 않을 경우를 위해 반복해서 시도
        while(retry) {
            try {
                // 스레드를 중지시킨다.
                m_thread.join();
                retry = false;
            }catch (InterruptedException e) {
                // 스레드가 종료되도록 계속 시도
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        m_state.onKeyDown(keyCode, event);
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        m_state.onTouchEvent(event);
        return true;
    }

    // 받아온 상태로 변경
    public void ChangeGameState(IState _state) {
        if(m_state != null)
            m_state.Destroy();
        _state.Init();
        m_state = _state;
    }
}
