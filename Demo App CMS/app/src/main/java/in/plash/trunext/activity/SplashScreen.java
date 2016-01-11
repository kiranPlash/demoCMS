package in.plash.trunext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.customfonts.Typewriter;


public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 100;
    String android_id;
    TextView textView;
    Typewriter tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_splash_screen);
        textView = (TextView) findViewById(R.id.txt_forbes);
        tv = (Typewriter) findViewById(R.id.txt_forbes1);

        Animation move = AnimationUtils.loadAnimation(this, R.anim.top_down);
        textView.startAnimation(move);

       /* YoYo.with(Techniques.DropOut)
                .duration(700)
                .playOn(findViewById(R.id.txt_forbes));*/

        move.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String text = "Welcome to the all New </font> <font color=#1572d2>Forbes India</font>";
                tv.setCharacterDelay(75);
                // tv.animateText((getResources().getString(R.string.forbes_login_text)));
                tv.animateText(Html.fromHtml(text));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Constants.ANDROID_ID = android_id;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                gotoLoginActivty();

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


    private void gotoLoginActivty() {
       // Intent i = new Intent(SplashScreen.this, SplashScreen2.class);
          Intent i = new Intent(SplashScreen.this, LoginInsideActivity.class);
        startActivity(i);
    }


}


