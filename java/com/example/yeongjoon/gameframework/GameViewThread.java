package com.example.yeongjoon.gameframework;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread {
    // 접근을 위한 멤버 변수
    private SurfaceHolder m_surfaceHolder;
    private GameView m_gameView;

    // 스레드 실행 상태 멤버 변수, parameter passing
    private boolean m_run = false;
    public GameViewThread(SurfaceHolder surfaceHolder, GameView gameView) {
        m_surfaceHolder = surfaceHolder;
        m_gameView = gameView;
    }
    public void setRunning(boolean run) {
        m_run = run;
    }
    @SuppressLint("WrongCall")
    public void run() {
        Canvas _canvas;
        while(m_run) {
            _canvas = null;
            try {
                m_gameView.Update();
                // SurfaceHolder를 통해 Surface에 접근해서 가져옴
                // View에 그림 그리는 권한이 canvas, SurfaceHolder만 사용하도록 잠금
                _canvas = m_surfaceHolder.lockCanvas(null);
                // surfaceholder가 작업이 다 끝나면 그리게 된다.(동기화)
                synchronized (m_surfaceHolder) {
                    // 그림을 그림
                    m_gameView.onDraw(_canvas);
                }
            } finally {
                // 제대로 다 그려지지 않더라도 그려진 것을 출력
                // canvas 잠금 해제, Surface를 화면에 표시함
                if(_canvas != null)
                    m_surfaceHolder.unlockCanvasAndPost(_canvas);
            }
        }
    }
}
