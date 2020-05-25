package com.example.try2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.try2.Files.Files;
import com.example.try2.R;

import java.util.ArrayList;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener {

    private static ArrayList<Files> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity_main);

        Button button = (Button)findViewById(R.id.buttonBack2);
        button.setOnClickListener(this);
        Button button2 = (Button)findViewById(R.id.projectButton2);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonBack2:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MainActivity.give(true, files);
                startActivity(intent);
                overridePendingTransition(R.anim.in4, R.anim.out4);
                break;
            case R.id.projectButton2:
                Intent intent2 = new Intent(this, ProjectActivity2.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ProjectActivity2.give(files);
                startActivity(intent2);
                overridePendingTransition(R.anim.in, R.anim.out);
                break;
            default:
                break;
        }
    }

    public static void give(ArrayList al)
    {
        files = al;
    }
}