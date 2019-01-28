package com.example.administrator.finalexam;


import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;

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
    private TextView author_id;
    private TextView author_name;
    private SurfaceView mSurfaceView;
    private ProgressBar mProcessBar;
    private ImageView process_notice;
    private LottieAnimationView lottieAnimationView;

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
        setContentView(R.layout.activity_video_player);

        //从另一个活动拿来
        etPath =getIntent().getStringExtra("url");
        author_id=(TextView)findViewById(R.id.author_id);
        author_name=(TextView)findViewById(R.id.author_name);
        author_name.setText(getIntent().getStringExtra("name"));
        author_id.setText(getIntent().getStringExtra("id"));

        mSeekbar = (SeekBar) findViewById(R.id.sb_progress);
        tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        tvTotalTime = (TextView) findViewById(R.id.tv_total_time);
        mSurfaceView=(SurfaceView)findViewById(R.id.surfaceview);
        mSeekbar.setOnSeekBarChangeListener(this);
        mSeekbar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mSeekbar.getThumb().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        mProcessBar=(ProgressBar)findViewById(R.id.progressBar);
        process_notice=(ImageView) findViewById(R.id.progress_notice);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        //SurfaceView帮助类对象
        holder = mSurfaceView.getHolder();
        //是采用自己内部的双缓冲区，而是等待别人推送数据
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);



        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            long[] mHints=new long[2];
            @Override
            public void onClick(View v) {
                if(currentState==PLAYING) {
                    System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
                    mHints[mHints.length - 1] = SystemClock.uptimeMillis();

                    if (SystemClock.uptimeMillis()-mHints[0]<=500){
                        currentState = PLAYING;
                        //TODO播放动画
                        lottieAnimationView = findViewById(R.id.doubleclick);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        lottieAnimationView.setAnimation("thumbs_up.json");
                        lottieAnimationView.setRepeatCount(0);
                        lottieAnimationView.playAnimation();
                        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                lottieAnimationView.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                    }
                }

                if(currentState==NORMAL){
                    mProcessBar.setVisibility(View.VISIBLE);
                }
                if(currentState==STOPING){
                    mProcessBar.setVisibility(View.INVISIBLE);
                }
                process_notice.setVisibility(View.INVISIBLE);

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


//        mProcessBar.setVisibility(View.VISIBLE);
//        process_notice.setVisibility(View.INVISIBLE);
//
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
            mProcessBar.setVisibility(View.INVISIBLE);
            //设置数据类型
            mMediapPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置以下播放器显示的位置
            mMediapPlayer.setDisplay(holder);

            mMediapPlayer.setDataSource(path);
            mMediapPlayer.prepare();
            mMediapPlayer.start();

            mMediapPlayer.setOnCompletionListener(this);
            mMediapPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e("MediaPlayer", "what: " + what + " extra: " + extra);
                    return false;
                }
            });
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
        if(mMediapPlayer!=null) {
            currentState=STOPING;
            process_notice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMediapPlayer!=null){
            mMediapPlayer.stop();
            mMediapPlayer.reset();
            try {
                mMediapPlayer.release();
            } catch (Exception e) {
                Log.e("MediaPlayer", e.toString());
            }
            mMediapPlayer = null;
        }
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
            while (!isStopUpdatingProgress && mMediapPlayer != null) {
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