package com.dapp.dapplication.admin_module;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dapp.dapplication.BaseActivity;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.LoginActivity;
import com.dapp.dapplication.R;
import com.dapp.dapplication.databinding.AdminHomepageBinding;

public class AdminDashBoard extends BaseActivity {

    private AdminHomepageBinding binding;
    private SharedHelper sharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage);
        sharedHelper = new SharedHelper(this);
        binding = DataBindingUtil.setContentView(AdminDashBoard.this, R.layout.admin_homepage);

        binding.addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, AddAssingnment.class));
            }
        });
        binding.addSyllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, AddSyllabus.class));

            }
        });
        binding.addTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, AddTimetable.class));

            }
        });
        binding.addAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, AddAttendance.class));

            }
        });
        binding.addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, AddNotes.class));

            }
        });
        binding.addTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, AddTutorial.class));

            }
        });
        binding.addNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, AddNotification.class));

            }
        });
        binding.addSessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, AddSessinal.class));

            }
        });

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

            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
            return true;

        }
        if (ID == R.id.feedbackview) {

            startActivity(new Intent(this,FeedbackView.class));

            return true;
        }
        return false;
    }
}
