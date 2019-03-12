package com.jasoncareter.onetick.mView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mModel.GetCurrentLyric;
import com.jasoncareter.onetick.mModel.GetLyricArray;


class setCurrentLyric implements Runnable{
    Handler handler = new Handler();
    public int i=0;
    private TextView textView;
    private MediaPlayer mediaPlayer ;
    private Activity activity;
    String s = "";

    public setCurrentLyric(Activity activity, MediaPlayer mediaPlayer , TextView textView){
        this.mediaPlayer = mediaPlayer;
        this.textView = textView;
        this.activity   = activity;
    }
    @Override
    public void run() {

        GetLyricArray getLyricArray = new GetLyricArray(activity);
        GetCurrentLyric currentLyric = new GetCurrentLyric(getLyricArray);

        while (mediaPlayer.isPlaying()){
            s = currentLyric.CurrentLyric(mediaPlayer.getCurrentPosition());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText( s );
                }
            });
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

public class BlankFragment extends Fragment {

    Context context = getContext();
    private int tapCount = 0 ;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private static final String TAG = BlankFragment.class.getName() ;
    static TextView lyrictext;
    Thread t;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_blank, container, false);

        // init and bind views
        ImageView cover = view.findViewById(R.id.cover);
        TextView helloText = view.findViewById(R.id.helloText);
        lyrictext = view.findViewById(R.id.lyrictext);
        /**
         * set images' animation
         */

        // part of HelloText
        AnimatorSet HelloTextSet = new AnimatorSet() ;
        ObjectAnimator AniHelloTextScaleY = ObjectAnimator.ofFloat(helloText,"scaleY",1f,4f);
        AniHelloTextScaleY.setDuration(4000);
        ObjectAnimator AniHelloTextScaleX = ObjectAnimator.ofFloat(helloText,"scaleX",1f,4f);
        AniHelloTextScaleX.setDuration(4000);
        ObjectAnimator AniHelloTextAlpha = ObjectAnimator.ofFloat(helloText,"alpha",1f,0f);
        AniHelloTextAlpha.setStartDelay(1000);
        AniHelloTextAlpha.setDuration(3000);
        HelloTextSet.setStartDelay(1000);
        HelloTextSet.playTogether(AniHelloTextScaleX,AniHelloTextScaleY , AniHelloTextAlpha );

        //part of Cover
        AnimatorSet CoverSet = new AnimatorSet();
        ObjectAnimator CoverRotate = ObjectAnimator.ofFloat(cover , "rotation" , 0f ,360f);
//            //get the Image cover's pivot x, y
//            float coverPivotX = cover.getRight() - cover.getWidth()/2 ;
//            float coverPivotY = cover.getBottom() - cover.getHeight()/2;
//        cover.setPivotX(coverPivotX);
//        cover.setPivotY(coverPivotY);
        CoverRotate.setRepeatCount(20);
        CoverRotate.setDuration(7200);
        CoverRotate.setInterpolator(null);
        ObjectAnimator CoverAlpha = ObjectAnimator.ofFloat(cover , "alpha",0f,1f);
        CoverAlpha.setInterpolator(null);
        CoverAlpha.setDuration(12000);
        CoverSet.playTogether(CoverRotate, CoverAlpha);


/**
 * FIrst we use Detector to listening the single tap (onDown ) event , every time the screen was touched , it will check
 *  that if it sastify the two conditions( global variable tapCount <= 4 && mediaPlayer is not playing ) , then call the setTapCount()
 *
 *  Second , using another self-defined function to judge if the tapcount equals 4 , once it satisfied the condition , we start playing the music
 */
        GestureDetector helloTextDetector = new GestureDetector(getActivity() ,
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e) {
                        if( tapCount < 2 && !mediaPlayer.isPlaying()) {
                            setTapCount( false );

                        }else if(!mediaPlayer.isPlaying()){
                            startPlaybg();
                            setTapCount(true);


                            HelloTextSet.start();
                            HelloTextSet.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) { }
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    CoverSet.start();
                                }
                                @Override
                                public void onAnimationCancel(Animator animation) { }
                                @Override
                                public void onAnimationRepeat(Animator animation) { }
                            });

                            /*
                             *  读取歌词文件，根据时间点来显示歌词
                             */
                            setCurrentLyric currentLyric = new setCurrentLyric(  getActivity(), mediaPlayer , lyrictext);
                            t = new Thread( currentLyric );
                            t.start();

                        }
                        return true ;
                    }
                });


        helloText.setClickable(true);
        helloText.setFocusable(true);
        helloText.setFocusableInTouchMode(true);
        helloText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return helloTextDetector.onTouchEvent(event);
            }
        });


        return view;
    }

    //judging whether or not meeting the requirement that tapcount equals 4, if so that we'll do something ,here means playing music
    private void startPlaybg(){
        if(tapCount>=2){
                mediaPlayer = MediaPlayer.create(getActivity() ,R.raw.one);
                mediaPlayer.start();
        }
    }
    // adding the tapcount step by 1
    private void setTapCount(boolean needToReset){
        if( !needToReset){
            tapCount ++ ;
            Log.d(TAG, "setTapCount: "+"single tap was activated" +tapCount);
        }else
            tapCount = 0 ;
    }



    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
