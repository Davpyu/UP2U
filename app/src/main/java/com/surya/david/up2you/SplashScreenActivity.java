package com.surya.david.up2you;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Thread.sleep;

public class SplashScreenActivity extends Activity {

    @BindView(R.id.logo)
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        boolean checkConnection = new ApplicationUtility().checkConnection(getApplicationContext());
        if (checkConnection) {
            Animation myanim = AnimationUtils.loadAnimation(this, R.anim.splashscreen);
            final Intent intent = new Intent(this, HomeActivity.class);
            logo.startAnimation(myanim);
            java.lang.Thread timer = new java.lang.Thread() {
                public void run() {
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }else{
            Intent in = new Intent(getApplicationContext(), NoConnectionActivity.class);
            startActivity(in);
        }
    }
}
