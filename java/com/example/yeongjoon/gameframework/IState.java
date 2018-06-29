package com.example.yeongjoon.gameframework;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

public interface IState {
    // 상태가 생성되었을 때
    public void Init();

    // 상태가 소멸될 때
    public void Destroy();

    // 지속적으로 수행할 것들
    public void Update();

    // 그려야 할 것들
    public void Render(Canvas canvas);

    // 키 입력 처리
    public boolean onKeyDown(int keyCode, KeyEvent event);

    // 터치 입력 처리
    public boolean onTouchEvent(MotionEvent event);
}
