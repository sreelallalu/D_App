package com.dapp.dapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dapp.dapplication.databinding.AdminHomepageBinding;

public class AdminDashBoard extends BaseActivity {

    private AdminHomepageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage);
        binding = DataBindingUtil.setContentView(AdminDashBoard.this, R.layout.admin_homepage);

        binding.addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this,AddAssingnment.class));
            }
        });
        binding.addSyllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this,AddSyllabus.class));

            }
        });
        binding.addTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this,AddTimetable.class));

            }
        });
        binding.addAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this,AddAttendance.class));

            }
        });
        binding.addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this,AddNotes.class));

            }
        });
        binding.addTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this,AddTutorial.class));

            }
        });
        binding.addNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this,AddNotification.class));

            }
        });
        binding.addSessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, AddSessinal.class));

            }
        });

    }
}
