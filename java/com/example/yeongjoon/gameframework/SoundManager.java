package com.example.yeongjoon.gameframework;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import static android.media.AudioManager.STREAM_MUSIC;

// Singleton pattern 적용
public class SoundManager {
    private static SoundManager s_instance;

    // 멤버 변수
    private SoundPool m_SoundPool;
    private HashMap m_SoundPoolMap;
    private AudioManager m_AudioManager;
    private Context m_Activity;

    public static SoundManager getInstance() {
        if(s_instance == null)
            s_instance = new SoundManager();
        return s_instance;
    }

    public void init(Context _context) {
        // 멤버 변수 생성과 초기화
        m_SoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        m_SoundPoolMap = new HashMap();
        m_AudioManager = (AudioManager)_context.getSystemService(Context.AUDIO_SERVICE);
        m_Activity = _context;
    }

    public void addSound(int _index, int _soundID) {
        // 사운드를 로드
        int id = m_SoundPool.load(m_Activity, _soundID, 1);
        // 해시맵에 아이디 값을 받아온 인덱스 저장
        m_SoundPoolMap.put(_index, id);
    }

    public void play(int _index) {
        // 사운드 재생
        float streamVolume = m_AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume / m_AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        m_SoundPool.play((Integer)m_SoundPoolMap.get(_index), streamVolume, streamVolume, 1, 0, 1f);
    }

    public void playLooped(int _index) {
        // 사운드 반복 재생
        float streamVolume = m_AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume / m_AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        m_SoundPool.play((Integer)m_SoundPoolMap.get(_index), streamVolume, streamVolume, 1, -1, 1f);
    }
}
