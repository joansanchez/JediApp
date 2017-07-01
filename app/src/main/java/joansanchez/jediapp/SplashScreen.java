package joansanchez.jediapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    ImageView imgag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imgag = (ImageView) findViewById(R.id.imageView3);

        RotateAnimation rotateAnimation = new RotateAnimation(30,90,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    }
}
