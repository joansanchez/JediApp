package joansanchez.jediapp;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    ImageView imgag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imgag = (ImageView) findViewById(R.id.imageView3);

        RotateAnimation rotateAnimation = new RotateAnimation(0,359,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        imgag.startAnimation(rotateAnimation);
        final Intent i = new Intent(this, LoginActivity.class);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                startActivity(i);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 2000);


    }
}
