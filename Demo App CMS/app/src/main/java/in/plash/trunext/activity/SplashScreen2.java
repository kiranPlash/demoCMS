package in.plash.trunext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import in.plash.trunext.R;

public class SplashScreen2 extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);

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
        Intent i = new Intent(SplashScreen2.this, LoginForbesActivity.class);
        //  Intent i = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(i);
    }

}
