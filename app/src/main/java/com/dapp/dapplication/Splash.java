package com.dapp.dapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dapp.dapplication.Helper.SharedHelper;

public class Splash extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        TextView textView = (TextView) findViewById(R.id.slashtxt);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Lobster.otf");
        textView.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedHelper sharedHelper = new SharedHelper(Splash.this);
                if (sharedHelper.getLoginCheck()) {
                    if (sharedHelper.getRegType().equalsIgnoreCase("admin")) {
                        startActivity(new Intent(Splash.this, AdminDashBoard.class));

                    } else if (sharedHelper.getRegType().equalsIgnoreCase("student")) {
                        startActivity(new Intent(Splash.this, StudentDashBoard.class));
                    }


                } else {
                    startActivity(new Intent(Splash.this, LoginActivity.class));

                }
                finish();
            }
        }, 2000);


    }
}
