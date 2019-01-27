package com.example.administrator.finalexam;


import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * @author bytedance
 */

public class videoPlayer extends AppCompatActivity implements OnSeekBarChangeListener, OnCompletionListener {

    private boolean isStopUpdatingProgress = false;
    private String etPath;
    private MediaPlayer mMediapPlayer;
    private SeekBar mSeekbar;
    private TextView tvCurrentTime;
    private TextView tvTotalTime;
    private SurfaceView mSurfaceView;

    /**
     * 闲置
     */
    private final int NORMAL = 0;
    /**
     * 播放中
     */
    private final int PLAYING = 1;
    /**
     * 暂停
     */
    private final int PAUSING = 2;
    /**
     * 停止中
     */
    private final int STOPING = 3;

    /**
     * 播放器当前的状态，默认是空闲状态
     */
    private int currentState = NORMAL;

    /**
     * 用行动打消忧虑
     */
    private SurfaceHolder holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_video_player);
        Transition explode = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(explode);
        }

        //从另一个活动拿来
        etPath =getIntent().getStringExtra("url");
        mSeekbar = (SeekBar) findViewById(R.id.sb_progress);
        tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        tvTotalTime = (TextView) findViewById(R.id.tv_total_time);
        mSurfaceView=(SurfaceView)findViewById(R.id.surfaceview);
        mSeekbar.setOnSeekBarChangeListener(this);

        SurfaceView mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        //SurfaceView帮助类对象
        holder = mSurfaceView.getHolder();
        //是采用自己内部的双缓冲区，而是等待别人推送数据
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediapPlayer != null) {
                    if (currentState != PAUSING) {
                        mMediapPlayer.start();
                        currentState = PLAYING;
                        //每次在调用刷新线程时，都要设为false
                        isStopUpdatingProgress = false;
                        return;
                        //下面这个判断完美的解决了停止后重新播放的，释放两个资源的问题
                    } else if (currentState == STOPING) {
                        mMediapPlayer.reset();
                        mMediapPlayer.release();
                    }
                }
                play();
            }
        });
    }

    /**
     * 开始
     *
     * @param v
     */
//    public void video_start_or_pause(View v) {
//        if (mMediapPlayer != null) {
//            if (currentState != PAUSING) {
//                mMediapPlayer.start();
//                currentState = PLAYING;
//                //每次在调用刷新线程时，都要设为false
//                isStopUpdatingProgress = false;
//                return;
//                //下面这个判断完美的解决了停止后重新播放的，释放两个资源的问题
//            } else if (currentState == STOPING) {
//                mMediapPlayer.reset();
//                mMediapPlayer.release();
//            }
//        }
//        play();
//    }

    /**
     * 停止
     *
     * @param v
     */
    public void stop(View v) {
        if (mMediapPlayer != null) {
            mMediapPlayer.stop();
        }
    }

    /**
     * 播放输入框的文件
     */
    private void play() {
        String path = etPath;
        mMediapPlayer = new MediaPlayer();
        try {
            //设置数据类型
            mMediapPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置以下播放器显示的位置
            mMediapPlayer.setDisplay(holder);

            mMediapPlayer.setDataSource(path);
            mMediapPlayer.prepare();
            mMediapPlayer.start();

            mMediapPlayer.setOnCompletionListener(this);
            //把当前播放器的状诚置为：播放中
            currentState = PLAYING;

            //把音乐文件的总长度取出来，设置给seekbar作为最大值
            //总时长
            int duration = mMediapPlayer.getDuration();
            mSeekbar.setMax(duration);
            //把总时间显示textView上
            int m = duration / 1000 / 60;
            int s = duration / 1000 % 60;
            tvTotalTime.setText("/" + m + ":" + s);
            tvCurrentTime.setText("00:00");

            isStopUpdatingProgress = false;
            new Thread(new UpdateProgressRunnable()).start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停
     *
     * @param v
     */
    public void pause(View v) {
        if (mMediapPlayer != null && currentState == PLAYING) {
            mMediapPlayer.pause();
            currentState = PAUSING;
            //停止刷新主线程
            isStopUpdatingProgress = true;
        }
    }

    /**
     * 重播
     *
     * @param v
     */
    public void video_restart(View v) {
        if (mMediapPlayer != null) {
            mMediapPlayer.reset();
            mMediapPlayer.release();
            play();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //当开始拖动时，那么就开始停止刷新线程
        isStopUpdatingProgress = true;
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        //播放器切换到指定的进度位置上
        mMediapPlayer.seekTo(progress);
        isStopUpdatingProgress = false;
        new Thread(new UpdateProgressRunnable()).start();
    }

    /**
     * 当播放完成时回调此方法
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(this, "播放完了，重新再播放", LENGTH_SHORT).show();
        mp.start();

    }

    /**
     * 刷新进度和时间的任务
     *
     * @author hjl
     */
    class UpdateProgressRunnable implements Runnable {

        @Override
        public void run() {
            //每隔1秒钟取一下当前正在播放的进度，设置给seekbar
            while (!isStopUpdatingProgress) {
                //得到当前进度
                int currentPosition = mMediapPlayer.getCurrentPosition();
                mSeekbar.setProgress(currentPosition);
                final int m = currentPosition / 1000 / 60;
                final int s = currentPosition / 1000 % 60;

                //此方法给定的runable对象，会执行主线程（UI线程中）
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        tvCurrentTime.setText(m + ":" + s);

                    }

                });
                SystemClock.sleep(1000);
            }

        }

    }

}