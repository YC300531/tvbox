package com.nagasoft.player;

import android.content.Context;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import androidx.base.a.b;
import java.io.IOException;
public class VJMediaPlayer {
    public static final String[] LIBS = {"ffplay", "p2pcore", "ffmpeg"};
    private static boolean sLoaded = false;
    private Context mContext;
    private boolean mPlaying;
    private Thread mRenderThread;

    public VJMediaPlayer() {
        initVideoView();
    }

    private native void Close();

    private native void EnableDecodeAudio(boolean z);

    private native void EnableErrorCallback();

    private native double GetProgress();

    private native void InitCodecs();

    private native void Open(String str);

    public native void Play();

    private native void SetSurface(Surface surface);

    private native void Stop();

    private void initVideoView() {
        loadLibs();
        if (sLoaded) {
            EnableErrorCallback();
            InitCodecs();
            Log.d("FFMpeg", "initVideoView");
        }
    }

    private static boolean loadLibs() {
        if (sLoaded) {
            return true;
        }
        int i = 0;
        boolean z = false;
        while (true) {
            String[] strArr = LIBS;
            if (i >= strArr.length) {
                break;
            }
            try {
                System.loadLibrary(strArr[i]);
                Log.d("FFMpeg", "load lib: " + strArr[i]);
            } catch (UnsatisfiedLinkError e) {
                StringBuilder d = b.d("Couldn't load lib: ");
                d.append(LIBS[i]);
                d.append(" - ");
                d.append(e.getMessage());
                Log.d("FFMpeg", d.toString());
                z = true;
            }
            i++;
        }
        if (!z) {
            sLoaded = true;
        }
        return sLoaded;
    }

    public native double GetCurrentTime();

    public native double GetDuration();

    public native boolean IsDecodingAudio();

    public native boolean IsLiveStream();

    public native boolean IsPlaying();

    public native boolean Pause(boolean z);

    public native void Seek(double d);

    public void decodeAudio(boolean z) {
        Log.d("FFMpeg", "decodeAudio");
        EnableDecodeAudio(z);
    }

    public void finalize() {
        Log.d("FFMpeg", "finalize");
        sLoaded = false;
    }

    public boolean pause() {
        Log.d("FFMpeg", "Pause");
        this.mPlaying = false;
        return Pause(true);
    }

    public void release() {
        Log.d("FFMpeg", "release");
        try {
            stop();
        } catch (InterruptedException unused) {
        }
        Close();
    }

    public boolean resume() {
        Log.d("FFMpeg", "resume");
        this.mPlaying = true;
        return Pause(false);
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        try {
            SetSurface(surfaceHolder.getSurface());
        } catch (IOException unused) {
        }
    }

    public void setVideoPath(String str) {
        Log.d("FFMpeg", "setVideoPath:" + str);
        Open(str);
    }

    public void start() {
        this.mPlaying = true;
        Log.d("FFMpeg", "startVideo");
        if (this.mRenderThread == null) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Play();
                    } catch (IOException unused) {
                        mPlaying = false;
                    }
                }
            };
            this.mRenderThread = thread;
            thread.start();
        }
    }

    public void stop() {
        if (this.mPlaying) {
            Log.d("FFMpeg", "stop");
            this.mPlaying = false;
            Stop();
            Thread thread = this.mRenderThread;
            if (thread != null) {
                thread.join();
            }
            this.mRenderThread = null;
        }
    }
}
