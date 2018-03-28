package com.dapp.dapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dapp.dapplication.Helper.SharedHelper;

public class StudentDashBoard extends BaseActivity {
    private SharedHelper sharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash_board);
        sharedHelper = new SharedHelper(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ID = item.getItemId();

        if (ID == R.id.sign_out) {

            sharedHelper.setStudent("");
            sharedHelper.setRegType("");
            sharedHelper.setLoginCheck(false);

            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
            return true;

        }
        return false;
    }
}
