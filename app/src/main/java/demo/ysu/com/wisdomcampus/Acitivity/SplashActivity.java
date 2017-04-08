package demo.ysu.com.wisdomcampus.Acitivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import demo.ysu.com.wisdomcampus.R;
import demo.ysu.com.wisdomcampus.utils.LINK;
import demo.ysu.com.wisdomcampus.utils.SpUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {


    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;

    private static final int[] SPLASH_ARRAY = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,

            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash12,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,
    };

    ImageView mIvSplash;
    TextView mSplashAppName;
    TextView mSplashSlogan;
    TextView mSplashVersionName;
    //TextView mSplashCopyright;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mIvSplash=(ImageView) findViewById(R.id.iv_splash);
        mSplashAppName=(TextView)  findViewById(R.id.splash_app_name);
        mSplashSlogan=(TextView)  findViewById(R.id.splash_slogan);
        mSplashVersionName=(TextView)  findViewById(R.id.splash_version_name);
        //mSplashCopyright=(TextView)  findViewById(R.id.splash_copyright);
        Random r = new Random(SystemClock.elapsedRealtime());
        mIvSplash.setImageResource(SPLASH_ARRAY[r.nextInt(SPLASH_ARRAY.length)]);
        SharedPreferences sp = SpUtil.getSp(this, "config");
        if (LINK.isNetworkAvailable(this)) {
            sp.edit().putBoolean("Intenet", true).apply();
        }
        //进入到loginActivity

        animateImage();
    }

    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIvSplash, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIvSplash, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();


        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
               startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                SplashActivity.this.finish();
            }
        });
    }

    private void finishActivity() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        overridePendingTransition(0, android.R.anim.fade_out);
                        finish();
                    }
                });
    }
}
