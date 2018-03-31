package com.dapp.dapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.databinding.StudentDashBoardBinding;
import com.dapp.dapplication.student.FeedBackActivity;
import com.dapp.dapplication.student.ProfileUpdation;
import com.dapp.dapplication.student.ViewAssignmnet;
import com.dapp.dapplication.student.ViewAttandance;
import com.dapp.dapplication.student.ViewNotes;
import com.dapp.dapplication.student.ViewNotification;
import com.dapp.dapplication.student.ViewSessional;
import com.dapp.dapplication.student.ViewSyllabus;
import com.dapp.dapplication.student.ViewTimetable;
import com.dapp.dapplication.student.ViewTutorial;

public class StudentDashBoard extends BaseActivity {
    private SharedHelper sharedHelper;
    private StudentDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.student_dash_board);
        sharedHelper = new SharedHelper(this);

        binding.addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this, ViewAssignmnet.class));
            }
        });
        binding.addSyllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this, ViewSyllabus.class));

            }
        });

        binding.addTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this, ViewTimetable.class));

            }
        });
        binding.addAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this, ViewAttandance.class));

            }
        });
        binding.addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this, ViewNotes.class));

            }
        });
        binding.addTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this, ViewTutorial.class));

            }
        });
        binding.addNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this, ViewNotification.class));

            }
        });
        binding.addSessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this, ViewSessional.class));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainstud, menu);
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


        }
        if(ID==R.id.feedbackview)
        {
            startActivity(new Intent(this,FeedBackActivity.class));
            return true;
        }if(ID==R.id.profile)
        {
            startActivity(new Intent(this, ProfileUpdation.class));
            return true;
        }
        return false;
    }
}
