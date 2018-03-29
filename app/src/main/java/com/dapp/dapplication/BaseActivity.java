package com.dapp.dapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dapp.dapplication.lib.loadingbutton.CircularProgressButton;

public class BaseActivity extends AppCompatActivity {

    private int color;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void SnakBar(String msg) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.snackbar));
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }
    static CallbackSnak  addCallback;
    public interface CallbackSnak{
        void back();
    }

    public void SnakBarCallback(String msg, CallbackSnak callback)
    {
        addCallback=callback;
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.snackbar));
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {

                addCallback.back();

            }
        });
        color = ContextCompat.getColor(this, R.color.green);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_done_white_48dp);

    }


    public void LoadingOn(CircularProgressButton view) {
        view.revertAnimation();
        view.startAnimation();
    }


    public void LoadingSucc(CircularProgressButton view) {
        view.doneLoadingAnimation(
                color,
                bitmap);

    }



    public void LoadingOff(CircularProgressButton view) {
        view.revertAnimation();
        view.setText("Upload");
    }
}
